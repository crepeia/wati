/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.utility;

import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    @Schedule(second = "0", minute = "0", hour = "*", dayOfWeek = "*")
    public void testTask() {
       Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, "Test task running");
    }
 
    @Schedule(second = "0", minute = "0", hour = "0", dayOfWeek = "*")
    public void midnigthTask() {
        Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, "Midnight task running");
        contactController.sendScheduledEmails();
        contactController.scheduleReaserach12MonthsEmailFix();
    }
    
    @Schedule(second = "0", minute = "0", hour = "8", dayOfWeek = "*")
    public void morningTask() {
        Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, "Morning task running");
        contactController.sendScheduledEmails();
    }
    
    @Schedule(second = "0", minute = "30", hour = "12", dayOfWeek = "*")
    public void noonTask() {
        Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, "Noon task running");
        contactController.sendScheduledEmails();
    }
    
    @Schedule(second = "0", minute = "0", hour = "16", dayOfWeek = "*")
    public void afternoonTask() {
        Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, "Afternoon task running");
        contactController.sendScheduledEmails();
    }
    
    @Schedule(second = "0", minute = "0", hour = "20", dayOfWeek = "*")
    public void eveningTask() {
        Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, "Evening task running");
        contactController.sendScheduledEmails();
    }
    

   

}
