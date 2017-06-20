/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.ws;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.ws.WebServiceContext;
import wati.controller.BaseController;
import wati.model.CigarrosWS;
import wati.model.PontosWS;
import wati.model.User;
import wati.model.UserWS;
import wati.persistence.GenericDAO;
import wati.persistence.UserDAO;

/**
 *
 * @author hedersb
 */
@WebService(serviceName = "AppWebService")
public class AppWebService extends BaseController<CigarrosWS>{

    @Resource
    WebServiceContext context;

    @PersistenceContext
    private EntityManager entityManager;

    private UserDAO userDAO;
    private CigarrosWS cigarrosWS;
    GenericDAO<UserWS> userWSDAO;
    GenericDAO<CigarrosWS> cigarrosWSDAO;
    GenericDAO<PontosWS> pontosWSDAO;

    public AppWebService() {
        
        try {
            this.userDAO = new UserDAO();
            this.cigarrosWS = new CigarrosWS();
            //this.daoBase = new GenericDAO<UserWS>(UserWS.class);
            this.userWSDAO = new GenericDAO<UserWS>(UserWS.class);
            this.cigarrosWSDAO = new GenericDAO<CigarrosWS>(CigarrosWS.class);
            this.pontosWSDAO = new GenericDAO<PontosWS>(PontosWS.class);
            
        } catch (NamingException ex) {
            Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service to validate e-mail and password.
     */
    @WebMethod(operationName = "validate")
    public UserWS validate(@WebParam(name = "email") String email) {
        try {
            List<User> userList = this.userDAO.list("email", email, this.entityManager);
            
            if(userList.isEmpty()){
                String message = email + "não pode logar";
                Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, message);
            }else{
                User user = userList.get(0);
                UserWS userWS = new UserWS();
                userWS.setName(user.getName());
                userWS.setEmail(user.getEmail());
                userWS.setGender(Character.toString(user.getGender()));
                
                //this.getDaoBase().insertOrUpdate(userWS, this.getEntityManager());
                
                String message = email + "pode logar";
                
                Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, message);
                
                return userWS;
            }
        }catch (SQLException ex) {
            Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // authentication failure
    }
    
    @WebMethod(operationName = "usuariosFacebook")
    public boolean usuariosFacebook(@WebParam(name = "name") String name, @WebParam(name = "email") String email, @WebParam(name = "gender") String gender){
        try{
            List<UserWS> userList = this.userWSDAO.list("email", email, this.entityManager);
            if(userList.isEmpty()){
                UserWS userWS = new UserWS();
                userWS.setName(name);
                userWS.setEmail(email);
                userWS.setGender(gender);
                this.userWSDAO.insert(userWS, entityManager);
            }else{
                UserWS userWS = userList.get(0);
                userWS.setName(name);
                userWS.setEmail(email);
                userWS.setGender(gender);
                this.userWSDAO.update(userWS, entityManager);
            }
            return true;
        }catch (SQLException ex) {
            Logger.getLogger(CigarrosWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @WebMethod(operationName = "enviaValorCigarro")
    public boolean enviaValorCigarro(@WebParam(name = "cigarros") Integer cigarros, @WebParam(name = "valorMaco") String valorMaco, @WebParam(name = "email") String email){
        try{
            List<UserWS> userList = this.userWSDAO.list("email", email, this.entityManager);
            if(userList.isEmpty()){
                System.out.println("Usuário não cadastrado inserindo valores de cigarros");
            }else{
                UserWS userWS = userList.get(0);
                userWS.setCigarros(cigarros);
                userWS.setValorMaco(valorMaco);
                this.userWSDAO.update(userWS, entityManager);
                
                System.out.println(userWS.getCigarros());
                System.out.println(userWS.getValorMaco());
            }
            return true;
        }catch (SQLException ex) {
            Logger.getLogger(CigarrosWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
     @WebMethod(operationName = "mediaGrafico")
    public int mediaGrafico(@WebParam(name = "data") long data) throws SQLException{
        
        Query query = entityManager.createQuery("select sum(c.cigarrosDiario) from CigarrosWS as c where c.data <= :date");//");// as c where c.data <= 2017-06-11"); //+ new Date(data));
        query.setParameter("date",new Date(data));
        
        int cigarrosTotais = ((Long) query.getResultList().get(0)).intValue();
        System.out.println("cigarrosTotais: " + cigarrosTotais);
        
        List<UserWS> list = this.userWSDAO.list(entityManager);
        int numUser = list.size();
        System.out.println("NumUser: " + numUser);
        
        int media = cigarrosTotais/numUser;
        System.out.println("media: " + media);
            
        return media;
    }
        
    
                        
                    
   /* @WebMethod(operationName = "mediaGrafico")
    public float mediaGrafico(@WebParam(name = "data") long data){
        try{
            Session session = null;
            
            SessionFactory sfact = new Configuration().configure().buildSessionFactory();
            session = sfact.openSession();
             
            Criteria criteria = session.createCriteria(CigarrosWS.class).setProjection(Projections.sum("cigarrosDiario")).add(Restrictions.le("data", new Date(data)));//le: menor que a data passada por parametro
            float valorTotal = (float)criteria.uniqueResult();
            
            System.out.println("Valor total: " + valorTotal);
            
            List<UserWS> list = this.userWSDAO.list(entityManager);
            int numUser = list.size();
            
            System.out.println("NumUser: " + numUser);
            
            float media = 0;
           // media = valorTotal/numUser;
            System.out.println("media: " + media);
        }catch (SQLException ex) {
            Logger.getLogger(CigarrosWS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return 1;
        
    }*/
   
    
    @WebMethod(operationName = "enviaCigarros")
    public boolean enviaCigarros(@WebParam(name = "cigarros") int cigarros, @WebParam(name = "data") long data, @WebParam(name = "email") String email ) throws SQLException{
        
        try{
            List<UserWS> userList = this.userWSDAO.list("email", email, this.entityManager);
            if(userList.isEmpty()){
                System.out.println("Problema no email do DAO");
            }
            else{
                UserWS userWS = userList.get(0);
                
                System.out.println("cigarros " + cigarros);
                System.out.println("data " + data);
                System.out.println("user " + email);
                
                cigarrosWS.setCigarrosDiario(cigarros);
                cigarrosWS.setData(new Date(data));
                cigarrosWS.setUserWS(userWS);
                
                this.cigarrosWSDAO.insert(cigarrosWS, entityManager);
                
                System.out.println("cigarros " + cigarrosWS.getCigarrosDiario());
                System.out.println("data " + cigarrosWS.getData());
                System.out.println("user " + cigarrosWS.getUserWS().getName());
            }
                
        
            
            return true;
        }catch (SQLException ex) {
            Logger.getLogger(CigarrosWS.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return false;
    }
    
    @WebMethod(operationName = "enviaPontos")
    public boolean enviaPontos(@WebParam(name = "pDica") int pDica,@WebParam(name = "pCadastroApp") int pCadastroApp, @WebParam(name = "pRegistro") int pRegistro, @WebParam(name = "pNaoFumar") int pNaoFumar, @WebParam(name = "pVisitaSite") int pVisitaSite, @WebParam(name = "email") String email){
        try{
            List<UserWS> userList = this.userWSDAO.list("email", email, this.entityManager);
            if(userList.isEmpty()){
                System.out.println("Usuário não cadastrado ganhando pontos");
            }else{
                UserWS userWS = userList.get(0);
                
                Query query = entityManager.createQuery("from PontosWS as p where p.userWS <= :userWS");//");// as c where c.data <= 2017-06-11"); //+ new Date(data));
                query.setParameter("userWS",userWS);
                
                List resultado = query.getResultList();
                ArrayList<PontosWS> pontosWSList = new ArrayList<PontosWS>();
                System.out.println(resultado.size());
                for(int i = 0; i < resultado.size(); i++){
                    pontosWSList.add((PontosWS)resultado.get(i));
                    System.out.println(pontosWSList.get(i).getPontoCadastroApp());
                }
                
                PontosWS pontosWS = pontosWSList.get(0);
                pontosWS.setPontoDica(pDica);
                pontosWS.setPontoNaoFumar(pNaoFumar);
                pontosWS.setPontoRegistro(pRegistro);
                pontosWS.setPontoSite(pVisitaSite);
                pontosWS.setPontoCadastroApp(pCadastroApp);
                pontosWS.setUserWS(userWS);
                this.pontosWSDAO.insertOrUpdate(pontosWS, entityManager);
                
                System.out.println(pontosWS.getPontoCadastroApp());
                System.out.println(pontosWS.getUserWS().getName());
            }
            return true;
        }catch (SQLException ex) {
            Logger.getLogger(CigarrosWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    @WebMethod(operationName = "enviaNaoFumou")
    public boolean enviaNaoFumou(@WebParam(name = "email") String email){
        try{
            List<UserWS> userList = this.userWSDAO.list("email", email, this.entityManager);
            if(userList.isEmpty()){
                System.out.println("Usuário não cadastrado marcando que não fumou");
            }else{
                UserWS userWS = userList.get(0);
                //Query query = entityManager.createQuery("from CigarrosWS as c where c.userWS <= :userWS");//");// as c where c.data <= 2017-06-11"); //+ new Date(data));
                //query.setParameter("userWS",userWS);
                
               // List resultado = query.getResultList();
                //ArrayList<CigarrosWS> cigarrosWSList = new ArrayList<CigarrosWS>();
               // System.out.println(resultado.size());
                //for(int i = 0; i < resultado.size(); i++){
                  //  cigarrosWSList.add((CigarrosWS)resultado.get(i));
                    //System.out.println(cigarrosWSList.get(i).getData());
                //}
                
                CigarrosWS cWS = new CigarrosWS();//cigarrosWSList.get(0);
                cWS.setNaoFumou(true);
                cWS.setUserWS(userWS);
                this.cigarrosWSDAO.insertOrUpdate(cWS, entityManager);
            }
        }catch(SQLException ex) {
            Logger.getLogger(CigarrosWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @WebMethod(operationName = "calculaPosicao")
    public int calculaPosicao(@WebParam(name = "email") String email){
        int posicao = 0;
        int l = 0;
        try{
            List<UserWS> userList = this.userWSDAO.list("email", email, this.entityManager);
            if(userList.isEmpty()){
                System.out.println("Usuário não cadastrado querendo saber sua posição");
            }else{
                UserWS userWS = userList.get(0);
                System.out.println("user: " + userWS.getName());
                System.out.println("user: " + userWS.getId());
                
                Query query = entityManager.createQuery("from PontosWS as p where p.userWS <= :userWS");//");// as c where c.data <= 2017-06-11"); //+ new Date(data));
                query.setParameter("userWS",userWS);
                
                List resultado = query.getResultList();
                ArrayList<PontosWS> pontosWSList = new ArrayList<PontosWS>();
                System.out.println(resultado.size());
                for(int i = 0; i < resultado.size(); i++){
                    pontosWSList.add((PontosWS)resultado.get(i));
                    System.out.println(pontosWSList.get(i).getPontoCadastroApp());
                }
                System.out.println("teste: " + userWS.getId());
                ArrayList<Integer> pontos = new ArrayList<>();
                
                int pontoUser = pontosWSList.get(0).getPontoDica() + 
                            pontosWSList.get(0).getPontoCadastroApp() +
                            pontosWSList.get(0).getPontoNaoFumar() + 
                            pontosWSList.get(0).getPontoRegistro() + 
                            pontosWSList.get(0).getPontoSite();
                        
                for(int i = 0; i < pontosWSList.size(); i++){
                    pontos.add(pontosWSList.get(i).getPontoDica() + 
                            pontosWSList.get(i).getPontoCadastroApp() +
                            pontosWSList.get(i).getPontoNaoFumar() + 
                            pontosWSList.get(i).getPontoRegistro() + 
                            pontosWSList.get(i).getPontoSite());
                    System.out.println("pontos" + pontos.get(i));
                }
                
                if(pontos.size() == 1){
                    posicao = 1;
                    
                }
                if(pontos.size() == 2){
                    if(pontos.get(0) < pontoUser){
                        posicao = 2;
                        System.out.println("posicao2 " + posicao);
                    }else{
                        posicao = 1;
                        System.out.println("posicao1 " + posicao);
                    }
                }
                if(pontos.size() >= 3){
                    for(int j = 0; j < pontos.size(); j++){ //ordenacao
                        int a = pontos.get(j);
                        for(int k = j - 1 ; k >= 0 && pontos.get(k) > a; k--){
                            pontos.set(k+1, pontos.get(k));
                            pontos.set(k, a);
                        }
                    }
                    
                    while(pontos.get(l) < pontoUser){
                        System.out.println("eh menor");
                        l = l + 1;
                    }
                    posicao = l + 1;
                    System.out.println("posicao " + posicao);
                }
            }
            return posicao;
        }catch (SQLException ex) {
            Logger.getLogger(AppWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
}
