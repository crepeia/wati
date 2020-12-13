/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.naming.NamingException;
import wati.model.Questionnaire;
import wati.model.User;
import wati.persistence.GenericDAO;

/**
 *
 * @author thiago
 */
@Named("questionnaireController")
@SessionScoped
public class QuestionnaireController extends BaseController<Questionnaire> {

    private Questionnaire questionnaire;
    private GenericDAO daoUser;

    
    private String[] procKnowWebSiteMarcados = null;
    private static final char baseline1 = 'a';
    private static final char baseline2 = 'b';
    private static final char baseline3 = 'c';
    private static final char baseline4 = 'd';
    private static final char baseline5 = 'e';
    private static final char baseline6 = 'f';
    private static final char baseline7 = 'g';
    
    private String[] procMetodosMarcados = null;
    private static final char procedure1 = 'a';
    private static final char procedure2 = 'b';
    private static final char procedure3 = 'c';
    private static final char procedure4 = 'd';
    private static final char procedure5 = 'e';
    private static final char procedure6 = 'f';
    private static final char procedure7 = 'g';
    private static final char procedure8 = 'h';
    
    public QuestionnaireController(){
        try {
            daoBase = new GenericDAO<>(Questionnaire.class);
            daoUser = new GenericDAO<>(User.class);
        } catch (NamingException ex) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Questionnaire getQuestionnaire() {
        if (questionnaire == null) {
            if (loggedUser()) {
                questionnaire = getLoggedUser().getQuestionnaire();
                if (questionnaire == null) {
                    questionnaire = new Questionnaire();
                    questionnaire.setDate(new Date());
                    questionnaire.setUser(getLoggedUser());
                }
            }
        }
        return questionnaire;
    }

    public String saveQuestionnaire() {
        try {
            daoBase.insertOrUpdate(getQuestionnaire(), getEntityManager());
        } catch (SQLException ex) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String randomizeUsers() {
        saveQuestionnaire();
        getLoggedUser().setExperimentalGroups(generateGroup());
        try {
            daoUser.update(getLoggedUser(), getEntityManager());
        } catch (SQLException ex) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (getLoggedUser().getExperimentalGroups() == 0){
            return  " ";
        } else {
            return " ";
        }
    }

    private int generateGroup() {
        Random r = new Random();
        return r.nextInt(2);
    }

    public List getCigarettes() {
        List<Integer> cigarettes = new ArrayList<>();
        for (int i = 0; i <= 130; i++) {
            cigarettes.add(i);
        }
        return cigarettes;
    }

    public List getTimesQuit() {
        List<Integer> timesQuit = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            timesQuit.add(i);
        }
        return timesQuit;
    }

    
     public String[] getProcKnowWebSiteMarcados() {
        if (this.procKnowWebSiteMarcados == null) {
            Questionnaire q= this.getQuestionnaire();
            int count = 0;
            if (q.isBaseline1()) {
                count++;
            }
            if (q.isBaseline2()) {
                count++;
            }
            if (q.isBaseline3()) {
                count++;
            }
            if (q.isBaseline4()) {
                count++;
            }
            if (q.isBaseline5()) {
                count++;
            }
            if (q.isBaseline6()) {
                count++;
            }
            if (q.isBaseline7()) {
                count++;
            }

            this.procKnowWebSiteMarcados = new String[count];
            count = 0;
            if (q.isBaseline1()){
                this.procKnowWebSiteMarcados[count] = String.valueOf(QuestionnaireController.baseline1);
                count++;
            }
            if (q.isBaseline2()){
                this.procKnowWebSiteMarcados[count] = String.valueOf(QuestionnaireController.baseline2);
                count++;
            }
            if (q.isBaseline3()){
                this.procKnowWebSiteMarcados[count] = String.valueOf(QuestionnaireController.baseline3);
                count++;
            }
            if (q.isBaseline4()){
                this.procKnowWebSiteMarcados[count] = String.valueOf(QuestionnaireController.baseline4);
                count++;
            }
            if (q.isBaseline5()){
                this.procKnowWebSiteMarcados[count] = String.valueOf(QuestionnaireController.baseline5);
                count++;
            }
            if (q.isBaseline6()){
                this.procKnowWebSiteMarcados[count] = String.valueOf(QuestionnaireController.baseline6);
                count++;
            }
            if (q.isBaseline7()){
                this.procKnowWebSiteMarcados[count] = String.valueOf(QuestionnaireController.baseline7);
                count++;
            }

        }

        return procKnowWebSiteMarcados;
    }

    public void setprocKnowWebSiteMarcados(String[] procKnowWebSiteMarcados) {
        this.procKnowWebSiteMarcados = procKnowWebSiteMarcados;
    }

    public void teste() {
        for (String s : this.procKnowWebSiteMarcados) {
            System.out.println(s);
        }

    }
    
    public String[] getProcMetodosMarcados() {
        if (this.procMetodosMarcados == null) {
            Questionnaire q= this.getQuestionnaire();
            int count = 0;
            if (q.isProcedure1()) {
                count++;
            }
            if (q.isProcedure2()) {
                count++;
            }
            if (q.isProcedure3()) {
                count++;
            }
            if (q.isProcedure4()) {
                count++;
            }
            if (q.isProcedure5()) {
                count++;
            }
            if (q.isProcedure6()) {
                count++;
            }
            if (q.isProcedure7()) {
                count++;
            }
            if(q.isProcedure8()) {
                count++;
            }

            this.procMetodosMarcados = new String[count];
            count = 0;
            if (q.isProcedure1()){
                this.procMetodosMarcados[count] = String.valueOf(QuestionnaireController.procedure1);
                count++;
            }
            if (q.isProcedure2()){
                this.procMetodosMarcados[count] = String.valueOf(QuestionnaireController.procedure2);
                count++;
            }
            if (q.isProcedure3()){
                this.procMetodosMarcados[count] = String.valueOf(QuestionnaireController.procedure3);
                count++;
            }
            if (q.isProcedure4()){
                this.procMetodosMarcados[count] = String.valueOf(QuestionnaireController.procedure4);
                count++;
            }
            if (q.isProcedure5()){
                this.procMetodosMarcados[count] = String.valueOf(QuestionnaireController.procedure5);
                count++;
            }
            if (q.isProcedure6()){
                this.procMetodosMarcados[count] = String.valueOf(QuestionnaireController.procedure6);
                count++;
            }
            if (q.isProcedure7()){
                this.procMetodosMarcados[count] = String.valueOf(QuestionnaireController.procedure7);
                count++;
            }
            if (q.isProcedure8()){
                this.procMetodosMarcados[count] = String.valueOf(QuestionnaireController.procedure8);
                count++;
            }
        }
        return procMetodosMarcados;
    }
    
    public void setProcMetodosMarcados(String[] procMetodosMarcados){
        this.procMetodosMarcados = procMetodosMarcados;
    }
    
}
