/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.qlcc.pojo.Room;
import com.qlcc.repositories.RoomRepository;
import com.qlcc.services.RoomService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Room> getRooms(Map<String, String> params) {
        return roomRepository.getRooms(params);
    }

    @Override
    public void addOrUpdate(Room r) {
        if (r.getFile() != null && !r.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(r.getFile().getBytes(), ObjectUtils.asMap("folder", "quanlychungcu"));
                r.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(RoomServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        roomRepository.addOrUpdate(r);
    }

    @Override
    public Room getRoomById(int id) {
        return roomRepository.getRoomById(id);
    }

    @Override
    public void deleteRoom(int id) throws Exception{
        roomRepository.deleteRoom(id);
    }

    @Override
    public int getTotalRooms() {
        return roomRepository.getTotalRooms();
    }

    @Override
    public boolean isRoomNameExists(String roomName) {
        return roomRepository.isRoomNameExists(roomName);
    }
}
