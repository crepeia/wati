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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;
import wati.model.Questionnaire;
import wati.model.User;
import wati.persistence.GenericDAO;

/**
 *
 * @author thiago
 */
@ManagedBean(name = "questionnaireController")
@SessionScoped
public class QuestionnaireController extends BaseController<Questionnaire> {

    private Questionnaire questionnaire;
    private GenericDAO daoUser;

    public QuestionnaireController() {
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

}
