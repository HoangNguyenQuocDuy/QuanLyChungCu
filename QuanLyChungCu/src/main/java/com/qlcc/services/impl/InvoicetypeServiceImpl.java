/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Invoicetype;
import com.qlcc.repositories.InvoicetypeRepository;
import com.qlcc.services.InvoicetypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class InvoicetypeServiceImpl implements InvoicetypeService{
    
    @Autowired
    private InvoicetypeRepository invoicetypeRepo;

    @Override
    public List<Invoicetype> getInvoicetypes() {
        return invoicetypeRepo.getInvoicetypes();
    }

    @Override
    public Invoicetype getInvoicetypeById(int id) {
        return invoicetypeRepo.getInvoicetypeById(id);
    }
    
}
