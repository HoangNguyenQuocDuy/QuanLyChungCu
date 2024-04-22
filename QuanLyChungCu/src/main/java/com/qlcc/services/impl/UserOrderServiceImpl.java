/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.qlcc.pojo.Locker;
import com.qlcc.pojo.Userorder;
import com.qlcc.services.LockerService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qlcc.repositories.UserOrderRepository;
import com.qlcc.services.UserOrderService;

/**
 *
 * @author DELL
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {
    
    @Autowired
    private UserOrderRepository orderRepo;
    
    @Autowired
    private LockerService lockerService;
    
    @Autowired
    private Cloudinary cloudinary;
    
    @Override
    public List<Userorder> getOrders(Map<String, String> params) throws Exception {
        try {
            return orderRepo.getOrders(params);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    @Override
    public void addOrUpdate(Userorder order) {
        if (order.getFile() != null && !order.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(order.getFile().getBytes(), ObjectUtils.asMap("folder", "quanlychungcu"));
                order.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(RoomServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (order.getId() == null) {
            order.setStatus("Pending");
            order.setCreatedAt(new Date());
        } else {
            order.setUpdatedAt(new Date());
        }
        
        orderRepo.addOrUpdate(order);
    }
    
    @Override
    public Userorder getOrderById(int id) {
        return orderRepo.getOrderById(id);
    }
    
    @Override
    public void deleteOrder(int id) throws Exception {
        Userorder order = this.getOrderById(id);
        
        if (order.getStatus().equals("Received")) {
            if (order.getLockerId() != null) {
                Locker locker = lockerService.getLockerById(order.getLockerId().getId());
                locker.getUserorderSet().remove(order);
                
                lockerService.addOrUpdate(locker);
            }
            
        } else {
            throw new Exception("Order is not in received status, cannot be deleted.");
        }
    }
    
    @Override
    public int getTotalOrders() {
        return orderRepo.getTotalOrders();
    }
    
}
