/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.service;

import com.google.gson.Gson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
import wati.controller.ChallengeUserController;
import wati.utility.Secured;
import wati.model.ChallengeUser;
import wati.model.Challenge;
import wati.model.User;

/**
 *
 * @author bruno
 */
@Stateless
@Secured
@Path("challengeuser")
public class ChallengeUserFacadeREST extends AbstractFacade<ChallengeUser> {

    @PersistenceContext(unitName = "watiPU")
    private EntityManager em;
    
    @Context
    SecurityContext securityContext;

    public ChallengeUserFacadeREST() {
        super(ChallengeUser.class);
    }

    @PUT
    @Path("completeCreateChallenge")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response completeCreateChallenge(ChallengeUser entity) {
        try{
            Challenge c = em.find(Challenge.class, entity.getChallenge().getId());
            List<ChallengeUser> chList = 
                    em.createQuery("SELECT ch FROM ChallengeUser ch "
                            + "WHERE ch.user.id=:userId AND ch.challenge.id=:challengeId "
                            + "ORDER BY ch.dateCompleted DESC")
                    .setParameter("userId", entity.getUser().getId())
                    .setParameter("challengeId", entity.getChallenge().getId())
                    .getResultList();
            
            Challenge.ChallengeType ct = c.getType();
            
            if(chList.isEmpty()){
                ChallengeUser newEntity = super.create(entity);
                return Response.ok(newEntity).build();
            } else {
                if(ct.equals( Challenge.ChallengeType.ONCE )){
                    return Response.status(Response.Status.NOT_MODIFIED).build();
                } else if(ct.equals( Challenge.ChallengeType.DAILY )) {
                    if(!chList.get(0).getDateCompleted().equals(entity.getDateCompleted())){
                        ChallengeUser newEntity = super.create(entity);
                        return Response.ok(newEntity).build();
                    }
                }
            }
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }catch(Exception e){
            e.printStackTrace();
            return null;

        }
    }
    
    @DELETE
    @Path("deleteChallenge/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteChallenge(@PathParam("id") Long id) {
        try{
            String userEmail = securityContext.getUserPrincipal().getName();
            ChallengeUser chUs = super.find(id);
            if(chUs.getUser().getEmail().equals(userEmail)){
                super.remove(super.find(id));
                return Response.ok().build();
            } else {
                return Response.notModified().build();
            }
            
        
        }catch(Exception e){
            e.printStackTrace();
            return null;

        }
    }
    
    @GET
    @Path("find/{userId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ChallengeUser> findByUser(@PathParam("userId") String uId) {
        try {
            String userEmail = securityContext.getUserPrincipal().getName();
            
            List<ChallengeUser> l = getEntityManager().createQuery("SELECT tu FROM ChallengeUser tu WHERE tu.user.id=:userId AND tu.user.email=:userEmail")
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

    @GET
    @Path("points")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String sumUserPoints(){
        String userEmail = securityContext.getUserPrincipal().getName();//httpRequest.getAttribute("userEmail").toString();
        try {
        return  getEntityManager().createQuery("SELECT SUM(c.score) FROM ChallengeUser c WHERE c.user.email=:email")
                .setParameter("email", userEmail)
                .getSingleResult().toString();
        } catch (Exception e) {
            return "0";
        }
    }
    
    @GET
    @Path("sent/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ChallengeUser> findBySentDate(@PathParam("startDate") String sd, @PathParam("endDate") String ed) {
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(sd);   
        Date endDate = sdf.parse(ed);

        String userEmail = securityContext.getUserPrincipal().getName();//httpRequest.getAttribute("userEmail").toString();
        
        return getEntityManager().createQuery("SELECT c FROM ChallengeUser c WHERE c.user.email=:email AND (c.dateCreated BETWEEN :start AND :end)")
                .setParameter("email", userEmail)
                .setParameter("start", startDate)
                .setParameter("end", endDate)
                .getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    @GET
    @Path("completed/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ChallengeUser> findByCompletedDate(@PathParam("startDate") String sd, @PathParam("endDate") String ed) {
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(sd);   
        Date endDate = sdf.parse(ed);

        String userEmail = securityContext.getUserPrincipal().getName();//httpRequest.getAttribute("userEmail").toString();
        
        return getEntityManager().createQuery("SELECT c FROM ChallengeUser c WHERE c.user.email=:email AND (c.dateCompleted BETWEEN :start AND :end)")
                .setParameter("email", userEmail)
                .setParameter("start", startDate)
                .setParameter("end", endDate)
                .getResultList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    
    @GET
    @Path("rankFromDate")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ChallengeUserController.NicknameScore> rankFromDate(@PathParam("startDate") String sd) {
        try {
            List<ChallengeUserController.NicknameScore> resultList = new LinkedList<>();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate dateStart = sdf.parse(sd).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();   

            List<User> users = getEntityManager()
                                .createQuery("SELECT u FROM User u WHERE u.inRanking = 1")
                                .getResultList();

            users.forEach(u -> {
                long points = getPointsFromDate(u, dateStart);
                resultList.add(new ChallengeUserController.NicknameScore(u.getNickname(), points));
            });

            return resultList;

        } catch (ParseException ex) {
            return null;
        }
    }
    
    public static class RankLists {
        List<ChallengeUserController.NicknameScore> weeklyResult;
        List<ChallengeUserController.NicknameScore> monthlyResult;
        List<ChallengeUserController.NicknameScore> yearlyResult;
        public RankLists(){
            weeklyResult = new LinkedList<>();
            monthlyResult = new LinkedList<>();
            yearlyResult = new LinkedList<>();
        }
    }
    
    
    @GET
    @Path("rank/{today}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response rank(@PathParam("today") String today) {
        
        try {
            RankLists rank = new RankLists();

            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate dateStart = sdf.parse(today).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();   

            List<User> users = getEntityManager()
                                .createQuery("SELECT u FROM User u WHERE u.inRanking = 1")
                                .getResultList();

            users.forEach(u -> {
                long points = getPointsFromDate(u, dateStart.with(ChronoField.DAY_OF_WEEK, 1));
                rank.weeklyResult.add(new ChallengeUserController.NicknameScore(u.getNickname(), points));
            });
            users.forEach(u -> {
                long points = getPointsFromDate(u, dateStart.with(ChronoField.DAY_OF_MONTH, 1));
                rank.monthlyResult.add(new ChallengeUserController.NicknameScore(u.getNickname(), points));
            });
            users.forEach(u -> {
                long points = getPointsFromDate(u, dateStart.with(ChronoField.DAY_OF_YEAR, 1));
                rank.yearlyResult.add(new ChallengeUserController.NicknameScore(u.getNickname(), points));
            });
            
            Gson g = new Gson();
            String json = g.toJson(rank);

            return Response.ok(json).build();

        } catch (ParseException ex) {
            return Response.serverError().build();
        }
    }
    

    protected long getPointsFromDate(User u, LocalDate date){
        Long score = (Long) this.getEntityManager()
                        .createQuery("SELECT SUM(c.score) FROM ChallengeUser c WHERE c.dateCompleted > :date AND c.user.id=:userId")
                        .setParameter("date", date)
                        .setParameter("userId", u.getId()).getSingleResult();
        if(score == null) score = Long.valueOf(0);
        return score;
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
