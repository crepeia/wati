/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.ws;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import wati.controller.BaseFormController;
import wati.controller.LanguageController;
import wati.controller.LoginController;
import wati.model.User;
import wati.persistence.GenericDAO;
import wati.persistence.UserDAO;
import wati.utility.Encrypter;

/**
 *
 * @author hedersb
 */
@WebService(serviceName = "AppWebService")
public class AppWebService {

    @Resource
    WebServiceContext context;

    @PersistenceContext
    private EntityManager entityManager;

    private UserDAO userDAO;

    public AppWebService() {
        try {
            this.userDAO = new UserDAO();
        } catch (NamingException ex) {
            Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service to validate e-mail and password.
     */
    @WebMethod(operationName = "validate")
    public boolean validate(@WebParam(name = "email") String email, @WebParam(name = "password") String password) {
        try {
            List<User> userList = this.userDAO.list("email", email, this.entityManager);
            
            ServletContext servletContext = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
            Encrypter encrypter = new Encrypter(servletContext.getInitParameter("key"));
            
            if (userList.isEmpty() || !encrypter.compare(password, userList.get(0).getPassword())) {
                //log message
                String message = email + " could not sign in.";
                Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, message);

            } else {

                User user = userList.get(0);

                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedUser", userList.get(0));
                String message = user.getEmail() + " signed in.";
                Logger.getLogger(AppWebService.class.getName()).log(Level.INFO, message);

                return true; //authentication validated
            }
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; // authentication failure
    }
}
