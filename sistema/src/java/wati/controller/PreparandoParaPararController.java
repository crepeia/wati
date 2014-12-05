/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import wati.model.PageNavigation;
import wati.model.ProntoParaParar;
import wati.model.User;
import wati.persistence.GenericDAO;

/**
 *
 * @author hedersb
 */
@ManagedBean(name = "preparandoParaPararController")
@SessionScoped
public class PreparandoParaPararController extends BaseController {

    private int question1;
    private int question2;
    private String texto;
    
    private int phq2_1;
    private int phq2_2;
    

    public PreparandoParaPararController() {
        this.question1 = 3;
        this.question2 = 3;
        this.texto = "";
    }
	
	public String prontoParaPararDeFumar() {
		
		Object obj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedUser");
		if ( obj != null ) {
			User user = (User) obj;
			if ( user.getId() > 0 ) {
				return "pronto-para-parar-de-fumar-introducao.xhtml";
			}
		}
		
		return "cadastrar-nova-conta.xhtml";
		
	}
        
        public void avaliar(){
            if(question1 == 1 || question2 == 1)
                texto = this.getText("preparando.aed.p.2");
            else
                texto = this.getText("preparando.aed.p.3");
            
        }
        
    public void evaluateScalePhq2(){
        int sum1 = this.phq2_1;
        int sum2 = this.phq2_2;
        int sumTotal = sum1 + sum2;
        System.out.println("Soma:" + sumTotal);
        if(2<= sumTotal && sumTotal<=5)
            System.out.println("fazer o que");
        else if(6 <= sumTotal && sumTotal <= 8)
            System.out.println("ruim");
        else
            System.out.println("Que bom");
    }    

    public String getTexto() {
         return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
        
        
        
        
        
    public int getQuestion1() {
        return question1;
    }

    public void setQuestion1(int question1) {
        this.question1 = question1;
    }

    public int getQuestion2() {
        return question2;
    }

    public void setQuestion2(int question2) {
        this.question2 = question2;
    }

    public int getPhq2_1() {
        return phq2_1;
    }

    public void setPhq2_1(int phq2_1) {
        this.phq2_1 = phq2_1;
    }

    public int getPhq2_2() {
        return phq2_2;
    }

    public void setPhq2_2(int phq_2) {
        this.phq2_2 = phq_2;
    }
    
    
        
        

    
}
