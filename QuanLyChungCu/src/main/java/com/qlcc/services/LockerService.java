/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

import com.qlcc.pojo.Locker;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface LockerService {

    List<Locker> getLockers(Map<String, String> params);
    
    void addOrUpdate(Locker locker);

    Locker getLockerById(int id);

    void deleteLocker(int id) throws Exception;

    int getTotalLockers();

}
