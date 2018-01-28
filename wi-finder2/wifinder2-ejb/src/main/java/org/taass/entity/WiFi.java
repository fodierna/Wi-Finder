
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package org.taass.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Odierna Francesco
 */
@NamedQueries({
    @NamedQuery(name = "listWiFi", query = "SELECT w FROM WiFi w"),
    @NamedQuery(name = "findAllWiFi", query = "SELECT r FROM WiFi r WHERE r.latCoordinate BETWEEN :x1 AND :x2 AND r.lonCoordinate BETWEEN :y1 AND :y2"),
    @NamedQuery(name = "findWiFiById", query = "SELECT w FROM WiFi w WHERE w.id=:id"),
    @NamedQuery(name = "findWiFiByPosition", query = "SELECT w FROM WiFi w WHERE w.latCoordinate=:latCoordinate AND w.lonCoordinate=:lonCoordinate")})

@Entity
public class WiFi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ssid;
    private double latCoordinate;
    private double lonCoordinate;
    private String description;
    private java.sql.Date creationDate;

    @ManyToOne
    private Utente creator;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="wifi", orphanRemoval = true)
    private List<Feedback> feedback;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="wifi", orphanRemoval = true)
    private List<Report> report;
    
    public WiFi() {
         this.feedback = new ArrayList<Feedback>();
         this.report = new ArrayList<Report>();
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
        if (!(object instanceof WiFi)) {
            return false;
        }
        WiFi other = (WiFi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public double getLonCoordinate() {
        return lonCoordinate;
    }

    public void setLonCoordinate(double lonCoordinate) {
        this.lonCoordinate = lonCoordinate;
    }

    public double getLatCoordinate() {
        return latCoordinate;
    }

    public void setLatCoordinate(double lanCoordinate) {
        this.latCoordinate = lanCoordinate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Utente getCreator() {
        return creator;
    }

    public void setCreator(Utente creator) {
        this.creator = creator;
    }

    public List<Feedback> getFeedback() {
        return feedback;
    }

    public void addFeedback(Feedback f) {
        f.setWifi(this);
        this.feedback.add(f);
        
    }

    
    
    @Override
    public String toString() {
        return "WiFi:\nid = " + id + "; "
                + "\nssid = " + ssid + "; "
                + "\nlonCoord = " + lonCoordinate + "; "
                + "\nlatCoord = " + latCoordinate + "; "
                + "\ndescription = " + description + ";"
                + "\ncreationDate = " + creationDate + ";"
                + "\ncreator = " + creator + ";";
                
    }

}
