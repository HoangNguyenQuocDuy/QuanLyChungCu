/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import customAnnotation.UserUnique;
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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPhone", query = "SELECT u FROM User u WHERE u.phone = :phone"),
    @NamedQuery(name = "User.findByStatus", query = "SELECT u FROM User u WHERE u.status = :status"),
    @NamedQuery(name = "User.findByRoleName", query = "SELECT u FROM User u WHERE u.roleName = :roleName")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull(message = "{user.firstname.notNullMsg}")
    @Size(min = 1, max = 20, message = "{user.firstname.sizeMsg}")
    @Column(name = "firstname")
    private String firstname;
    @Basic(optional = false)
    @NotNull(message = "{user.lastname.notNullMsg}")
    @Size(min = 1, max = 20, message = "{user.lastname.sizeMsg}")
    @Column(name = "lastname")
    private String lastname;
    @Basic(optional = false)
    @NotNull(message = "{user.username.notNullMsg}")
    @Size(min = 6, max = 18, message = "{user.username.sizeMsg}")
    @Column(name = "username")
    @UserUnique(message = "Username must be unique", value = "username")
    private String username;
    @Basic(optional = false)
    @NotNull(message = "{user.password.notNullMsg}")
    @Size(min = 6, max = 255, message = "{user.password.sizeMsg}")
    @Column(name = "password")
    @JsonIgnore
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull(message = "{user.email.notNullMsg}")
    @Size(min = 10, max = 40, message = "{user.email.sizeMsg}")
    @Column(name = "email")
    @UserUnique(message = "Email must be unique", value = "email")
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(min = 9, max = 12, message = "{user.phone.sizeMsg}")
    @NotNull(message = "{user.phone.notNullMsg}")
    @Column(name = "phone")
    @UserUnique(message = "Phone must be unique", value = "phone")
    private String phone;
    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;
    @Size(max = 50)
    @Column(name = "status")
    private String status;
    @Size(max = 50)
    @Column(name = "roleName")
    private String roleName;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Surveyresponse> surveyresponseSet;
    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Set<Feedback> feedbackSet;
    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Set<Payment> paymentSet;
    @JoinColumn(name = "locker", referencedColumnName = "id")
    @ManyToOne
    private Locker locker;
    @JoinColumn(name = "room", referencedColumnName = "id")
    @ManyToOne
    private Room room;
    @JsonIgnore
    @OneToMany(mappedBy = "userId")
    private Set<Relative> relativeSet;
    
    @JsonIgnore
    @Transient
    private MultipartFile file;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @XmlTransient
    public Set<Surveyresponse> getSurveyresponseSet() {
        return surveyresponseSet;
    }

    public void setSurveyresponseSet(Set<Surveyresponse> surveyresponseSet) {
        this.surveyresponseSet = surveyresponseSet;
    }

    @XmlTransient
    public Set<Feedback> getFeedbackSet() {
        return feedbackSet;
    }

    public void setFeedbackSet(Set<Feedback> feedbackSet) {
        this.feedbackSet = feedbackSet;
    }

    @XmlTransient
    public Set<Payment> getPaymentSet() {
        return paymentSet;
    }

    public void setPaymentSet(Set<Payment> paymentSet) {
        this.paymentSet = paymentSet;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @XmlTransient
    public Set<Relative> getRelativeSet() {
        return relativeSet;
    }

    public void setRelativeSet(Set<Relative> relativeSet) {
        this.relativeSet = relativeSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof User)) {
//            return false;
//        }
//        User other = (User) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public String toString() {
        return "com.qlcc.pojo.User[ id=" + id + " ]";
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
