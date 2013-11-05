/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import wati.model.ProntoParaParar;

/**
 *
 * @author hedersb
 */
@ManagedBean(name = "prontoParaPararController")
@SessionScoped
public class ProntoParaPararController extends BaseController<ProntoParaParar> {
    
    private ProntoParaParar prontoParaParar;
    
    private String[] vencendoAFissuraMarcados = {};

    /**
     * @return the prontoParaParar
     */
    public ProntoParaParar getProntoParaParar() {
        return prontoParaParar;
    }

    /**
     * @param prontoParaParar the prontoParaParar to set
     */
    public void setProntoParaParar(ProntoParaParar prontoParaParar) {
        this.prontoParaParar = prontoParaParar;
    }
    private static final int VENCENDO_FISSURA_BEBER_AGUA = 0;
    private static final int VENCENDO_FISSURA_COMER = 1;
    private static final int VENCENDO_FISSURA_RELAXAMENTO = 2;
    private static final int VENCENDO_FISSURA_LER_RAZOES = 3;

    public ProntoParaPararController() {
        super(ProntoParaParar.class); 
        this.prontoParaParar = new ProntoParaParar();
    }
    
    public String vencendoAFissura() {
        //TODO: salvar
        return "pronto-para-parar-de-fumar-metodos-de-parar.xhtml";
    }
    
    public String fumarMetodosDeParar() {
        
        this.prontoParaParar.limparVencendoFissura();
        
        for (String string : vencendoAFissuraMarcados) {
            
            switch (Integer.valueOf(string)-1) {
                case VENCENDO_FISSURA_BEBER_AGUA:
                    this.prontoParaParar.setEnfrentarFissuraBeberAgua(true);
                    break;
                case VENCENDO_FISSURA_COMER:
                    this.prontoParaParar.setEnfrentarFissuraComer(true);
                    break;
                case VENCENDO_FISSURA_LER_RAZOES:
                    this.prontoParaParar.setEnfrentarFissuraLerRazoes(true);
                    break;
                case VENCENDO_FISSURA_RELAXAMENTO:
                    this.prontoParaParar.setEnfrentarFissuraRelaxamento(true);
                    break;
            }
            
        }
        try {
            this.getDaoBase().insertOrUpdate(prontoParaParar, this.getEntityManager());
            return "pronto-para-parar-de-fumar-metodos-de-parar.xhtml";
        } catch (SQLException ex) {
            Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    public void save(ActionEvent actionEvent) {
        
        
        
    }
    
    
    
}
