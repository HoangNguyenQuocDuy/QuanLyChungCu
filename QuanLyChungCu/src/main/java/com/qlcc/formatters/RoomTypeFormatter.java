/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.formatters;

import com.qlcc.pojo.RoomType;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author DELL
 */
public class RoomTypeFormatter implements Formatter<RoomType>{

    @Override
    public String print(RoomType t, Locale locale) {
        return String.valueOf(t.getId());
    }

    @Override
    public RoomType parse(String rtId, Locale locale) throws ParseException {
        RoomType rt = new RoomType();
        rt.setId(Integer.parseInt(rtId));
        
        return rt;
    }
    
}
