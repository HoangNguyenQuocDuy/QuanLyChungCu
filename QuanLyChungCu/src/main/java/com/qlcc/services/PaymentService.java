/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

import com.qlcc.pojo.Payment;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface PaymentService {

    void addPayment(Payment payment);

    List<Payment> getPayments(Map<String, String> params) throws Exception;
}
