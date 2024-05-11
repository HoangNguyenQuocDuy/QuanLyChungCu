/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Surveyanswer;
import com.qlcc.repositories.SurveyAnswerRepository;
import com.qlcc.services.SurveyAnswerService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class SurveyAnswerServiceImpl implements SurveyAnswerService{
    
    @Autowired
    private SurveyAnswerRepository surveyAnswerRepo;

    @Override
    public List<Surveyanswer> getSurveyAnswers(Map<String, String> params) {
        return surveyAnswerRepo.getSurveyAnswers(params);
    }

    @Override
    public void addSurveyAnswer(Surveyanswer surveyAnswer) {

        surveyAnswerRepo.addSurveyAnswer(surveyAnswer);
    }
    
}
