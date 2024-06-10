/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.pojo.Feedback;
import com.qlcc.pojo.User;
import com.qlcc.services.FeedbackService;
import com.qlcc.services.UserService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author DELL
 */
@RestController
@RequestMapping("/api/feedbacks")
public class ApiFeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getFeedbacks(@RequestParam Map<String, String> params) {
        try {
            User user = userService.getUserById(Integer.parseInt(params.get("userId")));

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "User not found with ID: " + params.get("userId"));
            }

            List<Feedback> feedbacks = feedbackService.getFeedbacks(params);

            return ResponseEntity.status(HttpStatus.OK).body(feedbacks);
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
    }

    @GetMapping(path = "/{fbId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
    public ResponseEntity<?> getFeedback(@RequestBody Map<String, String> params,
            @PathVariable("fbId") int fbId) {
        try {
            User user = userService.getUserById(Integer.parseInt(params.get("userId")));
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "User not found with ID: " + params.get("userId"));
            }

            Feedback fb = feedbackService.getFeedbackById(fbId);
            if (fb == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Feedback not found with ID: " + fbId);
            }
            if (!Objects.equals(fb.getUserId().getId(), user.getId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "User doesnt occupy this feedback!");
            }

            return ResponseEntity.status(HttpStatus.OK).body(feedbackService.getFeedbackById(fbId));
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
    }

    @PostMapping(path = "/", consumes = {
        MediaType.APPLICATION_JSON_VALUE,})
    public ResponseEntity<?> createFeedback(@RequestBody Map<String, String> params) {
        try {
            User user = userService.getUserById(Integer.parseInt(params.get("userId")));

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "User not found with ID: " + params.get("userId"));
            }

            Feedback fb = new Feedback();
            fb.setTitle(params.get("title"));
            fb.setContent(params.get("content"));
            fb.setUserId(user);

            feedbackService.addOrUpdate(fb);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "Create feedback successfully!");
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
    }

    @PutMapping(path = "/{fbId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateFeedback(@RequestBody Map<String, String> params,
            @PathVariable("fbId") int fbId) {
        try {
            Feedback fb = feedbackService.getFeedbackById(fbId);
            if (fb == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Feedback not found with ID: " + fbId);
            }

            User user = userService.getUserById(Integer.parseInt(params.get("userId")));
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "User not found with ID: " + params.get("userId"));
            }

            if (!Objects.equals(fb.getUserId().getId(), user.getId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "User doesnt occupy this feedback!");
            }

            if (params.containsKey("title") && !params.get("title").equals("")) {
                fb.setTitle(params.get("title"));
            }
            if (params.containsKey("content") && !params.get("content").equals("")) {
                fb.setContent(params.get("content"));
            }

            feedbackService.addOrUpdate(fb);

            return ResponseEntity.status(HttpStatus.OK).body(
                    "Update feedback successfully!");
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
    }

    @DeleteMapping(path = "/{fbId}")
    public ResponseEntity<?> deleteFeedback(@PathVariable("fbId") int fbId) {
        try {
            Feedback fb = feedbackService.getFeedbackById(fbId);
            if (fb == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "Feedback not found with ID: " + fbId);
            }

            feedbackService.deleteFeedback(fbId);

            return ResponseEntity.status(HttpStatus.OK).body(
                    "Delete feedback successfully!");
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
    }
}
