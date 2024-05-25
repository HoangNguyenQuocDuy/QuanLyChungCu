/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.dto;

import com.qlcc.pojo.User;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author DELL
 */
@Data
@Builder
public class AuthResponse {
    private User user;
    private String accessToken;
//    private String refreshToken;
}
