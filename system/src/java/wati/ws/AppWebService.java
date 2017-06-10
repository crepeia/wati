/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.ws;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
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
import wati.controller.BaseController;
import wati.controller.BaseFormController;
import wati.controller.LanguageController;
import wati.controller.LoginController;
import wati.model.CigarrosWS;
import wati.model.User;
import wati.model.UserWS;
import wati.persistence.GenericDAO;
import wati.persistence.UserDAO;
import wati.utility.Encrypter;

/**
 *
 * @author hedersb
 */
@WebService(serviceName = "AppWebService")
public class AppWebService extends BaseController<CigarrosWS>{

    @Resource
    WebServiceContext context;

    @PersistenceContext
    private EntityManager entityManager;

    private UserDAO userDAO;
    private CigarrosWS cigarrosWS;
    GenericDAO<UserWS> userWSDAO;
    GenericDAO<CigarrosWS> cigarrosWSDAO;

    public AppWebService() {
        
        try {
            this.userDAO = new UserDAO();
            this.cigarrosWS = new CigarrosWS();
            //this.daoBase = new GenericDAO<UserWS>(UserWS.class);
            this.userWSDAO = new GenericDAO<UserWS>(UserWS.class);
            this.cigarrosWSDAO = new GenericDAO<CigarrosWS>(CigarrosWS.class);
            
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
    public UserWS validate(@WebParam(name = "email") String email) {
        try {
            List<User> userList = this.userDAO.list("email", email, this.entityManager);
            
            if(userList.isEmpty()){
                String message = email + "não pode logar";
                Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, message);
            }else{
                User user = userList.get(0);
                UserWS userWS = new UserWS();
                userWS.setName(user.getName());
                userWS.setEmail(user.getEmail());
                userWS.setGender(Character.toString(user.getGender()));
                
                //this.getDaoBase().insertOrUpdate(userWS, this.getEntityManager());
                
                String message = email + "pode logar";
                
                Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, message);
                
                return userWS;
            }
        }catch (SQLException ex) {
            Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // authentication failure
    }
    
    @WebMethod(operationName = "enviaCigarros")
    public boolean enviaCigarros(@WebParam(name = "cigarros") int cigarros, @WebParam(name = "data") long data, @WebParam(name = "email") String email ) throws SQLException{
        
        try{
            List<UserWS> userList = this.userWSDAO.list("email", email, this.entityManager);
            if(userList.isEmpty()){
                System.out.println("Problema no email do DAO");
            }
            else{
                UserWS userWS = userList.get(0);
                
                System.out.println("cigarros " + cigarros);
                System.out.println("data " + data);
                System.out.println("user " + email);
                
                cigarrosWS.setCigarrosDiario(cigarros);
                cigarrosWS.setData(new Date(data));
                cigarrosWS.setUserWS(userWS);
                
                this.cigarrosWSDAO.insert(cigarrosWS, entityManager);
                
                System.out.println("cigarros " + cigarrosWS.getCigarrosDiario());
                System.out.println("data " + cigarrosWS.getData());
                System.out.println("user " + cigarrosWS.getUserWS().getName());
            }
                
        
            
            return true;
        }catch (SQLException ex) {
            Logger.getLogger(CigarrosWS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return false;
    }
}
