/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wati.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Thiago
 */
public class UserDAO {
    
    @PersistenceContext
    private EntityManager entityManager = null;
    
    public List acompanhamentoDataDifente(EntityManager entityManager){
        Query query = entityManager.createQuery("from User as u where u.prontoParaParar.dataParar != u.prontoParaParar.dataInserido");
        return query.getResultList();
        
    }
    
    public List acompanhamentoPrimeiraSemana(EntityManager entityManager){
        Query query = entityManager.createQuery("from User as u where u.prontoParaParar.dataInserido >= :data and u.acompanhamentoEmail.segundaSemana is null");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -14);
        Date data = c.getTime();
        query.setParameter("data",data );
        return query.getResultList();
        
    }
    
    public List acompanhamentoSegundaSemana(EntityManager entityManager){
        Query query = entityManager.createQuery("from User as u where u.prontoParaParar.dataInserido >= :data and u.acompanhamentoEmail.segundaSemana is null");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -21);
        Date data = c.getTime();
        query.setParameter("data",data );
        return query.getResultList();
        
    }
    
   public List acompanhamentoTerceiraSemana(EntityManager entityManager){
        Query query = entityManager.createQuery("from User as u where u.prontoParaParar.dataInserido >= :data and u.acompanhamentoEmail.terceiraSemana is null");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -28);
        Date data = c.getTime();
        query.setParameter("data",data );
        return query.getResultList();
        
    }
    
    public List acompanhamentoMensal(EntityManager entityManager){
        Query query = entityManager.createQuery("from User as u where u.acompanhamentoEmail.terceiraSemana is not null and (u.acompanhamentoEmail.mensal is null or u.acompanhamentoEmail.mensal >= :data)");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -30);
        Date data = c.getTime();
        query.setParameter("data",data );
        return query.getResultList();
        
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    
    
    
}
