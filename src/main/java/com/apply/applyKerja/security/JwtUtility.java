package com.apply.applyKerja.security;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 16/10/24 22.42
@Last Modified 16/10/24 22.42
Version 1.0
*/

import com.apply.applyKerja.config.JwtConfig;
import com.apply.applyKerja.util.GlobalFunction;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtility implements Serializable, AuthenticationEntryPoint{
    private static final long serialVersionUID = 234234523523L;
    public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60;

    /**
     * Function disini hanya menerima token JWT yang sudah di decrypt
     * Yang dapat di claims disini adalah key yang diinput dari api Login
     */
    public Map<String,Object> mappingBodyToken(String token){
        /** claims adalah data payload yang ada di token */
        Map<String,Object> mapz = new HashMap<>();

        Claims claims = getAllClaimsFromToken(token);
        mapz.put("uid",claims.get("uid"));
        mapz.put("un",claims.getSubject());//untuk subject / username sudah ada di claims token JWT
        mapz.put("pn",claims.get("pn"));

        return mapz;
    }

    /**
     * KONFIGURASI CUSTOMISASI BERAKHIR DISINI
     * KONFIGURASI UNTUK JWT DIMULAI DARI SINI
     */

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public String getIdFromToken(String token) {
        Claims claim = getAllClaimsFromToken(token);
        return claim.get("uid").toString();
    }

    //parameter token habis waktu nya
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //kita dapat mengambil informasi dari token dengan menggunakan secret key
    //disini juga validasi dari expired token dan lihat signature  dilakukan
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JwtConfig.getJwtSecret()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token untuk user
    public String generateToken(UserDetails userDetails, Map<String,Object> claims) {
        claims = (claims==null)?new HashMap<String,Object>():claims;
        return doGenerateToken(claims, userDetails.getUsername());
    }
    /** proses yang dilakukan saat membuat token adalah :
     mendefinisikan claim token seperti penerbit (Issuer) , waktu expired , subject dan ID
     generate signature dengan menggunakan secret key dan algoritma HS512 (HMAC - SHA),
     */

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Long timeMilis = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(timeMilis))
                .setExpiration(new Date(timeMilis + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, JwtConfig.getJwtSecret()).compact();
    }

    public Boolean validateToken(String token) {
        /** Sudah otomatis tervalidaasi jika expired date masih aktif */
        String username = getUsernameFromToken(token);
        return (username!=null && !isTokenExpired(token));
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().write("{\"status\":108,\"message\":\"Token tidak tidak valid atau kadaluwarsa\",\"data\":null}");
    }
    /**
     * KONFIGURASI UNTUK JWT BERAKHIR DI SINI
     */
}
