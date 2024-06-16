/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "relative")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relative.findAll", query = "SELECT r FROM Relative r"),
    @NamedQuery(name = "Relative.findById", query = "SELECT r FROM Relative r WHERE r.id = :id"),
    @NamedQuery(name = "Relative.findByType", query = "SELECT r FROM Relative r WHERE r.type = :type")})
public class Relative implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @NotNull(message = "First name of related is required!")
    @Column(name = "firstname")
    private String firstname;
    @Size(max = 30)
    @NotNull(message = "Last name of related is required!")
    @Column(name = "lastname")
    private String lastname;
    @NotNull(message = "Type of related is required!")
    @Size(max = 50)
    @Column(name = "type")
    private String type;
    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;
    @JsonIgnore
    @OneToMany(mappedBy = "relativeId")
    private Set<ParkingRight> entryrightSet;
    @OneToMany(mappedBy = "relativeId")
    @JsonIgnore
    private Set<EntryRight> parkingrightSet;
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    @JsonIgnore
    @Transient
    private MultipartFile file;

    public Relative() {
    }

    public Relative(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public Set<ParkingRight> getEntryrightSet() {
        return entryrightSet;
    }

    public void setEntryrightSet(Set<ParkingRight> entryrightSet) {
        this.entryrightSet = entryrightSet;
    }

    @XmlTransient
    public Set<EntryRight> getParkingrightSet() {
        return parkingrightSet;
    }

    public void setParkingrightSet(Set<EntryRight> parkingrightSet) {
        this.parkingrightSet = parkingrightSet;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof Relative)) {
            return false;
        }
        Relative other = (Relative) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qlcc.pojo.Relative[ id=" + id + " ]";
    }

    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
