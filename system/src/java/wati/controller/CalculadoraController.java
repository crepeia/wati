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
    
    private String numberCigaretteDay;
    private String costMasso;
    private String costWeek;
    private String costMonth;
    private String costYear;
    
    public CalculadoraController() {
        
    }
    
    public void calculate() {
        int numeroCigarrosDiaInt = Integer.valueOf( numberCigaretteDay );
        double custoMassoDbl = Double.valueOf( costMasso);
        double numeroMassosDia = numeroCigarrosDiaInt / 20.0;
		      System.out.println("");
        double custoDia = numeroMassosDia * custoMassoDbl;
        costWeek = String.format("%.2f", 7.0*custoDia);
        costMonth = String.format("%.2f", 30.0*custoDia);
        costYear = String.format("%.2f", 365.0*custoDia);
        
    }

    public String getNumberCigaretteDay() {
        return numberCigaretteDay;
    }

    public void setNumberCigaretteDay(String numberCigaretteDay) {
        this.numberCigaretteDay = numberCigaretteDay;
    }

    public String getCostMasso() {
        return costMasso;
    }

    public void setCostMasso(String costMasso) {
        this.costMasso = costMasso;
    }

    public String getCostWeek() {
        return costWeek;
    }

    public void setCostWeek(String costWeek) {
        this.costWeek = costWeek;
    }

    public String getCostMonth() {
        return costMonth;
    }

    public void setCostMonth(String costMonth) {
        this.costMonth = costMonth;
    }

    public String getCostYear() {
        return costYear;
    }

    public void setCostYear(String costYear) {
        this.costYear = costYear;
    }

    
    
}
