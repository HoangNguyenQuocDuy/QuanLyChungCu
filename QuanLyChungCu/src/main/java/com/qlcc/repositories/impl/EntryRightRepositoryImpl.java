/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.EntryRight;
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
import com.qlcc.repositories.EntryRightRepository;

/**
 *
 * @author DELL
 */
@Transactional
@Repository
@PropertySource("classpath:application.properties")
public class EntryRightRepositoryImpl implements EntryRightRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment env;

    @Override
    public void addOrUpdate(EntryRight pr) {
        Session s = factory.getObject().getCurrentSession();
        Date currentDate = new Date();

        if (pr.getId() == null) {
            pr.setStatus("Pending");
            pr.setCreatedAt(currentDate);
            s.save(pr);
        } else {
            pr.setUpdatedAt(currentDate);
            s.update(pr);
        }
    }

    @Override
    public List<EntryRight> getEntryRight(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        String hql = "FROM EntryRight en WHERE 1=1";

        if (params.containsKey("roomId") && !params.get("roomId").equals("")) {
            hql += " AND en.relativeId.userId.room.id = :roomId";
        }
        if (params.containsKey("status") && !params.get("status").equals("")) {
            hql += " AND en.status = :status";
        }

        int page = 1;

        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }
        Query query = s.createQuery(hql);

        if (params.containsKey("roomId") && !params.get("roomId").isEmpty()) {
            query.setParameter("roomId", Integer.valueOf(params.get("roomId")));
        }
        if (params.containsKey("status") && !params.get("status").isEmpty()) {
            query.setParameter("status", params.get("status"));
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
    public EntryRight getEntryRightById(int id) {
        Session s = factory.getObject().getCurrentSession();

        return s.get(EntryRight.class, id);
    }

    @Override
    public void deleteEntryRight(int id) {
        Session s = factory.getObject().getCurrentSession();
        EntryRight pr = getEntryRightById(id);

        s.delete(pr);
    }

    @Override
    public int getTotalEntryRights() {
        Session s = factory.getObject().getCurrentSession();
        Query query = s.createQuery("SELECT COUNT(*) FROM EntryRight");

        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public List<EntryRight> getEntryRights(int customerId) {
        Session s = factory.getObject().getCurrentSession();
        javax.persistence.Query query = s.createQuery(""
                + "FROM EntryRight er WHERE er.relativeId.userId.id =: customerId");

        query.setParameter("customerId", customerId);

        return query.getResultList();
    }

}
