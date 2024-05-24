package uz.jurayev.dynamic_document_excel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ObjectMapperUtil {

    public static String objectToJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonToObject(String json, Class<T> t) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonToObject(String json, TypeReference<T> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonToObject(File file, TypeReference<T> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonToObject(InputStream inputStream, TypeReference<T> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
