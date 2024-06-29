/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

import com.qlcc.pojo.SurveyQuestion;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface SurveyQuestionService {
    
    List<SurveyQuestion> getSurveyQuestions(Map<String, String> params); 
    
    int addOrUpdate(SurveyQuestion surveyQuestion);

    SurveyQuestion getSurveyQuestionById(int id);
    
    void deleteSurveyQuestion(int id);
    
}
