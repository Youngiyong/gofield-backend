package com.gofield.admin.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;

import java.io.IOException;
        ;

public class AdminUtil {

    public static String toJsonStr(Object from) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.writeValueAsString(from);
        }catch (IOException e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }
    }

    public static <T> T toObject(Object from, Class<T> target) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(toJsonStr(from), target);
        }catch (IOException e){
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }
    }

    public static <T> T strToObject(String from, TypeReference<T> target) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(from, target);
        }catch (IOException e){
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }
    }

}
