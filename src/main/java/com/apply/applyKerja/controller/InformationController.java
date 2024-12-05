package com.apply.applyKerja.controller;

import com.apply.applyKerja.repository.BannerRepository;
import com.apply.applyKerja.repository.ServiceRepository;
import com.apply.applyKerja.service.BannerService;
import com.apply.applyKerja.service.MstServicesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 04/12/24 12.21
@Last Modified 04/12/24 12.21
Version 1.0
*/
@RestController
public class InformationController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private MstServicesService mstServicesService;

    @GetMapping("/banner")
    public ResponseEntity<Object> getAllBanner(HttpServletRequest request){
        return bannerService.getAllBanner(request);
    }
    @GetMapping("/service")
    public ResponseEntity<Object> getAllServices(HttpServletRequest request){
        return mstServicesService.getAllServices(request);
    }
}
