/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.User;
import com.qlcc.repositories.UserRepository;
import com.qlcc.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<User> getUsers(Map<String, String> params) {
        return userRepo.getUsers(params);
    }

    @Override
    public void addOrUpdate(User user) throws Exception {
        if (user.getId() == null) {
            user.setStatus("Active");
            user.setRoleName("CUSTOMER");
        }

        userRepo.addOrUpdate(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepo.getUserById(id);
    }

    @Override
    public void deleteUser(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isUsernameExists(String username) {
        return userRepo.isUsernameExists(username);
    }

    @Override
    public boolean isEmailExists(String email) {
        return userRepo.isEmailExists(email);
    }

    @Override
    public boolean isPhoneExists(String phone) {
        return userRepo.isPhoneExists(phone);
    }

    @Override
    public int getTotalUsers() {
        return userRepo.getTotalUsers();
    }

}
