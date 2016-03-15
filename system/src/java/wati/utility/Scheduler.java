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
       Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, "Scheduled task running");

    }
    
   //@Schedule(second = "0", minute = "0", hour = "15", dayOfWeek = "*")
    //public void testTask2() {
      //  contactController.sendTestEmail();
    //}

    //@Schedule(second = "*", minute = "*", hour = "*", dayOfWeek = "*")
    public void followUpMails() {
        contactController.sendDifferentDateEmail();
        contactController.sendFirstWeekEmail();
        contactController.sendSecondWeekEmail();
        contactController.sendThirdWeekEmail();
        contactController.sendMonthlyEmail();
        contactController.sendTwiceWeekEmail();
        
  
    }
    

   

}
