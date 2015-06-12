/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author thiago
 */
@Entity
@Table(name = "tb_contact")
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "sender")
    private String sender;
    @Column(name = "message")
    private String message;
    @Column(name = "time")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateSent;

    public long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
    
      
}
