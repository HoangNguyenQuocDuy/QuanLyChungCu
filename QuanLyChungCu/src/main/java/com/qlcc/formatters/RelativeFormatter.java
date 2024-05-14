/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.formatters;

import com.qlcc.pojo.Relative;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author DELL
 */
public class RelativeFormatter implements Formatter<Relative>{

    @Override
    public String print(Relative p, Locale locale) {
        return String.valueOf(p.getId());
    }

    @Override
    public Relative parse(String pId, Locale locale) throws ParseException {
        Relative p = new Relative();
        p.setId(Integer.valueOf(pId));
        
        return p;
    }
    
}
