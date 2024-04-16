/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories;

import com.qlcc.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface UserRepository {

    List<User> getUsers(Map<String, String> params);

    void addOrUpdate(User u);

    User getUserById(int id);

    void deleteUser(int id) throws Exception;
    
    void blockUser(int id);

    boolean isUsernameExists(String username);

    boolean isEmailExists(String email);

    boolean isPhoneExists(String phone);

    int getTotalUsers();
    
    User getUserByUsername(String username);
}
