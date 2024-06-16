/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

import java.util.List;

/**
 *
 * @author DELL
 */
public interface StatsService {

    List<Object[]> getReportForSurveyQuestion(int surveyId, int questionId);

    List<Object[]> getCountResponseForSurvey();
    
    List<Object[]> getRevenueByMonth(int month, int year);
}
