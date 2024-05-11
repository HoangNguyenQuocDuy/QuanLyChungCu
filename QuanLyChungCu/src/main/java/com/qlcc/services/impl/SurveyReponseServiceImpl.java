/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Surveyresponse;
import com.qlcc.repositories.SurveyResponseRepository;
import com.qlcc.services.SurveyResponseService;
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
public class SurveyReponseServiceImpl implements SurveyResponseService{
    
    @Autowired
    private SurveyResponseRepository surveyResponseRepo;

    @Override
    public List<Surveyresponse> getSurveys(Map<String, String> params) {
        return surveyResponseRepo.getSurveys(params);
    }

    @Override
    public int addSurveyResponse(Surveyresponse surveyResponse) {
        surveyResponse.setCreatedAt(new Date());
        
        return surveyResponseRepo.addSurveyResponse(surveyResponse);
    }

    @Override
    public Surveyresponse getSurveyResponseById(int id) {
        return surveyResponseRepo.getSurveyResponseById(id);
    }
    
}
