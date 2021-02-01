/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import wati.model.MobileOptions;
import wati.model.TipUser;

import wati.persistence.GenericDAO;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

/**
 *
 * @author bruno
 */
@Named("mobileOptionsController")
@ApplicationScoped
public class MobileOptionsController extends BaseController<MobileOptions> {
    
    private MobileOptions mobileOptions;
    
    @Inject
    private TipUserController tipUserController;
    
    public MobileOptionsController() {
    }
    
    @PostConstruct
    public void init() {
        mobileOptions = new MobileOptions();
        try {
            daoBase = new GenericDAO<>(MobileOptions.class);
        } catch (NamingException ex) {
            Logger.getLogger(MobileOptionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMobileTips(){
        try {
            List<MobileOptions> mobileOptionsList = daoBase.list(getEntityManager());
            for (MobileOptions mo : mobileOptionsList) {
                tipUserController.sendNewTip(mo.getUser());
            }
        } catch (SQLException ex) {
            Logger.getLogger(MobileOptionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
    
}
