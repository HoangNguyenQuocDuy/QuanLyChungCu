/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.Surveyquestion;
import com.qlcc.repositories.SurveyQuestionRepository;
import com.qlcc.repositories.SurveyRepository;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author DELL
 */
@Repository
@Transactional
public class SurveyQuestionRepositoryImpl implements SurveyQuestionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Surveyquestion> getSurveyQuestions(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        String hql = "FROM Surveyquestion sq WHERE 1=1";

        if (params.containsKey("surveyId") && !params.get("surveyId").equals("")) {
            hql += " AND sq.surveyId.id = :surveyId";
        }

        if (params.containsKey("questionType") && !params.get("questionType").equals("")) {
            hql += " AND sq.questionType LIKE :questionType";
        }

        Query query = s.createQuery(hql);

        if (params.containsKey("surveyId") && !params.get("surveyId").equals("")) {
            query.setParameter("surveyId", Integer.parseInt(params.get("surveyId")));
        }

        if (params.containsKey("questionType") && !params.get("questionType").isEmpty()) {
            query.setParameter("questionType", "%" + params.get("questionType") + "%");
        }

        return query.getResultList();
    }

    @Override
    public int addOrUpdate(Surveyquestion surveyQuestion) {
        Session s = factory.getObject().getCurrentSession();

        if (surveyQuestion.getId() != null) {
            s.update(surveyQuestion);
            return surveyQuestion.getId();
        } else {
            return (int) s.save(surveyQuestion);
        }
    }

    @Override
    public Surveyquestion getSurveyQuestionById(int id) {
        Session s = factory.getObject().getCurrentSession();

        return s.get(Surveyquestion.class, id);
    }

    @Override
    public void deleteSurveyQuestion(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
