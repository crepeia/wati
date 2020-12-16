/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
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
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import wati.controller.ContactController;
import wati.controller.UserController;
import wati.model.User;
import wati.utility.Secured;

/**
 *
 * @author bruno
 */
@Stateless
@Path("user")
@TransactionManagement(TransactionManagementType.BEAN)
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "watiPU")
    private EntityManager em;

    @Inject
    private UserController userController;
    
    @Inject
    private ContactController contactController;
    
    @Resource
    private UserTransaction userTransaction;
    

    @Context
    SecurityContext securityContext;
    
    public UserFacadeREST() {
        super(User.class);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{password}")
    public Response createUser(User entity, @PathParam("password") String p) throws DecoderException, UnsupportedEncodingException {
        List<User> userList = em.createQuery("SELECT u FROM User u WHERE u.email=:e").setParameter("e", entity.getEmail()).getResultList();
        
        if (!userList.isEmpty()) {
            return Response.status(Response.Status.CONFLICT).build();
        } else {
            try {
                //String p = Hex.encodeHexString( entity.getPassword() );
                byte[] b =  Hex.decodeHex(p.toCharArray());
                entity.setPassword(b);
                //byte[] b =  Hex.decodeHex(Arrays.toString(entity.getPassword()).toCharArray());
                
                entity.setExperimentalGroups(userController.GeraGrupoUsuario());
                
                userTransaction.begin();
                super.create(entity);
                userTransaction.commit();
                
                

                try {
                    userController.sendEmailTerm(entity);
                } catch (Exception ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Erro sending term emai to: " + entity.getEmail());

                }

                if (entity.isReceiveEmails()) {

                    try {
                        contactController.sendPesquisaSatisfacaoEmail(entity);
                        entity.setPesquisaEnviada(true);
                    } catch (Exception ex) {
                        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "Erro sending research email to: " + entity.getEmail());

                    }
                    
                    contactController.scheduleReaserach7DaysEmail(entity, new Date());
                    contactController.scheduleReaserachXMonthsEmail(entity, new Date(), 1);
                    contactController.scheduleReaserachXMonthsEmail(entity, new Date(), 3);
                    contactController.scheduleReaserachXMonthsEmail(entity, new Date(), 6);
                    contactController.scheduleReaserachXMonthsEmail(entity, new Date(), 12);

                }
                
                
              
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.INFO, "Usuário '" + entity.getEmail() + "'cadastrou no sistema.");
             } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Response.ok(entity).build();
    }
    
    @PUT
    @Path("/setInRanking")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User setInRanking(User entity) {
        String userEmail = securityContext.getUserPrincipal().getName();
        System.out.println("wati.service.UserFacadeREST.setInRanking()");
        try{
            
            User u = (User) em.createQuery("SELECT u from User u WHERE u.email = :email")
                                .setParameter("email", userEmail)
                                .getSingleResult();
            System.out.println(u.getEmail());
            System.out.println(entity.isInRanking());
            System.out.println(entity.getNickname());

            u.setInRanking(entity.isInRanking());
            u.setNickname(entity.getNickname());

            userTransaction.begin();
            super.edit(u);
            userTransaction.commit();
            return u;
        }catch(Exception e) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    @GET
    @Path("count")
    @Secured
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Secured
    @Path("login/{token}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public User login(@PathParam("token") String tkn) {
        return super.login(tkn);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
