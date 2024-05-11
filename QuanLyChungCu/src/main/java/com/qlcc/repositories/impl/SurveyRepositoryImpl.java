/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.Survey;
import com.qlcc.repositories.SurveyRepository;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author DELL
 */
@Repository
@Transactional
@PropertySource("classpath:application.properties")
public class SurveyRepositoryImpl implements SurveyRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment env;

    @Override
    public List<Survey> getSurveys(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();

        CriteriaQuery<Survey> criteriaQuery = criteriaBuilder.createQuery(Survey.class);

        Root<Survey> surveyRoot = criteriaQuery.from(Survey.class);

        Predicate predicate = criteriaBuilder.conjunction();

        if (params.containsKey("createdDate")) {
            String createdDate = params.get("createdDate");

            Predicate dateCondition = (Predicate) criteriaBuilder.equal(
                    surveyRoot.get("createdAt").as(String.class), createdDate
            );

            predicate = criteriaBuilder.and(predicate, dateCondition);
        }
        criteriaQuery.where(predicate);

        Query<Survey> q = s.createQuery(criteriaQuery);

        if (!params.containsKey("list")) {
            int page = 1;
            int pageSize = Integer.parseInt(env.getProperty("user.pageSize").toString());
            int startPosition = (page - 1) * pageSize;
            q.setFirstResult(startPosition);
            q.setMaxResults(pageSize);
            if (params.containsKey("page")) {
                page = Integer.parseInt(params.get("page"));
            }
            int offset = (page - 1) * pageSize;

            q.setFirstResult(offset);
            q.setMaxResults(pageSize);
        }

        return q.getResultList();
    }

    @Override
    public int addOrUpdate(Survey survey) {
        Session s = factory.getObject().getCurrentSession();

        if (survey.getId() != null) {
            s.update(survey);
            return survey.getId();
        } else {
            return (int) s.save(survey);
        }
    }

    @Override
    public Survey getSurveyById(int id) {
        Session s = factory.getObject().getCurrentSession();

        return s.get(Survey.class, id);
    }

    @Override
    public int getTotalSurveys() {
        Session s = factory.getObject().getCurrentSession();
        Query query = s.createQuery("SELECT COUNT(*) FROM Survey");

        return ((Number) query.getSingleResult()).intValue();
    }

}
