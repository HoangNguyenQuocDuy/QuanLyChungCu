/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.Surveyresponse;
import com.qlcc.repositories.SurveyResponseRepository;
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
public class SurveyResponseRepositoryImpl implements SurveyResponseRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Surveyresponse> getSurveys(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        String hql = "FROM Surveyresponse sr WHERE 1=1";

        if (params.containsKey("surveyId") && !params.get("surveyId").equals("")) {
            hql += " AND sr.surveyId.id = :surveyId";
        }

        if (params.containsKey("userId") && !params.get("userId").equals("")) {
            hql += " AND sr.userId.id = :userId";
        }

        Query query = s.createQuery(hql);

        if (params.containsKey("surveyId") && !params.get("surveyId").equals("")) {
            query.setParameter("surveyId", Integer.parseInt(params.get("surveyId")));
        }
        if (params.containsKey("userId") && !params.get("userId").equals("")) {
            query.setParameter("userId", Integer.parseInt(params.get("userId")));
        }

        return query.getResultList();
    }

    @Override
    public int addSurveyResponse(Surveyresponse surveyResponse) {
        Session s = factory.getObject().getCurrentSession();
        
        return (int) s.save(surveyResponse);
    }

    @Override
    public Surveyresponse getSurveyResponseById(int id) {
        Session s = factory.getObject().getCurrentSession();

        return s.get(Surveyresponse.class, id);
    }

}
