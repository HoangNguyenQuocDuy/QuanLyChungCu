/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "surveyoption")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Surveyoption.findAll", query = "SELECT s FROM Surveyoption s"),
    @NamedQuery(name = "Surveyoption.findById", query = "SELECT s FROM Surveyoption s WHERE s.id = :id")})
public class Surveyoption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull(message = "optionText is required")
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "optionText")
    private String optionText;
    @JoinColumn(name = "questionId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Surveyquestion questionId;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "optionId")
    private Set<Surveyanswer> surveyanswerSet;

    public Surveyoption() {
    }

    public Surveyoption(Integer id) {
        this.id = id;
    }

    public Surveyoption(Integer id, String optionText) {
        this.id = id;
        this.optionText = optionText;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Surveyquestion getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Surveyquestion questionId) {
        this.questionId = questionId;
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
        if (!(object instanceof Surveyoption)) {
            return false;
        }
        Surveyoption other = (Surveyoption) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qlcc.pojo.Surveyoption[ id=" + id + " ]";
    }
    
}
