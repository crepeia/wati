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
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
//@TransactionManagement(TransactionManagementType.BEAN)
@Stateless
public class EmailScheduler {

    @PersistenceContext
    private EntityManager entityManager = null;

    UserDAO dao;
    private EMailSSL emailSSL;

    final String MENSAGEM_DATA_DIFERENTE = 
            "Olá!" + "<br><br>" 
            + "Não deixe de começar seu plano na data escolhida." + "<br>" 
            + "Enviaremos e-mails de acompanhamento durante este período." + "<br>" 
            + "Caso você queira mudar seu plano, entre no nosso site. + <br><br>"
            + "Cordialmente," + "<br><br>"
            + "Equipe Viva sem Tabaco";

    final String MENSAGEM_PRIMEIRA_SEMANA = 
            "Faz uma semana que você entrou no nosso programa - Viva sem Tabaco." + "<br>"
            + "Se você já conseguiu parar de fumar, você pode estar sentindo algumas mudanças positivas no seu corpo:" + "<br>"
            + "* Melhora do ritmo cardíaco," + "<br>"
            + "* Redução do nível de monoxído de carbono no sangue - o mesmo que sai dos escapamentos de carros e ônibus," + "<br>"
            + "* Redução do risco úlceras no estômago. Se você ainda, não conseguiu parar." + "<br><br>"
            + "Sabemos que o processo de parar de fumar é difícil. O importante é continuar tentando. Convidamos a voltar em nosso site e tentar um novo plano." + "<br><br>"
            + "Cordialmente," + "<br><br>"
            + "Equipe Viva sem Tabaco";

    final String MENSAGEM_SEGUNDA_SEMANA = 
            "Parabéns! Fazem duas semanas que você decidiu parar. Nesta semana, gostaríamos de falar brevemente sobre dois temas: desejo intenso de fumar (fissura) e o medo do ganho de peso." + "<br><br>"
            + "A fissura, ou desejo intenso de fumar, costuma demorar apenas alguns minutos. Veja abaixo algumas dicas para vencer a fissura:" + "<br>"
            + "Comer frutas, beber água podem ajudá-lo a vencer estes momentos." + "<br>"
            + "Separamos para você, um exercício de relaxamento preparado por especialistas em tabagismo. Você pode acessá-lo ou fazer o download no site." + "<br><br>"
            + "Para evitar o ganho de peso, tente fazer exercícios físicos (como caminhada) e comer mais frutas e vegetais." + "<br><br>"
            + "Não conseguiu parar ainda? Teve uma recaída?" + "<br>"
            + "Sabemos que o processo de parar de fumar é difícil. Temos algumas informações e exercícios que podem ajudá-lo em nosso site." + "<br><br>"
            + "Cordialmente," + "<br><br>" 
            + "Equipe Viva sem Tabaco";

    final String MENSAGEM_TERCEIRA_SEMANA = 
            "Parabéns! Você está superando um dos maiores desafios de sua vida. E para comemorar, que tal comprar alguma coisa ou presentear alguém com o dinheiro economizado com o cigarro?" + "<br><br>"
            + "Você já deve ter percebido melhoras no seu organismo como seu fôlego, auto-estima. Além disso a partir desta semana, o risco de ter um ataque cardíaco começa a reduzir. Seus pulmões começam a funcionar melhor." + "<br><br>"
            + "Não conseguiu parar ainda? Teve uma recaída?" + "<br>"
            + "Sabemos que o processo de parar de fumar é difícil. Temos algumas informações e exercícios que podem ajudá-lo em nosso site." + "<br><br>"
            + "Cordialmente," + "<br><br>"
            + "Equipe Viva sem Tabaco";

    final String MENSAGEM_MENSAL = 
            "Hora do acompanhamento mensal! Caso tenha alguma dúvida sobre tabagismo, não deixe de entrar em nosso programa." + "<br><br>"
            + "Nós temos uma página no facebook e conta do twitter. Deixe um recado para gente ou para outras as pessoas que também estão tentando parar." + "<br><br>"
            + "Não conseguiu parar ainda? Teve uma recaída?" + "<br>"
            + "Sabemos que o processo de parar de fumar é difícil. Temos algumas informações e exercícios que podem ajudá-lo em nosso site." + "<br><br>" 
            + "Cordialmente," + "<br><br>"
            + "Equipe Viva sem Tabaco";

    public EmailScheduler() {
        try {
            dao = new UserDAO(User.class);
            emailSSL = new EMailSSL();
        } catch (NamingException ex) {
            Logger.getLogger(EmailScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Schedule(second = "5", minute = "*", hour = "*", dayOfWeek = "*")
    public void scheduleEmails() {
        String from = "watiufjf@gmail.com";
        System.out.println("Iniciando Envio de emails");
        List<User> usuarios;

        usuarios = dao.acompanhamentoDataDiferente(entityManager);
        if (!usuarios.isEmpty()) {
            try {
                for (User usuario : usuarios) {
                    emailSSL.send(from, usuario.getEmail(), "Wati", MENSAGEM_DATA_DIFERENTE);
                    Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, "Email data diferente enviado para " + usuario.getEmail());
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
                    Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, "Email primeira semana enviado para " + usuario.getEmail());
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
                    Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, "Email segunda semana enviado para " + usuario.getEmail());
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
                    Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, "Email terceira semana enviado para " + usuario.getEmail());
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
                    Logger.getLogger(EmailScheduler.class.getName()).log(Level.INFO, "Email mensal enviado para " + usuario.getEmail());
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

}
