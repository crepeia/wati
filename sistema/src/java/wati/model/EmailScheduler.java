/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import wati.controller.BaseController;
import wati.persistence.GenericDAO;
import wati.persistence.UserDAO;
import wati.utility.EMailSSL;

/**
 *
 * @author Thiago
 */
//@TransactionManagement(TransactionManagementType.BEAN)
@Stateless
public class EmailScheduler {

    @PersistenceContext
    private EntityManager entityManager = null;

    UserDAO dao;
    private EMailSSL emailSSL;
      
   final String MENSAGEM_DATA_DIFERENTE;

    final String MENSAGEM_PRIMEIRA_SEMANA;

    final String MENSAGEM_SEGUNDA_SEMANA;

    final String MENSAGEM_TERCEIRA_SEMANA;

    final String MENSAGEM_MENSAL;

    public EmailScheduler() {
        this.MENSAGEM_MENSAL = this.getText("msg.mensal1") + "<br><br>"
                + this.getText("msg.mensal2") + "<br><br>"
                + this.getText("msg.mensal3") + "<br>"
                + this.getText("msg.mensal4")  + "<br><br>"
                + this.getText("cordialmente") + "<br><br>"
                + this.getText("equipe.vst");
        this.MENSAGEM_TERCEIRA_SEMANA = this.getText("msg.terceira.semana1") + "<br><br>"
                + this.getText("msg.terceira.semana2") + "<br><br>"
                + this.getText("msg.terceira.semana3") + "<br>"
                + this.getText("msg.terceira.semana4")  + "<br><br>"
                + this.getText("cordialmente") + "<br><br>"
                + this.getText("equipe.vst");
        this.MENSAGEM_SEGUNDA_SEMANA = this.getText("msg.segunda.semana1") + "<br><br>"
                + this.getText("msg.segunda.semana2") + "<br>"
                + this.getText("msg.segunda.semana3") + "<br>"
                + this.getText("msg.segunda.semana4") + "<br><br>"
                + this.getText("msg.segunda.semana5")  + "<br><br>"
                + this.getText("msg.segunda.semana6") + "<br>"
                + this.getText("msg.segunda.semana7")  + "<br><br>"
                + this.getText("cordialmente") + "<br><br>"
                + this.getText("equipe.vst");
        this.MENSAGEM_PRIMEIRA_SEMANA = this.getText("msg.primeira.semana1") + "<br>"
                + this.getText("msg.primeira.semana2") + "<br>"
                + this.getText("msg.primeira.semana3") + "<br>"
                + this.getText("msg.primeira.semana4") + "<br>"
                + this.getText("msg.primeira.semana5") + "<br><br>"
                + this.getText("msg.primeira.semana6") + "<br><br>"
                + this.getText("cordialmente") + "<br><br>"
                + this.getText("equipe.vst");
        this.MENSAGEM_DATA_DIFERENTE = this.getText("hello") + "<br><br>" 
                + this.getText("msg.data.diferente1") + "<br>"
                + this.getText("msg.data.diferente2") + "<br>"
                + this.getText("msg.data.diferente3") + "<br><br>"
                + this.getText("cordialmente") + "<br><br>"
                + this.getText("equipe.vst");
        try {
            dao = new UserDAO(User.class);
            emailSSL = new EMailSSL();
        } catch (NamingException ex) {
            Logger.getLogger(EmailScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Schedule(minute = "0", hour = "0", dayOfWeek = "*")
    public void scheduleEmails() {
        String from = "watiufjf@gmail.com";
        System.out.println(this.getText("iniciando.envio.emails"));
        List<User> usuarios;

        usuarios = dao.acompanhamentoDataDiferente(entityManager);
        if (!usuarios.isEmpty()) {
            try {
                for (User usuario : usuarios) {
                    emailSSL.send(from, usuario.getEmail(), "Wati", MENSAGEM_DATA_DIFERENTE);
                    Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, this.getText("email.data.diferente.enviado") + usuario.getEmail());
                    usuario.getProntoParaParar().setEmailDataDiferente(new Date());
                    dao.updateWithoutTransaction(usuario, entityManager);
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmailScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        usuarios = dao.acompanhamentoPrimeiraSemana(entityManager);
        if (!usuarios.isEmpty()) {
            try {
                for (User usuario : usuarios) {
                    emailSSL.send(from, usuario.getEmail(), "Wati", MENSAGEM_PRIMEIRA_SEMANA);
                    Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, this.getText("email.primeira.semana.enviado") + usuario.getEmail());
                    usuario.getProntoParaParar().setEmailPrimeiraSemana(new Date());
                    dao.updateWithoutTransaction(usuario, entityManager);
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmailScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        usuarios = dao.acompanhamentoSegundaSemana(entityManager);
        if (!usuarios.isEmpty()) {
            try {
                for (User usuario : usuarios) {
                    emailSSL.send(from, usuario.getEmail(), "Wati", MENSAGEM_SEGUNDA_SEMANA);
                    Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, this.getText("email.segunda.semana.enviado") + usuario.getEmail());
                    usuario.getProntoParaParar().setEmailSegundaSemana(new Date());
                    dao.updateWithoutTransaction(usuario, entityManager);
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmailScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        usuarios = dao.acompanhamentoTerceiraSemana(entityManager);
        if (!usuarios.isEmpty()) {
            try {
                for (User usuario : usuarios) {
                    emailSSL.send(from, usuario.getEmail(), "Wati", MENSAGEM_TERCEIRA_SEMANA);
                    Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, this.getText("email.terceira.semana.enviado") + usuario.getEmail());
                    usuario.getProntoParaParar().setEmailTerceiraSemana(new Date());
                    dao.updateWithoutTransaction(usuario, entityManager);
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmailScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        usuarios = dao.acompanhamentoMensal(entityManager);
        if (!usuarios.isEmpty()) {
            try {
                for (User usuario : usuarios) {
                    emailSSL.send(from, usuario.getEmail(), "Wati", MENSAGEM_MENSAL);
                    Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, this.getText("email.mensal.enviado") + usuario.getEmail());
                    usuario.getProntoParaParar().setEmailMensal(new Date());
                    dao.updateWithoutTransaction(usuario, entityManager);
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmailScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
      public final String getText (String key){
           return PropertyResourceBundle.getBundle("wati.utility.messages").getString(key);   
        }

}
