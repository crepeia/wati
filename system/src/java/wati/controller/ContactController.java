/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.io.IOException;
import wati.utility.Scheduler;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import wati.model.Contact;
import wati.model.User;
import wati.persistence.GenericDAO;
import wati.persistence.UserDAO;
import wati.utility.EMailSSL;
import wati.utility.TemplateReader;

/**
 *
 * @author hedersb
 */
@ManagedBean(name = "contactController")
@ApplicationScoped
public class ContactController extends BaseController implements Serializable {

    private EMailSSL eMailSSL;
    private String template;
    private UserDAO userDAO;

    public ContactController() {
        eMailSSL = new EMailSSL();
        template = readTemplate();
        try {
            daoBase = new GenericDAO<>(Contact.class);
            userDAO = new UserDAO();
        } catch (NamingException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void task() {
        System.out.println("test");
        try {
            daoBase.insertOrUpdate(new Contact(), getEntityManager());
        } catch (SQLException ex) {
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

    public void sendDifferentDateEmail() {
        List<User> users = userDAO.acompanhamentoDataDiferente(getEntityManager());
        Contact contact;
        if (!users.isEmpty()) {
            for (User user : users) {
                try {
                    contact = new Contact();
                    contact.setSender("watiufjf@gmail.com");
                    contact.setRecipient(user.getEmail());
                    contact.setSubject(getText("subject.email.followup", user.getPreferedLanguage()));
                    contact.setHtml(fillTemplate(
                            getText("vivasemtabaco.title", user.getPreferedLanguage()),
                            getText("subject.email.followup", user.getPreferedLanguage()),
                            getText("email.msg", user.getPreferedLanguage()),
                            getFooter(user.getPreferedLanguage())));
                    contact.setDateSent(new Date());
                    contact.setUser(user);
                    sendEmail(contact);
                    user.getProntoParaParar().setFollowUpCount(2);
                    userDAO.insertOrUpdate(user, getEntityManager());
                    daoBase.insertOrUpdate(contact, getEntityManager());
                    Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, "DifferLent date email sent to:" + user.getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    /*public void sendFirstWeekEmail() {
     try {
     List<User> users = userDAO.acompanhamentoSemanal(getEntityManager(), 1);
     if (!users.isEmpty()) {
     for (User user : users) {
     String htmlMessage = templateReader.fillContactTemplate(
     getText("vivasemtabaco.title", user.getPreferedLanguage()),
     getText("subject.email.followup", user.getPreferedLanguage()),
     getText("email.msg"),
     getFooter(user.getPreferedLanguage()));

     Contact contact = new Contact();
     contact.setSender("watiufjf@gmail.com");
     contact.setRecipient(user.getEmail());
     contact.setSubject(getText("subject.email.followup", user.getPreferedLanguage()));
     contact.setHtml(htmlMessage);
     contact.setDateSent(new Date());
     contact.setUser(user);

     sendEmail(contact);
     user.getProntoParaParar().setFollowUpCount(2);
     userDAO.updateWithoutTransaction(user, getEntityManager());
     daoBase.insertOrUpdate(contact, getEntityManager());
     Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, this.getText("email.primeira.semana.enviado", user.getPreferedLanguage()) + user.getEmail());
     }
     }

     } catch (SQLException ex) {
     Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
     }
     }

     public void sendSecondWeekEmail() {
     try {
     List<User> users = userDAO.acompanhamentoSemanal(getEntityManager(), 2);
     if (!users.isEmpty()) {
     for (User user : users) {
     String htmlMessage = templateReader.fillContactTemplate(
     getText("vivasemtabaco.title", user.getPreferedLanguage()),
     getText("subject.email.followup", user.getPreferedLanguage()),
     getSecondWeekMessage(user.getPreferedLanguage(), "<br>"),
     getFooter(user.getPreferedLanguage()));

     Contact contact = new Contact();
     contact.setSender("watiufjf@gmail.com");
     contact.setRecipient(user.getEmail());
     contact.setSubject(getText("subject.email.followup", user.getPreferedLanguage()));
     contact.setText(getSecondWeekMessage(user.getPreferedLanguage(), "\n"));
     contact.setHtml(htmlMessage);
     contact.setDateSent(new Date());
     contact.setUser(user);

     sendEmail(contact);
     user.getProntoParaParar().setFollowUpCount(3);
     userDAO.updateWithoutTransaction(user, getEntityManager());
     daoBase.insertOrUpdate(contact, getEntityManager());
     Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, this.getText("email.segunda.semana.enviado", user.getPreferedLanguage()) + user.getEmail());
     }
     }

     } catch (SQLException ex) {
     Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
     }
     }

     public void sendThirdWeekEmail() {
     try {
     List<User> users = userDAO.acompanhamentoSemanal(getEntityManager(), 3);
     if (!users.isEmpty()) {
     for (User user : users) {
     String htmlMessage = templateReader.fillContactTemplate(
     getText("vivasemtabaco.title", user.getPreferedLanguage()),
     getText("subject.email.followup", user.getPreferedLanguage()),
     getThirdWeekMessage(user.getPreferedLanguage(), "<br>"),
     getFooter(user.getPreferedLanguage()));

     Contact contact = new Contact();
     contact.setSender("watiufjf@gmail.com");
     contact.setRecipient(user.getEmail());
     contact.setSubject(getText("subject.email.followup", user.getPreferedLanguage()));
     contact.setText(getThirdWeekMessage(user.getPreferedLanguage(), "\n"));
     contact.setHtml(htmlMessage);
     contact.setDateSent(new Date());
     contact.setUser(user);

     sendEmail(contact);
     user.getProntoParaParar().setFollowUpCount(4);
     userDAO.updateWithoutTransaction(user, getEntityManager());
     daoBase.insertOrUpdate(contact, getEntityManager());
     Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, this.getText("email.terceira.semana.enviado", user.getPreferedLanguage()) + user.getEmail());
     }
     }

     } catch (SQLException ex) {
     Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
     }
     }

     public void sendMonthlyEmail() {
     try {
     List<User> users = userDAO.acompanhamentoMensal(getEntityManager());
     if (!users.isEmpty()) {
     for (User user : users) {
     String htmlMessage = templateReader.fillContactTemplate(
     getText("vivasemtabaco.title", user.getPreferedLanguage()),
     getText("subject.email.followup", user.getPreferedLanguage()),
     getMonthlyMessage(user.getPreferedLanguage(), "<br>"),
     getFooter(user.getPreferedLanguage()));

     Contact contact = new Contact();
     contact.setSender("watiufjf@gmail.com");
     contact.setRecipient(user.getEmail());
     contact.setSubject(getText("subject.email.followup", user.getPreferedLanguage()));
     contact.setText(getMonthlyMessage(user.getPreferedLanguage(), "\n"));
     contact.setHtml(htmlMessage);
     contact.setDateSent(new Date());
     contact.setUser(user);

     sendEmail(contact);
     user.getProntoParaParar().setFollowUpCount(user.getProntoParaParar().getFollowUpCount() + 1);
     userDAO.updateWithoutTransaction(user, getEntityManager());
     daoBase.insertOrUpdate(contact, getEntityManager());
     Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, this.getText("email.mensal.enviado", user.getPreferedLanguage()) + user.getEmail());
     }
     }

     } catch (SQLException ex) {
     Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
     }
     }*/
    
    public String getFooter(String language) {
        return this.getText("vivasemtabaco", language) + "<br>"
                + this.getText("crepeia", language) + "<br>"
                + this.getText("ufjf", language);
    }

    public String readTemplate() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            String absolutePath = servletContext.getRealPath("/resources/default/templates/contact-template.html");
            byte[] encoded = Files.readAllBytes(Paths.get(absolutePath));
            String template = new String(encoded, StandardCharsets.UTF_8);
            return template;
        } catch (IOException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String fillTemplate(String title, String subtitle, String content, String footer) {
        String newTemplate = template;
        if (title != null) {
            newTemplate = template.replace("#title#", title);
        }
        if (subtitle != null) {
            newTemplate = template.replace("#subtitle#", subtitle);
        }
        if (content != null) {
            newTemplate = template.replace("#content#", content);
        }
        if (footer != null) {
            newTemplate = template.replace("#footer#", footer);
        }

        return newTemplate;
    }

    public String getText(String key, String language) {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("wati.utility.messages", new Locale(language));
        return bundle.getString(key);
    }

    public EMailSSL geteMailSSL() {
        return eMailSSL;
    }

    public void seteMailSSL(EMailSSL eMailSSL) {
        this.eMailSSL = eMailSSL;
    }

}
