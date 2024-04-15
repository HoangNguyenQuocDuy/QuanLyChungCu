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

        if (params.containsKey("type")) {
            hql += " AND r.roomType.type= :type";
        }

        int page = 1;

        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }
        Query query = s.createQuery(hql);

        if (params.containsKey("type")) {
            query.setParameter("type", params.get("type"));
        }
        int pageSize = Integer.parseInt(env.getProperty("user.pageSize"));

        int startPosition = (page - 1) * pageSize;
        query.setFirstResult(startPosition);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public void addOrUpdate(Room r) {
        Session s = factory.getObject().getCurrentSession();
        if (r.getId() != null) {
            s.update(r);
        } else {
            r.setStatus("blank");
            s.save(r);
        }
    }

    @Override
    public Room getRoomById(int id) {
        Session s = factory.getObject().getCurrentSession();

        return s.get(Room.class, id);
    }

    @Override
    public void deleteRoom(int id) {
        Session s = factory.getObject().getCurrentSession();
        Room r = getRoomById(id);
        s.delete(r);
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
        
        if (result == null){
            return false;
        }
        
        return result.size() > 0;
    }
}
