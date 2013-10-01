/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author hedersb
 */
@ManagedBean(name = "calculadoraController")
@SessionScoped
public class CalculadoraController implements Serializable {
    
    private String numeroCigarrosDia;
    private String custoMasso;
    private double custoSemana;
    private double custoMes;
    private double custoAno;
    
    public CalculadoraController() {
        
    }
    
    public void calcular( ActionEvent actionEvent ) {
        
        int numeroCigarrosDiaInt = Integer.valueOf( numeroCigarrosDia );
        double custoMassoDbl = Double.valueOf( custoMasso );
        double numeroMassosDia = numeroCigarrosDiaInt / 20.0;
        
        double custoDia = numeroMassosDia * custoMassoDbl;
        custoSemana = 7.0*custoDia;
        custoMes = 30.0*custoDia;
        custoAno = 365.0*custoDia;
        
    }

    /**
     * @return the numeroCigarrosDia
     */
    public String getNumeroCigarrosDia() {
        return numeroCigarrosDia;
    }

    /**
     * @param numeroCigarrosDia the numeroCigarrosDia to set
     */
    public void setNumeroCigarrosDia(String numeroCigarrosDia) {
        this.numeroCigarrosDia = numeroCigarrosDia;
    }

    /**
     * @return the custoMasso
     */
    public String getCustoMasso() {
        return custoMasso;
    }

    /**
     * @param custoMasso the custoMasso to set
     */
    public void setCustoMasso(String custoMasso) {
        this.custoMasso = custoMasso;
    }

    /**
     * @return the custoSemana
     */
    public double getCustoSemana() {
        return custoSemana;
    }

    /**
     * @param custoSemana the custoSemana to set
     */
    public void setCustoSemana(double custoSemana) {
        this.custoSemana = custoSemana;
    }

    /**
     * @return the custoMes
     */
    public double getCustoMes() {
        return custoMes;
    }

    /**
     * @param custoMes the custoMes to set
     */
    public void setCustoMes(double custoMes) {
        this.custoMes = custoMes;
    }

    /**
     * @return the custoAno
     */
    public double getCustoAno() {
        return custoAno;
    }

    /**
     * @param custoAno the custoAno to set
     */
    public void setCustoAno(double custoAno) {
        this.custoAno = custoAno;
    }
    
    
}
