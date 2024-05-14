/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

import com.qlcc.pojo.Relative;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface RelativeService {

    void addOrUpdate(Relative relative);

    List<Relative> getRelative(Map<String, String> params);

    Relative getRelativeById(int id);

    void deleteRelative(int id);
}
