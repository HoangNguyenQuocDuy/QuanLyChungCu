/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories;

import com.qlcc.pojo.Userorder;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface UserOrderRepository {

    List<Userorder> getOrders(Map<String, String> params) throws Exception;

    void addOrUpdate(Userorder order);

    Userorder getOrderById(int id);

    void deleteOrder(int id) throws Exception;
    
    int getTotalOrders();
}
