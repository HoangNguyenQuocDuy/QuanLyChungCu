/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.Feedback;
import com.qlcc.repositories.FeedbackRepository;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
@Transactional
@Repository
@PropertySource("classpath:application.properties")
public class FeedbackRepositoryImpl implements FeedbackRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment env;

    @Override
    public void addOrUpdate(Feedback fb) {
        Session s = factory.getObject().getCurrentSession();
        Date currentDate = new Date();

        if (fb.getId() == null) {
            fb.setCreatedAt(currentDate);
            s.save(fb);
        } else {
            fb.setUpdatedAt(currentDate);
            s.update(fb);
        }
    }

    @Override
    public List<Feedback> getFeedbacks(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        String hql = "FROM Feedback fb WHERE 1=1";

        if (params.containsKey("userId") && !params.get("userId").equals("")) {
            hql += " AND fb.userId.id = :userId";
        }
        if (params.containsKey("roomId") && !params.get("roomId").equals("")) {
            hql += " AND fb.userId.room.id = :roomId";
        }

        int page = 1;

        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }
        Query query = s.createQuery(hql);

        if (params.containsKey("userId") && !params.get("userId").isEmpty()) {
            query.setParameter("userId", Integer.valueOf(params.get("userId")));
        }
        if (params.containsKey("roomId") && !params.get("roomId").equals("")) {
            query.setParameter("roomId", Integer.valueOf(params.get("roomId")));
        }

        int pageSize = Integer.parseInt(env.getProperty("user.pageSize"));

        if (!params.containsKey("list")) {
            int startPosition = (page - 1) * pageSize;
            query.setFirstResult(startPosition);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    @Override
    public Feedback getFeedbackById(int id) {
        Session s = factory.getObject().getCurrentSession();

        return s.get(Feedback.class, id);
    }

    @Override
    public void deleteFeedback(int id) {
        Session s = factory.getObject().getCurrentSession();
        Feedback fb = getFeedbackById(id);
        
        s.delete(fb);
    }

    @Override
    public int getTotalFeedback() {
        Session s = factory.getObject().getCurrentSession();
        Query query = s.createQuery("SELECT COUNT(*) FROM Feedback");

        return ((Number) query.getSingleResult()).intValue();
    }
}
