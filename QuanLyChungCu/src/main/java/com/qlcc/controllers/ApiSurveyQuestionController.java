/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.services.SurveyQuestionService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author DELL
 */
@RestController
@RequestMapping("/api/surveyquestions")
public class ApiSurveyQuestionController {
    
    @Autowired
    private SurveyQuestionService surveyQuestionService;
    
    @GetMapping(path = "/")
    public ResponseEntity<?> getSurveyQuestions(@RequestParam Map<String, String> params) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(surveyQuestionService.getSurveyQuestions(params));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
    }
    
}
