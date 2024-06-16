/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.dto.UserResponse;
import com.qlcc.pojo.User;
import com.qlcc.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author DELL
 */
@RestController
//@RequestMapping("/api/users")
public class ApiUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/_users/")
    public ResponseEntity<?> getUserNa(@RequestParam int userId) {
        try {
            User user = userService.getUserById(userId);
            UserResponse userRes = new UserResponse();
            userRes.setUsername(user.getUsername());
            userRes.setStatus(user.getStatus());
            userRes.setFirstname(user.getFirstname());
            userRes.setLastname(user.getLastname());
            userRes.setEmail(user.getEmail());
            userRes.setPhone(user.getPhone());
            userRes.setRoleName(user.getRoleName());
            userRes.setLocker(user.getLocker());
            userRes.setRoom(user.getRoom());
            userRes.setId(user.getId());
            userRes.setAvatar(user.getAvatar());

            return ResponseEntity.status(HttpStatus.OK).body(userRes);
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
    }

    @PostMapping("/api/users/block/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void blockUser(Model model, @PathVariable("userId") int userId) {
        try {
            userService.blockUser(userId);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage(), ex);
        }
    }

    @PostMapping(path = "/api/users/{userId}", consumes = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<?> updateUser(@RequestParam Map<String, String> params,
            @RequestPart MultipartFile[] files, @PathVariable("userId") int userId) throws Exception {

        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "User not found with ID: " + userId);
            }

            if (files.length > 0) {
                user.setFile(files[0]);
            }
            if (params.get("password") != null) {
                user.setPassword(params.get("password"));
            }
            if (params.get("firstname") != null) {
                user.setFirstname(params.get("firstname"));
            }
            if (params.get("lastname") != null) {
                user.setLastname(params.get("lastname"));
            }
            if (params.get("email") != null) {
                user.setEmail(params.get("email"));
            }
            if (params.get("phone") != null) {
                user.setPhone(params.get("phone"));
            }

            userService.addOrUpdate(user);
            return ResponseEntity.status(HttpStatus.OK).body("Update user successful");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ex.getMessage());
        }
    }
}
