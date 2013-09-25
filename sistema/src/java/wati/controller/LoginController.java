/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import wati.model.User;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController extends BaseFormController<User> {
    
    private User user;
    private String password;
    
    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
        
        super( User.class );
        
        this.password = "";
        this.user = new User();
        
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
    
    public void showPopup() {
        
        Map<String,Object> options = new HashMap<String, Object>();  
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 320);
        //hint: available options are modal, draggable, resizable, width, height, contentWidth and contentHeight  
        
    }
    
}
