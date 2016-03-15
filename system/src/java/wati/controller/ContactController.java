/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.io.IOException;
import java.io.InputStream;
import wati.utility.Scheduler;
import java.io.Serializable;
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
@ManagedBean(name = "contactController")
@ApplicationScoped
public class ContactController extends BaseController implements Serializable {

    private EMailSSL eMailSSL;
    private String template;
    private UserDAO userDAO;
    private GenericDAO prontoDAO;

    public ContactController() {
        eMailSSL = new EMailSSL();
        template = readTemplate();
        try {
            daoBase = new GenericDAO<>(Contact.class);
            userDAO = new UserDAO();
            prontoDAO = new GenericDAO<>(ProntoParaParar.class);
        } catch (NamingException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public void sendTestEmail() {
        String[] emails = {"t.rizuti@gmail.com", "henriquepgomide@gmail.com", "cwsdanipereira@gmail.com",
            "feliperafael_95@yahoo.com.br", "leomartinsjf@gmail.com", "muncknathalia@gmail.com", "raizacampos2@gmail.com", "heder@ice.ufjf.br"};
        Contact contact;
        for (String email : emails) {
            try {
                contact = new Contact();
                contact.setSender("watiufjf@gmail.com");
                contact.setRecipient(email);
                contact.setSubject("TEST");
                contact.setHtml(fillTemplate(
                        getText("vivasemtabaco", "pt"),
                        getText("subject.email.followup", "pt"),
                        getText("subject.email.followup", "pt"),
                        getFooter("pt")));
                contact.setDateSent(new Date());
                sendEmail(contact);
                daoBase.insertOrUpdate(contact, getEntityManager());
                Logger.getLogger(ContactController.class.getName()).log(Level.INFO, "Test email  sent to:" + email);
            } catch (SQLException ex) {
                Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void sendDifferentDateEmail() {
        List<User> users = userDAO.followUpDifferentDate(getEntityManager());
        Contact contact;
        if (!users.isEmpty()) {
            for (User user : users) {
                try {
                    contact = new Contact();
                    contact.setSender("watiufjf@gmail.com");
                    contact.setRecipient(user.getEmail());
                    contact.setSubject(getText("subject.email.followup", user.getPreferedLanguage()));
                    contact.setHtml(fillTemplate(
                            getText("vivasemtabaco", user.getPreferedLanguage()),
                            getText("subject.email.followup", user.getPreferedLanguage()),
                            getText("subject.email.followup", user.getPreferedLanguage()),
                            getFooter(user.getPreferedLanguage())));
                    contact.setDateSent(new Date());
                    contact.setUser(user);
                    sendEmail(contact);
                    user.getProntoParaParar().setFollowUpCount(1);
                    prontoDAO.insertOrUpdate(user.getProntoParaParar(), getEntityManager());
                    daoBase.insertOrUpdate(contact, getEntityManager());
                    Logger.getLogger(ContactController.class.getName()).log(Level.INFO, "Different date follow up email  sent to:" + user.getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void sendFirstWeekEmail() {
        List<User> users = userDAO.followUpWeekly(getEntityManager(), 1);
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
                            getText("subject.email.followup", user.getPreferedLanguage()),
                            getFooter(user.getPreferedLanguage())));
                    contact.setDateSent(new Date());
                    contact.setUser(user);
                    sendEmail(contact);
                    user.getProntoParaParar().setFollowUpCount(2);
                    prontoDAO.insertOrUpdate(user.getProntoParaParar(), getEntityManager());
                    daoBase.insertOrUpdate(contact, getEntityManager());
                    Logger.getLogger(ContactController.class.getName()).log(Level.INFO, "Weekly follow up email sent to:" + user.getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void sendSecondWeekEmail() {
        List<User> users = userDAO.followUpWeekly(getEntityManager(), 2);
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
                            getText("subject.email.followup", user.getPreferedLanguage()),
                            getFooter(user.getPreferedLanguage())));
                    contact.setDateSent(new Date());
                    contact.setUser(user);
                    sendEmail(contact);
                    user.getProntoParaParar().setFollowUpCount(3);
                    prontoDAO.insertOrUpdate(user.getProntoParaParar(), getEntityManager());
                    daoBase.insertOrUpdate(contact, getEntityManager());
                    Logger.getLogger(ContactController.class.getName()).log(Level.INFO, "Weekly follow up email sent to:" + user.getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void sendThirdWeekEmail() {
        List<User> users = userDAO.followUpWeekly(getEntityManager(), 3);
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
                            getText("subject.email.followup", user.getPreferedLanguage()),
                            getFooter(user.getPreferedLanguage())));
                    contact.setDateSent(new Date());
                    contact.setUser(user);
                    sendEmail(contact);
                    user.getProntoParaParar().setFollowUpCount(4);
                    prontoDAO.insertOrUpdate(user.getProntoParaParar(), getEntityManager());
                    daoBase.insertOrUpdate(contact, getEntityManager());
                    Logger.getLogger(ContactController.class.getName()).log(Level.INFO, "Weekly follow up email sent to:" + user.getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
    
     public void sendPesquisaSatisfacao() {
        List<User> users = userDAO.followUpDifferentDate(getEntityManager());
        Contact contact;
        if (!users.isEmpty()) {
            for (User user : users) {
                try {
                    user.setPesquisaEnviada(true);
                    contact = new Contact();
                    contact.setSender("watiufjf@gmail.com");
                    contact.setRecipient(user.getEmail());
                    contact.setSubject(getText("subject.email.followup", user.getPreferedLanguage()));
                    contact.setHtml(fillTemplate(
                            getText("vivasemtabaco", user.getPreferedLanguage()),
                            "Participe da pesquisa de satisfação ",
                            user.getPesquisaSatisfacao().getUrlPesquisaSatisfacao(),
                            getFooter(user.getPreferedLanguage())));
                    contact.setDateSent(new Date());
                    contact.setUser(user);
                    sendEmail(contact);
                    daoBase.insertOrUpdate(contact, getEntityManager());
                    userDAO.insertOrUpdate(user, getEntityManager());
                    Logger.getLogger(ContactController.class.getName()).log(Level.INFO, "Different date follow up email  sent to:" + user.getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void sendMonthlyEmail() {
        List<User> users = userDAO.followUpMonthly(getEntityManager());
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
                            getText("subject.email.followup", user.getPreferedLanguage()),
                            getFooter(user.getPreferedLanguage())));
                    contact.setDateSent(new Date());
                    contact.setUser(user);
                    sendEmail(contact);
                    user.getProntoParaParar().setFollowUpCount(user.getProntoParaParar().getFollowUpCount() + 1);
                    prontoDAO.insertOrUpdate(user.getProntoParaParar(), getEntityManager());
                    daoBase.insertOrUpdate(contact, getEntityManager());
                    Logger.getLogger(ContactController.class.getName()).log(Level.INFO, "Monthly follow up email sent to:" + user.getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
    

    public void sendTwiceWeekEmail(){
        List<User> users = userDAO.followUpTwiceWeek(getEntityManager());

        Contact contact;
        if (!users.isEmpty()) {
            for (User user : users) {
                try {

                    if(user.getProntoParaParar().getFollowUpDayCount() == null){
                        user.getProntoParaParar().setFollowUpDayCount(1);
                    }

                    contact = new Contact();
                    contact.setSender("watiufjf@gmail.com");
                    contact.setRecipient(user.getEmail());
                    contact.setSubject(getText("subject.email.followup", user.getPreferedLanguage()));
                    contact.setHtml(fillTemplate(
                            getText("vivasemtabaco.title", user.getPreferedLanguage()),
                            getText("msg.email.diario." + user.getProntoParaParar().getFollowUpDayCount(), user.getPreferedLanguage()),
                            getText("subject.email.followup", user.getPreferedLanguage()),

                            getFooter(user.getPreferedLanguage())));
                    contact.setDateSent(new Date());
                    contact.setUser(user);
                    sendEmail(contact);
                    user.getProntoParaParar().setFollowUpDayCount(user.getProntoParaParar().getFollowUpDayCount() + 1);
                    prontoDAO.insertOrUpdate(user.getProntoParaParar(), getEntityManager());
                    daoBase.insertOrUpdate(contact, getEntityManager());
                    Logger.getLogger(ContactController.class.getName()).log(Level.INFO, "Daily follow up email sent to:" + user.getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
    
    public String getFooter(String language) {
        return this.getText("vivasemtabaco", language) + "<br>"
                + this.getText("crepeia", language) + "<br>"
                + this.getText("ufjf", language);
    }

    public String readTemplate() {
        try {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("wati/utility/contact-template.html");
            byte[] buffer = new byte[10240];
            return new String(buffer, 0, input.read(buffer));
        } catch (IOException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /*public String readTemplate() {
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
     }*/

    public String fillTemplate(String title, String subtitle, String content, String footer) {
        String newTemplate = template;
        if (title != null) {
            newTemplate = newTemplate.replace("#title#", title);
        }
        if (subtitle != null) {
            newTemplate = newTemplate.replace("#subtitle#", subtitle);
        }
        if (content != null) {
            newTemplate = newTemplate.replace("#content#", content);
        }
        if (footer != null) {
            newTemplate = newTemplate.replace("#footer#", footer);
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
