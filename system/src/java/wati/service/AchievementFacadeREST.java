/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.service;

import java.util.Collections;
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
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import wati.model.Achievement;
import wati.model.ChallengeUser;
import wati.model.DailyLog;
import wati.utility.Secured;

/**
 *
 * @author bruno
 */
@Stateless
@Path("achievement")
@Secured
public class AchievementFacadeREST extends AbstractFacade<Achievement> {

    @PersistenceContext(unitName = "watiPU")
    private EntityManager em;
    
    @Context
    SecurityContext securityContext;

    public AchievementFacadeREST() {
        super(Achievement.class);
    }


    @GET
    @Path("find/{userId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Achievement> findByUser(@PathParam("userId") String uId) {
        try {
            String userEmail = securityContext.getUserPrincipal().getName();
            
            List<Achievement> l = getEntityManager().createQuery("SELECT a FROM Achievement a WHERE a.user.id=:userId AND a.user.email=:userEmail")
                .setParameter("userId", Long.parseLong(uId))
                .setParameter("userEmail", userEmail)
                .getResultList();
            if(l.isEmpty()) {
                return Collections.emptyList();
            } else {
                return l;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @POST
    @Path("editOrCreate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editOrCreate(Achievement entity) {
        String action = "";
        try {

            Achievement a = (Achievement) getEntityManager().createQuery("SELECT a FROM Achievement a WHERE a.user.id=:userId AND a.logDate=:logDate")
                .setParameter("userId", entity.getUser().getId())
                .setParameter("logDate", entity.getLogDate())
                .getSingleResult();
                a.setCigarsNotSmoken(entity.getCigarsNotSmoken());
                a.setLifeTimeSaved(entity.getLifeTimeSaved());
                a.setMoneySaved(entity.getMoneySaved());
                super.edit(a);
                action = "edit";
            
        } catch( NoResultException e ) {
            super.create(entity);
            action = "create";
            //return Response.status(Response.Status.OK).entity(new JSONObject().put("action", action).toString()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
        
        try {
            return Response.status(Response.Status.OK).entity(new JSONObject().put("action", action).toString()).type(MediaType.APPLICATION_JSON).build();
        } catch (JSONException ex) {
            Logger.getLogger(DailyLogFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
