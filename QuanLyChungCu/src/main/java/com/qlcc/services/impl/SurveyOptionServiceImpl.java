/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Surveyoption;
import com.qlcc.repositories.SurveyOptionRepository;
import com.qlcc.services.SurveyOptionService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class SurveyOptionServiceImpl implements SurveyOptionService{
    
    @Autowired
    private SurveyOptionRepository surveyOptionRepo;

    @Override
    public List<Surveyoption> getSurveyOptions(Map<String, String> params) {
        return surveyOptionRepo.getSurveyOptions(params);
    }

    @Override
    public int addOrUpdate(Surveyoption surveyOption) {
        return surveyOptionRepo.addOrUpdate(surveyOption);
    }

    @Override
    public Surveyoption getSurveyOptionById(int id) {
        return surveyOptionRepo.getSurveyOptionById(id);
    }

    @Override
    public void deleteSurveyOption(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
