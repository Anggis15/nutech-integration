package com.apply.applyKerja.controller;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 03/12/24 20.35
@Last Modified 03/12/24 20.35
Version 1.0
*/

import com.apply.applyKerja.dto.request.EditProfileDTOValidasi;
import com.apply.applyKerja.dto.request.LoginDTOValidasi;
import com.apply.applyKerja.dto.request.RegisDTOValidasi;
import com.apply.applyKerja.model.UserProfile;
import com.apply.applyKerja.service.UserProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class UserProfileController {
    @Autowired
    UserProfileService userProfileService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/registration")
    public ResponseEntity<Object> regisUserProfile(@Valid @RequestBody RegisDTOValidasi regisUser, HttpServletRequest request){
        return userProfileService.registrasiUser(modelMapper.map(regisUser, UserProfile.class), request);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUserProfile(@Valid @RequestBody LoginDTOValidasi loginUser, HttpServletRequest request){
        return userProfileService.loginApp(modelMapper.map(loginUser, UserProfile.class), request);
    }
    @GetMapping("/profile")
    public ResponseEntity<Object> getProfile(HttpServletRequest request){
        return userProfileService.getProfile(request);
    }
    @PutMapping("/profile/update")
    public ResponseEntity<Object> editProfile(@Valid @RequestBody EditProfileDTOValidasi profie, HttpServletRequest request){
        return userProfileService.editProfile(profie,request);
    }
    @PutMapping(path = "/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> editImage(@RequestPart("file") MultipartFile image , HttpServletRequest request){
        return userProfileService.editImage(image, request);
    }
}
