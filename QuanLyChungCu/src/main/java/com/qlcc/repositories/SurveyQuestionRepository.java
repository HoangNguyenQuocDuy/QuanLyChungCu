/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories;

import com.qlcc.pojo.Surveyquestion;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface SurveyQuestionRepository {
    List<Surveyquestion> getSurveyQuestions(Map<String, String> params); 
    
    int addOrUpdate(Surveyquestion surveyQuestion);

    Surveyquestion getSurveyQuestionById(int id);
    
    void deleteSurveyQuestion(int id);
}
