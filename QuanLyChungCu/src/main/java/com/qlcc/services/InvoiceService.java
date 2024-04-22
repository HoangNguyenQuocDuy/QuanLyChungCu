/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

import com.qlcc.pojo.Invoice;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public interface InvoiceService {

    List<Invoice> getInvoices(Map<String, String> params);

    void addOrUpdate(Invoice invoice);

    Invoice getInvoiceById(int id);

    void deleteInvoice(int id) throws Exception;

    int getTotalInvoices();
}
