/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.Locker;
import com.qlcc.repositories.LockerRepository;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.hibernate.Session;
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
public class LockerRepositoryImpl implements LockerRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment env;

    @Override
    public List<Locker> getLockers(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        String hql = "FROM Locker l WHERE 1=1";

        if (params.containsKey("status") && !params.get("status").equals("")) {
            hql += " AND l.status = :status";
        }

        int page = 1;
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }

        Query query = s.createQuery(hql);

        if (params.containsKey("status") && !params.get("status").equals("")) {
            query.setParameter("status", params.get("status"));
        }

        int pageSize = Integer.parseInt(env.getProperty("user.pageSize").toString());

        if (!params.containsKey("list")) {
            int startPosition = (page - 1) * pageSize;
            query.setFirstResult(startPosition);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    @Override
    public void addOrUpdate(Locker locker) {
        Session s = factory.getObject().getCurrentSession();
        if (locker.getId() != null) {
            s.update(locker);
        } else {
            s.save(locker);
        }
    }

    @Override
    public Locker getLockerById(int id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Locker.class, id);
    }

    @Override
    public void deleteLocker(int id) throws Exception {
        Session s = factory.getObject().getCurrentSession();
        Locker locker = getLockerById(id);

        Query userQuery = s.createQuery("SELECT COUNT(*) FROM User WHERE locker = :locker");
        userQuery.setParameter("locker", locker);
        int userCount = ((Number) userQuery.getSingleResult()).intValue();

        Query orderQuery = s.createQuery("SELECT COUNT(*) FROM Userorder "
                + "WHERE status='Pending' AND lockerId = :locker");
        orderQuery.setParameter("locker", locker);
        int orderCount = ((Number) orderQuery.getSingleResult()).intValue();

        if (userCount > 0) {
            throw new Exception("Cannot delete locker because it is referenced by user.");
        } else if (orderCount > 0) {
            throw new Exception("Cannot delete locker because it has orders with status is Pending.");
        } else {
            Query deleteQuery = s.createQuery("DELETE FROM Userorder "
                    + "WHERE status = 'Received' AND lockerId = :locker");
            deleteQuery.setParameter("locker", locker);
            deleteQuery.executeUpdate();

            s.delete(locker);
        }
    }

    @Override
    public int getTotalLockers() {
        Session s = factory.getObject().getCurrentSession();
        Query query = s.createQuery("SELECT COUNT(*) FROM Locker");

        return ((Number) query.getSingleResult()).intValue();
    }

}
