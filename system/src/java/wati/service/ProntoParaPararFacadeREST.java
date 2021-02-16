/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.service;

import java.util.List;
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
import wati.model.ProntoParaParar;
import wati.model.User;
import wati.utility.Secured;

/**
 *
 * @author bruno
 */
@Stateless
@Path("prontoparaparar")
@Secured
public class ProntoParaPararFacadeREST extends AbstractFacade<ProntoParaParar> {

    @PersistenceContext(unitName = "watiPU")
    private EntityManager em;
    
    @Context
    SecurityContext securityContext;

    public ProntoParaPararFacadeREST() {
        super(ProntoParaParar.class);
    }

    @GET
    @Path("find/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("userId") Long userId) {
        String userEmail = securityContext.getUserPrincipal().getName();
        User u = em.find(User.class, userId);
        if(!u.getEmail().equals(userEmail)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        
        try {
            ProntoParaParar p = (ProntoParaParar) getEntityManager().createQuery("SELECT r FROM ProntoParaParar r WHERE r.usuario.id =:userId")
                    .setParameter("userId",userId)
                    .getSingleResult();
            return Response.ok(p).build();
            
        } catch(NoResultException e) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
