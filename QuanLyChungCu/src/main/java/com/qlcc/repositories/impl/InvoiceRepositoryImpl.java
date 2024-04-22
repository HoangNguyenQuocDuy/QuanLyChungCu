/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.Invoice;
import com.qlcc.repositories.InvoiceRepository;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author DELL
 */
@Repository
@Transactional
@PropertySource("classpath:application.properties")
public class InvoiceRepositoryImpl implements InvoiceRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment env;

    @Override
    public List<Invoice> getInvoices(Map<String, String> params) {
        Session s = factory.getObject().getCurrentSession();
        String hql = "FROM Invoice i WHERE 1=1";

        if (params.containsKey("status") && !params.get("status").equals("")) {
            hql += " AND i.status = :status";
        }
        if (params.containsKey("invoiceType") && !params.get("invoiceType").equals("")) {
            hql += " AND i.invoiceType.type = :invoiceType";
        }
//        if (params.containsKey("dueDate") && !params.get("dueDate").equals("")) {
//            hql += " AND DATE(i.dueDate) = :dueDate";
//        }
//        if (params.containsKey("fromDate") && !params.get("fromDate").equals("")
//                && params.containsKey("toDate") && !params.get("toDate").equals("")) {
//            hql += " AND DATE(i.createdAt) >= :DATE(fromDate) AND DATE(i.createdAt) <= :DATE(toDate)";
//        }
        if (params.containsKey("room") && !params.get("room").equals("")) {
            hql += " AND i.room.id = :room";
        }

        int page = 1;
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }

        Query query = s.createQuery(hql);

        if (params.containsKey("status") && !params.get("status").equals("")) {
            query.setParameter("status", params.get("status"));
        }
//        if (params.containsKey("dueDate") && !params.get("dueDate").equals("")) {
//            query.setParameter("dueDate", params.get("dueDate"));
//        }
//        if (params.containsKey("fromDate") && !params.get("fromDate").equals("")
//                && params.containsKey("toDate") && !params.get("toDate").equals("")) {
//            query.setParameter("fromDate", params.get("fromDate"));
//            query.setParameter("toDate", params.get("toDate"));
//        }
        if (params.containsKey("room") && !params.get("room").equals("")) {
            query.setParameter("room", Integer.parseInt(params.get("room")));
        }
        if (params.containsKey("invoiceType") && !params.get("invoiceType").equals("")) {
            query.setParameter("invoiceType", params.get("invoiceType"));
        }

        if (!params.containsKey("list")) {
            int pageSize = Integer.parseInt(env.getProperty("user.pageSize").toString());
            int startPosition = (page - 1) * pageSize;
            query.setFirstResult(startPosition);
            query.setMaxResults(pageSize);
        }

        return query.list();
    }

    @Override
    public void addOrUpdate(Invoice invoice) {
        Session s = factory.getObject().getCurrentSession();
        
        if (invoice.getId() != null) {
            s.update(invoice);
        } else {
            s.save(invoice);
        }
    }

    @Override
    public Invoice getInvoiceById(int id) {
        Session s = factory.getObject().getCurrentSession();

        return s.get(Invoice.class, id);
    }

    @Override
    public void deleteInvoice(int id) throws Exception {
        
    }

    @Override
    public int getTotalInvoices() {
        Session s = factory.getObject().getCurrentSession();
        Query query = s.createQuery("SELECT COUNT(*) FROM Invoice");

        return ((Number) query.getSingleResult()).intValue();
    }

}
