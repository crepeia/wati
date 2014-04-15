/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wati.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Thiago
 */
@Entity
@Table(name="tb_acompanhamento_email")
public class AcompanhamentoEmail {
    
    @Transient
    final String MENSAGEM_DATA_DIFERENTE = "teste1";
    @Transient
    final String MENSAGEM_PRIMEIRA_SEMANA = "teste2";  
    @Transient
    final String MENSAGEM_SEGUNDA_SEMANA = "teste3";  
    @Transient
    final String MENSAGEM_TERCEIRA_SEMANA = "teste4";  
    @Transient
    final String MENSAGEM_MENSAL = "teste5"; 
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "dataDiferente")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDiferente;
    @Column(name = "primeiraSemana")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date primeiraSemana;
    @Column(name = "segundaSemana")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date segundaSemana;
    @Column(name = "terceiraSemana")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date terceiraSemana;
    @Column(name = "mensal")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date mensal;
    
    @OneToOne
    private User usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataDiferente() {
        return dataDiferente;
    }

    public void setDataDiferente(Date dataDiferente) {
        this.dataDiferente = dataDiferente;
    }

    

    public Date getPrimeiraSemana() {
        return primeiraSemana;
    }

    public void setPrimeiraSemana(Date primeiraSemana) {
        this.primeiraSemana = primeiraSemana;
    }

    public Date getSegundaSemana() {
        return segundaSemana;
    }

    public void setSegundaSemana(Date segundaSemana) {
        this.segundaSemana = segundaSemana;
    }

    public Date getTereiraSemana() {
        return terceiraSemana;
    }

    public void setTereiraSemana(Date tereiraSemana) {
        this.terceiraSemana = tereiraSemana;
    }

    public Date getMensal() {
        return mensal;
    }

    public void setMensal(Date mensal) {
        this.mensal = mensal;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
    
    
    
    
}
