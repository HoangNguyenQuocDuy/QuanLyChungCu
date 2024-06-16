/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.repositories.impl;

import com.qlcc.pojo.Invoice;
import com.qlcc.pojo.Invoicetype;
import com.qlcc.pojo.Survey;
import com.qlcc.pojo.Surveyanswer;
import com.qlcc.pojo.Surveyoption;
import com.qlcc.pojo.Surveyquestion;
import com.qlcc.pojo.Surveyresponse;
import com.qlcc.repositories.StatsRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
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
public class StatsRepositoryImpl implements StatsRepository {
    
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<Object[]> getReportForSurveyQuestion(int surveyId, int questionId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        
        Root surveyQuestion = q.from(Surveyquestion.class);
        
        q.where(
                b.equal(surveyQuestion.get("surveyId").get("id"), surveyId),
                b.equal(surveyQuestion.get("id"), questionId)
        );
        
        Join<Surveyquestion, Surveyoption> optionsJoin = surveyQuestion.join("surveyoptionSet", JoinType.LEFT);
        Join<Surveyoption, Surveyanswer> answersJoin = optionsJoin.join("surveyanswerSet", JoinType.LEFT);
        
        Expression<Long> answersCount = b.count(answersJoin);
        
        q.multiselect(
                surveyQuestion.get("surveyId").get("title"),
                surveyQuestion.get("questionText"),
                surveyQuestion.get("questionType"),
                optionsJoin.get("optionText"),
                answersCount
        );
        
        q.groupBy(
                surveyQuestion.get("id"), // Group by question
                optionsJoin.get("id") // Group by option
        );
        
        List<Object[]> reportResults = s.createQuery(q).getResultList();
        
        return reportResults;
    }
    
    @Override
    public List<Object[]> getCountResponseForSurvey() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        
        Root survey = q.from(Survey.class);
        
        Join<Survey, Surveyresponse> responsesJoin = survey.join("surveyresponseSet", JoinType.LEFT);
        
        q.multiselect(
                survey.get("id"),
                survey.get("title"),
                b.count(responsesJoin)
        );
        
        q.groupBy(survey.get("id"));
        
        List<Object[]> results = s.createQuery(q).getResultList();
        
        return results;
    }
    
    @Override
    public List<Object[]> getRevenueByMonth(int month, int year) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        
        Root invoice = q.from(Invoice.class);
        Join<Invoice, Invoicetype> invoiceType = invoice.join("invoiceType");
        
        Expression<Integer> invoiceMonth = b.function("MONTH", Integer.class, invoice.get("dueDate"));
        Expression<Integer> invoiceYear = b.function("YEAR", Integer.class, invoice.get("dueDate"));
        
        q.multiselect(
                invoiceType.get("type"),
                b.sum(invoice.get("amount"))
        );
        
        q.where(
                b.equal(invoice.get("status"), "Paid"),
                b.equal(invoiceMonth, month),
                b.equal(invoiceYear, year)
        );
        
        q.groupBy(invoiceType.get("type"));
        q.orderBy(b.asc(invoiceType.get("type")));
        
        List<Object[]> results = s.createQuery(q).getResultList();
        
        return results;
    }
    
}
