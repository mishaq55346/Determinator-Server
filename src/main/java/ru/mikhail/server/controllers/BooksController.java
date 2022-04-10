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
import ru.mikhail.server.model.BookDTO;
import ru.mikhail.server.model.Role;
import ru.mikhail.server.service.BookService;
import ru.mikhail.server.util.JsonWrapper;
import ru.mikhail.server.util.RequestParser;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class BooksController {
    protected final static Log logger = LogFactory.getLog(BooksController.class);

    private final AuthenticationProvider authenticationProvider;
    private final BookService bookService;

    public BooksController(AuthenticationProvider authenticationProvider, BookService bookService) {
        this.authenticationProvider = authenticationProvider;
        this.bookService = bookService;
    }

    @RequestMapping(path = "/book/get", method = POST,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBook(@RequestBody String jsonInput) {
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
        long id;
        try {
            id = requestParser.getId();
        } catch (Exception e) {
            logger.error("Error parsing id from request");
            return getErrorResponseEntity(BAD_REQUEST, "No book_id in request");
        }

        Book requestedBook = bookService.getBook(id);
        if (requestedBook == null) {
            return ResponseEntity.badRequest()
                    .body(JsonWrapper.wrapErrorResponse("There is no books with such id"));
        }
        return ResponseEntity.ok()
                .body(JsonWrapper.wrapBook((Book) requestedBook));
    }

    @RequestMapping(path = "/book/list", method = POST,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBookList(@RequestBody String jsonInput) {
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
        String role = response.getBody();

        List<BookDTO> foundBooks = bookService.getAll(new Role(role));
        if (foundBooks == null || foundBooks.isEmpty()) {
            return getErrorResponseEntity(BAD_REQUEST, "No books found");
        }
        return ResponseEntity.ok()
                .body(JsonWrapper.wrapList(foundBooks));
    }

    @RequestMapping(path = "/book/search", method = POST,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findBook(@RequestBody String jsonInput) {
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
        String role = response.getBody();
        String searchString;
        try {
            searchString = requestParser.getSearchString();
        } catch (Exception e) {
            logger.error("Error parsing search string from request");
            return getErrorResponseEntity(BAD_REQUEST, "No search_string in request");
        }
        List<BookDTO> foundBooks = bookService.findBooks(searchString, new Role(role));
        if (foundBooks == null || foundBooks.isEmpty()) {
            return getErrorResponseEntity(BAD_REQUEST, "No books found");
        }
        return ResponseEntity.ok().body(JsonWrapper.wrapList(foundBooks));
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
