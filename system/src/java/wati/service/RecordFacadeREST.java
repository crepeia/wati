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
import javax.ws.rs.core.SecurityContext;
import wati.model.Record;
import wati.model.User;
import wati.utility.Secured;

/**
 *
 * @author bruno
 */
@Stateless
@Path("record")
@Secured
public class RecordFacadeREST extends AbstractFacade<Record> {

    @PersistenceContext(unitName = "watiPU")
    private EntityManager em;

    @Context
    SecurityContext securityContext;
    
    public RecordFacadeREST() {
        super(Record.class);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Record create(Record entity) {
        String userEmail = securityContext.getUserPrincipal().getName();
        User u = em.find(User.class, entity.getUser().getId());
        if(!u.getEmail().equals(userEmail)){
            return null;
        }
        try {
            return super.create(entity);
        } catch (Exception e) {
            return null;
        }
    }

    @POST
    @Path("create/{userId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Record create(@PathParam("userId") Long userId) {
        //String userEmail = securityContext.getUserPrincipal().getName();
        String userEmail = securityContext.getUserPrincipal().getName();
        User u = em.find(User.class, userId);
        if(!u.getEmail().equals(userEmail)){
            return null;
        }
        
        try {
            Record entity = new Record();
            entity.setUser(u);
            entity.setCigarsDaily(0);
            entity.setFilled(false);
            entity.setPackAmount(0);
            entity.setPackPrice(0);
            
            super.create(entity);
            return entity;
        } catch (Exception e) {
            return null;
        }

    }
    
    @PUT
    @Path("edit")
    @Consumes(MediaType.APPLICATION_JSON)
    public Record edit(Record entity) {
        String userEmail = securityContext.getUserPrincipal().getName();
        User u = em.find(User.class, entity.getUser().getId());
        if(u.getEmail().equals(userEmail)){
            super.edit(entity);
        } 
       
        return entity;
    }

    @GET
    @Path("find/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Record find(@PathParam("userId") Long userId) {
        String userEmail = securityContext.getUserPrincipal().getName();
        User u = em.find(User.class, userId);
        if(!u.getEmail().equals(userEmail)){
            return null;
        }
        
        try {
            return (Record) getEntityManager().createQuery("SELECT r FROM Record r WHERE r.user.id=:userId")
                    .setParameter("userId",userId)
                    .getSingleResult();
            
        } catch(NoResultException e) {
            return null;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
