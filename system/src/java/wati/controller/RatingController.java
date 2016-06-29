/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import wati.model.Page;
import wati.model.Rating;
import wati.model.User;
import wati.persistence.GenericDAO;

/**
 *
 * @author thiago
 */
@ManagedBean(name = "ratingController")
@RequestScoped
public class RatingController extends BaseController<Rating> {
    
    private GenericDAO daoPage;
    
    public RatingController() {
        try {
            daoBase = new GenericDAO<>(Rating.class);
            daoPage = new GenericDAO<>(Page.class);
        } catch (NamingException ex) {
            Logger.getLogger(RatingController.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }
    
    public void rate(){
        Page page;
        Rating rating = new Rating();
        List<Page> pages;
        try {
            pages = daoPage.list("url", getURL(), getEntityManager());
            if(pages.isEmpty()){
                page = new Page();
                page.setUrl(getURL());
                daoPage.insert(page, getEntityManager());
            }else{
                page = pages.get(0);
            }
            rating.setPage(page);
            rating.setUser(getUser());
        } catch (SQLException ex) {
            Logger.getLogger(RatingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getURL(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = ((HttpServletRequest) request).getRequestURI();
	url = url.substring( url.lastIndexOf('/') + 1 );
        return url;       
    }
    
    public User getUser(){
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedUser");
        return user;
    }
    
    
}
