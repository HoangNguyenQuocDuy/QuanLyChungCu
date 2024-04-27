/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.formatters;

import com.qlcc.pojo.Surveyoption;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author DELL
 */
public class SurveyOptionFormatter implements Formatter<Surveyoption>{
    @Override
    public String print(Surveyoption t, Locale locale) {
        return String.valueOf(t.getId());
    }

    @Override
    public Surveyoption parse(String rtId, Locale locale) throws ParseException {
        Surveyoption rt = new Surveyoption();
        rt.setId(Integer.parseInt(rtId));
        
        return rt;
    }
}
