package ru.mikhail.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class RequestParser {
    private transient final Logger logger = Logger.getLogger(this.getClass());

    private final JsonNode root;

    public RequestParser(String jsonString) throws JsonProcessingException {
        root = parse(jsonString);
    }

    private JsonNode parse(String jsonString) throws JsonProcessingException {
        return new ObjectMapper().readTree(jsonString);
    }

    public ImmutablePair<HttpStatus, String> checkRequiredParameters() {
        if (!root.has("authentication")) {
            logger.error("JSON request has no authentication field");
            return new ImmutablePair<>(BAD_REQUEST, "JSON request has no authentication field");
        }

        JsonNode authNode = root.get("authentication");
        if (!authNode.has("username") || !authNode.has("password")) {
            logger.error("JSON request has no username or password fields");
            return new ImmutablePair<>(BAD_REQUEST, "JSON request has no username or password fields");
        }
        return null;
    }

    public ImmutablePair<String, String> getCredentials() {
        JsonNode authNode = root.get("authentication");
        if (authNode.has("username") && authNode.has("password")) {
            return new ImmutablePair<>(authNode.get("username").asText(), authNode.get("password").asText());
        }
        return null;
    }

    public String getSearchString() {
        return root.get("search_string").asText();
    }

    public long getId() {
        return root.get("book_id").asLong();
    }
}