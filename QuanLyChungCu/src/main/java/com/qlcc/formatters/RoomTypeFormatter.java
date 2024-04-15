/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.formatters;

import com.qlcc.pojo.Roomtype;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author DELL
 */
public class RoomTypeFormatter implements Formatter<Roomtype>{

    @Override
    public String print(Roomtype t, Locale locale) {
        return String.valueOf(t.getId());
    }

    @Override
    public Roomtype parse(String rtId, Locale locale) throws ParseException {
        Roomtype rt = new Roomtype();
        rt.setId(Integer.parseInt(rtId));
        
        return rt;
    }
    
}
