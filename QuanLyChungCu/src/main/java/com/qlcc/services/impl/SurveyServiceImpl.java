/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Survey;
import com.qlcc.repositories.SurveyRepository;
import com.qlcc.services.SurveyService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class SurveyServiceImpl implements SurveyService{
    
    @Autowired
    private SurveyRepository surveyRepo;

    @Override
    public List<Survey> getSurveys(Map<String, String> params) {
        return surveyRepo.getSurveys(params);
    }

    @Override
    public int addOrUpdate(Survey survey) {
        if (survey.getId() == null) {
            survey.setCreatedAt(new Date());
            survey.setStatus("Open");
        }
        return surveyRepo.addOrUpdate(survey);
    }

    @Override
    public Survey getSurveyById(int id) {
        return surveyRepo.getSurveyById(id);
    }

    @Override
    public int getTotalSurveys() {
        return surveyRepo.getTotalSurveys();
    }
    
    
    
}
