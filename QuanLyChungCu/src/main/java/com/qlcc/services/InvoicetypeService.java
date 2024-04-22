/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.services;

import com.qlcc.pojo.Invoicetype;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface InvoicetypeService {

    List<Invoicetype> getInvoicetypes();
    
    Invoicetype getInvoicetypeById(int id);

}
