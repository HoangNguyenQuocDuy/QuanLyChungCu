/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories;

import com.qlcc.pojo.Parkingright;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface ParkingRightRepository {

    void addOrUpdate(Parkingright pr);

    List<Parkingright> getParkingRight(Map<String, String> params);

    Parkingright getParkingRightById(int id);

    void deleteParkingRight(int id);
    
    int getTotalParkings();
}
