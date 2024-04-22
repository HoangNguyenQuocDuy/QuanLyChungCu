/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.formatters;

import com.qlcc.pojo.Invoicetype;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author DELL
 */
public class InvoicetypeFormatter implements Formatter<Invoicetype>{
    @Override
    public String print(Invoicetype t, Locale locale) {
        return String.valueOf(t.getId());
    }

    @Override
    public Invoicetype parse(String rtId, Locale locale) throws ParseException {
        Invoicetype rt = new Invoicetype();
        rt.setId(Integer.parseInt(rtId));
        
        return rt;
    }
}
