/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories;

import com.qlcc.pojo.EntryRight;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface EntryRightRepository {

    void addOrUpdate(EntryRight pr);

    List<EntryRight> getEntryRight(Map<String, String> params);

    EntryRight getEntryRightById(int id);

    void deleteEntryRight(int id);
    
    int getTotalEntryRights();
}