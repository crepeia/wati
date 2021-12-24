/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.codec.binary.Hex;
import wati.model.AuthenticationToken;
import wati.utility.SecureRandomString;
import wati.utility.Secured;
import wati.model.User;
import wati.utility.Encrypter;

/**
 *
 * @author bruno
 */
@Stateless
@Path("authenticationtoken")
public class AuthenticationTokenFacadeREST extends AbstractFacade<AuthenticationToken> {

    @PersistenceContext(unitName = "watiPU")
    private EntityManager em;

    @Context
    SecurityContext securityContext;
    
    public AuthenticationTokenFacadeREST() {
        super(AuthenticationToken.class);
    }

    @GET
    @Path("{email}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authUser(@PathParam("email") String e, @PathParam("password") String p) {
        try {
            
            //User usr = super.login(e, p);
           // String token = issueToken(usr);
 
           String clientEncriptedHexPassword = p;
           String decriptedPassword = Encrypter.decrypt(clientEncriptedHexPassword);
           
           byte[] b =  Hex.decodeHex(p.toCharArray());
           User user = (User) getEntityManager().createNamedQuery("User.email").setParameter("email", e).getSingleResult();
           
           boolean hashMatches = Encrypter.compareHash(decriptedPassword, user.getPassword(), user.getSalt());
           
           if(hashMatches){
               String token  = issueToken(user);
               Logger.getLogger(AuthenticationTokenFacadeREST.class.getName()).log(Level.INFO, "Usuário '" + e + "' logou no sistema.");
               return Response.ok(token).build();

           }
           else{
                Logger.getLogger(AuthenticationTokenFacadeREST.class.getName()).log(Level.INFO, "Usuário '" + e + "' não conseguiu logar.");
                return Response.status(Response.Status.FORBIDDEN).build();
           }
            

            //return Response.ok(token).build();
        } catch(Exception exp) {
            Logger.getLogger(AuthenticationTokenFacadeREST.class.getName()).log(Level.SEVERE, null, exp);
            return Response.status(Response.Status.FORBIDDEN).entity(exp.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("secured/logout/{token}")
    @Secured
    public Response logout(@PathParam("token") String token) {
        try {
        String userEmail = securityContext.getUserPrincipal().getName();//httpRequest.getAttribute("userEmail").toString();
        
        AuthenticationToken at = (AuthenticationToken) getEntityManager().createQuery("SELECT at FROM AuthenticationToken at WHERE at.token=:token AND at.user.email=:uEmail")
                .setParameter("token", token)
                .setParameter("uEmail", userEmail)
                .getSingleResult();
        super.remove(at);
        
        return Response.ok().build();
        } catch( NoResultException e ) {
            return Response.ok().build(); //se o token não existe ou já foi deletado ignoro o erro
        }catch(Exception e){
            Logger.getLogger(AuthenticationTokenFacadeREST.class.getName()).log(Level.SEVERE, null, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    private String issueToken(User usr){
        String token = SecureRandomString.generate();
        
        AuthenticationToken authToken = new AuthenticationToken();
        authToken.setToken(token);
        authToken.setUser(usr);
        authToken.setDateCreated(new Date());
        super.create(authToken);
        
        return token;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
