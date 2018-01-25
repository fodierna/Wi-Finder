/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.taass.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.taass.businesslogic.*;
import org.taass.entity.Feedback;
import org.taass.entity.Utente;
import org.taass.entity.WiFi;

/**
 *
 * @author Odierna Francesco
 */


public class Controller extends HttpServlet {
    
    @EJB
    private wifinderEJBLocal wifinderEJB;
    private double meters= 600;
    private double multiplier= 0.0000089;
    private double RANGE = meters*multiplier;
    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
    
    
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        
        switch (service) {
            case "wifiListByPosition":{
                double lat = Double.parseDouble(request.getParameter("lat"));
                double lon = Double.parseDouble(request.getParameter("lon"));
                
                Gson json= new Gson();
                PrintWriter out = response.getWriter();
                try {
                    
                    out.print(json.toJson(wifinderEJB.getWifiList(RANGE, lat, lon)));
                    response.setContentType("application/json;charset=UTF-8");
                    System.out.println(json.toJson(wifinderEJB.getWifiList(RANGE, lat, lon)));
                    
                } catch (Exception e) {
                }
                break;
            }
            
            case "insertWiFi":{
                String ssid = request.getParameter("ssid");
                double lon = Double.parseDouble(request.getParameter("lon"));
                double lat = Double.parseDouble(request.getParameter("lat"));
                String description = request.getParameter("description");
                java.sql.Date creationDate = new Date(System.currentTimeMillis());
                Long id = Long.parseLong(request.getParameter("userId"));
                
                Utente u= wifinderEJB.findUserById(id);
                
                WiFi w=wifinderEJB.createWifi(ssid, lat, lon, description, creationDate, u);
                Gson json= new Gson();
                try{
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    if(w!=null)
                        out.println(json.toJson("OK"));
                    else
                        out.println(json.toJson("NOK"));
                } catch (Exception e) {
                }
                break;
                
            }
            
            case "insertFeedback":{
                int rate = Integer.parseInt(request.getParameter("rate"));
                String comment = request.getParameter("comment");
                java.sql.Date feedbackDate= new Date(System.currentTimeMillis());
                String wifissid = request.getParameter("ssid");
                Gson json= new Gson();
                List<Utente> utenti= wifinderEJB.getUserList();
                List<WiFi> wifiList =  wifinderEJB.getWifiList();
                WiFi wifi=null;
                for(WiFi w : wifiList){
                    if(w.getSsid().equals(wifissid));
                    wifi=w;
                }
                
                
                Feedback f= wifinderEJB.createFeedback(rate, comment, feedbackDate, utenti.get(0), wifi);
                wifi.addFeedback(f);
                
                
                try{
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.println(json.toJson("OK"));
                } catch (Exception e) {
                }
                break;
            }
            
            case "getFeedbackListFromWifi":{
                Long id = Long.parseLong(request.getParameter("wifiId"));
                
                ObjectMapper objMapper = new ObjectMapper();
                try {
                    String jsonStr = objMapper.writeValueAsString(wifinderEJB.getFeedbackFromWifi(id));
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.println(jsonStr);
                } catch (Exception e) {
                }
                break;
                
            }
        }
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
