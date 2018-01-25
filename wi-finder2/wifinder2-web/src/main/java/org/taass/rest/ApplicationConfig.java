/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.taass.rest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.taass.businesslogic.wifinderEJB;
import org.taass.businesslogic.wifinderEJBLocal;
/**
 *
 * @author Odierna Francesco
 */
@ApplicationPath("services")
public class ApplicationConfig extends Application {
    
    private final Set<Class<?>> classes;
    
    public ApplicationConfig() {
        HashSet<Class<?>> c = new HashSet<>();
        c.add(wifinderEJB.class);
        c.add(MOXyJsonProvider.class);
        classes = Collections.unmodifiableSet(c);
    }
    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}