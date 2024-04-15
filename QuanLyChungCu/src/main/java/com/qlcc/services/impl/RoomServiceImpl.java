/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Room;
import com.qlcc.repositories.RoomRepository;
import com.qlcc.services.RoomService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> getRooms(Map<String, String> params) {
        return roomRepository.getRooms(params);
    }

    @Override
    public void addOrUpdate(Room r) {
        roomRepository.addOrUpdate(r);
    }

    @Override
    public Room getRoomById(int id) {
        return roomRepository.getRoomById(id);
    }

    @Override
    public void deleteRoom(int id) {
        roomRepository.deleteRoom(id);
    }

    @Override
    public int getTotalRooms() {
        return roomRepository.getTotalRooms();
    }
}
