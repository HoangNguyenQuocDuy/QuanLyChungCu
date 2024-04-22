/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.pojo.Locker;
import com.qlcc.pojo.Room;
import com.qlcc.services.InvoicetypeService;
import com.qlcc.services.LockerService;
import com.qlcc.services.RoomService;
import com.qlcc.services.RoomTypeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author DELL
 */
@Controller
@ControllerAdvice
@PropertySource("classpath:application.properties")
public class IndexController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private Environment env;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private LockerService lockerService;
    
    @Autowired
    private InvoicetypeService invoicetypeService;

    @ModelAttribute
    public void commonAtrr(Model model, @RequestParam Map<String, String> paramsRequest) {
        model.addAttribute("roomtypes", roomTypeService.getRoomtypes());
        model.addAttribute("invoicetypes", invoicetypeService.getInvoicetypes());

//        model.addAttribute("rooms", roomService.getRooms(params));
        Map<String, String> paramsLocker = new HashMap<>();
        paramsLocker.put("status", "Using");
        paramsLocker.put("list", "true");
        List<Locker> lockers = lockerService.getLockers(paramsLocker);

        Map<String, String> paramsRoom = new HashMap<>();
        paramsRoom.put("status", "Rented");
        paramsRoom.put("list", "true");
        List<Room> rooms = roomService.getRooms(paramsRoom);

        model.addAttribute("roomsUsing", rooms);
    }

    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        List<Room> rooms = roomService.getRooms(params);

        int totalRooms = roomService.getTotalRooms();
        int pageSize = Integer.parseInt(env.getProperty("user.pageSize"));
        int totalPages = (int) Math.ceil((double) totalRooms / pageSize);

        model.addAttribute("rooms", rooms);
        model.addAttribute("totalPages", totalPages);

        return "index";
    }
}
