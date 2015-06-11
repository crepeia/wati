/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wati.persistence;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import wati.model.User;

/**
 *
 * @author Thiago
 */
public class UserDAO extends GenericDAO {
    
    public UserDAO(Class classe) throws NamingException {
        super(classe);
    }
    
    public List acompanhamentoDataDiferente(EntityManager entityManager){
        Query query = entityManager.createQuery("from User as u where u.prontoParaParar.dataParar != u.prontoParaParar.dataInserido and u.prontoParaParar.dataInserido <= :data and u.prontoParaParar.emailDataDiferente is null and u.receiveEmails == true");
        Date data = Calendar.getInstance().getTime();
        query.setParameter("data",data );
        query.setHint("toplink.refresh", "true");
        return query.getResultList();
    }
    
    
     public List acompanhamentoSemanal(EntityManager entityManager, int semana){
        Query query = entityManager.createQuery("from User as u where u.prontoParaParar.dataInserido <= :data and u.receiveEmails == true");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -7*(semana+1));
        Date data = c.getTime();
        query.setParameter("data",data );
        return query.getResultList();
        
    }
    
    public List acompanhamentoMensal(EntityManager entityManager){
        Query query = entityManager.createQuery("from User as u where u.receiveEmails == true and u.prontoParaParar.emailTerceiraSemana is not null and ((u.prontoParaParar.emailMensal is null and u.prontoParaParar.emailTerceiraSemana <= :data) or (u.prontoParaParar.emailMensal is not null and u.prontoParaParar.emailMensal <= :data and u.prontoParaparar.emailMensalCont < 12))");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -30);
        Date data = c.getTime();
        query.setParameter("data",data );
        return query.getResultList();
        
    }
    
    public void updateWithoutTransaction(User objeto, EntityManager entityManager) throws SQLException {
		try {

			entityManager.merge(objeto);

		} catch (Exception erro) {
			throw new SQLException(erro);
		}
	}

}
