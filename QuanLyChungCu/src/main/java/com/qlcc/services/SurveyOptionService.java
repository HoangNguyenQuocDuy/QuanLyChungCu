/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

import com.qlcc.pojo.SurveyOption;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface SurveyOptionService {
    List<SurveyOption> getSurveyOptions(Map<String, String> params); 
    
    int addOrUpdate(SurveyOption surveyOption);

    SurveyOption getSurveyOptionById(int id);
    
    void deleteSurveyOption(int id);
}
