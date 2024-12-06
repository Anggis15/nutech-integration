package com.apply.applyKerja.dto.response;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 06/12/24 00.39
@Last Modified 06/12/24 00.39
Version 1.0
*/


public class ErorrResponse {
    private String message;

    public int getStatus() {
        return status;
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

    private int status;

    public ErorrResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }


}
