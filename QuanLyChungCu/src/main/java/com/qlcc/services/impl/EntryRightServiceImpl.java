/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.EntryRight;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qlcc.repositories.EntryRightRepository;
import com.qlcc.services.EntryRightService;

/**
 *
 * @author DELL
 */
@Service
public class EntryRightServiceImpl implements  EntryRightService{
    
    @Autowired
    private EntryRightRepository parkingRightRepo;

    @Override
    public void addOrUpdate(EntryRight pr) {
        parkingRightRepo.addOrUpdate(pr);
    }

    @Override
    public List<EntryRight> getEntryRight(Map<String, String> params) {
        return parkingRightRepo.getEntryRight(params);
    }

    @Override
    public EntryRight getEntryRightById(int id) {
        return parkingRightRepo.getEntryRightById(id);
    }

    @Override
    public void deleteEntryRight(int id) {
        parkingRightRepo.deleteEntryRight(id);
    }

    @Override
    public int getTotalEntryRights() {
        return parkingRightRepo.getTotalEntryRights();
    }
    
}
