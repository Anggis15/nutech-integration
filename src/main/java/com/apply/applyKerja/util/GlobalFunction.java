package com.apply.applyKerja.util;

import com.apply.applyKerja.handler.ResponseHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public class GlobalFunction {

    public static ResponseEntity<Object> generateResponse(int status,
                                                             HttpStatus httpStatus,
                                                             String message,
                                                             Object responseObj,
                                                             HttpServletRequest request){
        return new ResponseHandler().generateResponse(
                status,
                httpStatus,
                message,
                responseObj,
                request
        );
    }

    /**
     * Mengconvert Object java ke JSON
     */
    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}