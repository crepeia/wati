/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.model;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author thiago
 */
@Entity
@Table(name = "tb_cigarrosWS")
public class CigarrosWS implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "data")
    private Date data;

    @Column(name = "salvo")
    private boolean salvo;

    @Column(name = "cigarros_diario")
    private int cigarrosDiario;

    @OneToOne
    private UserWS userWS;
  
    public CigarrosWS(){}

    public CigarrosWS(long id, Date data, boolean salvo, int cigarrosDiario, UserWS user){
        this.id = id;
        this.data = data;
        this.salvo = salvo;
        this.cigarrosDiario = cigarrosDiario;
        this.userWS = user;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isSalvo() {
        return salvo;
    }

    public void setSalvo(boolean salvo) {
        this.salvo = salvo;
    }

    public int getCigarrosDiario() {
        return cigarrosDiario;
    }

    public void setCigarrosDiario(int cigarrosDiario) {
        this.cigarrosDiario = cigarrosDiario;
    }

    public UserWS getUserWS() {
        return userWS;
    }

    public void setUserWS(UserWS userWS) {
        this.userWS = userWS;
    }

    
}    
    
    
    
    