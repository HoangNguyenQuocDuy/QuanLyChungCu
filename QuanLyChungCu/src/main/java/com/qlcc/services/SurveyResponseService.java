/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

import com.qlcc.pojo.SurveyResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface SurveyResponseService {
    List<SurveyResponse> getSurveys(Map<String, String> params); 
    
    int addSurveyResponse(SurveyResponse surveyResponse);
    
    SurveyResponse getSurveyResponseById(int id);
}
