/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import wati.model.Contact;
import wati.model.ProntoParaParar;
import wati.model.User;
import wati.persistence.GenericDAO;
import wati.utility.EMailSSL;

/**
 *
 * @author hedersb
 */
@ManagedBean(name = "contatoController")
@RequestScoped
public class ContatoController extends BaseController implements Serializable{

    private EMailSSL eMailSSL;
    private String email;
    private String message;

    public ContatoController() {

        super();

        this.eMailSSL = new EMailSSL();

    }

    public void sendEmail() {

        eMailSSL.send(this.email, "watiufjf@gmail.com", "Viva Sem Tabaco -- Contato ("+email+")", message);
        Contact contact = new Contact();
        contact.setDateSent(new Date());
        contact.setMessage(message);
        contact.setSender(email);
        try {
           daoBase = new GenericDAO<Contact>(Contact.class);
            daoBase.insert(contact, getEntityManager());
        } catch (SQLException ex) {
            Logger.getLogger(ContatoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ContatoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String message = this.getText("mensagem.enviada");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
        Logger.getLogger(BaseFormController.class.getName()).log(Level.INFO, message);
        this.clear();

    }
    
    public void clear(){
        this.email = "";
        this.message = "";
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
