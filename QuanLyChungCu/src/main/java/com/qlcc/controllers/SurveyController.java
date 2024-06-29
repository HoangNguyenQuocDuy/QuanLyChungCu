/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.dto.Question;
import com.qlcc.dto.SurveyForm;
import com.qlcc.pojo.Survey;
import com.qlcc.pojo.SurveyOption;
import com.qlcc.pojo.SurveyQuestion;
import com.qlcc.services.SurveyOptionService;
import com.qlcc.services.SurveyQuestionService;
import com.qlcc.services.SurveyService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author DELL
 */
@Controller
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyQuestionService surveyQuestionService;

    @Autowired
    private SurveyOptionService surveyOptionService;

    @Autowired
    private Environment env;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/surveys")
    public String createView(Model model, @RequestParam Map<String, String> params) {

        int totalSurveys = surveyService.getTotalSurveys();
        int pageSize = Integer.parseInt(env.getProperty("user.pageSize"));
        int totalPages = (int) Math.ceil((double) totalSurveys / pageSize);

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("surveys", surveyService.getSurveys(params));
        return "survey_list";
    }

    @GetMapping("/surveys/")
    public String addSurveyView(Model model) {
        model.addAttribute("surveyForm", new SurveyForm());

        return "survey";
    }

    @GetMapping("/surveys/{surveyId}")
    public String updateSurveyView(Model model, @PathVariable("surveyId") int surveyId) {
        SurveyForm sf = new SurveyForm();
        Survey sv = surveyService.getSurveyById(surveyId);

        Map<String, String> params = new HashMap<>();
        params.put("surveyId", sv.getId().toString());
        List<SurveyQuestion> surveyQuestions = surveyQuestionService.getSurveyQuestions(params);

        List<Question> questions = new ArrayList<>();

        for (SurveyQuestion sq : surveyQuestions) {
            Question q = new Question();

            Map<String, String> paramQuestion = new HashMap<>();
            paramQuestion.put("questionId", sq.getId().toString());

            List<SurveyOption> options = surveyOptionService.getSurveyOptions(paramQuestion);
            q.setQuestion(sq);
            q.setOptions(options);

            questions.add(q);
        }

        sf.setSurvey(sv);
        model.addAttribute("surveyForm", sf);
        model.addAttribute("questions", questions);

        return "survey";
    }

    @PostMapping("/surveys/")
    public String addSurveyProcess(Model model, @ModelAttribute(value = "surveyForm") @Valid SurveyForm surveyForm,
            BindingResult rs) {
        if (!rs.hasErrors()) {
            try {
                int id = surveyService.addOrUpdate(surveyForm.getSurvey());

                Survey survey = surveyService.getSurveyById(id);

                for (SurveyQuestion sq : surveyForm.getQuestions()) {
                    sq.setSurveyId(survey);
                    List<SurveyOption> options = sq.getSurveyoptionSet();
                    sq.setSurveyoptionSet(null);
                    int sqId = surveyQuestionService.addOrUpdate(sq);

                    SurveyQuestion nsq = surveyQuestionService.getSurveyQuestionById(sqId);

                    for (SurveyOption so : options) {
                        so.setQuestionId(nsq);
                        surveyOptionService.addOrUpdate(so);
                    }
                }

                messagingTemplate.convertAndSend("/notification/surveys",
                        "Admin has created new survey");

                return "redirect:/surveys";
            } catch (Exception ex) {
                model.addAttribute("errMsg", ex.toString());
                System.err.println("Err: " + ex.getMessage());
            }
        }
        return "survey_list";
    }
}
