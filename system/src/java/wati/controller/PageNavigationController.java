package wati.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import wati.model.PageNavigation;
import wati.model.User;
import wati.model.UserAgent;
import wati.persistence.GenericDAO;

/**
 *
 * @author thiagorizuti
 */
@ManagedBean(name = "pageNavigationController")
@SessionScoped
public class PageNavigationController extends BaseController<PageNavigation> {

    private PageNavigation pageNavigation;
    private GenericDAO<UserAgent> userAgentDAO;
    private UserAgent currentUserAgent; //TODO: it is not used yet but this variable can be used to improve performance.

    public PageNavigationController() {
        try {
            this.daoBase = new GenericDAO<>(PageNavigation.class);
            this.userAgentDAO = new GenericDAO<>( UserAgent.class );
        } catch (NamingException ex) {
            Logger.getLogger(PageNavigationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String saveNavigation() {
        pageNavigation = new PageNavigation();
        pageNavigation.setIp(this.getIpAdress());
        pageNavigation.setTimeStamp(new Date());
        pageNavigation.setUrl(this.getURL());
        pageNavigation.setUser(this.getUser());
        pageNavigation.setCampaign(this.getCampaign());
        pageNavigation.setReferer(this.getReferer());
        try {
            pageNavigation.setUserAgent(this.getUserAgent());
            //pageNavigation.getUserAgent().getPageNavigation().add(pageNavigation); //TODO: is this necessary?
            this.daoBase.insert(pageNavigation, this.getEntityManager());
        } catch (SQLException ex) {
            Logger.getLogger(PageNavigationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public User getUser(){
        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedUser");
        return user;
    }

    public String getIpAdress() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
    
    public String getURL(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = ((HttpServletRequest) request).getRequestURI();
	url = url.substring( url.lastIndexOf('/') + 1 );
        return url;
        
    }
    
    public String getReferer(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getHeader("referer");
    }
    
    
    public String getCampaign(){
        return FacesContext.getCurrentInstance().getExternalContext().
                getRequestParameterMap().get("id");
    }
    /*
        This method verifies if the current user-agent is in database.
        Yes; then return the current UserAgent.
        No; then insert the user-agent and return an UserAgent object.
    */
    private UserAgent getUserAgent() throws SQLException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String description = ((HttpServletRequest) request).getHeader("user-agent");
        //TODO: to improve the performance (for instance, avoiding the unnecessary consults to the database?)
        UserAgent ua = this.userAgentDAO.listOnce("description", description, this.getEntityManager());
        if (ua == null) {
            ua = new UserAgent();
            ua.setDescription(description);
            this.userAgentDAO.insert(ua, this.getEntityManager());
        }
        return ua;
        
    }

    /**
     * @return the currentUserAgent
     */
    public UserAgent getCurrentUserAgent() {
        return currentUserAgent;
    }

    /**
     * @param currentUserAgent the currentUserAgent to set
     */
    public void setCurrentUserAgent(UserAgent currentUserAgent) {
        this.currentUserAgent = currentUserAgent;
    }
    
    

}
