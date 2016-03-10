/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.utility;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import wati.controller.ContactController;

/**
 *
 * @author thiago
 */
@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class Scheduler {

    @Inject
    private ContactController contactController;
    
    public Scheduler() {
        
    }

    @Schedule(second = "*/10", minute = "*", hour = "*", dayOfWeek = "*")
    public void testTask() {
        System.out.println("Started scheduler");
        contactController.sendDifferentDateEmail();
    }
    

   

}
