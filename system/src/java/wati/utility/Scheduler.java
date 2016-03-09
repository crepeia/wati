/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.utility;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import wati.controller.ContactController;
import wati.model.Contact;
import wati.model.EmailScheduler;
import wati.model.User;
import wati.persistence.GenericDAO;
import wati.persistence.UserDAO;

/**
 *
 * @author thiago
 */
@Singleton
public class Scheduler {

    @PersistenceContext
    private EntityManager entityManager = null;

    private UserDAO userDAO;
    private GenericDAO contactDAO;
    private EMailSSL emailSSL;
    private TemplateReader templateReader;

    public Scheduler() {
        try {
            userDAO = new UserDAO();
            contactDAO = new GenericDAO<>(Contact.class);
            emailSSL = new EMailSSL();
            templateReader = new TemplateReader();
        } catch (NamingException ex) {
            Logger.getLogger(EmailScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Schedule(second = "10", minute = "*", hour = "*", dayOfWeek = "*")
    public void testTask() {
        Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
    }

    @Schedule(minute = "*", hour = "*", dayOfWeek = "*")
    public void scheduleEmails() {
        sendDifferentDateEmail();
        sendFirstWeekEmail();
        sendSecondWeekEmail();
        sendThirdWeekEmail();
        sendMonthlyEmail();
    }

    public void sendDifferentDateEmail() {
        try {
            List<User> users = userDAO.acompanhamentoDataDiferente(entityManager);
            if (!users.isEmpty()) {
                for (User user : users) {
                    String htmlMessage = templateReader.fillContactTemplate(
                            getText("vivasemtabaco.title", user.getPreferedLanguage()),
                            getText("subject.email.followup", user.getPreferedLanguage()),
                            getDifferentEmailMessage(user.getPreferedLanguage(), "<br>"),
                            getFooter(user.getPreferedLanguage()));

                    Contact contact = new Contact();
                    contact.setSender("watiufjf@gmail.com");
                    contact.setRecipient(user.getEmail());
                    contact.setSubject(getText("subject.email.followup", user.getPreferedLanguage()));
                    contact.setText(getDifferentEmailMessage(user.getPreferedLanguage(), "\n"));
                    contact.setHtml(htmlMessage);
                    contact.setDateSent(new Date());
                    contact.setUser(user);

                    sendEmail(contact);
                    user.getProntoParaParar().setFollowUpCount(1);
                    userDAO.updateWithoutTransaction(user, entityManager);
                    contactDAO.insertOrUpdate(contact, entityManager);
                    Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, this.getText("email.data.diferente.enviado", user.getPreferedLanguage()) + user.getEmail());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendFirstWeekEmail() {
        try {
            List<User> users = userDAO.acompanhamentoSemanal(entityManager, 1);
            if (!users.isEmpty()) {
                for (User user : users) {
                    String htmlMessage = templateReader.fillContactTemplate(
                            getText("vivasemtabaco.title", user.getPreferedLanguage()),
                            getText("subject.email.followup", user.getPreferedLanguage()),
                            getFirstWeekMessage(user.getPreferedLanguage(), "<br>"),
                            getFooter(user.getPreferedLanguage()));

                    Contact contact = new Contact();
                    contact.setSender("watiufjf@gmail.com");
                    contact.setRecipient(user.getEmail());
                    contact.setSubject(getText("subject.email.followup", user.getPreferedLanguage()));
                    contact.setText(getFirstWeekMessage(user.getPreferedLanguage(), "\n"));
                    contact.setHtml(htmlMessage);
                    contact.setDateSent(new Date());
                    contact.setUser(user);

                    sendEmail(contact);
                    user.getProntoParaParar().setFollowUpCount(2);
                    userDAO.updateWithoutTransaction(user, entityManager);
                    contactDAO.insertOrUpdate(contact, entityManager);
                    Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, this.getText("email.primeira.semana.enviado", user.getPreferedLanguage()) + user.getEmail());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendSecondWeekEmail() {
        try {
            List<User> users = userDAO.acompanhamentoSemanal(entityManager, 2);
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
                    userDAO.updateWithoutTransaction(user, entityManager);
                    contactDAO.insertOrUpdate(contact, entityManager);
                    Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, this.getText("email.segunda.semana.enviado", user.getPreferedLanguage()) + user.getEmail());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendThirdWeekEmail() {
        try {
            List<User> users = userDAO.acompanhamentoSemanal(entityManager, 3);
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
                    userDAO.updateWithoutTransaction(user, entityManager);
                    contactDAO.insertOrUpdate(contact, entityManager);
                    Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, this.getText("email.terceira.semana.enviado", user.getPreferedLanguage()) + user.getEmail());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMonthlyEmail() {
        try {
            List<User> users = userDAO.acompanhamentoMensal(entityManager);
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
                    userDAO.updateWithoutTransaction(user, entityManager);
                    contactDAO.insertOrUpdate(contact, entityManager);
                    Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, this.getText("email.mensal.enviado", user.getPreferedLanguage()) + user.getEmail());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getDifferentEmailMessage(String language, String lineBreak) {
        return this.getText("hello", language) + "lineBreaklineBreak"
                + this.getText("msg.data.diferente1", language) + "lineBreak"
                + this.getText("msg.data.diferente2", language) + "lineBreak"
                + this.getText("msg.data.diferente3", language) + "lineBreaklineBreak"
                + this.getText("cordialmente", language) + "lineBreaklineBreak"
                + this.getText("equipe.vst", language);
    }

    public String getFirstWeekMessage(String language, String lineBreak) {
        return this.getText("hello", language) + "lineBreaklineBreak"
                + this.getText("msg.primeira.semana1", language) + "lineBreak"
                + this.getText("msg.primeira.semana2", language) + "lineBreak"
                + this.getText("msg.primeira.semana3", language) + "lineBreak"
                + this.getText("msg.primeira.semana4", language) + "lineBreak"
                + this.getText("msg.primeira.semana5", language) + "lineBreaklineBreak"
                + this.getText("msg.primeira.semana6", language) + "lineBreaklineBreak"
                + this.getText("cordialmente", language) + "lineBreaklineBreak"
                + this.getText("equipe.vst", language);
    }

    public String getSecondWeekMessage(String language, String lineBreak) {
        return this.getText("hello", language) + "lineBreaklineBreak"
                + this.getText("msg.segunda.semana1", language) + "lineBreaklineBreak"
                + this.getText("msg.segunda.semana2", language) + "lineBreak"
                + this.getText("msg.segunda.semana3", language) + "lineBreak"
                + this.getText("msg.segunda.semana4", language) + "lineBreaklineBreak"
                + this.getText("msg.segunda.semana5", language) + "lineBreaklineBreak"
                + this.getText("msg.segunda.semana6", language) + "lineBreak"
                + this.getText("msg.segunda.semana7", language) + "lineBreaklineBreak"
                + this.getText("cordialmente", language) + "lineBreaklineBreak"
                + this.getText("equipe.vst", language);
    }

    public String getThirdWeekMessage(String language, String lineBreak) {
        return this.getText("hello", language) + "lineBreaklineBreak"
                + this.getText("msg.terceira.semana1", language) + "lineBreaklineBreak"
                + this.getText("msg.terceira.semana2", language) + "lineBreaklineBreak"
                + this.getText("msg.terceira.semana3", language) + "lineBreak"
                + this.getText("msg.terceira.semana4", language) + "lineBreaklineBreak"
                + this.getText("cordialmente", language) + "lineBreaklineBreak"
                + this.getText("equipe.vst", language);
    }

    public String getMonthlyMessage(String language, String lineBreak) {
        return this.getText("hello", language) + "lineBreaklineBreak"
                + this.getText("msg.mensal1", language) + "lineBreaklineBreak"
                + this.getText("msg.mensal2", language) + "lineBreaklineBreak"
                + this.getText("msg.mensal3", language) + "lineBreak"
                + this.getText("msg.mensal4", language) + "lineBreaklineBreak"
                + this.getText("cordialmente", language) + "lineBreaklineBreak"
                + this.getText("equipe.vst", language);
    }

    public String getFooter(String language) {
        return this.getText("vivasemtabaco", language) + "<br>"
                + this.getText("crepeia", language) + "<br>"
                + this.getText("ufjf", language);
    }

    public void sendEmail(Contact contact) {
        try {
            emailSSL.send(contact.getSender(), contact.getRecipient(), contact.getSubject(), contact.getText(),
                    contact.getHtml(), contact.getPdf(), contact.getPdfName());
            contactDAO.insertOrUpdate(contact, entityManager);
        } catch (SQLException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getText(String key, String language) {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("wati.utility.messages", new Locale(language));
        return bundle.getString(key);
    }

}
