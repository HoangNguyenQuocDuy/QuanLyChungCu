/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.pojo.Userorder;
import com.qlcc.services.LockerService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.qlcc.services.UserOrderService;

/**
 *
 * @author DELL
 */
@Controller
public class OrderController {

    @Autowired
    private UserOrderService orderService;

    @Autowired
    private LockerService lockerService;

    @Autowired
    private Environment env;

    @GetMapping("/orders")
    public String createView(Model model, @RequestParam Map<String, String> paramsRequest) {

        try {
            int totalOrders = orderService.getTotalOrders();
            int pageSize = Integer.parseInt(env.getProperty("user.pageSize"));
            int totalPages = (int) Math.ceil((double) totalOrders / pageSize);


            model.addAttribute("totalPages", totalPages);
            model.addAttribute("userOrders", orderService.getOrders(paramsRequest));
        } catch (Exception ex) {
            model.addAttribute("errMsg", ex.toString());
            System.err.println("Err: " + ex.getMessage());
        }
        return "order_list";
    }

    @GetMapping("/orders/")
    public String addOrderView(Model model) {
        model.addAttribute("userOrder", new Userorder());

        return "order";
    }

    @PostMapping("/orders/")
    public String addOrderProcess(Model model, @ModelAttribute(value = "userOrder") Userorder userOrder) {
        try {
            orderService.addOrUpdate(userOrder);

            return "redirect:/orders";
        } catch (Exception ex) {
            model.addAttribute("errMsg", ex.toString());
            System.err.println("Err: " + ex.getMessage());
        }

        return "order";
    }

}
