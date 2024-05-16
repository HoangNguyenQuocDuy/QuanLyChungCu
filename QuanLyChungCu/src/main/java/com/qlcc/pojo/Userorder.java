/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "userorder")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userorder.findAll", query = "SELECT u FROM Userorder u"),
    @NamedQuery(name = "Userorder.findById", query = "SELECT u FROM Userorder u WHERE u.id = :id"),
    @NamedQuery(name = "Userorder.findByStatus", query = "SELECT u FROM Userorder u WHERE u.status = :status"),
    @NamedQuery(name = "Userorder.findByCreatedAt", query = "SELECT u FROM Userorder u WHERE u.createdAt = :createdAt"),
    @NamedQuery(name = "Userorder.findByUpdatedAt", query = "SELECT u FROM Userorder u WHERE u.updatedAt = :updatedAt"),
    @NamedQuery(name = "Userorder.findByImage", query = "SELECT u FROM Userorder u WHERE u.image = :image")})
public class Userorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "status")
    private String status;
    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Size(max = 255)
    @Column(name = "image")
    private String image;
    @JoinColumn(name = "lockerId", referencedColumnName = "id")
    @ManyToOne
    private Locker lockerId;
    
    @JsonIgnore
    @Transient
    private MultipartFile file;

    public Userorder() {
    }

    public Userorder(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Locker getLockerId() {
        return lockerId;
    }

    public void setLockerId(Locker lockerId) {
        this.lockerId = lockerId;
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
        if (!(object instanceof Userorder)) {
            return false;
        }
        Userorder other = (Userorder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qlcc.pojo.Userorder[ id=" + id + " ]";
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
    
}
