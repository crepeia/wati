/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import wati.model.PesquisaSatisfacao;
import wati.model.User;
import wati.persistence.GenericDAO;

/**
 *
 * @author felipe
 */
@ManagedBean(name = "pesquisaSatisfacao")
@SessionScoped
public class PesquisaSatisfacaoController extends BaseController<PesquisaSatisfacao> {

    private PesquisaSatisfacao PesquisaSatisfacao;

    public PesquisaSatisfacaoController() {
        try {
            daoBase = new GenericDAO<>(PesquisaSatisfacao.class);
        } catch (NamingException ex) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PesquisaSatisfacao getPesquisaSatisfacao() {
        if (PesquisaSatisfacao == null) {
            if (loggedUser()) {
                PesquisaSatisfacao = getLoggedUser().getPesquisaSatisfacao();
                if (PesquisaSatisfacao == null) {
                    PesquisaSatisfacao = new PesquisaSatisfacao();
                    PesquisaSatisfacao.setDate(new Date());
                    PesquisaSatisfacao.setUser(getLoggedUser());
                }
            } else {
                User user = getUserPesquisa();

                if (user != null && user.getPesquisaSatisfacao() != null) {
                    PesquisaSatisfacao = user.getPesquisaSatisfacao();
                } else {
                    PesquisaSatisfacao = new PesquisaSatisfacao();
                    PesquisaSatisfacao.setDate(new Date());
                    PesquisaSatisfacao.setUser(user);
                }

            }
        }
        return PesquisaSatisfacao;
    }

    public void savePesquisaSatisfacao() {
        System.out.println("ok");
        try {
            daoBase.insertOrUpdate(getPesquisaSatisfacao(), getEntityManager());
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (SQLException ex) {
            Logger.getLogger(PesquisaSatisfacaoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PesquisaSatisfacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
        retorna o id do usuario contido na url de acesso vivasemtabaco.com.br/pagina...xhtml?uid=XXXX
     */
    private User getUserPesquisa() {
        try {
            Map<String, String> params = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap();
            long id = Long.parseLong(params.get("uid"));
            if (id % 1357 != 0) {
                return null;
            } else {
                id = id / 1357;
                GenericDAO daoUser = new GenericDAO<>(User.class);
                List users = daoUser.list("id", id, getEntityManager());
                if (users.isEmpty()) {
                    return null;
                } else {
                    return (User) users.get(0);
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PesquisaSatisfacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
