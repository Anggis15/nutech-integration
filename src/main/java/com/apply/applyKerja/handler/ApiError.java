package com.apply.applyKerja.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {

    private int status;
    private String message;
    @JsonProperty("sub_errors")
    private List<ApiValidationError> subErrors;



    ApiError(HttpStatus serverResponse, Throwable ex) {
        this.message = HttpStatus.INTERNAL_SERVER_ERROR.name();
    }

    ApiError(String message,
             Throwable ex, int status) {
        this.message = message;
        this.status=status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ApiValidationError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<ApiValidationError> subErrors) {
        this.subErrors = subErrors;
    }

    public int getStatus() {
        return status;
    }
}
