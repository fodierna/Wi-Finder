/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.taass.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Odierna Francesco
 */
@NamedQueries({
    @NamedQuery(name = "findAllUser", query = "SELECT u FROM Utente u"),
    @NamedQuery(name = "findCreatedWiFiByUserId", query = "SELECT w FROM WiFi w WHERE w.creator.id=:id"),
    @NamedQuery(name = "findUserByCredentials", query = "SELECT u FROM Utente u WHERE u.username=:username AND u.password=:password"),
    @NamedQuery(name = "findUserById", query = "SELECT u FROM Utente u WHERE u.id=:id")})

@Entity
public class Utente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean isAdmin;
    @OneToMany(mappedBy = "creator")
    private List<WiFi> createdWiFi;
    @OneToMany(cascade = CascadeType.ALL,mappedBy="author", orphanRemoval = true)
    private List<Feedback> releasedFeedback;
    @OneToMany(cascade = CascadeType.ALL,mappedBy="reporter", orphanRemoval = true)
    private List<Report> sendedReport;
    
    public Utente() {
        this.createdWiFi = new ArrayList<WiFi>();
        this.releasedFeedback = new ArrayList<Feedback>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Utente)) {
            return false;
        }
        Utente other = (Utente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
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

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "User:\nid = " + id + "; "
                + "\nusername = " + username + "; "
                + "\npassword = " + password + "; "
                + "\nemail = " + email + "; "
                + "\nisAdmin = " + isAdmin + ";";
    }

}
