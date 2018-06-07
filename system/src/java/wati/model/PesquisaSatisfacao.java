/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author felipe
 */
@Entity
@Table(name = "tb_pesquisaSatisfacao")
public class PesquisaSatisfacao implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    
    @Column(name ="Q1")
    private String q1;
    
    @Column(name ="Q2")
    private String q2;
     
    @Column(name ="Q3")
    private String q3;
    
    @Column(name ="Q4")
    private String q4;
    
    @Column(name ="Q5")
    private String q5;
     
     
    @Column(name ="Q6")
    private String q6;
     
      
      
     
     @OneToOne
    private User user;
     
       public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    /**
     *
     * @return
     */
    public String getQ1(){
        return q1;
    }
    
    public void setQ1(String q1_){
        this.q1 = q1_;
    }
    
     public String getQ2(){
        return q2;
    }
    
    public void setQ2(String q){
        this.q2 = q;
    }
    
     public String getQ3(){
        return q3;
    }
    
    public void setQ3(String q){
        this.q3 = q;
    }
    
    
     public String getQ4(){
        return q4;
    }
    
    public void setQ4(String q4){
        this.q4 = q4;
    }
    
     public String getQ5(){
        return q5;
    }
    
    public void setQ5(String q){
        this.q5 = q;
    }
    
     public String getQ6(){
        return q6;
    }
    
    public void setQ6(String q){
        this.q6 = q;
    }
    
    public String getUrlPesquisaSatisfacao(){
        return "http://www.vivasemtabaco.com.br/pesquisa-satisfacao.xhtml?uid="+user.getId();
    }
    
    
}
