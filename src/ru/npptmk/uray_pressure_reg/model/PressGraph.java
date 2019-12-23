package ru.npptmk.uray_pressure_reg.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Для хранения графика опрессовки, id графика сохраняется в списке внутри
 * сущности трубы.
 *
 * @author RazumnovAA
 */
@Entity
public class PressGraph implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ElementCollection
    @Column(name = "X_GRAPH")
    private List<Float> xGraph;
    @Column(name="TEST_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date testDate;

    public List<Float> getxGraph() {
        return xGraph;
    }

    public void setxGraph(List<Float> xGraph) {
        this.xGraph = xGraph;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public List<Float> getyGraph() {
        return yGraph;
    }

    public void setyGraph(List<Float> yGraph) {
        this.yGraph = yGraph;
    }
    @ElementCollection
    @Column(name = "Y_GRAPH")
    private List<Float> yGraph;

    public void addPoint(Float x, Float y) {
        xGraph.add(x);
        yGraph.add(y);
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
        if (!(object instanceof PressGraph)) {
            return false;
        }
        PressGraph other = (PressGraph) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.npptmk.uray_pressure_reg.model.PressGraph[ id=" + id + " ]";
    }

}
