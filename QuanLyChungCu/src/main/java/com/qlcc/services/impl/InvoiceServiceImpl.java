/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services.impl;

import com.qlcc.pojo.Invoice;
import com.qlcc.repositories.InvoiceRepository;
import com.qlcc.services.InvoiceService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepo;

    @Override
    public List<Invoice> getInvoices(Map<String, String> params) {
        return invoiceRepo.getInvoices(params);
    }

    @Override
    public void addOrUpdate(Invoice invoice) {
        Date currentDate = new Date();

        if (invoice.getId() == null) {
            invoice.setCreatedAt(currentDate);

            if (invoice.getDueDate() == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DAY_OF_MONTH, 5);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 0);

                invoice.setDueDate(new Date(calendar.getTimeInMillis()));
            }

            invoice.setStatus("Unpaid");
        } else {
            invoice.setUpdatedAt(currentDate);
        }

        invoiceRepo.addOrUpdate(invoice);
    }

    @Override
    public Invoice getInvoiceById(int id) {
        return invoiceRepo.getInvoiceById(id);
    }

    @Override
    public void deleteInvoice(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getTotalInvoices() {
        return invoiceRepo.getTotalInvoices();
    }
}
