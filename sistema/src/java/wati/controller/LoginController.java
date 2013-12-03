/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import wati.model.User;
import wati.persistence.GenericDAO;
import wati.utility.Encrypter;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController extends BaseFormController<User> {

	private User user = new User();
	private String password;

	private boolean showName;

	GenericDAO<User> userDAO;

	@PersistenceContext
	private EntityManager entityManager = null;

	/**
	 * Creates a new instance of LoginController
	 */
	public LoginController() {

		super(User.class);

		this.password = "";

		try {
			userDAO = new GenericDAO<User>(User.class);
		} catch (NamingException ex) {
			Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
		}

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
			List<User> userList = userDAO.list("email", this.user.getEmail(), this.entityManager);

			if (userList.isEmpty() || !Encrypter.compare(this.password, userList.get(0).getPassword())) {
				//log message
				Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "O usuário com o e-mail '" + this.getUser().getEmail() + "' não conseguiu logar.");
				//message to the user
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail ou senha inválida.", null));

			} else {

				this.user = userList.get(0);

//                              FacesContext facesContext = FacesContext.getCurrentInstance();
//                              HttpSession session = (HttpSession) facesContext.getExternalContext().getSession( false );
//                              session.setAttribute( "loggedUser" , userList.get( 0 ));
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedUser", userList.get(0));

				Logger.getLogger(LoginController.class.getName()).log(Level.INFO, "O usuário '" + this.getUser().getName() + "' logou no sistema.");

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
			String message = PropertyResourceBundle.getBundle("br.org.bssystem.utility.messages").getString("database_error");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, null));
			Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}

		if (this.user.getId() > 0) {
			this.showName = true;
		} else {
			this.showName = false;
		}

	}

	public String logout() {

		Logger.getLogger(LoginController.class.getName()).log(Level.INFO, "O usuário '" + this.getUser().getName() + "' saiu no sistema.");
		
		this.user = new User();

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedUser", this.user);

		this.showName = false;
		
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
