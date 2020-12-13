/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import wati.model.Contact;
import wati.model.ProntoParaParar;
import wati.model.User;
import wati.persistence.GenericDAO;
import wati.persistence.UserDAO;
import wati.utility.EMailSSL;

/**
 *
 * @author hedersb
 */
@Named( "contactController")
@ApplicationScoped
public class ContactController extends BaseController implements Serializable {

    private EMailSSL eMailSSL;
    private String htmlTemplate;
    private UserDAO userDAO;
    private GenericDAO prontoDAO;
    
    private final String SENDER = "contato@vivasemtabaco.com.br"; 

    public ContactController() {
        eMailSSL = new EMailSSL();
        htmlTemplate = readHTMLTemplate("wati/utility/contact-template.html");
        try {
            daoBase = new GenericDAO<>(Contact.class);
            userDAO = new UserDAO();
            prontoDAO = new GenericDAO<>(ProntoParaParar.class);
        } catch (NamingException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendContactFormEmail(ActionEvent event) {
        String email = (String) event.getComponent().getAttributes().get("email");
        String message = (String) event.getComponent().getAttributes().get("msg");
        Contact contact = new Contact();
        contact.setSender(email);
        contact.setRecipient(SENDER);
        contact.setSubject("Contato via formulario - " + email);
        contact.setContent(message);
        sendPlainTextEmail(contact);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, getText("mensagem.enviada"), null));
    }

    public void scheduleDifferentDateEmail(User user, Date date) {
        Contact contact = new Contact();
        contact.setUser(user);
        contact.setSender(SENDER);
        contact.setRecipient(user.getEmail());
        contact.setSubject("subject.email.followup");
        contact.setContent("msg.data.diferente1");
        contact.setDateScheduled(date);
        save(contact);
    }

    public void scheduleWeeklyEmail(User user, Date date) {
        Contact contact;
        for (int i = 1; i <= 3; i++) {
            contact = new Contact();
            contact.setUser(user);
            contact.setSender(SENDER);
            contact.setRecipient(user.getEmail());
            contact.setSubject("subject.email.followup");
            if (i == 1) {
                contact.setContent("msg.primeira.semana1");
            }
            if (i == 2) {
                contact.setContent("msg.segunda.semana1");
            }
            if (i == 3) {
                contact.setContent("msg.terceira.semana1");
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 7 * i);
            contact.setDateScheduled(cal.getTime());
            save(contact);
        }
    }

    public void scheduleMonthlyEmail(User user, Date date) {
        Contact contact;
        for (int i = 1; i <= 12; i++) {
            contact = new Contact();
            contact.setUser(user);
            contact.setSender(SENDER);
            contact.setRecipient(user.getEmail());
            contact.setSubject("subject.email.followup");
            contact.setContent("msg.mensal1");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, i);
            contact.setDateScheduled(cal.getTime());
            save(contact);
        }
    }

    public void scheduleDaillyEmail(User user, Date date,int day) {
        Contact contact = new Contact();
        contact.setUser(user);
        contact.setSender(SENDER);
        contact.setRecipient(user.getEmail());
        contact.setSubject("subject.email.followup");
        contact.setContent("msg.email.twice." + ((day + 1) / 2));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        contact.setDateScheduled(cal.getTime());
        save(contact);
    }
    
    public void scheduleReaserach7DaysEmail(User user, Date date) {
        Contact contact = new Contact();
        contact.setUser(user);
        contact.setSender(SENDER);
        contact.setRecipient(user.getEmail());
        contact.setSubject("msg.1semana.r.subject");
        contact.setContent("msg.1semana.r.body");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 7);
        contact.setDateScheduled(cal.getTime());
        save(contact);
    }
    
    public void scheduleReaserachXMonthsEmail(User user, Date date, int months) {
        Contact contact = new Contact();
        contact.setUser(user);
        contact.setSender(SENDER);
        contact.setRecipient(user.getEmail());
        contact.setSubject("msg."+months+"mes.r.subject");
        contact.setContent("msg."+months+"mes.r.body");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        contact.setDateScheduled(cal.getTime());
        save(contact);
    }
    
    public void scheduleReaserach12MonthsEmailFix() {
        try {
            List<Contact> contacts = daoBase.list("subject","msg.6mes.r.subject" , getEntityManager());
            for (Contact c : contacts) {
                Contact contact = new Contact();
                contact.setUser(c.getUser());
                contact.setSender(SENDER);
                contact.setRecipient(c.getRecipient());
                contact.setSubject("msg.12mes.r.subject");
                contact.setContent("msg.12mes.r.body");
                Calendar cal = Calendar.getInstance();
                cal.setTime(c.getDateScheduled());
                cal.add(Calendar.MONTH, 6);
                contact.setDateScheduled(cal.getTime());
                save(contact);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContactController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void sendPesquisaSatisfacaoEmail(User user) {
        Contact contact = new Contact();
        contact.setUser(user);
        contact.setSender(SENDER);
        contact.setRecipient(user.getEmail());
        contact.setSubject("msg.satisfaction.header");
        contact.setContent("msg.pesquisa.satisfacao.body");
        sendHTMLEmail(contact);
    }

    private void sendHTMLEmail(Contact contact) {
        
        try{    
            String content = getContent(contact);
            String subject = getSubject(contact);
            eMailSSL.send(contact.getSender(), contact.getRecipient(), subject, content, contact.getPdf(), contact.getAttachment());
            contact.setDateSent(new Date());
            save(contact);
            Logger.getLogger(ContactController.class.getName()).log(Level.INFO, "Email sent to: " + contact.getRecipient());
        } catch (MessagingException | MissingResourceException ex ) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void sendPlainTextEmail(Contact contact) {
        try {
            eMailSSL.send(contact.getSender(), contact.getRecipient(), contact.getSubject(), contact.getContent(), contact.getPdf(), contact.getAttachment());
            contact.setDateSent(new Date());
            save(contact);
            Logger.getLogger(ContactController.class.getName()).log(Level.INFO, "Email sent to: " + contact.getRecipient());
        } catch (MessagingException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearFollowUpEmails(User user) {
        try {
            List<Contact> contacts = daoBase.list("user", user, getEntityManager());
            for (Contact contact : contacts) {
                if (contact.getDateScheduled() != null && contact.getDateSent() == null) {
                    if (!contact.getSubject().contains("r.subject")) {
                        daoBase.delete(contact, getEntityManager());
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContactController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendScheduledEmails() {
        try {
            Logger.getLogger(ContactController.class.getName()).log(Level.INFO, "Sending scheduled emails");
            List<Contact> contacts = daoBase.list(getEntityManager());
            Calendar today = Calendar.getInstance();
            Calendar scheduledDate = Calendar.getInstance();
            int count = 0;
            for (Contact contact : contacts) {
                if (contact.getDateScheduled() != null && contact.getDateSent() == null) {
                    scheduledDate.setTime(contact.getDateScheduled());
                    if (today.compareTo(scheduledDate) >= 0) {
                        sendHTMLEmail(contact);
                        count++;
                    }
                }
                if(count >= 99){
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void save(Contact contact) {
        try {
            daoBase.insertOrUpdate(contact, getEntityManager());

        } catch (SQLException ex) {
            Logger.getLogger(ContactController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String readHTMLTemplate(String path) {
        try {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            byte[] buffer = new byte[102400];
            return new String(buffer, 0, input.read(buffer), StandardCharsets.UTF_8);

        } catch (IOException ex) {
            Logger.getLogger(ContactController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String getContent(Contact contact) {
        String htmlMessage = htmlTemplate;
        ResourceBundle bundle = PropertyResourceBundle.getBundle("wati.utility.messages", new Locale(contact.getUser().getPreferedLanguage()));
        htmlMessage = htmlMessage.replace("#title#", bundle.getString("vivasemtabaco"));
        htmlMessage = htmlMessage.replace("#content#", bundle.getString(contact.getContent()));
        htmlMessage = htmlMessage.replace("#footer#",
                bundle.getString("vivasemtabaco") + "<br>"
                + bundle.getString("crepeia") + "<br>"
                + bundle.getString("ufjf"));
        htmlMessage = htmlMessage.replace("#user#", contact.getUser().getName());
        htmlMessage = htmlMessage.replace("#email#", contact.getUser().getEmail());
        htmlMessage = htmlMessage.replace("#code#", String.valueOf(contact.getUser().getRecoverCode()));
        htmlMessage = htmlMessage.replace("#link#", "http://www.vivasemtabaco.com.br/pesquisa-satisfacao.xhtml?uid=" + contact.getUser().getHashedId());
        htmlMessage = htmlMessage.replace("#HID#", contact.getUser().getHashedId());
        return htmlMessage;
    }

    private String getSubject(Contact contact) throws MissingResourceException {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("wati.utility.messages", new Locale(contact.getUser().getPreferedLanguage()));
        return bundle.getString(contact.getSubject());
    }

}
