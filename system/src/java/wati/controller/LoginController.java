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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import wati.model.User;
import wati.utility.Encrypter;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController extends BaseFormController<User> {

    private User user = new User();
    private String password;
    private boolean showName;

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

    public void login() {

        try {

            List<User> userList = this.getDaoBase().list("email", this.user.getEmail(), this.getEntityManager());

            Encrypter encrypter = new Encrypter();
            if (userList.isEmpty() || !encrypter.compare(this.password, userList.get(0).getPassword())) {
                //log message
                String message = this.getUser().getEmail() + " could not sign in.";
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, message);
                //message to the user
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, this.getText("email.senha"), null));

            } else {

                this.user = userList.get(0);

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedUser", userList.get(0));

                String language = userList.get(0).getPreferedLanguage();
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                LanguageController languageController = (LanguageController) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "languageController");
                languageController.setLanguage(language);

                String message = this.getUser().getEmail() + " signed in.";
                Logger.getLogger(LoginController.class.getName()).log(Level.INFO, message);

                this.showName = this.user.getId() > 0;

                Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("url");
                if (object != null) {
                    String url = (String) object;
                    FacesContext.getCurrentInstance().getExternalContext().redirect(url);
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("escolha-uma-etapa.xhtml");
                }

            }

        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, this.getText("mensagem.delete2"), null));
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    public String getLogout() {

        String message = this.getUser().getEmail() + " signed out.";
        Logger.getLogger(LoginController.class.getName()).log(Level.INFO, message);

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
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
