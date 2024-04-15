/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.formatters;

import com.qlcc.pojo.Room;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author DELL
 */
public class RoomFormatter implements Formatter<Room>{
    @Override
    public String print(Room r, Locale locale) {
        return String.valueOf(r.getId());
    }

    @Override
    public Room parse(String roomId, Locale locale) throws ParseException {
        Room r = new Room();
        r.setId(Integer.parseInt(roomId));
        
        return r;
    }
}
