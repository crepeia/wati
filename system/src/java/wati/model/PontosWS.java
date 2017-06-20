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
@Table(name = "tb_pontosWS")
public class PontosWS implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @OneToOne
    private UserWS userWS;

    @Column(name = "ponto_dica")
    private int pontoDica;
    
    @Column(name = "ponto_site")
    private int pontoSite;
    
    @Column(name = "pontos_cadasroApp")
    private int pontoCadastroApp;
    
    @Column(name = "ponto_registro")
    private int pontoRegistro;
    
    @Column(name = "ponto_naoFumar")
    private int pontoNaoFumar;
  
    public PontosWS(){}

    public PontosWS(UserWS user, int pontoDica, int pontoSite, int pontoCadastroApp, int pontoRegistro, int pontoNaoFumar){
        this.userWS = user;
        this.pontoDica = pontoDica;
        this.pontoSite = pontoSite;
        this.pontoCadastroApp = pontoCadastroApp;
        this.pontoRegistro = pontoRegistro;
        this.pontoNaoFumar = pontoNaoFumar;
    }

    public UserWS getUserWS() {
        return userWS;
    }

    public void setUserWS(UserWS userWS) {
        this.userWS = userWS;
    }

    public int getPontoDica() {
        return pontoDica;
    }

    public void setPontoDica(int pontoDica) {
        this.pontoDica = pontoDica;
    }

    public int getPontoSite() {
        return pontoSite;
    }

    public void setPontoSite(int pontoSite) {
        this.pontoSite = pontoSite;
    }

    public int getPontoCadastroApp() {
        return pontoCadastroApp;
    }

    public void setPontoCadastroApp(int pontoCadastroApp) {
        this.pontoCadastroApp = pontoCadastroApp;
    }

    public int getPontoRegistro() {
        return pontoRegistro;
    }

    public void setPontoRegistro(int pontoRegistro) {
        this.pontoRegistro = pontoRegistro;
    }

    public int getPontoNaoFumar() {
        return pontoNaoFumar;
    }

    public void setPontoNaoFumar(int pontoNaoFumar) {
        this.pontoNaoFumar = pontoNaoFumar;
    }
    
    
    
}    
    
    
    
    