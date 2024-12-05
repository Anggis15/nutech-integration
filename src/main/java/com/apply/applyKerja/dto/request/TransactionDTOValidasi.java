package com.apply.applyKerja.dto.request;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 04/12/24 15.00
@Last Modified 04/12/24 15.00
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionDTOValidasi {
    @JsonProperty("service_code")
    private String serviceCode;

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
