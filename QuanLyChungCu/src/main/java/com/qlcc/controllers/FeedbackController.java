/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.services.FeedbackService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author DELL
 */
@Controller
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    
    @Autowired
    private Environment env;
    
    @GetMapping("/feedbacks")
    public String createView(Model model, @RequestParam Map<String, String> paramsRequest) {

        try {
            int totalEntryRightsFeedbacks = feedbackService.getTotalFeedback();
            int pageSize = Integer.parseInt(env.getProperty("user.pageSize"));
            int totalPages = (int) Math.ceil((double) totalEntryRightsFeedbacks / pageSize);


            model.addAttribute("totalPages", totalPages);
            model.addAttribute("feedbacks", feedbackService.getFeedbacks(paramsRequest));
        } catch (NumberFormatException ex) {
            model.addAttribute("errMsg", ex.toString());
            System.err.println("Err: " + ex.getMessage());
        }
        return "feedback_list";
    }
    
    @GetMapping("/feedbacks/{fbId}")
    public String viewDetailFeedback(Model model, @PathVariable("fbId") int fbId) {
        model.addAttribute("feedback", feedbackService.getFeedbackById(fbId));

        return "feedback";
    }
}
