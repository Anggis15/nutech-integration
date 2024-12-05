package com.apply.applyKerja.service;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 03/12/24 16.31
@Last Modified 03/12/24 16.31
Version 1.0
*/

import com.apply.applyKerja.dto.request.EditProfileDTOValidasi;
import com.apply.applyKerja.dto.response.ResponseProfile;
import com.apply.applyKerja.model.UserProfile;
import com.apply.applyKerja.repository.UserProfileRepository;
import com.apply.applyKerja.security.BcryptImpl;
import com.apply.applyKerja.security.JwtUtility;
import com.apply.applyKerja.util.GetEMailFromToken;
import com.apply.applyKerja.util.GlobalFunction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserProfileService implements UserDetailsService {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    private CloudinaryService cloudinaryService;

    GetEMailFromToken getEMailFromToken = new GetEMailFromToken();

    private Map<String,Object> m = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserProfile> opUser = userProfileRepository.findByEmail(email);
        if(opUser.isEmpty())
        {
            throw new UsernameNotFoundException("TOKEN TIDAK VALID");
//            return null;
        }
        UserProfile userNext = opUser.get();

        return new org.springframework.security.core.userdetails.
                User(userNext.getEmail(),userNext.getPassword(),userNext.getAuthorities());
    }


    public ResponseEntity<Object> registrasiUser(UserProfile userProfile, HttpServletRequest request){
        Optional<UserProfile> opUser = userProfileRepository.findByEmail(userProfile.getEmail());
        if(opUser.isEmpty()){
            userProfile.setPassword(BcryptImpl.hash(userProfile.getPassword()+userProfile.getEmail()));
            try{
                userProfileRepository.insertUserProfile(UUID.randomUUID().toString(),userProfile.getEmail(), userProfile.getFirstName(), userProfile.getLastName(), userProfile.getPassword());
            } catch (Exception e) {
                return GlobalFunction.generateResponse(108,HttpStatus.NOT_FOUND,"Format data tidak sesuai", null, request);
            }
        }
        return GlobalFunction.generateResponse(0,HttpStatus.OK, "User Berhasil di Registrasi!!!", null, request);
    }

    public ResponseEntity<Object> loginApp(UserProfile user, HttpServletRequest request){
        String email = user.getEmail();
        Optional<UserProfile> opUser = userProfileRepository.findByEmail(email);
        if(opUser.isEmpty()){
            return GlobalFunction.generateResponse(107,HttpStatus.BAD_REQUEST,"email belum terdaftar", null,request);
        }
        UserProfile userProfile = opUser.get();
        if(!BcryptImpl.verifyHash(user.getPassword()+userProfile.getEmail(),
                userProfile.getPassword()))
        {
            return GlobalFunction.generateResponse(108,HttpStatus.BAD_REQUEST, "paswword atau email salaha", null, request);
        }
        Map<String,Object> mapForClaims = new HashMap<>();
        UserDetails userDetails = loadUserByUsername(user.getEmail());
        mapForClaims.put("uid",userProfile.getId());
        String token = jwtUtility.generateToken(userDetails,mapForClaims);
        m.put("token", token);
        return GlobalFunction.generateResponse(0,HttpStatus.ACCEPTED,"Berhasil Login", m, request);

    }

    public ResponseEntity<Object> getProfile(HttpServletRequest request){
        String email = getEMailFromToken.getEmailFromToken(request.getHeader("Authorization"));
        if(email.isEmpty()){
            return GlobalFunction.generateResponse(108,HttpStatus.BAD_REQUEST,"Username tidak ditemukan", null, request);
        }

        Optional<UserProfile> opUser = userProfileRepository.findByEmail(email);
        if(opUser.isEmpty()){
            return GlobalFunction.generateResponse(108,HttpStatus.BAD_REQUEST,"Username tidak ditemukan", null, request);
        }
        UserProfile getProfile = opUser.get();
        ResponseProfile resProfile = new ResponseProfile();
        resProfile.setEmail(getProfile.getEmail());
        resProfile.setFirstName(getProfile.getFirstName());
        resProfile.setLastName(getProfile.getLastName());
        resProfile.setImage(getProfile.getImage());
        return GlobalFunction.generateResponse(0,HttpStatus.OK,"Sukses", resProfile,request);
    }
    public ResponseEntity<Object> editProfile(EditProfileDTOValidasi profile, HttpServletRequest request){
        String email = getEMailFromToken.getEmailFromToken(request.getHeader("Authorization"));
        UserProfile userProfile = null;
        try {
            userProfileRepository.editProfile(profile.getFirstName(), profile.getLastName(), email);
            Optional<UserProfile> opUser = userProfileRepository.findByEmail(email);
            if(opUser.isEmpty()){
                return GlobalFunction.generateResponse(108,HttpStatus.BAD_REQUEST,"Gagal Mendapatkan profile", null,request);
            }
            userProfile = opUser.get();
        } catch (Exception e) {
            return GlobalFunction.generateResponse(108,HttpStatus.BAD_REQUEST,"Gagal edit profile", null, request);
        }

        ResponseProfile resProfile = new ResponseProfile();
        resProfile.setEmail(userProfile.getEmail());
        resProfile.setFirstName(userProfile.getFirstName());
        resProfile.setLastName(userProfile.getLastName());
        resProfile.setImage(userProfile.getImage());
        return GlobalFunction.generateResponse(0,HttpStatus.OK,"Sukses", resProfile,request);
    }
    public ResponseEntity<Object> editImage(MultipartFile image, HttpServletRequest request){
        String email = getEMailFromToken.getEmailFromToken(request.getHeader("Authorization"));
        UserProfile userProfile = null;
        String urlImage = cloudinaryService.uploadFile(image);
        if(urlImage==null){
            return GlobalFunction.generateResponse(108,HttpStatus.BAD_REQUEST,"Gagal Upload File", null,request);
        }
        try {
            userProfileRepository.editImage(urlImage, email);
            Optional<UserProfile> opUser = userProfileRepository.findByEmail(email);
            if(opUser.isEmpty()){
                return GlobalFunction.generateResponse(108,HttpStatus.BAD_REQUEST,"Gagal Mendapatkan profile", null,request);
            }
            userProfile = opUser.get();
        } catch (Exception e) {
            return GlobalFunction.generateResponse(108,HttpStatus.BAD_REQUEST,"Gagal edit profile", null, request);
        }

        ResponseProfile resProfile = new ResponseProfile();
        resProfile.setEmail(userProfile.getEmail());
        resProfile.setFirstName(userProfile.getFirstName());
        resProfile.setLastName(userProfile.getLastName());
        resProfile.setImage(userProfile.getImage());
        return GlobalFunction.generateResponse(0,HttpStatus.OK,"Sukses", resProfile,request);
    }
}
