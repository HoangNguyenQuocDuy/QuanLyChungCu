/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "surveyquestion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Surveyquestion.findAll", query = "SELECT s FROM Surveyquestion s"),
    @NamedQuery(name = "Surveyquestion.findById", query = "SELECT s FROM Surveyquestion s WHERE s.id = :id"),
    @NamedQuery(name = "Surveyquestion.findByQuestionType", query = "SELECT s FROM Surveyquestion s WHERE s.questionType = :questionType")})
public class Surveyquestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull(message = "Question text is required")
    @Size(min = 1, max = 65535)
    @Column(name = "questionText")
    private String questionText;
    @Basic(optional = false)
    @NotNull(message = "Question type is required")
    @Size(min = 1, max = 15)
    @Column(name = "questionType")
    private String questionType;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId")
    private List<Surveyoption> surveyoptionSet;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId")
    private List<Surveyanswer> surveyanswerSet;
    @JoinColumn(name = "surveyId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Survey surveyId;

    public Surveyquestion() {
    }

    public Surveyquestion(Integer id) {
        this.id = id;
    }

    public Surveyquestion(Integer id, String questionText, String questionType) {
        this.id = id;
        this.questionText = questionText;
        this.questionType = questionType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    @XmlTransient
    public List<Surveyoption> getSurveyoptionSet() {
        return surveyoptionSet;
    }

    public void setSurveyoptionSet(List<Surveyoption> surveyoptionSet) {
        this.surveyoptionSet = surveyoptionSet;
    }

    @XmlTransient
    public List<Surveyanswer> getSurveyanswerSet() {
        return surveyanswerSet;
    }

    public void setSurveyanswerSet(List<Surveyanswer> surveyanswerSet) {
        this.surveyanswerSet = surveyanswerSet;
    }

    public Survey getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Survey surveyId) {
        this.surveyId = surveyId;
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
        if (!(object instanceof Surveyquestion)) {
            return false;
        }
        Surveyquestion other = (Surveyquestion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qlcc.pojo.Surveyquestion[ id=" + id + " ]";
    }
    
}
