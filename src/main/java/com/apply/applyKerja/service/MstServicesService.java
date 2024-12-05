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

import com.apply.applyKerja.dto.response.ResponseService;
import com.apply.applyKerja.model.Banner;
import com.apply.applyKerja.model.MstService;
import com.apply.applyKerja.repository.ServiceRepository;
import com.apply.applyKerja.util.GlobalFunction;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MstServicesService {

    @Autowired
    ServiceRepository serviceRepository;

    public ResponseEntity<Object> getAllServices(HttpServletRequest request){
        List<MstService> services = new ArrayList<>();
        List<ResponseService> resServices = new ArrayList<>();
        try {
            services = serviceRepository.findAllService();
        } catch (Exception e) {
            return GlobalFunction.generateResponse(108, HttpStatus.NOT_FOUND, "Banner tidak bisa di temukan", null, request);
        }
        for(MstService service:services){
            ResponseService responseService = new ResponseService();
            responseService.setServiceCode(service.getServiceCode());
            responseService.setServiceName(service.getServiceName());
            responseService.setServiceIcon(service.getServiceIcon());
            responseService.setServiceTariff(service.getServiceTariff());
            resServices.add(responseService);
        }
        return GlobalFunction.generateResponse(0,HttpStatus.OK,"Sukses",resServices,request);
    }
}
