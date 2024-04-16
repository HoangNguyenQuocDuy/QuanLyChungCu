/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

import com.qlcc.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author DELL
 */
public interface UserService extends UserDetailsService{

    List<User> getUsers(Map<String, String> params);

    void addOrUpdate(User u) throws Exception;

    User getUserById(int id);

    void deleteUser(int id);

    boolean isUsernameExists(String username);

    boolean isEmailExists(String email);

    boolean isPhoneExists(String phone);
    
    int getTotalUsers();
    
    User getUserByUsername(String username);
    
    void blockUser(int id) throws Exception;
}
