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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import wati.model.User;

/**
 *
 * @author thiago
 */
@Entity
@Table(name = "tb_follow_up")
public class FollowUp implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;   
    @Column(name = "smoked_last_7_days")
    private Boolean smokedLast7days;
    @Column(name = "average_per_day_past_7_days")
    private Integer averagePerDayPast7days;
    @Column(name = "times_tried_quit_24_hours")
    private Integer timesTriedQuit24Hours;
    @Column(name = "used_other_products")
    private Boolean usedOtherProducts;
    @Column(name = "longest_time_without_smoking")
    private Integer longestTimeWithoutSmoking;
    @Column(name = "average_per_day")
    private Integer averagePerDay;
    @Column(name = "used_try_quit_1")
    private Boolean usedTryQuit1;
    @Column(name = "used_try_quit_2")
    private Boolean usedTryQuit2;
    @Column(name = "used_try_quit_3")
    private Boolean usedTryQuit3;
    @Column(name = "used_try_quit_4")
    private Boolean usedTryQuit4;
    @Column(name = "used_try_quit_5")
    private Boolean usedTryQuit5;
    @Column(name = "used_try_quit_6")
    private Boolean usedTryQuit6;
    @Column(name = "used_try_quit_7")
    private Boolean usedTryQuit7;
    @Column(name = "used_try_quit_8")
    private Boolean usedTryQuit8;
    @Column(name = "smoked_last_30_days")
    private Boolean smokedLast30days;
    @Column(name = "days_smoking_past_30_days")
    private Integer daysSmokingPast30days;
    @Column(name = "count")
    private Integer count;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_answered")
    private Date dateAnswered;
    
    @ManyToOne
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getSmokedLast7days() {
        return smokedLast7days;
    }

    public void setSmokedLast7days(Boolean smokedLast7days) {
        this.smokedLast7days = smokedLast7days;
    }

    public Integer getAveragePerDayPast7days() {
        return averagePerDayPast7days;
    }

    public void setAveragePerDayPast7days(Integer averagePerDayPast7days) {
        this.averagePerDayPast7days = averagePerDayPast7days;
    }

    public Integer getTimesTriedQuit24Hours() {
        return timesTriedQuit24Hours;
    }

    public void setTimesTriedQuit24Hours(Integer timesTriedQuit24Hours) {
        this.timesTriedQuit24Hours = timesTriedQuit24Hours;
    }

    public Boolean getUsedOtherProducts() {
        return usedOtherProducts;
    }

    public void setUsedOtherProducts(Boolean usdedOtherProducts) {
        this.usedOtherProducts = usdedOtherProducts;
    }

    public Integer getLongestTimeWithoutSmoking() {
        return longestTimeWithoutSmoking;
    }

    public void setLongestTimeWithoutSmoking(Integer longestTimeWithoutSmoking) {
        this.longestTimeWithoutSmoking = longestTimeWithoutSmoking;
    }

    public Integer getAveragePerDay() {
        return averagePerDay;
    }

    public void setAveragePerDay(Integer averagePerDay) {
        this.averagePerDay = averagePerDay;
    }

    public Boolean getUsedTryQuit1() {
        return usedTryQuit1;
    }

    public void setUsedTryQuit1(Boolean usedTryQuit1) {
        this.usedTryQuit1 = usedTryQuit1;
    }

    public Boolean getUsedTryQuit2() {
        return usedTryQuit2;
    }

    public void setUsedTryQuit2(Boolean usedTryQuit2) {
        this.usedTryQuit2 = usedTryQuit2;
    }

    public Boolean getUsedTryQuit3() {
        return usedTryQuit3;
    }

    public void setUsedTryQuit3(Boolean usedTryQuit3) {
        this.usedTryQuit3 = usedTryQuit3;
    }

    public Boolean getUsedTryQuit4() {
        return usedTryQuit4;
    }

    public void setUsedTryQuit4(Boolean usedTryQuit4) {
        this.usedTryQuit4 = usedTryQuit4;
    }

    public Boolean getUsedTryQuit5() {
        return usedTryQuit5;
    }

    public void setUsedTryQuit5(Boolean usedTryQuit5) {
        this.usedTryQuit5 = usedTryQuit5;
    }

    public Boolean getUsedTryQuit6() {
        return usedTryQuit6;
    }

    public void setUsedTryQuit6(Boolean usedTryQuit6) {
        this.usedTryQuit6 = usedTryQuit6;
    }

    public Boolean getUsedTryQuit7() {
        return usedTryQuit7;
    }

    public void setUsedTryQuit7(Boolean usedTryQuit7) {
        this.usedTryQuit7 = usedTryQuit7;
    }

    public Boolean getUsedTryQuit8() {
        return usedTryQuit8;
    }

    public void setUsedTryQuit8(Boolean usedTryQuit8) {
        this.usedTryQuit8 = usedTryQuit8;
    }

    public Boolean getSmokedLast30days() {
        return smokedLast30days;
    }

    public void setSmokedLast30days(Boolean smokedLast30days) {
        this.smokedLast30days = smokedLast30days;
    }

    public Integer getDaysSmokingPast30days() {
        return daysSmokingPast30days;
    }

    public void setDaysSmokingPast30days(Integer daysSmokingPast30days) {
        this.daysSmokingPast30days = daysSmokingPast30days;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateAnswered() {
        return dateAnswered;
    }

    public void setDateAnswered(Date dateAnswered) {
        this.dateAnswered = dateAnswered;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
      
    
    
}
