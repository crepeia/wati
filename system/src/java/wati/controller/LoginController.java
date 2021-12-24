/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import wati.model.User;
import wati.utility.Encrypter;
import wati.utility.EncrypterException;

@Named("loginController")
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

    public void login(){

        try {

            List<User> userList = this.getDaoBase().list("email", this.user.getEmail(), this.getEntityManager());

            if (userList.isEmpty() || !Encrypter.compareHash(password, userList.get(0).getPassword(), userList.get(0).getSalt())) {
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

        } catch (SQLException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, this.getText("mensagem.delete2"), null));
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }catch(EncrypterException | IOException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, this.getText("mensagem.erro"), null));
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
