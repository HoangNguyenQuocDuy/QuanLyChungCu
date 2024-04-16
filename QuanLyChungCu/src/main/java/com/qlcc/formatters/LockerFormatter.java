/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.formatters;

import com.qlcc.pojo.Locker;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author DELL
 */
public class LockerFormatter implements Formatter<Locker>{

    @Override
    public String print(Locker l, Locale locale) {
        return String.valueOf(l.getId());
    }

    @Override
    public Locker parse(String lockerId, Locale locale) throws ParseException {
        Locker l = new Locker();
        l.setId(Integer.parseInt(lockerId));
        
        return l;
    }
    
}
