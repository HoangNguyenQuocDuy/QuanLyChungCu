/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.pojo.Locker;
import com.qlcc.services.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author DELL
 */
@RestController
@RequestMapping("/lockers")
public class ApiLockerController {
    
    @Autowired
    private LockerService lockerService;
    
    @DeleteMapping("/{lockerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(Model model, @PathVariable("lockerId") int lockerId) {
        try {
            lockerService.deleteLocker(lockerId);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage(), ex);
        }
    }
    
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addLocker(Model model) {
        try {
            Locker locker = new Locker();
            lockerService.addOrUpdate(locker);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage(), ex);
        }
    }
}
