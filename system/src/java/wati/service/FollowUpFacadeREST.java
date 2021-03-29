/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import wati.model.FollowUp;
import wati.utility.Secured;

/**
 *
 * @author bruno
 */
@Stateless
@Path("followup")
@Secured
public class FollowUpFacadeREST extends AbstractFacade<FollowUp> {

    @PersistenceContext(unitName = "watiPU")
    private EntityManager em;

    public FollowUpFacadeREST() {
        super(FollowUp.class);
    }


    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
