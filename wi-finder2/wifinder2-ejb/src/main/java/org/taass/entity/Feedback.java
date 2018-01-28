/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.taass.entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Odierna Francesco
 */
@Entity
public class Feedback implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rate;
    private String comment;
    private java.sql.Date feedbackDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Utente author;
    @ManyToOne(fetch = FetchType.LAZY)
    private WiFi wifi;
    
    public Feedback() {
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
        if (!(object instanceof Feedback)) {
            return false;
        }
        Feedback other = (Feedback) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public int getRate() {
        return rate;
    }
    
    public void setRate(int rate) {
        this.rate = rate;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Date getFeedbackDate() {
        return feedbackDate;
    }
    
    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
    
    public Utente getAuthor() {
        return author;
    }
    
    public void setAuthor(Utente author) {
        this.author = author;
    }
    
    public WiFi getWifi() {
        return wifi;
    }
    
    public void setWifi(WiFi wifi) {
        this.wifi = wifi;
    }
    
    @Override
    public String toString() {
        return "Feedback{" + "id=" + id + ", rate=" + rate + ", comment=" + comment + ", feedbackDate=" + feedbackDate + ", author=" + author + ", wifi=" + wifi + '}';
    }
    
}
