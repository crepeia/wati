/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author hedersb
 */
@ManagedBean(name = "prontoParaPararController")
@SessionScoped
public class ProntoParaPararController {
    
    private String[] vencendoAFissuraMarcados = {};

    public ProntoParaPararController() {
    }
    
    public String vencendoAFissura() {
        //TODO: salvar
        return "pronto-para-parar-de-fumar-metodos-de-parar.xhtml";
    }
    
    public String fumarMetodosDeParar() {
        //TODO: salvar
        return "pronto-para-parar-de-fumar-metodos-de-parar.xhtml";
    }
 
    /**
     * @return the vencendoAFissuraMarcados
     */
    public String[] getVencendoAFissuraMarcados() {
        return vencendoAFissuraMarcados;
    }

    /**
     * @param vencendoAFissuraMarcados the vencendoAFissuraMarcados to set
     */
    public void setVencendoAFissuraMarcados(String[] vencendoAFissuraMarcados) {
        this.vencendoAFissuraMarcados = vencendoAFissuraMarcados;
    }
    
}
