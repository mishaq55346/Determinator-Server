package ru.mikhail.server.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mikhail.server.authentication.AuthenticationProvider;
import ru.mikhail.server.model.Book;
import ru.mikhail.server.model.Role;
import ru.mikhail.server.model.UserDTO;
import ru.mikhail.server.service.BookService;
import ru.mikhail.server.service.UserService;
import ru.mikhail.server.util.JsonWrapper;
import ru.mikhail.server.util.RequestParser;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class UserController {
    protected final static Log logger = LogFactory.getLog(BooksController.class);

    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;

    public UserController(AuthenticationProvider authenticationProvider, BookService bookService, UserService userService) {
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
    }

    @RequestMapping(path = "/user/info", method = POST,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserInfo(@RequestBody String jsonInput) {
        RequestParser requestParser;
        try {
            requestParser = new RequestParser(jsonInput);
        } catch (JsonProcessingException e) {
            logger.error("Error occurred while parsing request: " + e.getMessage());
            return getErrorResponseEntity(INTERNAL_SERVER_ERROR, "Bad JSON syntax");
        }
        ResponseEntity<String> response = processInput(requestParser);
        if (response.getStatusCode().isError()) {
            return response;
        }
        String username;
        try {
            username = requestParser.getCredentials().getLeft();
        } catch (Exception e) {
            logger.error("Error parsing username from request");
            return getErrorResponseEntity(BAD_REQUEST, "No username in request");
        }
        UserDTO userInfo = userService.getUserInfo(username);
        if (userInfo == null) {
            return ResponseEntity.badRequest()
                    .body(JsonWrapper.wrapErrorResponse("There is no such user"));
        }
        return ResponseEntity.ok()
                .body(JsonWrapper.wrapUser(userInfo));
    }

    private ResponseEntity<String> processInput(RequestParser requestParser) {
        ImmutablePair<HttpStatus, String> error = requestParser.checkRequiredParameters();
        if (error != null) {
            logger.error(error.getRight());
            return getErrorResponseEntity(error.getLeft(), error.getRight());
        }
        ImmutablePair<String, String> credentials = requestParser.getCredentials();
        if (!authenticationProvider.authenticate(credentials.getLeft(), credentials.getRight())) {
            return getErrorResponseEntity(FORBIDDEN, "Bad credentials");
        }
        Role role = authenticationProvider.getRole(credentials.getLeft());
        return ResponseEntity.ok().body(role.getUniversityCode());
    }


    private ResponseEntity<String> getErrorResponseEntity(HttpStatus statusCode, String error) {
        return ResponseEntity.status(statusCode.value()).body(error);
    }
}

