package ru.mikhail.server.util;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.mikhail.server.model.Book;
import ru.mikhail.server.model.BookDTO;
import ru.mikhail.server.model.UserDTO;

import java.util.List;
import java.util.Map;

public class JsonWrapper {
    private static transient final Logger logger = Logger.getLogger(JsonWrapper.class);
    @SneakyThrows
    public static String wrapList(List<BookDTO> list) {
        JsonMapper mapper = new JsonMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return String.format("{\"books\": %s}", mapper.writeValueAsString(list));
    }

    @SneakyThrows
    public static String wrapObject(Object object) {
        return new JsonMapper().writeValueAsString(object);
    }

    @SneakyThrows
    public static String wrapBook(Book book) {
        JsonMapper mapper = new JsonMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.writeValueAsString(book);
    }

    @SneakyThrows
    public static String wrapMap(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(map);
        return jsonObject.toJSONString();
    }

    @SneakyThrows
    public static String wrapErrorResponse(String error) {
        return new JsonMapper().writeValueAsString(error);
    }

    @SneakyThrows
    public static String wrapUser(UserDTO userInfo) {
        JsonMapper mapper = new JsonMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.writeValueAsString(userInfo);
    }
}
