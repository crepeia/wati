/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author hedersb
 */
@Entity
@Table(name="tb_pronto_para_parar")
public class ProntoParaParar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="enfrentar_fissura_beber_agua")
    private boolean enfrentarFissuraBeberAgua;
    
    @Column(name="enfrentar_fissura_comer")
    private boolean enfrentarFissuraComer;
    
    @Column(name="enfrentar_fissura_relaxamento")
    private boolean enfrentarFissuraRelaxamento;
    
    @Column(name="enfrentar_fissura_ler_razoes")
    private boolean enfrentarFissuraLerRazoes;
    
//    @Column(name="usuario")
//    @OneToOne
//    private User usuario;

    public ProntoParaParar() {
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
    
}
