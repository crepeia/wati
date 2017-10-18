/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import wati.model.User;
import wati.utility.Encrypter;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController extends BaseFormController<User> {

    private User user = new User();
    private String password;
    private boolean showName;

    private String logout;

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {

        super(User.class);

        this.password = "";

    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public void loginDialog() {

        try {
            List<User> userList = this.getDaoBase().list("email", this.user.getEmail(), this.getEntityManager());

            if (userList.isEmpty() || !Encrypter.compare(this.password, userList.get(0).getPassword())) {
                //log message
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, this.getText("usuario.email") + this.getUser().getEmail() + this.getText("not.login"));
                //message to the user
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, this.getText("email.senha"), null));

            } else {

                this.user = userList.get(0);

//                              FacesContext facesContext = FacesContext.getCurrentInstance();
//                              HttpSession session = (HttpSession) facesContext.getExternalContext().getSession( false );
//                              session.setAttribute( "loggedUser" , userList.get( 0 ));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedUser", userList.get(0));
                String language = userList.get(0).getPreferedLanguage();
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                LanguageController languageController = (LanguageController) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "languageController");
                languageController.setLanguage(language);

                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                String url = ((HttpServletRequest) request).getRequestURI();
                url = url.substring(url.lastIndexOf('/') + 1);
                if (url.contains("cadastrar-nova-conta") || url.contains("login") ){
                    url = "index.xhtml";
                }
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(url);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Logger.getLogger(LoginController.class.getName()).log(Level.INFO, this.getText("user") + this.getUser().getName() + this.getText("login"));

            }

        } catch (InvalidKeyException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, this.getText("mensagem.delete2"), null));
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        if (this.user.getId() > 0) {
            this.showName = true;
        } else {
            this.showName = false;
        }

    }

    public void loginBegin() {

        try {

            List<User> userList = this.getDaoBase().list("email", this.user.getEmail(), this.getEntityManager());

            if (userList.isEmpty() || !Encrypter.compare(this.password, userList.get(0).getPassword())) {
                //log message
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, this.getText("usuario.email") + this.getUser().getEmail() + this.getText("not.login"));
                //message to the user
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, this.getText("email.senha"), null));

            } else {

                this.user = userList.get(0);

//                              FacesContext facesContext = FacesContext.getCurrentInstance();
//                              HttpSession session = (HttpSession) facesContext.getExternalContext().getSession( false );
//                              session.setAttribute( "loggedUser" , userList.get( 0 ));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedUser", userList.get(0));
                String language = userList.get(0).getPreferedLanguage();
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                LanguageController languageController = (LanguageController) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "languageController");
                languageController.setLanguage(language);
                Logger.getLogger(LoginController.class.getName()).log(Level.INFO, this.getText("user") + this.getUser().getName() + this.getText("login"));

                if (this.user.getId() > 0) {
                    this.showName = true;
                } else {
                    this.showName = false;
                }

                //Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("url");
                //if (object != null) {
                //String url = (String) object;
                try {

                    //Logger.getLogger(LoginController.class.getName()).log(Level.INFO, url);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("escolha-uma-etapa.xhtml");
                    //FacesContext.getCurrentInstance().getExternalContext().redirect(url);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        } catch (InvalidKeyException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, this.getText("mensagem.delete2"), null));
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    public void login() {

        try {

            List<User> userList = this.getDaoBase().list("email", this.user.getEmail(), this.getEntityManager());

            if (userList.isEmpty() || !Encrypter.compare(this.password, userList.get(0).getPassword())) {
                //log message
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, this.getText("usuario.email") + this.getUser().getEmail() + this.getText("not.login"));
                //message to the user
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, this.getText("email.senha"), null));

            } else {

                this.user = userList.get(0);

//                              FacesContext facesContext = FacesContext.getCurrentInstance();
//                              HttpSession session = (HttpSession) facesContext.getExternalContext().getSession( false );
//                              session.setAttribute( "loggedUser" , userList.get( 0 ));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedUser", userList.get(0));
                String language = userList.get(0).getPreferedLanguage();
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                LanguageController languageController = (LanguageController) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "languageController");
                languageController.setLanguage(language);
                Logger.getLogger(LoginController.class.getName()).log(Level.INFO, this.getText("user") + this.getUser().getName() + this.getText("login"));

                if (this.user.getId() > 0) {
                    this.showName = true;
                } else {
                    this.showName = false;
                }
                
                FacesContext.getCurrentInstance().getExternalContext().redirect("escolha-uma-etapa.xhtml");
                         

            }

        } catch (InvalidKeyException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, this.getText("mensagem.delete2"), null));
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //TODO -- Fix the logout system functionality
    public String getLogout() {

        Logger.getLogger(LoginController.class.getName()).log(Level.INFO, this.getText("user") + this.getUser().getName() + this.getText("logout"));

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");

            //this.user = new User();
            //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedUser", this.user);
            //this.showName = false;
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";

    }

    /**
     * @return the showName
     */
    public boolean isShowName() {
        return showName;
    }

    /**
     * @param showName the showName to set
     */
    public void setShowName(boolean showName) {
        this.showName = showName;
    }
}
