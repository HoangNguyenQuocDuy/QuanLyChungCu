/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.pojo.Surveyquestion;
import com.qlcc.services.StatsService;
import com.qlcc.services.SurveyQuestionService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author DELL
 */
@Controller
public class StatsController {

    @Autowired
    private StatsService statsService;
    @Autowired
    private SurveyQuestionService surveyQuestionService;

    @GetMapping("/stats")
    public String addStatsView(Model model, @RequestParam Map<String, String> params) {

        List<Object[]> statsResponsesForPerSurvey = statsService.getCountResponseForSurvey();
        model.addAttribute("statsResponsesForPerSurvey", statsResponsesForPerSurvey);

        String surveyId = params.getOrDefault("surveyId", "1");
        String questionId = params.getOrDefault("questionId", "1");

        Map<String, String> questionParams = new HashMap<>();
        questionParams.put("surveyId", surveyId);
        List<Surveyquestion> surveyQuestions = surveyQuestionService.getSurveyQuestions(questionParams);
        model.addAttribute("questions", surveyQuestions);

        List<Object[]> statsCountQuestion = statsService.getReportForSurveyQuestion(
                Integer.parseInt(surveyId), Integer.parseInt(questionId));
        model.addAttribute("statsCountQuestion", statsCountQuestion);

        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        int year = Integer.parseInt(params.getOrDefault("year", String.valueOf(currentYear)));
        int month = Integer.parseInt(params.getOrDefault("month", String.valueOf(currentMonth)));
        List<Object[]> statsRevenueByMonth = statsService.getRevenueByMonth(month, year);
        model.addAttribute("statsRevenueByMonth", statsRevenueByMonth);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        
        return "stats";
    }

}
