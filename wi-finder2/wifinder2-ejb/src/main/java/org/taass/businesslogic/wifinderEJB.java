/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.taass.businesslogic;

import java.sql.Date;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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

@Path("/rest")
@Stateless
@Local(wifinderEJBLocal.class)
public class wifinderEJB implements wifinderEJBLocal {
    
    @Inject
    private EntityManager em;
    
    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/{range}/{lat}/{lon}")
    public List<WiFi> getWifiList(@PathParam("range") double range, @PathParam("lat") double latCoordinate, @PathParam("lon") double lonCoordinate) {
        Query query;
        query = em.createNamedQuery("findAllWiFi", WiFi.class);
        double newLat1, newLat2, newLon1, newLon2;
        
        newLat1=latCoordinate - range;
        newLat2=latCoordinate + range;
        newLon1=lonCoordinate - range / Math.cos(latCoordinate * 0.018);
        newLon2=lonCoordinate + range / Math.cos(latCoordinate * 0.018);
        System.out.println("Lat1: "+ newLat1 + " Lat 2: "+newLat2+ " Lon1: "+newLon1+" Lon2: "+newLon2);
        query.setParameter("x1", newLat1);
        query.setParameter("x2", newLat2);
        
        query.setParameter("y1", newLon1);
        query.setParameter("y2", newLon2);
        
        
        return query.getResultList();
    }
    
    @Override
    public List<WiFi> getWifiListCreatedByUser(Long userId) {
        Query query;
        query = em.createNamedQuery("findCreatedWiFiByUserId", Utente.class);
        query.setParameter("id", userId);
        
        return query.getResultList();
    }
    
    @Override
    public List<Utente> getUserList() {
        Query query;
        query = em.createNamedQuery("findAllUser", Utente.class);
        
        return query.getResultList();
    }
    
    @Override
    public Utente createUser(String username, String password, String email, boolean isAdmin) {
        
        Utente u = new Utente();
        u.setUsername(username);
        u.setPassword(password);
        u.setEmail(email);
        u.setIsAdmin(isAdmin);
        
        em.persist(u);
        
        return u;
    }
    
    @Override
    public Utente findUserById(Long id) {
        Query query;
        query = em.createNamedQuery("findUserById", Utente.class);
        query.setParameter("id", id);
        
        return (Utente) query.getSingleResult();
    }
    
    @Override
    public Utente removeUser(Long id) {
        Utente u = findUserById(id);
        em.remove(u);
        
        return u;
    }
    
    @Override
    public Utente userExists(String username, String password) {
        Utente u = null;
        Query query;
        query = em.createNamedQuery("findUserByCredentials", Utente.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        
        return (Utente) query.getSingleResult();
    }
    
    @Override
    public boolean userIsAdmin(Long id) {
        Utente u = findUserById(id);
        
        return u.isIsAdmin();
    }
    
    @Override
    public WiFi createWifi(String ssid, double latCoordinate, double lonCoordinate, String description, Date creationDate, Utente creator) {
        WiFi w = new WiFi();
        w.setSsid(ssid);
        w.setLatCoordinate(latCoordinate);
        w.setLonCoordinate(lonCoordinate);
        w.setDescription(description);
        w.setCreationDate(creationDate);
        w.setCreator(creator);
        
        em.persist(w);
        
        return w;
    }
    
    @Override
    public WiFi findWiFiById(Long id) {
        Query query;
        query = em.createNamedQuery("findWiFiById", WiFi.class);
        query.setParameter("id", id);
        
        return (WiFi) query.getSingleResult();
    }
    
    @Override
    public WiFi removeWifi(Long id) {
        WiFi w = findWiFiById(id);
        em.remove(w);
        
        return w;
    }
    
    @Override
    public WiFi wifiExists(double lonCoordinate, double latCoordinate) {
        Query query;
        query = em.createNamedQuery("findWiFiByPosition", WiFi.class);
        query.setParameter("latCoordinate", latCoordinate);
        query.setParameter("lonCoordinate", lonCoordinate);
        
        return (WiFi) query.getSingleResult();
    }
    
    @Override
    public Report createReport(int reportCode, String message, Date reportDate, Utente reporter, WiFi wifi) {
        Report r = new Report();
        r.setReportCode(reportCode);
        r.setMessage(message);
        r.setReportDate(reportDate);
        r.setReporter(reporter);
        r.setWifi(wifi);
        
        em.persist(r);
        
        return r;
    }
    
    @Override
    public Report removeReport(Long id) {
        Report r = findReportById(id);
        em.remove(r);
        
        return r;
    }
    
    @Override
    public Feedback createFeedback(int rate, String comment, Date feedbackDate, Utente author, WiFi wifi) {
        
        Feedback f = new Feedback();
        f.setRate(rate);
        f.setComment(comment);
        f.setFeedbackDate(feedbackDate);
        f.setAuthor(author);
        wifi.addFeedback(f);
        em.persist(f);
        System.out.println("Feedback: "+f);
        return f;
    }
    
    @Override
    public Report findReportById(Long id) {
        Query query;
        query = em.createNamedQuery("findReportById", Report.class);
        query.setParameter("id", id);
        
        return (Report) query.getSingleResult();
    }
    
    @Override
    public List<Feedback> getFeedbackFromWifi(Long id) {
        WiFi w= findWiFiById(id);
        return w.getFeedback();
    }
    
    @Override
    public List<WiFi> getWifiList() {
        Query query;
        query = em.createNamedQuery("listWiFi", WiFi.class);
        
        return query.getResultList();
    }
    
}
