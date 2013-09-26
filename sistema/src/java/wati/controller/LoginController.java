/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import wati.model.User;

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

    public void loginDialog() {
        
        this.user.setId( 1 );
        this.user.setName( "Usuário" );
        
        if ( this.user.getId() == 0 ) {
            
            this.showName = false;
            
        } else {
            
            this.showName = true;
            
        }
        
        System.out.println( this.user.getName() );
        
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
