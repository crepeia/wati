/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import wati.model.Contact;
import wati.persistence.GenericDAO;
import wati.utility.EMailSSL;

/**
 *
 * @author hedersb
 */
@ManagedBean(name = "contactController")
@ApplicationScoped
public class ContactController extends BaseController{

    private EMailSSL eMailSSL;
    private Contact contact;


    public ContactController() {
        eMailSSL = new EMailSSL();
        contact = new Contact();
        try {
            daoBase = new GenericDAO<>(Contact.class);
        } catch (NamingException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void contactFormSend(){
        contact.setDateSent(new Date());
        contact.setSubject("Contato via formulário - " + contact.getSender());
        contact.setRecipient("watiufjf@gmail.com");
        sendEmail();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, getText("mensagem.enviada"), null));

    }
    
    public void sendEmail() {
        try {          
            eMailSSL.send(contact.getSender(), contact.getRecipient(), contact.getSubject(), contact.getText(),
            contact.getHtml(), contact.getPdf(), contact.getPdfName());
            daoBase.insertOrUpdate(contact, getEntityManager());
            contact = new Contact();
        } catch (SQLException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public EMailSSL geteMailSSL() {
        return eMailSSL;
    }

    public void seteMailSSL(EMailSSL eMailSSL) {
        this.eMailSSL = eMailSSL;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    
    


}
