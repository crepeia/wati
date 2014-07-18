/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.io.IOException;
import wati.persistence.GenericDAO;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import wati.utility.EMailSSL;

/**
 *
 * @author hedersb
 */
public abstract class BaseController<T> implements Serializable {

	@PersistenceContext
	private EntityManager entityManager = null;

	protected GenericDAO<T> daoBase;

	public BaseController() {
	}

//	public BaseController(Class<T> cls) {
//
//		try {
//			this.daoBase = new GenericDAO<T>(cls);
//		} catch (NamingException ex) {
//			String message = "Ocorreu um erro inesperado.";
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, null));
//			Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
//		}
//
//	}

//	/**
//	 * @return the entityManager
//	 */
//	public EntityManager getEntityManager() {
//		try {
//			if (this.entityManager == null) {
//				this.entityManager = Persistence.createEntityManagerFactory("watiPU").createEntityManager();
//				System.out.println("entity");
//				System.out.println(this.entityManager);
//			}
//
//		} catch (Exception e) {
//			Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, null, e);
//		}
//		return this.entityManager;
//	}
//
//	/**
//	 * @param entityManager the entityManager to set
//	 */
//	public void setEntityManager(EntityManager entityManager) {
//		this.entityManager = entityManager;
//	}

	/**
	 * @return the daoBase
	 */
	public GenericDAO<T> getDaoBase() {
		return daoBase;
	}

	/**
	 *
	 * @return ParameterMap
	 */
	public Map<String, String> getParameterMap() {
		return (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	}

	protected Locale getLocale() {
		return FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{languageBean.language}", Locale.class);
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
               
        public String getText (String key){
           return PropertyResourceBundle.getBundle("wati.utility.messages").getString(key);   
        }
        
        public String defaultEmail(String subtitle, String text){
            
            EMailSSL e = new EMailSSL();
            try {
                URL url = FacesContext.getCurrentInstance().getExternalContext().getResource("/resources/default/template-email-vst/template.html");
                String template = e.readTemplateToString("/resources/default/template-email-vst/template.html");
                String[] tags = {"#title#","#subtitle#","#text#","#footer#"};
                String[] content = new String[4];
                content[0] = "Viva Sem Tabaco";
                content[1] = subtitle;
                content[2] = text;
                content[3] = "<span style='font-weight:bold;'>Viva sem Tabaco</span><br>CREPEIA<br>Universidade Federal de Juiz de Fora";
                return e.fillTemplate(template, tags, content);
                
            } catch (IOException ex) {
                Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, "Erro ao ler template default", ex);
            }
            
            return null;
        }

}
