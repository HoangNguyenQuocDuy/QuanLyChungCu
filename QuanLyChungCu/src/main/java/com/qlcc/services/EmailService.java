/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

/**
 *
 * @author DELL
 */
public interface EmailService {
    public void sendMail(String to, String subject, String message) throws Exception;
}
