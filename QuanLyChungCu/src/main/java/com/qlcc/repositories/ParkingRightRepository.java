/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories;

import com.qlcc.pojo.ParkingRight;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface ParkingRightRepository {

    void addOrUpdate(ParkingRight pr);

    List<ParkingRight> getParkingRight(Map<String, String> params);

    ParkingRight getParkingRightById(int id);

    void deleteParkingRight(int id);

    int getTotalParkingRights();

    List<ParkingRight> getParkings(int customerId);

}
