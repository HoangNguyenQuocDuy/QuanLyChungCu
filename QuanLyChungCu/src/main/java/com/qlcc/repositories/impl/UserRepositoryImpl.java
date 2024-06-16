/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.User;
import com.qlcc.repositories.UserRepository;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
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
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public List<User> getUsers(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        String hql = "FROM User u WHERE 1=1";

        if (params.containsKey("phone") && !params.get("phone").equals("")) {
            hql += " AND u.phone LIKE :phone";
        }
        if (params.containsKey("roleName") && !params.get("roleName").equals("")) {
            hql += " AND u.roleName = :roleName";
        }
        if (params.containsKey("email") && !params.get("email").equals("")) {
            hql += " AND u.email LIKE :email";
        }
        if (params.containsKey("username") && !params.get("username").equals("")) {
            hql += " AND u.username LIKE :username";
        }
        if (params.containsKey("status") && !params.get("status").equals("")) {
            hql += " AND u.status = :status";
        }
        if (params.containsKey("roomName") && !params.get("roomName").equals("")) {
            hql += " AND u.room.name LIKE :roomName";
        }

        int page = 1;
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }

        Query query = s.createQuery(hql);

        if (params.containsKey("phone") && !params.get("phone").isEmpty()) {
            query.setParameter("phone", "%" + params.get("phone") + "%");
        }
        if (params.containsKey("email") && !params.get("email").isEmpty()) {
            query.setParameter("email", "%" + params.get("email") + "%");
        }
        if (params.containsKey("username") && !params.get("username").isEmpty()) {
            query.setParameter("username", "%" + params.get("username") + "%");
        }
        if (params.containsKey("status") && !params.get("status").isEmpty()) {
            query.setParameter("status", params.get("status"));
        }
        if (params.containsKey("roomName") && !params.get("roomName").isEmpty()) {
            query.setParameter("roomName", "%" + params.get("roomName") + "%");
        }
        if (params.containsKey("roleName") && !params.get("roleName").isEmpty()) {
            query.setParameter("roleName", params.get("roleName"));
        }

        int pageSize = Integer.parseInt(env.getProperty("user.pageSize").toString());

        int startPosition = (page - 1) * pageSize;
        query.setFirstResult(startPosition);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public void addOrUpdate(User u) {
        Session s = factory.getObject().getCurrentSession();
        if (u.getId() != null) {
            s.update(u);
        } else {
            s.save(u);
        }
    }

    @Override
    public User getUserById(int id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(User.class, id);
    }

    @Override
    public void deleteUser(int id) {
        Session s = factory.getObject().getCurrentSession();
        User user = getUserById(id);
        s.delete(user);
    }

    //Lặp code - sửa sau
    @Override
    public boolean isUsernameExists(String username) {
        Session s = factory.getObject().getCurrentSession();
        Query query = s.createQuery("FROM User WHERE username = :username", User.class);
        query.setParameter("username", username);
        List<User> result = query.getResultList();

        if (result == null) {
            return false;
        }

        return result.size() > 0;
    }

    @Override
    public boolean isEmailExists(String email) {
        Session s = factory.getObject().getCurrentSession();
        Query query = s.createQuery("FROM User WHERE email = :email", User.class);
        query.setParameter("email", email);
        List<User> result = query.getResultList();

        if (result == null) {
            return false;
        }

        return result.size() > 0;
    }

    @Override
    public boolean isPhoneExists(String phone) {
        Session s = factory.getObject().getCurrentSession();
        Query query = s.createQuery("FROM User WHERE phone = :phone", User.class);
        query.setParameter("phone", phone);
        List<User> result = query.getResultList();

        if (result == null) {
            return false;
        }

        return result.size() > 0;
    }

    @Override
    public int getTotalUsers() {
        Session s = factory.getObject().getCurrentSession();
        Query query = s.createQuery("SELECT COUNT(*) FROM User");

        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public User getUserByUsername(String username) {
        Session s = factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User WHERE username=:username");
        q.setParameter("username", username);

        User user = null;
        try {
            user = (User) q.getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void blockUser(int id) {
        Session s = factory.getObject().getCurrentSession();
        User user = getUserById(id);

        if (user != null && user.getStatus().equals("Active")) {
            user.setStatus("Block");
            user.setRoom(null);
            user.setLocker(null);

            s.update(user);
        }
    }

}
