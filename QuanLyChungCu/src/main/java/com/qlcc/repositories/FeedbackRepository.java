/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories;

import com.qlcc.pojo.Feedback;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface FeedbackRepository {

    void addOrUpdate(Feedback fb);

    List<Feedback> getFeedbacks(Map<String, String> params);

    Feedback getFeedbackById(int id);

    void deleteFeedback(int id);

    int getTotalFeedback();
}
