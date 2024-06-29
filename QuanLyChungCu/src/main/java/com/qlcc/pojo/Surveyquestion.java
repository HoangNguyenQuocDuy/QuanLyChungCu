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
    @NamedQuery(name = "SurveyQuestion.findAll", query = "SELECT s FROM SurveyQuestion s"),
    @NamedQuery(name = "SurveyQuestion.findById", query = "SELECT s FROM SurveyQuestion s WHERE s.id = :id"),
    @NamedQuery(name = "SurveyQuestion.findByQuestionType", query = "SELECT s FROM SurveyQuestion s WHERE s.questionType = :questionType")})
public class SurveyQuestion implements Serializable {

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
    private List<SurveyOption> surveyoptionSet;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId")
    private List<SurveyAnswer> surveyanswerSet;
    @JoinColumn(name = "surveyId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Survey surveyId;

    public SurveyQuestion() {
    }

    public SurveyQuestion(Integer id) {
        this.id = id;
    }

    public SurveyQuestion(Integer id, String questionText, String questionType) {
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
    public List<SurveyOption> getSurveyoptionSet() {
        return surveyoptionSet;
    }

    public void setSurveyoptionSet(List<SurveyOption> surveyoptionSet) {
        this.surveyoptionSet = surveyoptionSet;
    }

    @XmlTransient
    public List<SurveyAnswer> getSurveyanswerSet() {
        return surveyanswerSet;
    }

    public void setSurveyanswerSet(List<SurveyAnswer> surveyanswerSet) {
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
        if (!(object instanceof SurveyQuestion)) {
            return false;
        }
        SurveyQuestion other = (SurveyQuestion) object;
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
