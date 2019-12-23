package ru.npptmk.uray_pressure_reg.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author RazumnovAA
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "getAllPipes", query = "SELECT a FROM Pipe a")
    ,
    @NamedQuery(name = "getPipesByDatePeriod", query = "SELECT a FROM Pipe a WHERE a.testDate BETWEEN :start AND :end")
    ,
    @NamedQuery(name = "getPipesByShiftId", query = "SELECT a FROM Pipe a WHERE a.shiftId = :siftId")
        ,
    @NamedQuery(name = "getPipesByLocationID", query = "SELECT a FROM Pipe a WHERE a.LocationID = :locationID")
})
public class Pipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ElementCollection
    @Column(name="TEST_GRAPHS_ID")
    Long graphsId;    
    @Column(name = "TARGET_PRESSURE")
    private Float targetPressure;
    @Column(name = "SHIFT_ID")
    private Long shiftId;
    private int LocationID;

    public int getLocationID() {
        return LocationID;
    }

    public void setLocationID(int LocationID) {
        this.LocationID = LocationID;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TEST_DATE")
    private Date testDate;

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Float getTargetPressure() {
        return targetPressure;
    }

    public void setTargetPressure(Float targetPressure) {
        this.targetPressure = targetPressure;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pipe)) {
            return false;
        }
        Pipe other = (Pipe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.npptmk.uray_pressure_reg.model.Pipe[ id=" + id + " ]";
    }

}
