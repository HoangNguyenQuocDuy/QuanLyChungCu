/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Payment;
import com.qlcc.repositories.PaymentRepository;
import com.qlcc.services.PaymentService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class PaymenServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepo;

    @Override
    public void addPayment(Payment payment) {
        paymentRepo.addPayment(payment);
    }

    @Override
    public List<Payment> getPayments(Map<String, String> params) throws Exception {
        return paymentRepo.getPayments(params);
    }

}
