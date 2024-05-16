/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.pojo;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "parkingright")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParkingRight.findAll", query = "SELECT p FROM ParkingRight p"),
    @NamedQuery(name = "ParkingRight.findById", query = "SELECT p FROM ParkingRight p WHERE p.id = :id"),
    @NamedQuery(name = "ParkingRight.findByStatus", query = "SELECT p FROM ParkingRight p WHERE p.status = :status"),
    @NamedQuery(name = "ParkingRight.findByTypeOfVehicle", query = "SELECT e FROM ParkingRight e WHERE e.typeOfVehicle = :typeOfVehicle"),
    @NamedQuery(name = "ParkingRight.findByLicensePlates", query = "SELECT e FROM ParkingRight e WHERE e.licensePlates = :licensePlates")})
public class ParkingRight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @NotNull(message = "Type of vehicle is required!")
    @Size(max = 50)
    @Column(name = "typeOfVehicle")
    private String typeOfVehicle;
    @NotNull(message = "License plates is required!")
    @Size(max = 20)
    @Column(name = "licensePlates")
    private String licensePlates;
    @Size(max = 50)
    @Column(name = "status")
    private String status;
    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "relativeId", referencedColumnName = "id")
    @ManyToOne
    private Relative relativeId;

    public ParkingRight() {
    }

    public ParkingRight(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeOfVehicle() {
        return typeOfVehicle;
    }

    public void setTypeOfVehicle(String typeOfVehicle) {
        this.typeOfVehicle = typeOfVehicle;
    }

    public String getLicensePlates() {
        return licensePlates;
    }

    public void setLicensePlates(String licensePlates) {
        this.licensePlates = licensePlates;
    }

    public Relative getRelativeId() {
        return relativeId;
    }

    public void setRelativeId(Relative relativeId) {
        this.relativeId = relativeId;
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
        if (!(object instanceof ParkingRight)) {
            return false;
        }
        ParkingRight other = (ParkingRight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qlcc.pojo.Entryright[ id=" + id + " ]";
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
