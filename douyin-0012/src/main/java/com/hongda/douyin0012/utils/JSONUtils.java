package com.hongda.douyin0012.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode parse(String json) throws Exception {
        return objectMapper.readTree(json);
    }
}
