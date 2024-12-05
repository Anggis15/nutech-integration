package com.apply.applyKerja.dto.response;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 04/12/24 12.14
@Last Modified 04/12/24 12.14
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseBanner {
    @JsonProperty("banner_name")
    private String bannerName;
    @JsonProperty("banner_image")
    private String bannerImage;
    private String description;

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
