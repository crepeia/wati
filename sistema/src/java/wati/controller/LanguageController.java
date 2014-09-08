/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
        locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        languages.put("English", "en");
        languages.put("Español", "es");
        languages.put("Português", "pt");
        languages.put("Deutsch", "de");
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
        //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("locale", locale);
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
