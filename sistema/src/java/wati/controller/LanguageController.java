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

    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    private Map<String, String> languages = new LinkedHashMap<String, String>();

    public LanguageController() {
        languages.put("English", "en");
        languages.put("Español", "es");
        languages.put("Português", "pt");
        // languages.put("Deutsch", "de");
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
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("locale", locale);
    }

    public Map<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }

}
