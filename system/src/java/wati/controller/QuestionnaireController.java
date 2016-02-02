/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;
import wati.model.Questionnaire;
import wati.persistence.GenericDAO;

/**
 *
 * @author thiago
 */
@ManagedBean(name = "questionnaireController")
@SessionScoped
public class QuestionnaireController extends BaseController<Questionnaire> {
    
    private Questionnaire questionnaire;    
    
    public QuestionnaireController(){
        try {
            daoBase = new GenericDAO<>(Questionnaire.class);
        } catch (NamingException ex) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Questionnaire getQuestionnaire(){
       if(questionnaire == null){
           if(loggedUser()){
             questionnaire = getLoggedUser().getQuestionnaire();
           }
           if(questionnaire == null){
               questionnaire = new Questionnaire();
               questionnaire.setDate(new Date()); 
               questionnaire.setUser(getLoggedUser());
           }
       }
       return questionnaire;
    }
    
    public String saveQuestionnaire(){
        System.out.println(getQuestionnaire().getDependencyLevel1());
        try {
            daoBase.insertOrUpdate(getQuestionnaire(), getEntityManager());
            return "index.html";
        } catch (SQLException ex) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    
    
}
