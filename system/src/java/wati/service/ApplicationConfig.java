/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.service;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author bruno
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {
/*
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
*/
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        // following code can be used to customize Jersey 1.x JSON provider:
        resources.add(JacksonFeature.class);
        addRestResourceClasses(resources);
        return resources;
    }
    
    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(wati.service.AcompanhamentoFacadeREST.class);
        resources.add(wati.service.AuthenticationFilter.class);
        resources.add(wati.service.AuthenticationTokenFacadeREST.class);
        resources.add(wati.service.ChallengeFacadeREST.class);
        resources.add(wati.service.ChallengeUserFacadeREST.class);
        resources.add(wati.service.ContactFacadeREST.class);
        resources.add(wati.service.DailyLogFacadeREST.class);
        resources.add(wati.service.FollowUpFacadeREST.class);
        resources.add(wati.service.MobileOptionsFacadeREST.class);
        resources.add(wati.service.PageFacadeREST.class);
        resources.add(wati.service.PageNavigationFacadeREST.class);
        resources.add(wati.service.PesquisaSatisfacaoFacadeREST.class);
        resources.add(wati.service.ProntoParaPararFacadeREST.class);
        resources.add(wati.service.RatingFacadeREST.class);
        resources.add(wati.service.RecordFacadeREST.class);
        resources.add(wati.service.TipFacadeREST.class);
        resources.add(wati.service.TipUserFacadeREST.class);
        resources.add(wati.service.UserAgentFacadeREST.class);
        resources.add(wati.service.UserFacadeREST.class);
        resources.add(wati.utility.ObjectMapperContextResolver.class);
    }
    
}
