/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author thiago
 */
@Entity
@Table(name = "tb_questionnaire")
public class Questionnaire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    @Column(name = "know_website")
    private String knowWebsite;
    @Column(name = "country")
    private String country;
    @Column(name = "education")
    private Integer education;
    @Column(name = "employed")
    private Boolean employed;
    @Column(name = "tried_quit")
    private Integer triedQuit;
    @Column(name = "procedure_1")
    private Boolean procedure1;
    @Column(name = "procedure_2")
    private Boolean procedure2;
    @Column(name = "procedure_3")
    private Boolean procedure3;
    @Column(name = "procedure_4")
    private Boolean procedure4;
    @Column(name = "procedure_5")
    private Boolean procedure5;
    @Column(name = "procedure_6")
    private Boolean procedure6;
    @Column(name = "procedure_7")
    private Boolean procedure7;
    @Column(name = "procedure_8")
    private Boolean procedure8;
    @Column(name = "dependency_level_1")
    private Integer dependencyLevel1;
    @Column(name = "dependency_level_2")
    private Boolean dependencyLevel2;
    @Column(name = "dependency_level_3")
    private Integer dependencyLevel3;
    @Column(name = "dependency_level_4")
    private Integer dependencyLevel4;
    @Column(name = "dependency_level_5")
    private Boolean dependencyLevel5;
    @Column(name = "dependency_level_6")
    private Boolean dependencyLevel6;
    @Column(name = "problems_1")
    private Integer problems1;
    @Column(name = "problems2")
    private Integer problems2;
    @Column(name = "alcohol_use_1")
    private Integer alcolholUse1;
    @Column(name ="alcohol_use_2")
    private Integer alcoholUse2;
    @Column(name ="alcohol_use_3")
    private Integer alcoholUse3;
    
    

    @OneToOne
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getKnowWebsite() {
        return knowWebsite;
    }

    public void setKnowWebsite(String knowWebsite) {
        this.knowWebsite = knowWebsite;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Boolean getEmployed() {
        return employed;
    }

    public void setEmployed(Boolean employed) {
        this.employed = employed;
    }

    public Integer getTriedQuit() {
        return triedQuit;
    }

    public void setTriedQuit(Integer triedQuit) {
        this.triedQuit = triedQuit;
    }

    public Boolean getProcedure1() {
        return procedure1;
    }

    public void setProcedure1(Boolean procedure1) {
        this.procedure1 = procedure1;
    }

    public Boolean getProcedure2() {
        return procedure2;
    }

    public void setProcedure2(Boolean procedure2) {
        this.procedure2 = procedure2;
    }

    public Boolean getProcedure3() {
        return procedure3;
    }

    public void setProcedure3(Boolean procedure3) {
        this.procedure3 = procedure3;
    }

    public Boolean getProcedure4() {

        return procedure4;
    }

    public void setProcedure4(Boolean procedure4) {
        this.procedure4 = procedure4;
    }

    public Boolean getProcedure5() {
        return procedure5;
    }

    public void setProcedure5(Boolean procedure5) {
        this.procedure5 = procedure5;
    }

    public Boolean getProcedure6() {
        return procedure6;
    }

    public void setProcedure6(Boolean procedure6) {
        this.procedure6 = procedure6;
    }

    public Boolean getProcedure7() {
        return procedure7;
    }

    public void setProcedure7(Boolean procedure7) {
        this.procedure7 = procedure7;
    }

    public Boolean getProcedure8() {
        return procedure8;
    }

    public void setProcedure8(Boolean procedure8) {
        this.procedure8 = procedure8;
    }

    public Integer getDependencyLevel1() {
        return dependencyLevel1;
    }

    public void setDependencyLevel1(Integer dependencyLevel1) {
        this.dependencyLevel1 = dependencyLevel1;
    }

    public Boolean getDependencyLevel2() {
        return dependencyLevel2;
    }

    public void setDependencyLevel2(Boolean dependencyLevel2) {
        this.dependencyLevel2 = dependencyLevel2;
    }

    public Integer getDependencyLevel3() {
        return dependencyLevel3;
    }

    public void setDependencyLevel3(Integer dependencyLevel3) {
        this.dependencyLevel3 = dependencyLevel3;
    }

    public Integer getDependencyLevel4() {
        return dependencyLevel4;
    }

    public void setDependencyLevel4(Integer dependencyLevel4) {
        this.dependencyLevel4 = dependencyLevel4;
    }

    public Boolean getDependencyLevel5() {
        return dependencyLevel5;
    }

    public void setDependencyLevel5(Boolean dependencyLevel5) {
        this.dependencyLevel5 = dependencyLevel5;
    }

    public Boolean getDependencyLevel6() {
        return dependencyLevel6;
    }

    public void setDependencyLevel6(Boolean dependencyLevel6) {
        this.dependencyLevel6 = dependencyLevel6;
    }

    public Integer getProblems1() {
        return problems1;
    }

    public void setProblems1(Integer problems1) {
        this.problems1 = problems1;
    }

    public Integer getProblems2() {
        return problems2;
    }

    public void setProblems2(Integer problems2) {
        this.problems2 = problems2;
    }

    public Integer getAlcolholUse1() {
        return alcolholUse1;
    }

    public void setAlcolholUse1(Integer alcolholUse1) {
        this.alcolholUse1 = alcolholUse1;
    }

    public Integer getAlcoholUse2() {
        return alcoholUse2;
    }

    public void setAlcoholUse2(Integer alcoholUse2) {
        this.alcoholUse2 = alcoholUse2;
    }

    public Integer getAlcoholUse3() {
        return alcoholUse3;
    }

    public void setAlcoholUse3(Integer alcoholUse3) {
        this.alcoholUse3 = alcoholUse3;
    }
    
    

    
}
