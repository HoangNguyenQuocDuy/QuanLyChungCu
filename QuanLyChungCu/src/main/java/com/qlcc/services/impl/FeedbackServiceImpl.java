/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Feedback;
import com.qlcc.repositories.FeedbackRepository;
import com.qlcc.services.FeedbackService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class FeedbackServiceImpl implements FeedbackService{
    
    @Autowired
    private FeedbackRepository feedbackRepo;

    @Override
    public void addOrUpdate(Feedback fb) {
        feedbackRepo.addOrUpdate(fb);
    }

    @Override
    public List<Feedback> getFeedbacks(Map<String, String> params) {
        return feedbackRepo.getFeedbacks(params);
    }

    @Override
    public Feedback getFeedbackById(int id) {
        return feedbackRepo.getFeedbackById(id);
    }

    @Override
    public void deleteFeedback(int id) {
        feedbackRepo.deleteFeedback(id);
    }

    @Override
    public int getTotalFeedback() {
        return feedbackRepo.getTotalFeedback();
    }
    
}
