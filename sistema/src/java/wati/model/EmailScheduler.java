/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wati.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import wati.persistence.GenericDAO;
import wati.persistence.UserDAO;
import wati.utility.EMailSSL;

/**
 *
 * @author Thiago
 */
@Stateless
public class EmailScheduler {
  
  @PersistenceContext
  private EntityManager entityManager = null;
  
  private GenericDAO genericDAO;
  
  private AcompanhamentoEmail acompanhamentoEmail;
  UserDAO dao;
  
  private EMailSSL emailSSL; 
  
  
  public EmailScheduler(){
      try {
          genericDAO = new GenericDAO(AcompanhamentoEmail.class);
          dao = new UserDAO();
          emailSSL = new EMailSSL();
      } catch (NamingException ex) {
          Logger.getLogger(EmailScheduler.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  
    
    
 @Schedule(second="0", minute = "0", hour = "0", dayOfWeek = "*")
 public void scheduleEmails() throws SQLException{
    
     try{
        String from = "watiufjf@gmail.com";
        List<User> usuarios;

        usuarios = dao.acompanhamentoDataDifente(entityManager);
        for (User usuario : usuarios){
           acompanhamentoEmail = new AcompanhamentoEmail();
           emailSSL.send(from, usuario.getEmail(), "Wati" , acompanhamentoEmail.MENSAGEM_DATA_DIFERENTE);
           Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, "Email data diferente enviado para" + usuario.getEmail());
           acompanhamentoEmail.setUsuario(usuario);
           acompanhamentoEmail.setDataDiferente(new Date());
           genericDAO.insert(acompanhamentoEmail, entityManager);

       }

          usuarios = dao.acompanhamentoPrimeiraSemana(entityManager);
          for (User usuario : usuarios){
            acompanhamentoEmail = new AcompanhamentoEmail();
            emailSSL.send(from, usuario.getEmail(), "Wati" , acompanhamentoEmail.MENSAGEM_PRIMEIRA_SEMANA);
            Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, "Email semana 1 enviado para" + usuario.getEmail());
            acompanhamentoEmail.setUsuario(usuario);
            acompanhamentoEmail.setPrimeiraSemana(new Date());
            genericDAO.insertOrUpdate(acompanhamentoEmail, entityManager);

        }

        usuarios = dao.acompanhamentoSegundaSemana(entityManager);
          for (User usuario : usuarios){
            acompanhamentoEmail = new AcompanhamentoEmail();
            emailSSL.send(from, usuario.getEmail(), "Wati" , acompanhamentoEmail.MENSAGEM_SEGUNDA_SEMANA);
            Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, "Email semana 2 enviado para" + usuario.getEmail());
            acompanhamentoEmail.setUsuario(usuario);
            acompanhamentoEmail.setSegundaSemana(new Date());
            genericDAO.insertOrUpdate(acompanhamentoEmail, entityManager);

        }

        usuarios = dao.acompanhamentoTerceiraSemana(entityManager);
          for (User usuario : usuarios){
            acompanhamentoEmail = new AcompanhamentoEmail();
            emailSSL.send(from, usuario.getEmail(), "Wati" , acompanhamentoEmail.MENSAGEM_TERCEIRA_SEMANA);
            Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, "Email semana 3 enviado para" + usuario.getEmail());
            acompanhamentoEmail.setUsuario(usuario);
            acompanhamentoEmail.setTereiraSemana(new Date());
            genericDAO.insertOrUpdate(acompanhamentoEmail, entityManager);

        }

        usuarios = dao.acompanhamentoMensal(entityManager);
          for (User usuario : usuarios){
            acompanhamentoEmail = new AcompanhamentoEmail();
            emailSSL.send(from, usuario.getEmail(), "Wati" , acompanhamentoEmail.MENSAGEM_MENSAL);
            Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, "Email mensal enviado para" + usuario.getEmail());
            acompanhamentoEmail.setUsuario(usuario);
            acompanhamentoEmail.setMensal(new Date());
            genericDAO.insertOrUpdate(acompanhamentoEmail, entityManager);
         
        }
          
     }catch(SQLException e){
         Logger.getLogger(EmailScheduler.class.getName()).log(Level.SEVERE, "Erro SQL ao enviar Email");
     }
     
 
 }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
   
    
}
    

