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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "entryright")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entryright.findAll", query = "SELECT e FROM Entryright e"),
    @NamedQuery(name = "Entryright.findById", query = "SELECT e FROM Entryright e WHERE e.id = :id"),
    @NamedQuery(name = "Entryright.findByTypeOfVehicle", query = "SELECT e FROM Entryright e WHERE e.typeOfVehicle = :typeOfVehicle"),
    @NamedQuery(name = "Entryright.findByLicensePlates", query = "SELECT e FROM Entryright e WHERE e.licensePlates = :licensePlates")})
public class Entryright implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "typeOfVehicle")
    private String typeOfVehicle;
    @Size(max = 20)
    @Column(name = "licensePlates")
    private String licensePlates;
    @JoinColumn(name = "relativeId", referencedColumnName = "id")
    @ManyToOne
    private Relative relativeId;

    public Entryright() {
    }

    public Entryright(Integer id) {
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
        if (!(object instanceof Entryright)) {
            return false;
        }
        Entryright other = (Entryright) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qlcc.pojo.Entryright[ id=" + id + " ]";
    }
    
}
