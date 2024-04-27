/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Surveyquestion;
import com.qlcc.repositories.SurveyQuestionRepository;
import com.qlcc.services.SurveyQuestionService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class SurveyQuestionServiceImpl implements SurveyQuestionService{
    
    @Autowired
    private SurveyQuestionRepository surveyQuestionRepo;

    @Override
    public List<Surveyquestion> getSurveyQuestions(Map<String, String> params) {
        return surveyQuestionRepo.getSurveyQuestions(params);
    }

    @Override
    public int addOrUpdate(Surveyquestion surveyQuestion) {
        return surveyQuestionRepo.addOrUpdate(surveyQuestion);
    }

    @Override
    public Surveyquestion getSurveyQuestionById(int id) {
        return surveyQuestionRepo.getSurveyQuestionById(id);
    }

    @Override
    public void deleteSurveyQuestion(int id) {
        surveyQuestionRepo.deleteSurveyQuestion(id);
    }
    
    
    
}
