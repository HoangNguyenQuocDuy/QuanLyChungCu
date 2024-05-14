/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "surveyresponse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Surveyresponse.findAll", query = "SELECT s FROM Surveyresponse s"),
    @NamedQuery(name = "Surveyresponse.findById", query = "SELECT s FROM Surveyresponse s WHERE s.id = :id"),
    @NamedQuery(name = "Surveyresponse.findByCreatedAt", query = "SELECT s FROM Surveyresponse s WHERE s.createdAt = :createdAt")})
public class Surveyresponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "surveyId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Survey surveyId;
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responseId")
    private Set<Surveyanswer> surveyanswerSet;

    public Surveyresponse() {
    }

    public Surveyresponse(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Survey getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Survey surveyId) {
        this.surveyId = surveyId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @XmlTransient
    public Set<Surveyanswer> getSurveyanswerSet() {
        return surveyanswerSet;
    }

    public void setSurveyanswerSet(Set<Surveyanswer> surveyanswerSet) {
        this.surveyanswerSet = surveyanswerSet;
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
        if (!(object instanceof Surveyresponse)) {
            return false;
        }
        Surveyresponse other = (Surveyresponse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qlcc.pojo.Surveyresponse[ id=" + id + " ]";
    }
    
}
