package com.apply.applyKerja.service;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 04/12/24 12.01
@Last Modified 04/12/24 12.01
Version 1.0
*/

import com.apply.applyKerja.dto.response.ResponseBanner;
import com.apply.applyKerja.model.Banner;
import com.apply.applyKerja.repository.BannerRepository;
import com.apply.applyKerja.util.GlobalFunction;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public ResponseEntity<Object> getAllBanner(HttpServletRequest request){
        List<Banner> banners = new ArrayList<>();
        List<ResponseBanner> resBanners = new ArrayList<>();
        try {
            banners = bannerRepository.findAllBanner();
        } catch (Exception e) {
            return GlobalFunction.generateResponse(108, HttpStatus.NOT_FOUND, "Banner tidak bisa di temukan", null, request);
        }
        for(Banner banner: banners){
            ResponseBanner responseBanner = new ResponseBanner();
            responseBanner.setBannerName(banner.getBannerName());
            responseBanner.setBannerImage(banner.getBannerImage());
            responseBanner.setDescription(banner.getDescription());
            resBanners.add(responseBanner);
        }
        return GlobalFunction.generateResponse(0,HttpStatus.OK,"Sukses",resBanners,request);
    }
}
