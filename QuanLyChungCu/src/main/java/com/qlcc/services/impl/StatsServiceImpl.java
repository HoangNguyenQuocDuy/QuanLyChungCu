/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.repositories.StatsRepository;
import com.qlcc.services.StatsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class StatsServiceImpl implements StatsService{
    
    @Autowired
    private StatsRepository statsRepo;

    @Override
    public List<Object[]> getReportForSurveyQuestion(int surveyId, int questionId) {
        return statsRepo.getReportForSurveyQuestion(surveyId, questionId);
    }

    @Override
    public List<Object[]> getCountResponseForSurvey() {
        return statsRepo.getCountResponseForSurvey();
    }

    @Override
    public List<Object[]> getRevenueByMonth(int month, int year) {
        return statsRepo.getRevenueByMonth(month, year);
    }
    
}
