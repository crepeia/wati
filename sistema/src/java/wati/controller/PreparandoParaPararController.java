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
public class PreparandoParaPararController {
    
    private int pergunta1;
    private int pergunta2;
    private String texto;
    

    public PreparandoParaPararController() {
        this.pergunta1 = 3;
        this.pergunta2 = 3;
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
            if(pergunta1 == 1 || pergunta2 == 1)
                texto = PropertyResourceBundle.getBundle("wati.utility.messages").getString("preparando.aed.p.2");
            else
                texto = PropertyResourceBundle.getBundle("wati.utility.messages").getString("preparando.aed.p.3");
            
        }

    public String getTexto() {
         return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
        
        
        
        
        
    public int getPergunta1() {
        return pergunta1;
    }

    public void setPergunta1(int pergunta1) {
        this.pergunta1 = pergunta1;
    }

    public int getPergunta2() {
        return pergunta2;
    }

    public void setPergunta2(int pergunta2) {
        this.pergunta2 = pergunta2;
    }
        
        

    
}
