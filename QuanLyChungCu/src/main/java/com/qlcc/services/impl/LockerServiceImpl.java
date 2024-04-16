/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Locker;
import com.qlcc.repositories.LockerRepository;
import com.qlcc.services.LockerService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class LockerServiceImpl implements LockerService{

    @Autowired
    private LockerRepository lockerRepo;

    @Override
    public List<Locker> getLockers(Map<String, String> params) {
        return lockerRepo.getLockers(params);
    }

    @Override
    public void addOrUpdate(Locker locker) {
        if (locker.getId() == null) {
            locker.setStatus("Blank");
        }
        lockerRepo.addOrUpdate(locker);
    }

    @Override
    public Locker getLockerById(int id) {
        return lockerRepo.getLockerById(id);
    }

    @Override
    public void deleteLocker(int id) throws Exception {
        lockerRepo.deleteLocker(id);
    }

    @Override
    public int getTotalLockers() {
        return lockerRepo.getTotalLockers();
    }
    
    
    
}

