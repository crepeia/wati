/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import wati.model.FollowUp;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.naming.NamingException;
import wati.model.User;
import wati.persistence.GenericDAO;

/**
 *
 * @author thiago
 */
@Named("followUpController")
@ViewScoped
public class FollowUpController extends BaseController<FollowUp>{
    
    private FollowUp followUp;
    private GenericDAO daoUser;
    private long id;
    private boolean validate;
    private String methods[];

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        id = params.get("hid") != null ? Long.parseLong(params.get("hid")) : 0;
        try {
            daoBase = new GenericDAO<>(FollowUp.class);
            daoUser = new GenericDAO<>(User.class);
        } catch (NamingException ex) {
            Logger.getLogger(FollowUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FollowUp getFollowUp() {
        if (followUp == null) {
            followUp = new FollowUp();
        }
        return followUp;
    }

    private User getUser() {
        try {
            if(loggedUser()){
                return getLoggedUser();
            }
            else if (id % 1357 != 0) {
                Logger.getLogger(FollowUpController.class.getName()).log(Level.SEVERE, "User answering follow up form with invalid id: " + id);
                return null;
            } else {
                id = id / 1357;
                List users = daoUser.list("id", id, getEntityManager());
                if (users.isEmpty()) {
                    Logger.getLogger(FollowUpController.class.getName()).log(Level.SEVERE, "User answering follow up form with invalid id: " + id);
                    return null;
                } else {
                    return (User) users.get(0);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FollowUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

  

    public void save(ActionEvent event) {
        try {
            String count = (String) event.getComponent().getAttributes().get("count");
            getFollowUp().setDateAnswered(new Date());
            getFollowUp().setUser(getUser());
            getFollowUp().setCount(Integer.valueOf(count));
            daoBase.insert(getFollowUp(), getEntityManager());
            followUp = null;
        } catch (SQLException ex) {
            Logger.getLogger(FollowUpController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
 

    public GenericDAO getDaoUser() {
        return daoUser;
    }

    public void setDaoUser(GenericDAO daoUser) {
        this.daoUser = daoUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }
    
    
}
