/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.ParkingRight;
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
public class ParkingRightServiceImpl implements ParkingRightService{
    
    @Autowired
    private ParkingRightRepository parkingRightRepo;

    @Override
    public void addOrUpdate(ParkingRight pr) {
        parkingRightRepo.addOrUpdate(pr);
    }

    @Override
    public List<ParkingRight> getParkingRight(Map<String, String> params) {
        return parkingRightRepo.getParkingRight(params);
    }

    @Override
    public ParkingRight getParkingRightById(int id) {
        return parkingRightRepo.getParkingRightById(id);
    }

    @Override
    public void deleteParkingRight(int id) {
        parkingRightRepo.deleteParkingRight(id);
    }

    @Override
    public int getTotalParkingRights() {
        return parkingRightRepo.getTotalParkingRights();
    }

    @Override
    public List<ParkingRight> getParkings(int customerId) {
        return parkingRightRepo.getParkings(customerId);
    }
    
}
