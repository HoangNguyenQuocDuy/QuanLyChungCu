/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

import com.qlcc.pojo.User;

/**
 *
 * @author DELL
 */
public interface JWTService {
    String generateToken(User user);
}
