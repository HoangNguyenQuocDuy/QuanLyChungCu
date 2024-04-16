/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories;

import com.qlcc.pojo.Room;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface RoomRepository {

    List<Room> getRooms(Map<String, String> params);

    void addOrUpdate(Room r);

    Room getRoomById(int id);

    void deleteRoom(int id) throws Exception;

    int getTotalRooms();
    
    boolean isRoomNameExists(String roomName);
}
