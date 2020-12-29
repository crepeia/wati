package wati.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "tb_record")
@XmlRootElement
public class Record implements Serializable{
       
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "cigars_Daily")
    private int cigarsDaily;
    
    @Column(name = "pack_price")
    private float packPrice;
    
    @Column(name = "pack_amount")
    private int packAmount;
    
    @Column(name = "filled", nullable = false )
    private boolean filled;
    
    @JsonBackReference
    @OneToOne
    private User user;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<DailyLog> dailyLogs;
    
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
    
    @JsonIgnore
    @XmlTransient
    public List<DailyLog> getDailyLogs() {
        return dailyLogs;
    }

    public void setDailyLogs(List<DailyLog> dailyLogs) {
        this.dailyLogs = dailyLogs;
    }

    public int getCigarsDaily() {
        return cigarsDaily;
    }

    public void setCigarsDaily (int cigarsDaily) {
        this.cigarsDaily = cigarsDaily;
    }

    public float getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(float packPrice) {
        this.packPrice = packPrice;
    }

    public int getPackAmount() {
        return packAmount;
    }

    public void setPackAmount(int packAmount) {
        this.packAmount = packAmount;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }
    
    
    
}
