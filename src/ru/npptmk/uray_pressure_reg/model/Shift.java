/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.npptmk.uray_pressure_reg.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Класс представляет собой модель рабочей смены на предприятии.
 *
 * @author RazumnovAA
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "getAllShifts", query = "SELECT a FROM Shift a")
    ,
    @NamedQuery(name = "getAllShiftsByState", query = "SELECT a FROM Shift a WHERE a.state = :state")
    ,
    @NamedQuery(name = "getShiftsByDatePeriod", query = "SELECT a FROM Shift a WHERE a.beginning BETWEEN :start AND :end")
})
public class Shift implements Serializable {

    private static final long serialVersionUID = 1584879322587654281L;
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginning;
    @Temporal(TemporalType.TIMESTAMP)
    private Date finish;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ElementCollection
    private final Map<String, Operator> personal;
    @Enumerated(EnumType.STRING)
    @Column(name = "SHIFT_STATE")
    private ShiftState state;

    public Shift() {
        personal = new HashMap<>();
        state = ShiftState.NOT_STARTED;
    }

    public ShiftState getState() {
        return state;
    }

    public void setState(ShiftState state) {
        this.state = state;
    }

    public void addPersonal(String role, Operator operator) {
        personal.put(role, operator);
    }

    public void addAllPersonal(Map<String, Operator> personal) {
        if (personal != null) {
            personal.putAll(personal);
        }
    }

    public void removePersonal(String role, Operator operator) {
        personal.put(role, operator);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shift)) {
            return false;
        }
        Shift other = (Shift) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * @return the beginning
     */
    public Date getBeginning() {
        return beginning;
    }

    /**
     * @param beginning the beginning to set
     */
    public void setBeginning(Date beginning) {
        this.beginning = beginning;
    }

    /**
     * @return the finish
     */
    public Date getFinish() {
        return finish;
    }

    /**
     * @param finish the finish to set
     */
    public void setFinish(Date finish) {
        this.finish = finish;
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
    public String toString() {
        return "ru.npptmk.bazaTest.defect.model.Shift[ id=" + id + " ]";
    }

}
