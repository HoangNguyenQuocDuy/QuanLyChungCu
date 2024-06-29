/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.pojo.SurveyAnswer;
import com.qlcc.pojo.SurveyResponse;
import com.qlcc.services.SurveyAnswerService;
import com.qlcc.services.SurveyOptionService;
import com.qlcc.services.SurveyQuestionService;
import com.qlcc.services.SurveyResponseService;
import com.qlcc.services.SurveyService;
import com.qlcc.services.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author DELL
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiSurveyAnswerController {

    @Autowired
    private SurveyAnswerService surveyAnswerService;

    @Autowired
    private SurveyResponseService surveyResponseService;

    @Autowired
    private UserService userService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyQuestionService questionService;

    @Autowired
    private SurveyOptionService optionService;

    @PostMapping(path = "/surveyAnswers/", consumes = {
        "application/json"
    })
    public ResponseEntity<?> createAnswer(@RequestBody Map<String, String> body) {
        String surveyId = body.get("surveyId");
        String userId = body.get("userId");

        Map<String, String> params = new HashMap<>();
        params.put("surveyId", surveyId);
        params.put("userId", userId);

        List<SurveyResponse> sr = surveyResponseService.getSurveys(params);

        int srId;

        if (sr != null && !sr.isEmpty()) {
            srId = sr.get(0).getId();
        } else {
            SurveyResponse nSr = new SurveyResponse();
            nSr.setSurveyId(surveyService.getSurveyById(Integer.parseInt(surveyId)));
            nSr.setUserId(userService.getUserById(Integer.parseInt(userId)));

            srId = surveyResponseService.addSurveyResponse(nSr);
        }

        SurveyAnswer sa = new SurveyAnswer();
        sa.setResponseId(surveyResponseService.getSurveyResponseById(srId));
        sa.setOptionId(optionService.getSurveyOptionById(Integer.parseInt(body.get("optionId"))));
        sa.setQuestionId(questionService.getSurveyQuestionById(Integer.parseInt(body.get("questionId"))));
        
        surveyAnswerService.addSurveyAnswer(sa);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

}
