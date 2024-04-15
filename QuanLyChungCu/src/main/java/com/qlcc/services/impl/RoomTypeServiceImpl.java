/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Roomtype;
import com.qlcc.repositories.RoomTypeRepository;
import com.qlcc.services.RoomTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class RoomTypeServiceImpl implements RoomTypeService{
    
    @Autowired
    private RoomTypeRepository roomTypeRepo;

    @Override
    public List<Roomtype> getRoomtypes() {
        return roomTypeRepo.getRoomtypes();
    }
    
}
