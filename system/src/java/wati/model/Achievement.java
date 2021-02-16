/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "tb_achievement")
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "cigars_not_smoken")
    private Integer cigarsNotSmoken;
    
    @Column(name = "life_time_saved")
    private Integer lifeTimeSaved;
    
    @Column(name = "money_saved")
    private Float moneySaved;
    
    @Column(name = "logDate")
    private LocalDate logDate;
    
    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCigarsNotSmoken() {
        return cigarsNotSmoken;
    }

    public void setCigarsNotSmoken(Integer cigarsNotSmoken) {
        this.cigarsNotSmoken = cigarsNotSmoken;
    }

    public Integer getLifeTimeSaved() {
        return lifeTimeSaved;
    }

    public void setLifeTimeSaved(Integer lifeTimeSaved) {
        this.lifeTimeSaved = lifeTimeSaved;
    }

    public Float getMoneySaved() {
        return moneySaved;
    }

    public void setMoneySaved(Float moneySaved) {
        this.moneySaved = moneySaved;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }
    
    
    
}
