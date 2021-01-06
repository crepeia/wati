/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wati.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hedersb
 */
@Entity
@Table(name = "tb_user_agent")
@XmlRootElement
public class UserAgent implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<PageNavigation> pageNavigation;
    

    public UserAgent() {
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the pageNavigation
     */
    @XmlTransient
    @JsonIgnore
    public List<PageNavigation> getPageNavigation() {
        return pageNavigation;
    }

    /**
     * @param pageNavigation the pageNavigation to set
     */
    public void setPageNavigation(List<PageNavigation> pageNavigation) {
        this.pageNavigation = pageNavigation;
    }
    
    
}
