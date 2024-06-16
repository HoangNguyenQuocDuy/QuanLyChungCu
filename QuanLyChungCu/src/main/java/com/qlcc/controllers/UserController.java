/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.controllers;

import com.qlcc.pojo.Locker;
import com.qlcc.pojo.Room;
import com.qlcc.pojo.User;
import com.qlcc.services.EmailService;
import com.qlcc.services.LockerService;
import com.qlcc.services.RoomService;
import com.qlcc.services.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author DELL
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private LockerService lockerService;

    @Autowired
    private Environment env;

    @GetMapping("/users")
    public String createView(Model model, @RequestParam Map<String, String> params) {

        int totalUsers = userService.getTotalUsers();
        int pageSize = Integer.parseInt(env.getProperty("user.pageSize"));
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);
        params.put("roleName", "ROLE_CUSTOMER");
        
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("users", userService.getUsers(params));
        return "user_list";
    }

    @GetMapping("/users/")
    public String addUserView(Model model) {
        model.addAttribute("user", new User());
        Map<String, String> params = new HashMap<>();
        params.put("status", "Blank");
        params.put("list", "true");

        List<Room> rooms = roomService.getRooms(params);
        List<Locker> lockers = lockerService.getLockers(params);

        model.addAttribute("rooms", rooms);
        model.addAttribute("lockers", lockers);

        return "user";
    }

    @GetMapping("/users/{userId}")
    public String updateRoomView(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("user", userService.getUserById(userId));
        Map<String, String> params = new HashMap<>();
        params.put("status", "Blank");
        params.put("list", "true");

        List<Room> rooms = roomService.getRooms(params);
        List<Locker> lockers = lockerService.getLockers(params);

        model.addAttribute("rooms", rooms);
        model.addAttribute("lockers", lockers);

        return "user";
    }

    @PostMapping("/users/")
    public String addUserProcess(Model model, @ModelAttribute(value = "user") @Valid User user,
            BindingResult rs) {
        if (!rs.hasErrors() || user.getId() != null) {
            try {
                boolean flag = false;
                if (user.getId() == null) {
                    flag = true;
                }

                String initPassword = user.getPassword();

                userService.addOrUpdate(user);

                Room room = roomService.getRoomById(user.getRoom().getId());
                if (room != null) {
                    room.setStatus("Rented");
                    roomService.addOrUpdate(room);
                } else {
                    throw new Exception("Room not found!");
                }

                Locker locker = lockerService.getLockerById(user.getLocker().getId());
                if (locker != null) {
                    locker.setStatus("Using");
                    lockerService.addOrUpdate(locker);
                } else {
                    throw new Exception("Locker not found!");
                }

                if (flag) {
                    String subject = "DV APARTMENT - REGISTRATION";
                    String message = "Your account:\n - username: " + user.getUsername()
                            + "\n - password: " + initPassword
                            + "\nPlease log in and change your password";

                    emailService.sendMail(user.getEmail(), subject, message);
                }

                return "redirect:/users";
            } catch (Exception ex) {
                model.addAttribute("errMsg", ex.toString());
                System.err.println("Err: " + ex.getMessage());
            }
        }

        return "user";
    }
}
