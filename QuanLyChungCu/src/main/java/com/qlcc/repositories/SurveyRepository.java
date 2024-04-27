/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories;

import com.qlcc.pojo.Survey;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface SurveyRepository {
    List<Survey> getSurveys(Map<String, String> params); 
    
    int addOrUpdate(Survey survey);

    Survey getSurveyById(int id);

    int getTotalSurveys();
}
