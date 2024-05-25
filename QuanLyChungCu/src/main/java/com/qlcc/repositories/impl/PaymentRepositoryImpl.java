/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.Payment;
import com.qlcc.repositories.PaymentRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author DELL
 */
@Transactional
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addPayment(Payment payment) {
        Session s = factory.getObject().getCurrentSession();

        s.save(payment);
    }

    @Override
    public List<Payment> getPayments(Map<String, String> params) throws Exception {
        try {
            Session s = factory.getObject().getCurrentSession();
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Payment> q = b.createQuery(Payment.class);
            Root<Payment> r = q.from(Payment.class);
            q.select(r);

            List<Predicate> predicates = new ArrayList<>();

            if (params.containsKey("fromDate")) {
                Date fromDate = null;

                fromDate = parseToDate(params.get("fromDate"));
                predicates.add(b.greaterThanOrEqualTo(r.get("createdAt"), fromDate));
            }

            if (params.containsKey("toDate")) {
                Date toDate = null;
                toDate = parseToDate(params.get("toDate"));
                predicates.add(b.lessThanOrEqualTo(r.get("createdAt"), toDate));
            }

            if (params.containsKey("month") && params.containsKey("year")) {
                Integer month = Integer.valueOf(params.get("month"));
                Integer year = Integer.valueOf(params.get("year"));

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month - 1); //month start from 0
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                Date firstDayOfMonth = calendar.getTime();

                calendar.add(Calendar.MONTH, 1);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                Date lastDayOfMonth = calendar.getTime();

                predicates.add(b.greaterThanOrEqualTo(r.get("createdAt"), firstDayOfMonth));
                predicates.add(b.lessThanOrEqualTo(r.get("createdAt"), lastDayOfMonth));
            }
            
            if (params.containsKey("userId")) {
                Integer userId = Integer.valueOf(params.get("userId"));
                predicates.add(b.equal(r.get("userId").get("id"), userId));
            }

            if (!predicates.isEmpty()) {
                q.where(predicates.toArray(new Predicate[0]));
            }

            q.orderBy(b.desc(r.get("createdAt")));

            Query<Payment> query = s.createQuery(q);

            return query.getResultList();
        } catch (NumberFormatException | ParseException | HibernateException e) {
            throw new Exception(e);
        }
    }

    private Date parseToDate(String dateString) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(dateString);
    }

}
