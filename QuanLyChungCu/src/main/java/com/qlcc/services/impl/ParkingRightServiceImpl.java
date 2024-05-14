/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Parkingright;
import com.qlcc.repositories.ParkingRightRepository;
import com.qlcc.services.ParkingRightService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class ParkingRightServiceImpl implements  ParkingRightService{
    
    @Autowired
    private ParkingRightRepository parkingRightRepo;

    @Override
    public void addOrUpdate(Parkingright pr) {
        parkingRightRepo.addOrUpdate(pr);
    }

    @Override
    public List<Parkingright> getParkingRight(Map<String, String> params) {
        return parkingRightRepo.getParkingRight(params);
    }

    @Override
    public Parkingright getParkingRightById(int id) {
        return parkingRightRepo.getParkingRightById(id);
    }

    @Override
    public void deleteParkingRight(int id) {
        parkingRightRepo.deleteParkingRight(id);
    }

    @Override
    public int getTotalParkings() {
        return parkingRightRepo.getTotalParkings();
    }
    
}
