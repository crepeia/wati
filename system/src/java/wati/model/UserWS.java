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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import wati.controller.BaseController;

/**
 *
 * @author thiago
 */
@Entity
@Table(name = "tb_userWS") 
public class UserWS implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "cigarros")
    private Integer cigarros;

    @Column(name = "valor_maco")
    private String valorMaco;

    @Column(name = "gender")
    private String gender;

    public UserWS(){}

    public UserWS(int id, String name, String email, Integer cigarros, String valorMaco, String gender){
        this.id = id;
        this.name = name;
        this.email = email;
        this.cigarros = cigarros;
        this.valorMaco = valorMaco;
        this.gender = gender;

    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCigarros() {
        return cigarros;
    }

    public void setCigarros(Integer cigarros) {
        this.cigarros = cigarros;
    }

    public String getValorMaco() {
        return valorMaco;
    }

    public void setValorMaco(String valorMaco) {
        this.valorMaco = valorMaco;
    }

    public String getGender() { return gender;}

    public void setGender(String gender) { this.gender = gender; }

}    
    
    
    
    