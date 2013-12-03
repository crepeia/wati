/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.controller;

import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import wati.model.ProntoParaParar;

/**
 *
 * @author hedersb
 */
@ManagedBean(name = "prontoParaPararController")
@SessionScoped
public class ProntoParaPararController extends BaseController<ProntoParaParar> {

	private ProntoParaParar prontoParaParar;

	private String[] vencendoAFissuraMarcados = {};

	private int dia;
	private int mes;
	private int ano;

	/**
	 * @return the prontoParaParar
	 */
	public ProntoParaParar getProntoParaParar() {
		return prontoParaParar;
	}

	/**
	 * @param prontoParaParar the prontoParaParar to set
	 */
	public void setProntoParaParar(ProntoParaParar prontoParaParar) {
		this.prontoParaParar = prontoParaParar;
	}
	private static final int VENCENDO_FISSURA_BEBER_AGUA = 0;
	private static final int VENCENDO_FISSURA_COMER = 1;
	private static final int VENCENDO_FISSURA_RELAXAMENTO = 2;
	private static final int VENCENDO_FISSURA_LER_RAZOES = 3;

	public ProntoParaPararController() {
		super(ProntoParaParar.class);
		this.prontoParaParar = new ProntoParaParar();
	}

	public String vencendoAFissura() {
		this.prontoParaParar.limparVencendoFissura();

		for (String string : vencendoAFissuraMarcados) {

			switch (Integer.valueOf(string) - 1) {
				case VENCENDO_FISSURA_BEBER_AGUA:
					this.prontoParaParar.setEnfrentarFissuraBeberAgua(true);
					break;
				case VENCENDO_FISSURA_COMER:
					this.prontoParaParar.setEnfrentarFissuraComer(true);
					break;
				case VENCENDO_FISSURA_LER_RAZOES:
					this.prontoParaParar.setEnfrentarFissuraLerRazoes(true);
					break;
				case VENCENDO_FISSURA_RELAXAMENTO:
					this.prontoParaParar.setEnfrentarFissuraRelaxamento(true);
					break;
			}

		}
		try {
			this.getDaoBase().insertOrUpdate(prontoParaParar, this.getEntityManager());
			return "pronto-para-parar-de-fumar-medicamentos.xhtml";
		} catch (SQLException ex) {
			Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public String fumarMetodosDeParar() {

		return "pronto-para-parar-de-fumar-metodos-de-parar.xhtml";

	}

	public String dataParaParar() {

//		if ( this.ano.equals("Atual") ) {
//			this.ano = String.valueOf( new GregorianCalendar().get( GregorianCalendar.YEAR ) );
//		}
//		
//		if ( this.mes.equals("Atual") ) {
//			this.mes = String.valueOf( new GregorianCalendar().get( GregorianCalendar.MONTH ) );
//		}
//		
//		if ( this.dia.equals("Atual") ) {
//			this.dia = String.valueOf( new GregorianCalendar().get( GregorianCalendar.DAY_OF_MONTH ) );
//		}
		this.prontoParaParar.setDataParar(new GregorianCalendar(this.ano, this.mes, this.dia).getTime());

		try {
			this.getDaoBase().insertOrUpdate(prontoParaParar, this.getEntityManager());
			return "pronto-para-parar-de-fumar-como-evitar-recaidas.xhtml";
		} catch (SQLException ex) {
			Logger.getLogger(ProntoParaPararController.class.getName()).log(Level.SEVERE, null, ex);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problemas ao gravar a data de parar de fumar.", null));
		}
		return null;
	}

	/**
	 * @return the vencendoAFissuraMarcados
	 */
	public String[] getVencendoAFissuraMarcados() {
		return vencendoAFissuraMarcados;
	}

	/**
	 * @param vencendoAFissuraMarcados the vencendoAFissuraMarcados to set
	 */
	public void setVencendoAFissuraMarcados(String[] vencendoAFissuraMarcados) {
		this.vencendoAFissuraMarcados = vencendoAFissuraMarcados;
	}

	public void save(ActionEvent actionEvent) {

	}

	/**
	 * @return the dia
	 */
	public int getDia() {
		return dia;
	}

	/**
	 * @param dia the dia to set
	 */
	public void setDia(int dia) {
		this.dia = dia;
	}

	/**
	 * @return the mes
	 */
	public int getMes() {
		return mes;
	}

	/**
	 * @param mes the mes to set
	 */
	public void setMes(int mes) {
		this.mes = mes;
	}

	/**
	 * @return the ano
	 */
	public int getAno() {
		return ano;
	}

	/**
	 * @param ano the ano to set
	 */
	public void setAno(int ano) {
		this.ano = ano;
	}

}
