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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import wati.model.Contact;
import wati.persistence.GenericDAO;
import wati.utility.EMailSSL;
import wati.utility.TemplateReader;

/**
 *
 * @author hedersb
 */
@ManagedBean(name = "contactController")
@SessionScoped
public class ContactController extends BaseController {

    private EMailSSL eMailSSL;
    private TemplateReader templateReader;

    public ContactController() {
        eMailSSL = new EMailSSL();
        templateReader = new TemplateReader();
        try {
            daoBase = new GenericDAO<>(Contact.class);
        } catch (NamingException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void contactFormSend(String sender, String message) {
        Contact contact = new Contact();
        contact.setSender(sender);
        contact.setRecipient("watiufjf@gmail.com");
        contact.setSubject("Contato via formulário - " + contact.getSender());
        contact.setText(message);
        contact.setDateSent(new Date());
        sendEmail(contact);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, getText("mensagem.enviada"), null));

    }

    public void sendEmail(Contact contact) {
        try {
            eMailSSL.send(contact.getSender(), contact.getRecipient(), contact.getSubject(), contact.getText(),
                    contact.getHtml(), contact.getPdf(), contact.getPdfName());
            daoBase.insertOrUpdate(contact, getEntityManager());
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
 
}
