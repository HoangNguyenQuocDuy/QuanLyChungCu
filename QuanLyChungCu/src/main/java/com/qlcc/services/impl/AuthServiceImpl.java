/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.dto.AuthRequest;
import com.qlcc.dto.AuthResponse;
import com.qlcc.pojo.User;
import com.qlcc.services.AuthService;
import com.qlcc.services.JWTService;
import com.qlcc.services.UserService;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Override
    public AuthResponse authenticate(AuthRequest request, HttpServletResponse response) {
        try {
            User user = userService.getUserByUsername(request.getUsername());

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String jwtAccessToken = jwtService.generateToken(user);

            response.setHeader("Authentication", "Bearer " + jwtAccessToken);

            return AuthResponse.builder()
                    .accessToken(jwtAccessToken)
                    .user(user)
                    .build();
        } catch (AuthenticationException e) {
            Logger.getLogger(AuthServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public AuthResponse refreshAccessToken(String oldRefreshToken) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
