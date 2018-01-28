/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.taass.businesslogic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.taass.entity.Feedback;
import org.taass.entity.Utente;
import org.taass.entity.WiFi;

/**
 *
 * @author Odierna Francesco
 */
@Singleton
@LocalBean
@Startup
@DataSourceDefinition(
        className="org.apache.derby.jdbc.EmbeddedDataSource",
        name="java:global/jdbc/wifinderPU",
        user="APP",
        password="app",
        databaseName="wifinder",
        properties={"connectionAttributes=;create=true"}
)

public class WiFinderPopulator {
    
    @EJB
    private wifinderEJBLocal wifinderEJB;
    
    @PostConstruct
    public void populateDB(){
        wifinderEJB.createUser("Francesco", "psw1", "frank.od", false);
        wifinderEJB.createUser("Mario", "psw2", "Ma.rossi", false);
        List<Utente> utenti = wifinderEJB.getUserList();
        
        
        
        WiFi w1=wifinderEJB.createWifi("WiFi talucchi", 45.077659, 7.656542, "wifi 1", new Date(2017 - 1900, 12 - 1, 14), utenti.get(0));
        WiFi w2=wifinderEJB.createWifi("bar chin chong", 45.078572, 7.659824, "wifi 2", new Date(2017 - 1900, 12 - 1, 11), utenti.get(0));
        WiFi w3=wifinderEJB.createWifi("Bar vip", 45.077130, 7.659871, "wifi 3", new Date(2017 - 1900, 12 - 1, 12), utenti.get(0));
        WiFi w4=wifinderEJB.createWifi("Mensa uniTO", 45.123, 7.625, "wifi 4", new Date(2017 - 1900, 12 - 1, 7), utenti.get(1));
        WiFi w5=wifinderEJB.createWifi("LontanoWiFi", 45.626, 7.123, "wifi 5", new Date(2017 - 1900, 12 - 1, 7), utenti.get(1));
        WiFi w6=wifinderEJB.createWifi("Undefined wifi", 46.128, 7.129, "wifi 6", new Date(2017 - 1900, 12 - 1, 9), utenti.get(1));
        
        
        
        wifinderEJB.createFeedback(5, "Talucchi: Ottima rete. Funzionante e veloce", new Date(2017 - 1900, 12 - 1, 24), utenti.get(0), w1);
        wifinderEJB.createFeedback(3, "Talucchi: Non male dai", new Date(2017 - 1900, 12 - 1, 14), utenti.get(1), w1);
        
        wifinderEJB.createFeedback(3, "Bar ching chong: cinese, come loro", new Date(2017 - 1900, 12 - 1, 24), utenti.get(0), w2);
        wifinderEJB.createFeedback(2, "Bar ching chong: pessima", new Date(2017 - 1900, 12 - 1, 14), utenti.get(1), w2);
        
        wifinderEJB.createFeedback(5, "Bar vip: una garanzia, come il locale", new Date(2017 - 1900, 12 - 1, 24), utenti.get(1), w3);
        wifinderEJB.createFeedback(5, "Bar vip: Qualità secca", new Date(2017 - 1900, 12 - 1, 14), utenti.get(0), w3);
        
        
    }
}
