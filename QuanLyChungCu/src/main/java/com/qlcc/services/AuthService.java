/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

import com.qlcc.dto.AuthRequest;
import com.qlcc.dto.AuthResponse;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public interface AuthService {

    AuthResponse authenticate(AuthRequest request, HttpServletResponse response);

    AuthResponse refreshAccessToken(String oldRefreshToken) throws Exception;
}
