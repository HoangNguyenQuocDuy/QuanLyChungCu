/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories;

import com.qlcc.pojo.SurveyAnswer;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface SurveyAnswerRepository {
    List<SurveyAnswer> getSurveyAnswers(Map<String, String> params); 
    
    void addSurveyAnswer(SurveyAnswer surveyAnswer);
}
