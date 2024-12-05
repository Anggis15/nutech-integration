package com.apply.applyKerja.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public ResponseEntity<Object> generateResponse(int status,
                                                   HttpStatus httpStatus,
                                                   String message,
                                                   Object responseObj,
                                                   HttpServletRequest request) {
        ZonedDateTime jakartaNow = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
        LocalDateTime date = jakartaNow.toLocalDateTime();
        TemplateResponse response = new TemplateResponse();
        response.setStatus(status);
        response.setMessage(message);
        response.setData(responseObj);
        return new ResponseEntity<Object>(response,httpStatus);
    }

    public Map<String,Object> generateResponseRaw(String message,
                                                   HttpStatus status,
                                                   Object responseObj,
                                                   Object errorCode,
                                                   HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj==null?"":responseObj);
        map.put("timestamp", new Date());
        map.put("success",!status.isError());
        if(errorCode != null)
        {
            map.put("errorCode",errorCode);
            map.put("path",request.getPathInfo());
        }
        map.put("httpStatus",status);
        return map;
    }
}