/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

//import wati.model.User;
import wati.utility.Encrypter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.enterprise.inject.Default;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author hedersb
 */
public abstract class BaseFormController<T> extends BaseController<T> {
	
	public BaseFormController( Class<T> cls ) {
		super( cls );
	}
	
	public void delete( ActionEvent actionEvent ) {
		
		T object = ( T ) actionEvent.getComponent().getAttributes().get( "object" );
		String typeName = ( String ) actionEvent.getComponent().getAttributes().get( "typeName" );
		
		try {
			
			this.getDaoBase().delete( object, this.getEntityManager() );
			
			String message = 
					PropertyResourceBundle.getBundle( "br.org.bssystem.utility.messages" ).getString( typeName ) +
					" " +
					PropertyResourceBundle.getBundle( "br.org.bssystem.utility.messages" ).getString( "deleted" );
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
			Logger.getLogger(BaseFormController.class.getName()).log(Level.INFO, message);
			//Logger.getLogger(User.class.getName()).log(Level.INFO, object.toString());

		} catch (SQLException ex) {
			String message = PropertyResourceBundle.getBundle("br.org.bssystem.utility.messages").getString("database_error");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, null));
			Logger.getLogger(BaseFormController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
			//Logger.getLogger(User.class.getName()).log(Level.SEVERE, object.toString());
		}
		
	}
	
	public void save( ActionEvent actionEvent ) {
		
		T object = ( T ) actionEvent.getComponent().getAttributes().get( "object" );
		String typeName = ( String ) actionEvent.getComponent().getAttributes().get( "typeName" );
		
		try {
			
			this.getDaoBase().insertOrUpdate( object, this.getEntityManager() );
			
			String message = 
					PropertyResourceBundle.getBundle( "br.org.bssystem.utility.messages" ).getString( typeName ) +
					" " +
					PropertyResourceBundle.getBundle( "br.org.bssystem.utility.messages" ).getString( "saved" );
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
			Logger.getLogger(BaseFormController.class.getName()).log(Level.INFO, message);
			//Logger.getLogger(User.class.getName()).log(Level.INFO, object.toString());

		} catch (SQLException ex) {
			String message = PropertyResourceBundle.getBundle("br.org.bssystem.utility.messages").getString("database_error");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, null));
			Logger.getLogger(BaseFormController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
			//Logger.getLogger(User.class.getName()).log(Level.SEVERE, object.toString());
		}
		
	}
	
}
