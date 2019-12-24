package ru.npptmk.uray_pressure_reg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "getPipesByLocation", query = "SELECT a FROM Pipe a WHERE a.location = :location")
})
public class Pipe implements Serializable {

    public static enum Locations {
        PRESS_TEST_1,
        PRESS_TEST_2,
        OUT_1,
        OU2_2
    }

    public Pipe() {
        this.graphs = new ArrayList<>();
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private final List<Graph> graphs;

    /**
     * Возвращает последний график или null если еще ни одного графика нет.
     *
     * @return последний добавленный в список график.
     */
    public Graph getLastGraph() {
        if (graphs.isEmpty()) {
            return null;
        }
        return graphs.get(graphs.size() - 1);
    }

    public List<Graph> getGraphs() {
        return graphs;
    }

    @Column(name = "TARGET_PRESSURE")
    private Float targetPressure;
    @Column(name = "SHIFT_ID")
    private Long shiftId;
    @Enumerated(EnumType.STRING)
    private Locations location;

    public Locations getLocationID() {
        return location;
    }

    public void setLocationID(Locations location) {
        this.location = location;
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
