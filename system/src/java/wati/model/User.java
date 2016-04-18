/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.model;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author hedersb
 */
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "email", length = 50, unique = true)
    private String email;
    @Column(name = "password", length = 16)
    private byte[] password;
    @Column(name = "birth")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birth;
    @Column(name = "gender")
    private char gender;
    @Column(name = "receive_emails")
    private boolean receiveEmails;
    @Column(name = "authorize_data")
    private boolean authorizeData;
    @Column(name = "prefered_language")
    private String preferedLanguage;
    @Column(name = "recover_code")
    private Integer recoverCode;
    @Column(name = "experimental_groups")
    private Integer experimentalGroups;
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "dt_cadastro")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dt_cadastro;
    
    @OneToOne(mappedBy = "usuario")
    private ProntoParaParar prontoParaParar;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Acompanhamento> acompanhamentos;
    
    @OneToOne(mappedBy = "user")
    private Questionnaire questionnaire;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Contact> contacts;
    
    @OneToOne(mappedBy = "user")
    private PesquisaSatisfacao pesquisaSatisfacao;
    
    @Column(name = "pesquisa_enviada")
    private Boolean pesquisa_enviada;


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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public byte[] getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(byte[] password) {
        this.password = password;
    }

    public boolean isReceiveEmails() {
        return receiveEmails;
    }

    public void setReceiveEmails(boolean receiveEmails) {
        this.receiveEmails = receiveEmails;
    }

    public boolean isAuthorizeData() {
        return authorizeData;
    }

    public void setAuthorizeData(boolean authorizeData) {
        this.authorizeData = authorizeData;
    }

    public Integer getRecoverCode() {
        return recoverCode;
    }

    public void setRecoverCode(Integer recoverCode) {
        this.recoverCode = recoverCode;
    }

    @Override
    public String toString() {
        return this.id + ", " + this.name + ", " + this.email + ", " + new String(this.password);
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the birth
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * @param birth the birth to set
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * @return the gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

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

    /**
     * @return the acompanhamentos
     */
    public List<Acompanhamento> getAcompanhamentos() {
        return acompanhamentos;
    }

    /**
     * @param acompanhamentos the acompanhamentos to set
     */
    public void setAcompanhamentos(List<Acompanhamento> acompanhamentos) {
        this.acompanhamentos = acompanhamentos;
    }

    public String getPreferedLanguage() {
        return preferedLanguage;
    }

    public void setPreferedLanguage(String preferedLanguage) {
        this.preferedLanguage = preferedLanguage;
    }

    public Integer getExperimentalGroups() {
        return experimentalGroups;
    }

    public void setExperimentalGroups(Integer experimentalGroups) {
        this.experimentalGroups = experimentalGroups;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
    
    public Date getDtCadastro() {
        return dt_cadastro;
    }

    /**
     */
    public void setDtCadastro(Date dt) {
        this.dt_cadastro = dt;
    }
    
    
    public PesquisaSatisfacao getPesquisaSatisfacao() {
        return pesquisaSatisfacao;
    }

    public void setPesquisaSatisfacao(PesquisaSatisfacao pesquisaSatisfacao) {
        this.pesquisaSatisfacao = pesquisaSatisfacao;
    }
    
    /**
     *
     * @return pesquisa_enviada
     */
    public boolean getPesquisaEnviada() {
        if(pesquisa_enviada == null){
            return false;
        }
        return pesquisa_enviada;
    }

    public void setPesquisaEnviada(Boolean num) {
        this.pesquisa_enviada = num;
    }

    

}

