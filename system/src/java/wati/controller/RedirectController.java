/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hedersb
 */
@Named("redirectController")
@RequestScoped
public class RedirectController {

    /**
     * Creates a new instance of RedirectController
     */
    public RedirectController() {
    }

    public void redirect(boolean test) {

        if (test) {

            Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedUser");
            if (object == null) {
                try {
                    Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    if (request instanceof HttpServletRequest) {
                        String url = ((HttpServletRequest) request).getRequestURI();
                        url = url.substring(url.lastIndexOf('/') + 1);
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("url", url);
                    }
                    FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");

                } catch (IOException ex) {
                    Logger.getLogger(RedirectController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    public void redirectIndex(boolean redirect) {
        if (redirect) {
            Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedUser");
            if (object != null) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(RedirectController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
