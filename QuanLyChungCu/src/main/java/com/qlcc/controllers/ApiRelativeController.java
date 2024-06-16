/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.pojo.Relative;
import com.qlcc.pojo.User;
import com.qlcc.services.RelativeService;
import com.qlcc.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author DELL
 */
@RestController
@RequestMapping("/api/relatives")
public class ApiRelativeController {

    @Autowired
    private RelativeService relativeService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getRelative(@RequestParam Map<String, String> params) {
        return ResponseEntity.status(HttpStatus.OK).body(relativeService.getRelative(params));
    }

    @PostMapping(path = "/", consumes = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<?> createRelative(@RequestPart MultipartFile[] files,
            @RequestParam Map<String, String> params) {
        try {
            User user = userService.getUserById(Integer.parseInt(params.get("userId")));
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "User not found with ID: " + params.get("userId"));
            }

            Relative relative = new Relative();
            relative.setFirstname(params.get("firstname"));
            relative.setLastname(params.get("lastname"));
            relative.setType(params.get("type"));
            relative.setUserId(user);

            if (files.length > 0) {
                relative.setFile(files[0]);
            }

            relativeService.addOrUpdate(relative);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "Create relative successfully!");
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{relativeId}")
    public ResponseEntity<?> deleteRelative(@PathVariable int relativeId) {
        try {
            Relative relative = relativeService.getRelativeById(relativeId);
            if (relative == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Relative not found with ID: " + relativeId);
            }

            relativeService.deleteRelative(relativeId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping(path = "/{relativeId}", consumes = {
        MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<?> updateRelative(@PathVariable("relativeId") int relativeId,
            @RequestPart MultipartFile[] files, @RequestParam Map<String, String> params) {
        try {
            Relative relative = relativeService.getRelativeById(relativeId);
            if (relative == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Relative not found with ID: " + relativeId);
            }

            if (files.length > 0) {
                relative.setFile(files[0]);
            }
            if (params.containsKey("firstname")) {
                relative.setFirstname(params.get("firstname"));
            }
            if (params.containsKey("lastname")) {
                relative.setLastname(params.get("lastname"));
            }
            if (params.containsKey("type")) {
                relative.setType(params.get("type"));
            }

            relativeService.addOrUpdate(relative);

            return ResponseEntity.status(HttpStatus.OK).body(
                    "Update relative successfully!");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
