/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.Invoicetype;
import com.qlcc.repositories.InvoicetypeRepository;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author DELL
 */
@Repository
@Transactional
public class InvoicetypeRepositoryImpl implements InvoicetypeRepository{
    
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Invoicetype> getInvoicetypes() {
        Session s = factory.getObject().getCurrentSession();
        
        return s.createQuery("FROM Invoicetype", Invoicetype.class).list();
    }

    @Override
    public Invoicetype getInvoicetypeById(int id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Invoicetype.class, id);
    }
    
}
