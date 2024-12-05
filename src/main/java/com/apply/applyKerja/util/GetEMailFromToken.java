package com.apply.applyKerja.util;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 04/12/24 10.42
@Last Modified 04/12/24 10.42
Version 1.0
*/

import com.apply.applyKerja.security.Crypto;
import com.apply.applyKerja.security.JwtUtility;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GetEMailFromToken {

    JwtUtility jwtUtility = new JwtUtility();

    public String getEmailFromToken(String authorization){
        String token = null;
        String email = null;
        if(!"".equals(authorization) && authorization.startsWith("Bearer ") && authorization.length()>7)
        {
            token = authorization.substring(7);
            token = Crypto.performDecrypt(token);
            email = jwtUtility.getUsernameFromToken(token);

        }
        return email;
    }
    public LocalDateTime getJakartaTime(){

        Instant instant = Instant.now();

        // Create a ZonedDateTime object for Jakarta timezone
        ZoneId jakartaZoneId = ZoneId.of("Asia/Jakarta");
        ZonedDateTime jakartaDateTime = instant.atZone(jakartaZoneId);

        // Format the Jakarta date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = jakartaDateTime.format(formatter);
        return LocalDateTime.parse(formattedDateTime.replace(" ","T"));
    }

    public String getIdFromToken(String authorization){
        String token = null;
        String id = null;
        if(!"".equals(authorization) && authorization.startsWith("Bearer ") && authorization.length()>7)
        {
            token = authorization.substring(7);
            token = Crypto.performDecrypt(token);
            id = jwtUtility.getIdFromToken(token);

        }
        return id;
    }
}
