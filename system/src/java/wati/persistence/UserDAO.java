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
    
    public UserDAO() throws NamingException{
        super(User.class);
    }
    
    public List acompanhamentoDataDiferente(EntityManager entityManager){
        Query query = entityManager.createQuery("from User as u where "
                + "u.receiveEmails = true");
        //Date date = Calendar.getInstance().getTime();
        //query.setParameter("date",date);
        query.setHint("toplink.refresh", "true");
        return query.getResultList();
    }
    
    
     public List acompanhamentoSemanal(EntityManager entityManager, int semana){
        Query query = entityManager.createQuery("from User as u where "
                + "u.receiveEmails == true and"
                + "u.prontoParaParar.dataInserido <= :date and"
                + "u.prontoParaParar.followUpCount < :count");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -7*(semana));
        Date date = c.getTime();
        int count = semana + 1;
        query.setParameter("count", count);
        query.setParameter("date",date );
        return query.getResultList();
        
    }
    
    public List acompanhamentoMensal(EntityManager entityManager){
        Query query = entityManager.createQuery("from User as u where "
                + "u.receiveEmails == true and "
                + "u.prontoParaParar.followUpCount >= 4 and"
                + "u.prontoParaParar.followUpCount < 16 and");
        List<User> users = query.getResultList();
        for(User user : users){
            int count = user.getProntoParaParar().getFollowUpCount() - 4;
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, (count+1));
            Date date = c.getTime();          
            if(user.getProntoParaParar().getDataInserido().compareTo(date) > 0){
                users.remove(user);
            }
        }
        return users;
        
    }
      
    public void updateWithoutTransaction(User objeto, EntityManager entityManager) throws SQLException {
		try {

			entityManager.merge(objeto);

		} catch (Exception erro) {
			throw new SQLException(erro);
		}
	}

}
