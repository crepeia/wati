/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import wati.model.User;
import wati.persistence.GenericDAO;

/**
 *
 * @author thiagorizuti
 */
@ManagedBean(name = "languageController")
@SessionScoped
public class LanguageController extends BaseController<Object> {

    private Locale locale;
    private Map<String, String> languages = new LinkedHashMap<String, String>();

    public LanguageController() {
         try {
            daoBase = new GenericDAO(User.class);
        } catch (NamingException ex) {
            Logger.getLogger(LanguageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (loggedUser() && getLoggedUser().getPreferedLanguage()!= null) {
            locale = new Locale(getLoggedUser().getPreferedLanguage());
        } else {
            locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        }
        languages.put("English", "en");
        languages.put("Español", "es");
        languages.put("Português", "pt");
        languages.put("Deutsch", "de");
        languages.put("Pусский", "ru");
        languages.put("Italiano", "it");
        languages.put("العربية", "ar");
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
       locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        if (loggedUser()) {
            getLoggedUser().setPreferedLanguage(language);
            try {
                daoBase.insertOrUpdate(getLoggedUser(), getEntityManager());
            } catch (SQLException ex) {
                Logger.getLogger(LanguageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Map<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }

    public String getLogo() {
        if (locale.getLanguage().contains("en")) {
            return "images/live-without-tobacco.png";
        } else if (locale.getLanguage().contains("es")) {
            return "images/viva-sin-tabaco.png";
        } else if (locale.getLanguage().contains("de")) {
            return "images/lebe-ohne-tabak.png";
        } else if (locale.getLanguage().contains("ru")) {
            return "images/viva-sem-tabaco-ru.png";                           
        } else {
            return "images/viva-sem-tabaco.png";
        }
    }
    
    public String getQuitline() {
        if (locale.getLanguage().contains("pt")) {
            return "images/viva-voz.png";       
        } else {
            return "images/info1.png";
        }
    }
    
    public String getHealthservice() {
        if (locale.getLanguage().contains("pt")) {
            return "images/sus.png";       
        } else {
            return "images/info2.png";
        }
    }
    
}
