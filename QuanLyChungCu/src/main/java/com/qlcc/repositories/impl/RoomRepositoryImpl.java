/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.Room;
import com.qlcc.repositories.RoomRepository;
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
public class RoomRepositoryImpl implements RoomRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public List<Room> getRooms(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        String hql = "FROM Room r WHERE 1=1";

        if (params.containsKey("type") && !params.get("type").equals("")) {
            hql += " AND r.roomtype.type LIKE :type";
        }
        
        if (params.containsKey("name") && !params.get("name").equals("")) {
            hql += " AND r.name LIKE :name";
        }
        
        if (params.containsKey("status") && !params.get("status").equals("")) {
            hql += " AND r.status LIKE :status";
        }

        int page = 1;

        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }
        Query query = s.createQuery(hql);

        if (params.containsKey("type") && !params.get("type").isEmpty()) {
            query.setParameter("type", "%" + params.get("type") + "%");
        }
        if (params.containsKey("status") && !params.get("status").isEmpty()) {
            query.setParameter("status", "%" + params.get("status") + "%");
        }
        if (params.containsKey("name") && !params.get("name").isEmpty()) {
            query.setParameter("name", "%" + params.get("name") + "%");
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
    public void addOrUpdate(Room r) {
        Session s = factory.getObject().getCurrentSession();
        if (r.getId() != null) {
            s.update(r);
        } else {
            r.setStatus("Blank");
            s.save(r);
        }
    }

    @Override
    public Room getRoomById(int id) {
        Session s = factory.getObject().getCurrentSession();

        return s.get(Room.class, id);
    }

    @Override
    public void deleteRoom(int id) throws Exception {
        Session s = factory.getObject().getCurrentSession();
        Room room = getRoomById(id);

        Query invoiceQuery = s.createQuery("SELECT COUNT(*) FROM Invoice WHERE room = :room");
        invoiceQuery.setParameter("room", room);
        int invoiceCount = ((Number) invoiceQuery.getSingleResult()).intValue();

        if (invoiceCount > 0) {
            throw new Exception("Cannot delete room because it is referenced by invoices.");
        }

        Query userQuery = s.createQuery("SELECT COUNT(*) FROM User WHERE room = :room");
        userQuery.setParameter("room", room);
        int userCount = ((Number) userQuery.getSingleResult()).intValue();

        if (userCount > 0) {
            throw new Exception("Cannot delete room because it is referenced by users.");
        } else {
            s.delete(room);
        }
    }

    @Override
    public int getTotalRooms() {
        Session s = factory.getObject().getCurrentSession();
        Query query = s.createQuery("SELECT COUNT(*) FROM Room");

        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public boolean isRoomNameExists(String roomName) {
        Session s = factory.getObject().getCurrentSession();
        Query query = s.createQuery("FROM Room WHERE name= :name", Room.class);
        query.setParameter("name", roomName);
        List<Room> result = query.getResultList();

        if (result == null) {
            return false;
        }

        return result.size() > 0;
    }
}
