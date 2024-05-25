/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.qlcc.pojo.User;
import com.qlcc.services.JWTService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
@PropertySource("classpath:application.properties")
public class JWTServiceImpl implements JWTService{
    
    @Autowired
    private Environment env;

    @Override
    public String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 50*60*1000))
                .sign(Algorithm.HMAC256(env.getProperty("secret.key").getBytes()));
    }
    
}
