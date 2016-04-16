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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import wati.model.PesquisaSatisfacao;
import wati.persistence.GenericDAO;



/**
 *
 * @author felipe
 */
@ManagedBean(name = "pesquisaSatisfacao")
@SessionScoped
public class PesquisaSatisfacaoController extends BaseController<PesquisaSatisfacao> {
      private PesquisaSatisfacao PesquisaSatisfacao;
      
      
    public PesquisaSatisfacaoController(){
        try {
            daoBase = new GenericDAO<>(PesquisaSatisfacao.class);
        } catch (NamingException ex) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public PesquisaSatisfacao getPesquisaSatisfacao(){
       if(PesquisaSatisfacao == null){
           if(loggedUser()){
             PesquisaSatisfacao = getLoggedUser().getPesquisaSatisfacao();
             if(PesquisaSatisfacao == null){
               PesquisaSatisfacao = new PesquisaSatisfacao();
               PesquisaSatisfacao.setDate(new Date()); 
               PesquisaSatisfacao.setUser(getLoggedUser());
             }
           }
       }
       return PesquisaSatisfacao;
    }
    
    public String savePesquisaSatisfacao(){
        //String id = this.getUserPesquisa();
        //PesquisaSatisfacao.setId((Integer.valueOf(id)));
        try {
            daoBase.insertOrUpdate(getPesquisaSatisfacao(), getEntityManager());
            return "index.xhtml";
        } catch (SQLException ex) {
            Logger.getLogger(PesquisaSatisfacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    /*
        retorna o id do usuario contido na url de acesso vivasemtabaco.com.br/pagina...xhtml?uid=XXXX
    */
    private String getUserPesquisa(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = ((HttpServletRequest) request).getRequestURI();
        String id;
	id = url.substring( url.lastIndexOf('?') + 1 );
        return id;
        
    }
    
    
}
