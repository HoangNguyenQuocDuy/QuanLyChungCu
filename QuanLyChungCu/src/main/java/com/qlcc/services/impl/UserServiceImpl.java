/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.qlcc.pojo.Locker;
import com.qlcc.pojo.Room;
import com.qlcc.pojo.User;
import com.qlcc.repositories.UserRepository;
import com.qlcc.services.LockerService;
import com.qlcc.services.RoomService;
import com.qlcc.services.UserService;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service()
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoomService roomService;
    @Autowired
    private LockerService lockerService;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<User> getUsers(Map<String, String> params) {
        return userRepo.getUsers(params);
    }

    @Override
    public void addOrUpdate(User user) throws Exception {

        if (user.getId() == null) {
            user.setStatus("Active");
            user.setRoleName("CUSTOMER");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else if (user.getStatus().equals("Block")) {
            user.setStatus("Active");
        } else if (user.getFile() != null && !user.getFile().isEmpty()) {
            String storedPassword = userRepo.getUserById(user.getId()).getPassword();

            if (!passwordEncoder.matches(user.getPassword(), storedPassword)) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            try {
                Map res = cloudinary.uploader().upload(user.getFile().getBytes(), ObjectUtils.asMap("folder", "quanlychungcu"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    @Override
    public User getUserByUsername(String username) {
        User user = userRepo.getUserByUsername(username);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getRoleName()));

        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public void blockUser(int id) throws Exception {
        User user = getUserById(id);

        if (user == null) {
            throw new Exception("User not found!");
        }

        Room room = roomService.getRoomById(user.getRoom().getId());
        Locker locker = lockerService.getLockerById(user.getLocker().getId());

        if (room == null) {
            throw new Exception("Room not found!");
        }

        if (locker == null) {
            throw new Exception("Locker not found!");
        }

        room.setStatus("Blank");
        roomService.addOrUpdate(room);
        locker.setStatus("Blank");
        lockerService.addOrUpdate(locker);

        userRepo.blockUser(id);
    }

}
