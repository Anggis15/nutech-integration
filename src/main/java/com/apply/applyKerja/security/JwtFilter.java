package com.apply.applyKerja.security;

import com.apply.applyKerja.handler.GlobalExceptionHandler;
import com.apply.applyKerja.service.UserProfileService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 16/10/24 22.47
@Last Modified 16/10/24 22.47
Version 1.0
*/

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;
    @Autowired
    private UserProfileService userDetailService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");//ambil header Authorization
        authorization = authorization == null ? "": authorization;
        String token = null;
        String userName = null;
        try{
            if(!"".equals(authorization) && authorization.startsWith("Bearer ") && authorization.length()>7) {
                token = authorization.substring(7);//memotong setelah kata Bearer+spasi = 7 digit
                token = Crypto.performDecrypt(token);
                userName = jwtUtility.getUsernameFromToken(token);
                if(userName != null &&
                        SecurityContextHolder.getContext().getAuthentication()==null)
                {
                    if(jwtUtility.validateToken(token))
                    {
                        final UserDetails userDetails = userDetailService.loadUserByUsername(userName);
                        final UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null,
                                        userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        catch (Exception ex) {
//            throw new ResponseStatusException("token tidak valid");
            globalExceptionHandler.apiException(request);
//            response.setContentType("application/json");
//            response.setStatus(401);
//            response.getWriter().write("{\"status\":108,\"message\":\"token tidak valid\",\"data\":null}");

        }

        filterChain.doFilter(request,response);
    }

}
