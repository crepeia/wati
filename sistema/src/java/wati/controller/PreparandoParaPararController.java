/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.util.PropertyResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import wati.model.User;

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
                texto = this.getText("preparando.aed.p.3");
            else
                texto = this.getText("preparando.aed.p.3");
            
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
        
        

    
}
