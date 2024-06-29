/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.formatters;

import com.qlcc.pojo.SurveyOption;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author DELL
 */
public class SurveyOptionFormatter implements Formatter<SurveyOption>{
    @Override
    public String print(SurveyOption t, Locale locale) {
        return String.valueOf(t.getId());
    }

    @Override
    public SurveyOption parse(String rtId, Locale locale) throws ParseException {
        SurveyOption rt = new SurveyOption();
        rt.setId(Integer.parseInt(rtId));
        
        return rt;
    }
}
