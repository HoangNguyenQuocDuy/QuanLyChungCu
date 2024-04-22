/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.Userorder;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.qlcc.repositories.UserOrderRepository;
import javax.persistence.Query;

/**
 *
 * @author DELL
 */
@Repository
@Transactional
@PropertySource("classpath:application.properties")
public class UserOrderRepositoryImpl implements UserOrderRepository {
    
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Autowired
    private Environment env;
    
    @Override
    public List<Userorder> getOrders(Map<String, String> params) throws Exception {
        Session s = factory.getObject().getCurrentSession();
        String hql = "FROM Userorder uord WHERE 1=1";
        
        if (params.containsKey("status") && !params.get("status").equals("")) {
            hql += " AND uord.status LIKE :status";
        }
        
        if (params.containsKey("lockerId") && !params.get("lockerId").equals("")) {
            hql += " AND uord.lockerId.id = :lockerId";
        }
        
        int page = 1;
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }
//
        Query query = s.createQuery(hql);
//
        if (params.containsKey("status") && !params.get("status").equals("")) {
            query.setParameter("status", "%" + params.get("status") + "%");
        }
        
        if (params.containsKey("lockerId") && !params.get("lockerId").equals("")) {
            query.setParameter("lockerId", Integer.parseInt(params.get("lockerId")));
        }
        
        int pageSize = Integer.parseInt(env.getProperty("user.pageSize").toString());
        
        try {
            if (!params.containsKey("list")) {
                int startPosition = (page - 1) * pageSize;
                query.setFirstResult(startPosition);
                query.setMaxResults(pageSize);
            }
            
            return query.getResultList();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        
    }
    
    @Override
    public void addOrUpdate(Userorder order) {
        Session s = factory.getObject().getCurrentSession();
        
        if (order.getId() != null) {
            s.update(order);
        } else {
            s.save(order);
        }
    }
    
    @Override
    public Userorder getOrderById(int id) {
        Session s = factory.getObject().getCurrentSession();
        
        return s.get(Userorder.class, id);
    }
    
    @Override
    public void deleteOrder(int id) throws Exception {
        Session s = factory.getObject().getCurrentSession();
        Userorder order = this.getOrderById(id);
        
        s.delete(order);
    }
    
    @Override
    public int getTotalOrders() {
        Session s = factory.getObject().getCurrentSession();
        Query query = s.createQuery("SELECT COUNT(*) FROM Userorder");
        
        return ((Number) query.getSingleResult()).intValue();
    }
    
}
