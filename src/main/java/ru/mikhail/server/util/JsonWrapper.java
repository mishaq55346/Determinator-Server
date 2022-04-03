package ru.mikhail.server.util;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public class JsonWrapper {
    @SneakyThrows
    public static String wrapList(List<?> list) {
        return new JsonMapper().writeValueAsString(list);
    }

    @SneakyThrows
    public static String wrapObject(Object object) {
        return new JsonMapper().writeValueAsString(object);
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
}