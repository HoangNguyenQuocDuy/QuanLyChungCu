/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.services.ParkingRightService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author DELL
 */
@Controller
public class ParkingRightController {
    
    @Autowired
    private ParkingRightService parkingRightService;
    
    @Autowired
    private Environment env;
    
    @GetMapping("/parkings")
    public String createView(Model model, @RequestParam Map<String, String> paramsRequest) {

        try {
            int totalEntryRights = parkingRightService.getTotalParkingRights();
            int pageSize = Integer.parseInt(env.getProperty("user.pageSize"));
            int totalPages = (int) Math.ceil((double) totalEntryRights / pageSize);


            model.addAttribute("totalPages", totalPages);
            model.addAttribute("parkings", parkingRightService.getParkingRight(paramsRequest));
        } catch (NumberFormatException ex) {
            model.addAttribute("errMsg", ex.toString());
            System.err.println("Err: " + ex.getMessage());
        }
        return "parking_list";
    }
}
