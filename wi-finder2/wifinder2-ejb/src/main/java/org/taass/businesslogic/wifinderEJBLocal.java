/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.taass.businesslogic;


import java.util.List;
import javax.ejb.Local;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.taass.entity.Feedback;
import org.taass.entity.Report;
import org.taass.entity.Utente;
import org.taass.entity.WiFi;

/**
 *
 * @author Odierna Francesco
 */

@Local

public interface wifinderEJBLocal {
   
    List<WiFi> getWifiList();
    
   
    List<WiFi> getWifiList( double range,   double latCoordinate,   double lonCoordinate);
    
    List<WiFi> getWifiListCreatedByUser(Long userId);
    List<Utente> getUserList();
    
    Utente createUser(String username, String password, String email, boolean isAdmin);
    Utente removeUser(Long id);
    Utente userExists(String username, String password);
    boolean userIsAdmin(Long id);
    Utente findUserById(Long id);
            
    WiFi createWifi(String ssid, double lonCoordinate, double latCoordinate, String description, java.sql.Date creationDate, Utente creator);
    WiFi removeWifi(Long id);
    WiFi wifiExists(double lonCoordinate, double latCoordinate);
    WiFi findWiFiById(Long id);
    
    Report createReport(int reportCode, String message, java.sql.Date reportDate, Utente reporter, WiFi wifi);
    Report removeReport(Long id);
    Report findReportById(Long id);
    
    Feedback createFeedback(int rate, String comment, java.sql.Date feedbackDate, Utente author, WiFi wifi);  
    List<Feedback> getFeedbackFromWifi(Long id);
}