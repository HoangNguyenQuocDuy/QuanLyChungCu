/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.Surveyoption;
import com.qlcc.repositories.SurveyOptionRepository;
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
public class SurveyOptionRepositoryImpl implements SurveyOptionRepository{
    
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Surveyoption> getSurveyOptions(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        String hql = "FROM Surveyoption so WHERE 1=1";

        if (params.containsKey("questionId") && !params.get("questionId").equals("")) {
            hql += " AND so.questionId.id = :questionId";
        }
        
        if (params.containsKey("surveyId") && !params.get("surveyId").equals("")) {
            hql += " AND so.questionId.surveyId.id = :surveyId";
        }

        Query query = s.createQuery(hql);

        if (params.containsKey("questionId") && !params.get("questionId").equals("")) {
            query.setParameter("questionId", Integer.valueOf(params.get("questionId")));
        }
        
        if (params.containsKey("surveyId") && !params.get("surveyId").equals("")) {
            query.setParameter("surveyId", Integer.valueOf(params.get("surveyId")));
        }

        return query.getResultList();
    }

    @Override
    public int addOrUpdate(Surveyoption surveyOption) {
        Session s = factory.getObject().getCurrentSession();

        if (surveyOption.getId() != null) {
            s.update(surveyOption);
            return surveyOption.getId();
        } else {
            return (int) s.save(surveyOption);
        }
    }

    @Override
    public Surveyoption getSurveyOptionById(int id) {
        Session s = factory.getObject().getCurrentSession();

        return s.get(Surveyoption.class, id);
    }

    @Override
    public void deleteSurveyOption(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
