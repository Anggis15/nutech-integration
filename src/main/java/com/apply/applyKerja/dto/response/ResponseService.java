package com.apply.applyKerja.dto.response;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 04/12/24 12.17
@Last Modified 04/12/24 12.17
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseService {
    @JsonProperty("service_code")
    private String serviceCode;
    @JsonProperty("service_name")
    private String serviceName;
    @JsonProperty("service_icon")
    private String serviceIcon;
    @JsonProperty("service_tariff")
    private Long serviceTariff;

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(String serviceIcon) {
        this.serviceIcon = serviceIcon;
    }

    public Long getServiceTariff() {
        return serviceTariff;
    }

    public void setServiceTariff(Long serviceTariff) {
        this.serviceTariff = serviceTariff;
    }
}
