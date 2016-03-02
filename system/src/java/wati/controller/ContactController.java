/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
public class ContactController extends BaseController {

    private EMailSSL eMailSSL;
    private ScheduledExecutorService scheduler;
    private TemplateReader templateReader;

    @PostConstruct
    public void init() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new DailyEmails(), 0, 1, TimeUnit.DAYS);
    }

    @PreDestroy
    public void destroy() {
        scheduler.shutdownNow();
    }

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

    public class DailyEmails implements Runnable {

        @PersistenceContext
        private EntityManager entityManager = null;

        @Override
        public void run() {
            try {
                UserDAO userDAO = new UserDAO(User.class);
                List<User> users;
                String htmlMessage;
                String htmlFooter;
                String textMessage;

                //First week emails
                users = userDAO.acompanhamentoSemanal(entityManager, 1);
                if (!users.isEmpty()) {
                    for (User user : users) {
                        htmlMessage = this.getText("hello", user.getPreferedLanguage()) + "<br><br>"
                                + this.getText("msg.primeira.semana1", user.getPreferedLanguage()) + "<br>"
                                + this.getText("msg.primeira.semana2", user.getPreferedLanguage()) + "<br>"
                                + this.getText("msg.primeira.semana3", user.getPreferedLanguage()) + "<br>"
                                + this.getText("msg.primeira.semana4", user.getPreferedLanguage()) + "<br>"
                                + this.getText("msg.primeira.semana5", user.getPreferedLanguage()) + "<br><br>"
                                + this.getText("msg.primeira.semana6", user.getPreferedLanguage()) + "<br><br>"
                                + this.getText("cordialmente", user.getPreferedLanguage()) + "<br><br>"
                                + this.getText("equipe.vst", user.getPreferedLanguage());
                        textMessage = this.getText("hello", user.getPreferedLanguage()) + "/n/n"
                                + this.getText("msg.primeira.semana1", user.getPreferedLanguage()) + "/n"
                                + this.getText("msg.primeira.semana2", user.getPreferedLanguage()) + "/n"
                                + this.getText("msg.primeira.semana3", user.getPreferedLanguage()) + "/n"
                                + this.getText("msg.primeira.semana4", user.getPreferedLanguage()) + "/n"
                                + this.getText("msg.primeira.semana5", user.getPreferedLanguage()) + "/n/n"
                                + this.getText("msg.primeira.semana6", user.getPreferedLanguage()) + "/n/n"
                                + this.getText("cordialmente", user.getPreferedLanguage()) + "/n/n"
                                + this.getText("equipe.vst", user.getPreferedLanguage());
                        htmlFooter = this.getText("vivasemtabaco", user.getPreferedLanguage()) + "<br>" 
                                + this.getText("crepeia", user.getPreferedLanguage()) + "<br>"
                                + this.getText("ufjf", user.getPreferedLanguage());
                        htmlMessage = templateReader.fillContactTemplate(getText("vivasemtabaco.title", user.getPreferedLanguage()),
                                getText("msg.primeira.semana0", user.getPreferedLanguage()),
                                htmlMessage,
                                getText("msg.primeira.semana0", user.getPreferedLanguage()));
                        Contact contact = new Contact();
                        contact.setSender("watiufjf@gmail.com");
                        contact.setRecipient(user.getEmail());
                        contact.setSubject(getText("msg.primeira.semana0", user.getPreferedLanguage()));
                        contact.setText(textMessage);
                        contact.setHtml(htmlMessage);
                        contact.setDateSent(new Date());
                        sendEmail(contact);
                        user.getProntoParaParar().setEmailPrimeiraSemana(new Date());
                        userDAO.updateWithoutTransaction(user, entityManager);
                        Logger.getLogger(DailyEmails.class.getName()).log(Level.INFO, this.getText("email.data.diferente.enviado", user.getPreferedLanguage()) + user.getEmail());

                    }
                }

            } catch (NamingException | SQLException ex) {
                Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        public String getText(String key, String language) {
            ResourceBundle bundle = PropertyResourceBundle.getBundle("wati.utility.messages", new Locale(language));
            return bundle.getString(key);
        }
    }

}
