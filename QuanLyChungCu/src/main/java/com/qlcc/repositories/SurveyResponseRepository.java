/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories;

import com.qlcc.pojo.Surveyresponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface SurveyResponseRepository {
    List<Surveyresponse> getSurveys(Map<String, String> params); 
    
    int addSurveyResponse(Surveyresponse surveyResponse);
    
    Surveyresponse getSurveyResponseById(int id);
}
