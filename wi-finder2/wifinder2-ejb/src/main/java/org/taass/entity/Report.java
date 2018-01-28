/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.taass.entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Odierna Francesco
 */
@NamedQueries({
    @NamedQuery(name = "findReportById", query = "SELECT r FROM Report r WHERE r.id=:id"),})
@Entity
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int reportCode;
    private String message;
    private java.sql.Date reportDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Utente reporter;
    @ManyToOne(fetch = FetchType.LAZY)
    private WiFi wifi;

    public Report() {
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
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public int getReportCode() {
        return reportCode;
    }

    public void setReportCode(int reportCode) {
        this.reportCode = reportCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Utente getReporter() {
        return reporter;
    }

    public void setReporter(Utente reporter) {
        this.reporter = reporter;
    }

    public WiFi getWifi() {
        return wifi;
    }

    public void setWifi(WiFi wifi) {
        this.wifi = wifi;
    }

    @Override
    public String toString() {
        return "Report{" + "id=" + id + ", reportCode=" + reportCode + ", message=" + message + ", reportDate=" + reportDate + ", reporter=" + reporter + ", wifi=" + wifi + '}';
    }

}
