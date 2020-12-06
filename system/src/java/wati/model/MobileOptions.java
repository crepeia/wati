/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "tb_mobile_options")
@XmlRootElement
public class MobileOptions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private User user;

    @Column(name = "allow_cigar_notifications")
    private boolean allowCigarNotifications;
    
    @Column(name = "allow_tip_notifications")
    private boolean allowTipNotifications;
    
    @Column(name = "allow_achievment_notifications")
    private boolean allowAchievmentNotifications;
    
    //@Temporal(javax.persistence.TemporalType.TIME)
    @Column(name = "cigar_notification_time")
    private OffsetTime cigarNotificationTime;
    
    //@Temporal(javax.persistence.TemporalType.TIME)
    @Column(name = "tip_notification_time")
    private OffsetTime tipNotificationTime;
    
    @Column(name = "achievment_notification_time")
    private OffsetTime achievmentNotificationTime;
    
    @Column(name = "notification_token")
    private String notificationToken;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public boolean isAllowTipNotifications() {
        return allowTipNotifications;
    }

    public void setAllowTipNotifications(boolean allowTipNotifications) {
        this.allowTipNotifications = allowTipNotifications;
    }

    public boolean isAllowAchievmentNotifications() {
        return allowAchievmentNotifications;
    }

    public void setAllowAchievmentNotifications(boolean allowAchievmentNotifications) {
        this.allowAchievmentNotifications = allowAchievmentNotifications;
    }
    


    public OffsetTime getTipNotificationTime() {
        return tipNotificationTime;
    }

    public void setTipNotificationTime(OffsetTime tipNotificationTime) {
        this.tipNotificationTime = tipNotificationTime;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public OffsetTime getAchievmentNotificationTime() {
        return achievmentNotificationTime;
    }

    public void setAchievmentNotificationTime(OffsetTime achievmentNotificationTime) {
        this.achievmentNotificationTime = achievmentNotificationTime;
    }

    public boolean isAllowCigarNotifications() {
        return allowCigarNotifications;
    }

    public void setAllowCigarNotifications(boolean allowCigarNotifications) {
        this.allowCigarNotifications = allowCigarNotifications;
    }

    public OffsetTime getCigarNotificationTime() {
        return cigarNotificationTime;
    }

    public void setCigarNotificationTime(OffsetTime cigarNotificationTime) {
        this.cigarNotificationTime = cigarNotificationTime;
    }
    
    
}
