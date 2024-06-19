/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.dto.AuthRequest;
import com.qlcc.pojo.User;
import com.qlcc.services.AuthService;
import com.qlcc.services.UserService;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author DELL
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {

        try {
            User userFind = userService.getUserByUsername(authRequest.getUsername());
            if (userFind == null) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                        "User not found!");
            } else if (!passwordEncoder.matches(authRequest.getPassword(), userFind.getPassword())) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                        "Wrong password");
            } else {
                return ResponseEntity.ok(authService.authenticate(authRequest, response));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    "Failed when register "
                    + e.getMessage()
            );
        }
    }
    
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        try {
            userService.forgotPassword(body.get("email"));
            return ResponseEntity.status(HttpStatus.OK).body("Verify token has been sent");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ex.getMessage());
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> data) {
        try {
            String email = data.get("email");
            String verificationCode = data.get("verificationCode");
            String newPassword = data.get("newPassword");
            
            userService.resetPassword(email, verificationCode, newPassword);
            
            return ResponseEntity.status(HttpStatus.OK).body("Change password successful");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ex.getMessage());
        }
    }
}
