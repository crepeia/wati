/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hedersb
 */
@Entity
@Table(name = "tb_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.login", query = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password"),
    @NamedQuery(name = "User.password", query = "SELECT u.password FROM User u WHERE u.email = :email AND u.password IS NOT NULL")

})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    @JsonManagedReference
    @OneToOne(mappedBy = "usuario")
    private ProntoParaParar prontoParaParar;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Acompanhamento> acompanhamentos;
    
    @JsonManagedReference
    @OneToOne(mappedBy = "user")
    private Questionnaire questionnaire;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Contact> contacts;
    
    @JsonManagedReference
    @OneToOne(mappedBy = "user")
    private PesquisaSatisfacao pesquisaSatisfacao;
    
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Record record;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Rating> ratings;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<FollowUp> followUps;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<TipUser> tips;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<ChallengeUser> challenges;
    
    @Column(name = "pesquisa_enviada")
    private Boolean pesquisa_enviada;

    @Column(name = "app_signup", nullable = false )
    private boolean app_signup;
    
    @Column(name = "in_ranking", nullable = false )
    private boolean inRanking;
    
    @Column(name = "nickname")
    private String nickname;
    
    

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    
    public String getHashedId(){
        return String.valueOf(id*1357);
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
    @XmlTransient
    @org.codehaus.jackson.annotate.JsonIgnore
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

    @XmlTransient
    @org.codehaus.jackson.annotate.JsonIgnore
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

    public Date getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Date dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

    @XmlTransient
    @org.codehaus.jackson.annotate.JsonIgnore
    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    @XmlTransient
    @org.codehaus.jackson.annotate.JsonIgnore
    public List<FollowUp> getFollowUps() {
        return followUps;
    }

    public void setFollowUps(List<FollowUp> followUps) {
        this.followUps = followUps;
    }

    public Boolean getPesquisa_enviada() {
        return pesquisa_enviada;
    }

    public void setPesquisa_enviada(Boolean pesquisa_enviada) {
        this.pesquisa_enviada = pesquisa_enviada;
    }

    @XmlTransient
    @org.codehaus.jackson.annotate.JsonIgnore
    public List<TipUser> getTips() {
        return tips;
    }

    public void setTips(List<TipUser> tips) {
        this.tips = tips;
    }

    @XmlTransient
    @org.codehaus.jackson.annotate.JsonIgnore
    public List<ChallengeUser> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<ChallengeUser> challenges) {
        this.challenges = challenges;
    }

    public boolean isApp_signup() {
        return app_signup;
    }

    public void setApp_signup(boolean app_signup) {
        this.app_signup = app_signup;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public boolean isInRanking() {
        return inRanking;
    }

    public void setInRanking(boolean inRanking) {
        this.inRanking = inRanking;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    
    
}

