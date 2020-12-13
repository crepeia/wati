/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
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
@Named("ratingController")
@ViewScoped
public class RatingController extends BaseController<Rating> {

    private GenericDAO daoPage;
    private Rating rating;
    private Page page;

    public RatingController() {
        try {
            daoBase = new GenericDAO<>(Rating.class);
            daoPage = new GenericDAO<>(Page.class);
        } catch (NamingException ex) {
            Logger.getLogger(RatingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Rating getRating() {
        if (rating == null) {
            try {
                if (getUser() != null) {
                    List<Rating> ratings = daoBase.list("user", getUser(), getEntityManager());
                    if (!ratings.isEmpty()) {
                        for (Rating r : ratings) {
                            if (r.getPage() != null && r.getPage().getUrl().contains(getURL())) {
                                rating = r;
                            }
                        }
                    }
                }
                if (rating == null) {
                    rating = new Rating();
                    rating.setUser(getUser());
                    rating.setPage(getPage());
                    rating.setDateRated(new Date());
                }
            } catch (SQLException ex) {
                Logger.getLogger(RatingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rating;
    }

    public Page getPage() {
        if (page == null) {
            try {
                List<Page> pages = daoPage.list("url", getURL(), getEntityManager());
                if (pages.isEmpty()) {
                    page = new Page();
                    page.setUrl(getURL());
                    daoPage.insert(page, getEntityManager());
                } else {
                    page = pages.get(0);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RatingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return page;
    }

    public void rate(ActionEvent event) {
        try {
            String action = (String) event.getComponent().getAttributes().get("button");
            if (action.contains("like")) {
                getRating().setRelevant(true);
            }
            if (action.contains("unlike")) {
                getRating().setRelevant(false);
            }
            rating.setDateRated(new Date());
            daoBase.insertOrUpdate(getRating(), getEntityManager());
        } catch (SQLException ex) {
            Logger.getLogger(RatingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getURL() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = ((HttpServletRequest) request).getRequestURI();
        url = url.substring(url.lastIndexOf('/') + 1);
        return url;
    }

    public User getUser() {
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedUser");
        return user;
    }

    public String getImageLike() {
        if (getRating().getRelevant() == null) {
            return "images/like.png";
        } else if (!getRating().getRelevant()) {
            return "images/like.png";
        } else {
            return "images/like-pressed.png";
        }
    }

    public String getImageUnlike() {
        if (getRating().getRelevant() == null) {
            return "images/unlike.png";
        } else if (getRating().getRelevant()) {
            return "images/unlike.png";
        } else {
            return "images/unlike-pressed.png";
        }
    }

    public boolean isLiked() {
        if (getRating().getRelevant() == null) {
            return false;
        } else if (!getRating().getRelevant()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isUnliked() {
        if (getRating().getRelevant() == null) {
            return false;
        } else if (getRating().getRelevant()) {
            return false;
        } else {
            return true;
        }
    }

}
