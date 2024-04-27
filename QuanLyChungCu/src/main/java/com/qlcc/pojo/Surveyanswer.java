/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "surveyanswer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Surveyanswer.findAll", query = "SELECT s FROM Surveyanswer s"),
    @NamedQuery(name = "Surveyanswer.findById", query = "SELECT s FROM Surveyanswer s WHERE s.id = :id")})
public class Surveyanswer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "optionId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Surveyoption optionId;
    @JoinColumn(name = "questionId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Surveyquestion questionId;
    @JoinColumn(name = "responseId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Surveyresponse responseId;

    public Surveyanswer() {
    }

    public Surveyanswer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Surveyoption getOptionId() {
        return optionId;
    }

    public void setOptionId(Surveyoption optionId) {
        this.optionId = optionId;
    }

    public Surveyquestion getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Surveyquestion questionId) {
        this.questionId = questionId;
    }

    public Surveyresponse getResponseId() {
        return responseId;
    }

    public void setResponseId(Surveyresponse responseId) {
        this.responseId = responseId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Surveyanswer)) {
            return false;
        }
        Surveyanswer other = (Surveyanswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qlcc.pojo.Surveyanswer[ id=" + id + " ]";
    }
    
}
