 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.model;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import wati.controller.BaseController;

/**
 *
 * @author hedersb
 */
@Entity
@Table(name = "tb_pronto_para_parar")
public class ProntoParaParar extends BaseController implements Serializable {

    private static final int MOTIVATION_STANDARD_VALUE = 5;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "enfrentar_fissura_beber_agua")
    private boolean enfrentarFissuraBeberAgua;
    @Column(name = "enfrentar_fissura_comer")
    private boolean enfrentarFissuraComer;
    @Column(name = "enfrentar_fissura_relaxamento")
    private boolean enfrentarFissuraRelaxamento;
    @Column(name = "enfrentar_fissura_ler_razoes")
    private boolean enfrentarFissuraLerRazoes;
    @Column(name = "data_parar")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataParar;
    @Column(name = "tentou_parar")
    private boolean tentouParar;
    @Column(name = "evitar_recaida_1")
    private String evitarRecaida1;
    @Column(name = "evitar_recaida_2")
    private String evitarRecaida2;
    @Column(name = "evitar_recaida_3")
    private String evitarRecaida3;
    @Column(name = "evitar_recaida_fara_1")
    private String evitarRecaidaFara1;
    @Column(name = "evitar_recaida_fara_2")
    private String evitarRecaidaFara2;
    @Column(name = "evitar_recaida_fara_3")
    private String evitarRecaidaFara3;
    //@Column(name="usuario")
    @OneToOne
    private User usuario;
    @Column(name = "data_inserido")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInserido;

    @Column(name = "email_data_diferente")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date emailDataDiferente;
    @Column(name = "email_primeira_semana")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date emailPrimeiraSemana;
    @Column(name = "email_segunda_semana")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date emailSegundaSemana;
    @Column(name = "email_terceira_semana")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date emailTerceiraSemana;
    @Column(name = "email_mensal")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date emailMensal;
    @Column(name = "email_mensal_cont")
    private Integer emailMensalCont;
    
    // PHQ - Depression Scale
    
    @Column(name = "phq_1")
    private Integer phq1;
    @Column(name = "phq_2")
    private Integer phq2;
    @Column(name = "phq_3")
    private Integer phq3;
    @Column(name = "phq_4")
    private Integer phq4;
    @Column(name = "phq_5")
    private Integer phq5;
    @Column(name = "phq_6")
    private Integer phq6;
    @Column(name = "phq_7")
    private Integer phq7;
    @Column(name = "phq_8")
    private Integer phq8;
    @Column(name = "phq_9")
    private Integer phq9;
    
    // FTND - Nicotine Dependence Scale
    
    @Column(name = "ftnd_1")
    private Integer ftnd1;
    @Column(name = "ftnd_2")
    private Integer ftnd2;
    @Column(name = "ftnd_3")
    private Integer ftnd3;
    @Column(name = "ftnd_4")
    private Integer ftnd4;
    @Column(name = "ftnd_5")
    private Integer ftnd5;
    @Column(name = "ftnd_6")
    private Integer ftnd6;
    
    // Motivation Slider
        
    @Column(name="mot_01")
    private Integer mot1;
    @Column(name="mot_02")
    private Integer mot2;
    
    @Column(name="pnad_a")
    private Boolean pnadA;
    @Column(name="pnad_b")
    private Boolean pnadB;
    @Column(name="pnad_c")
    private Boolean pnadC;
    @Column(name="pnad_d")
    private Boolean pnadD;
    @Column(name="pnad_e")
    private Boolean pnadE;
    @Column(name="pnad_f")
    private Boolean pnadF;
    @Column(name="pnad_g")
    private Boolean pnadG;
    @Column(name="pnad_h")
    private Boolean pnadH;
    
     

    public ProntoParaParar() {
        this.dataInserido = ((GregorianCalendar) GregorianCalendar.getInstance()).getTime();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the enfrentarFissuraBeberAgua
     */
    public boolean isEnfrentarFissuraBeberAgua() {
        return enfrentarFissuraBeberAgua;
    }

    /**
     * @param enfrentarFissuraBeberAgua the enfrentarFissuraBeberAgua to set
     */
    public void setEnfrentarFissuraBeberAgua(boolean enfrentarFissuraBeberAgua) {
        this.enfrentarFissuraBeberAgua = enfrentarFissuraBeberAgua;
    }

    /**
     * @return the enfrentarFissuraComer
     */
    public boolean isEnfrentarFissuraComer() {
        return enfrentarFissuraComer;
    }

    /**
     * @param enfrentarFissuraComer the enfrentarFissuraComer to set
     */
    public void setEnfrentarFissuraComer(boolean enfrentarFissuraComer) {
        this.enfrentarFissuraComer = enfrentarFissuraComer;
    }

    /**
     * @return the enfrentarFissuraRelaxamento
     */
    public boolean isEnfrentarFissuraRelaxamento() {
        return enfrentarFissuraRelaxamento;
    }

    /**
     * @param enfrentarFissuraRelaxamento the enfrentarFissuraRelaxamento to set
     */
    public void setEnfrentarFissuraRelaxamento(boolean enfrentarFissuraRelaxamento) {
        this.enfrentarFissuraRelaxamento = enfrentarFissuraRelaxamento;
    }

    /**
     * @return the enfrentarFissuraLerRazoes
     */
    public boolean isEnfrentarFissuraLerRazoes() {
        return enfrentarFissuraLerRazoes;
    }

    /**
     * @param enfrentarFissuraLerRazoes the enfrentarFissuraLerRazoes to set
     */
    public void setEnfrentarFissuraLerRazoes(boolean enfrentarFissuraLerRazoes) {
        this.enfrentarFissuraLerRazoes = enfrentarFissuraLerRazoes;
    }

    public void limparVencendoFissura() {
        this.setEnfrentarFissuraBeberAgua(false);
        this.setEnfrentarFissuraComer(false);
        this.setEnfrentarFissuraLerRazoes(false);
        this.setEnfrentarFissuraRelaxamento(false);
    }

    /**
     * @return the dataParar
     */
    public Date getDataParar() {
        return dataParar;
    }

    /**
     * @param dataParar the dataParar to set
     */
    public void setDataParar(Date dataParar) {
        this.dataParar = dataParar;
    }

    /**
     * @return the usuario
     */
    public User getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the tentouParar
     */
    public boolean isTentouParar() {
        return tentouParar;
    }

    /**
     * @param tentouParar the tentouParar to set
     */
    public void setTentouParar(boolean tentouParar) {
        this.tentouParar = tentouParar;
    }

    /**
     * @return the evitarRecaida1
     */
    public String getEvitarRecaida1() {
        return evitarRecaida1;
    }

    /**
     * @param evitarRecaida1 the evitarRecaida1 to set
     */
    public void setEvitarRecaida1(String evitarRecaida1) {
        this.evitarRecaida1 = evitarRecaida1;
    }

    /**
     * @return the evitarRecaida2
     */
    public String getEvitarRecaida2() {
        return evitarRecaida2;
    }

    /**
     * @param evitarRecaida2 the evitarRecaida2 to set
     */
    public void setEvitarRecaida2(String evitarRecida2) {
        this.evitarRecaida2 = evitarRecida2;
    }

    /**
     * @return the evitarRecaida3
     */
    public String getEvitarRecaida3() {
        return evitarRecaida3;
    }

    /**
     * @param evitarRecaida3 the evitarRecaida3 to set
     */
    public void setEvitarRecaida3(String evitarRecida3) {
        this.evitarRecaida3 = evitarRecida3;
    }

    /**
     * @return the evitarRecaidaFara1
     */
    public String getEvitarRecaidaFara1() {
        return evitarRecaidaFara1;
    }

    /**
     * @param evitarRecaidaFara1 the evitarRecaidaFara1 to set
     */
    public void setEvitarRecaidaFara1(String evitarRecaidaFara1) {
        this.evitarRecaidaFara1 = evitarRecaidaFara1;
    }

    /**
     * @return the evitarRecaidaFara2
     */
    public String getEvitarRecaidaFara2() {
        return evitarRecaidaFara2;
    }

    /**
     * @param evitarRecaidaFara2 the evitarRecaidaFara2 to set
     */
    public void setEvitarRecaidaFara2(String evitarRecaidaFara2) {
        this.evitarRecaidaFara2 = evitarRecaidaFara2;
    }

    /**
     * @return the evitarRecaidaFara3
     */
    public String getEvitarRecaidaFara3() {
        return evitarRecaidaFara3;
    }

    /**
     * @param evitarRecaidaFara3 the evitarRecaidaFara3 to set
     */
    public void setEvitarRecaidaFara3(String evitarRecaidaFara3) {
        this.evitarRecaidaFara3 = evitarRecaidaFara3;
    }

    public String getDataPararStr() {
        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        gc.setTime(dataParar);
        return gc.get(GregorianCalendar.DAY_OF_MONTH) + "/" + String.format("%02d", gc.get(GregorianCalendar.MONTH) + 1) + "/" + gc.get(GregorianCalendar.YEAR);
    }

    public String getFissuraBeberAgua() {
        if (isEnfrentarFissuraBeberAgua()) {
            return (this.getText("pronto.fis.lab1"));
        } else {
            return null;
        }
    }

    public String getFissuraComer() {
        if (isEnfrentarFissuraComer()) {
            return (this.getText("pronto.fis.lab2")) + "\n";
        } else {
            return null;
        }

    }

    public String getFissuraLerRazoes() {
        if (isEnfrentarFissuraLerRazoes()) {
            return (this.getText("pronto.fis.lab4") + "\n");
        } else {
            return null;
        }
    }

    public String getFissuraRelaxamento() {
        if (isEnfrentarFissuraRelaxamento()) {
            return (this.getText("exercicio.relaxamento"));
        } else {
            return null;
        }
    }

    public String getLink() {
        if (isEnfrentarFissuraRelaxamento()) {
            return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/download/surfandoafissura.mp3";
        } else {
            return null;
        }
    }

    public String getTextoLink() {
        if (isEnfrentarFissuraRelaxamento()) {
            return (this.getText("exercicio.relaxamento1") + "\n");
        } else {
            return null;
        }
    }

    public String getFissuraStr() {
        StringBuilder s = new StringBuilder();
        if (isEnfrentarFissuraBeberAgua()) {
            s.append("Beber um copo de água pausadamente.").append("\n");
        }
        if (isEnfrentarFissuraComer()) {
            s.append("Comer alimentos com baixa quantidade de calorias como frutas cristalizadas (uva passas), balas dietéticas e chicletes dietéticos.").append("\n");
        }
        if (isEnfrentarFissuraLerRazoes()) {
            s.append("Fazer exercício de relaxamento - em áudio MP3 - vivasemtabaco.com.br/download/surfandoafissura.mp3").append("\n");
        }
        if (isEnfrentarFissuraRelaxamento()) {
            s.append("Ler um cartão com suas razões para ter parado de fumar.").append("\n");
        }
        return s.toString();
    }

    /**
     * @return the dataInserido
     */
    public Date getDataInserido() {
        return dataInserido;
    }

    /**
     * @param dataInserido the dataInserido to set
     */
    public void setDataInserido(Date dataInserido) {
        this.dataInserido = dataInserido;
    }

    public Date getEmailDataDiferente() {
        return emailDataDiferente;
    }

    public void setEmailDataDiferente(Date emailDataDiferente) {
        this.emailDataDiferente = emailDataDiferente;
    }

    public Date getEmailPrimeiraSemana() {
        return emailPrimeiraSemana;
    }

    public void setEmailPrimeiraSemana(Date emailPrimeiraSemana) {
        this.emailPrimeiraSemana = emailPrimeiraSemana;
    }

    public Date getEmailSegundaSemana() {
        return emailSegundaSemana;
    }

    public void setEmailSegundaSemana(Date emailSegundaSemana) {
        this.emailSegundaSemana = emailSegundaSemana;
    }

    public Date getEmailTerceiraSemana() {
        return emailTerceiraSemana;
    }

    public void setEmailTerceiraSemana(Date emailTerceiraSemana) {
        this.emailTerceiraSemana = emailTerceiraSemana;
    }

    public Date getEmailMensal() {
        return emailMensal;
    }

    public void setEmailMensal(Date emailMensal) {
        this.emailMensal = emailMensal;
    }

    public Integer getEmailMensalCont() {
        return emailMensalCont;
    }

    public void setEmailMensalCont(Integer emailMensalCont) {
        this.emailMensalCont = emailMensalCont;
    }

    public Integer getPhq1() {
        return phq1;
    }

    public void setPhq1(Integer phq1) {
        this.phq1 = phq1;
    }

    public Integer getPhq2() {
        return phq2;
    }

    public void setPhq2(Integer phq2) {
        this.phq2 = phq2;
    }

    public Integer getPhq3() {
        return phq3;
    }

    public void setPhq3(Integer phq3) {
        this.phq3 = phq3;
    }

    public Integer getPhq4() {
        return phq4;
    }

    public void setPhq4(Integer phq4) {
        this.phq4 = phq4;
    }

    public Integer getPhq5() {
        return phq5;
    }

    public void setPhq5(Integer phq5) {
        this.phq5 = phq5;
    }

    public Integer getPhq6() {
        return phq6;
    }

    public void setPhq6(Integer phq6) {
        this.phq6 = phq6;
    }

    public Integer getPhq7() {
        return phq7;
    }

    public void setPhq7(Integer phq7) {
        this.phq7 = phq7;
    }

    public Integer getPhq8() {
        return phq8;
    }

    public void setPhq8(Integer phq8) {
        this.phq8 = phq8;
    }

    public Integer getPhq9() {
        return phq9;
    }

    public void setPhq9(Integer phq9) {
        this.phq9 = phq9;
    }
    
    public Integer getFtnd1() {
        return ftnd1;
    }

    public void setFtnd1(Integer ftnd1) {
        this.ftnd1 = ftnd1;
    }

    public Integer getFtnd2() {
        return ftnd2;
    }

    public void setFtnd2(Integer ftnd2) {
        this.ftnd2 = ftnd2;
    }

    public Integer getFtnd3() {
        return ftnd3;
    }

    public void setFtnd3(Integer ftnd3) {
        this.ftnd3 = ftnd3;
    }

    public Integer getFtnd4() {
        return ftnd4;
    }

    public void setFtnd4(Integer ftnd4) {
        this.ftnd4 = ftnd4;
    }

    public Integer getFtnd5() {
        return ftnd5;
    }

    public void setFtnd5(Integer ftnd5) {
        this.ftnd5 = ftnd5;
    }

    public Integer getFtnd6() {
        return ftnd6;
    }

    public void setFtnd6(Integer ftnd6) {
        this.ftnd6 = ftnd6;
    }

    private void append(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public void limparProcPararFumar(){
        this.setPnadA(false);
        this.setPnadB(false);
        this.setPnadC(false);
        this.setPnadD(false);
        this.setPnadE(false);
        this.setPnadF(false);
        this.setPnadG(false);
        this.setPnadH(false);
    }

    /**
     * @return the mot1
     */
    public Integer getMot1() {
        if (this.mot1==null) {
	    this.mot1 = ProntoParaParar.MOTIVATION_STANDARD_VALUE; //standard value?
	}
	return mot1;
    }

    /**
     * @param mot1 the mot1 to set
     */
    public void setMot1(Integer mot1) {
        this.mot1 = mot1;
    }

    /**
     * @return the mot2
     */
    public Integer getMot2() {
	if (this.mot2==null) {
	    this.mot2 = ProntoParaParar.MOTIVATION_STANDARD_VALUE; //standard value?
	}
        return mot2;
    }

    /**
     * @param mot2 the mot2 to set
     */
    public void setMot2(Integer mot2) {
        this.mot2 = mot2;
    }

    /**
     * @return the pnadA
     */
    public Boolean isPnadA() {
        return getPnadA();
    }

    /**
     * @param pnadA the pnadA to set
     */
    public void setPnadA(Boolean pnadA) {
        this.pnadA = pnadA;
    }

    /**
     * @return the pnadB
     */
    public Boolean isPnadB() {
        return getPnadB();
    }

    /**
     * @param pnadB the pnadB to set
     */
    public void setPnadB(Boolean pnadB) {
        this.pnadB = pnadB;
    }

    /**
     * @return the pnadC
     */
    public Boolean isPnadC() {
        return getPnadC();
    }

    /**
     * @param pnadC the pnadC to set
     */
    public void setPnadC(Boolean pnadC) {
        this.pnadC = pnadC;
    }

    /**
     * @return the pnadD
     */
    public Boolean isPnadD() {
        return getPnadD();
    }

    /**
     * @param pnadD the pnadD to set
     */
    public void setPnadD(Boolean pnadD) {
        this.pnadD = pnadD;
    }

    /**
     * @return the pnadE
     */
    public Boolean isPnadE() {
        return getPnadE();
    }

    /**
     * @param pnadE the pnadE to set
     */
    public void setPnadE(Boolean pnadE) {
        this.pnadE = pnadE;
    }

    /**
     * @return the pnadF
     */
    public Boolean isPnadF() {
        return getPnadF();
    }

    /**
     * @param pnadF the pnadF to set
     */
    public void setPnadF(Boolean pnadF) {
        this.pnadF = pnadF;
    }

    /**
     * @return the pnadG
     */
    public Boolean isPnadG() {
        return getPnadG();
    }

    /**
     * @param pnadG the pnadG to set
     */
    public void setPnadG(Boolean pnadG) {
        this.pnadG = pnadG;
    }

    /**
     * @return the pnadH
     */
    public Boolean isPnadH() {
        return getPnadH();
    }

    /**
     * @param pnadH the pnadH to set
     */
    public void setPnadH(Boolean pnadH) {
        this.pnadH = pnadH;
    }

    /**
     * @return the pnadA
     */
    public Boolean getPnadA() {
	return pnadA;
    }

    /**
     * @return the pnadB
     */
    public Boolean getPnadB() {
	return pnadB;
    }

    /**
     * @return the pnadC
     */
    public Boolean getPnadC() {
	return pnadC;
    }

    /**
     * @return the pnadD
     */
    public Boolean getPnadD() {
	return pnadD;
    }

    /**
     * @return the pnadE
     */
    public Boolean getPnadE() {
	return pnadE;
    }

    /**
     * @return the pnadF
     */
    public Boolean getPnadF() {
	return pnadF;
    }

    /**
     * @return the pnadG
     */
    public Boolean getPnadG() {
	return pnadG;
    }

    /**
     * @return the pnadH
     */
    public Boolean getPnadH() {
	return pnadH;
    }

}
