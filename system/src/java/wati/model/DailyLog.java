package wati.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "tb_daily_log")
@XmlRootElement
public class DailyLog implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "logDate")
    //@Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate logDate;
    //private Date logDate;
    @Column(name = "cigars")
    private Integer cigars;
    
    @ManyToOne
    private Record record;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public Integer getCigars() {
        return cigars;
    }

    public void setCigars(Integer cigars) {
        this.cigars = cigars;
    }
      
    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

}
