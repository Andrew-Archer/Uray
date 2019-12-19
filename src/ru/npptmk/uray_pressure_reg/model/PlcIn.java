/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.npptmk.uray_pressure_reg.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author RazumnovAA
 */
@Entity
@Table(name="PLC_IN")
public class PlcIn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name = "Не указано";

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    private Integer bitNumber;

    /**
     * Get the value of bitNumber
     *
     * @return the value of bitNumber
     */
    public Integer getBitNumber() {
        return bitNumber;
    }

    /**
     * Set the value of bitNumber
     *
     * @param bitNumber new value of bitNumber
     */
    public void setBitNumber(Integer bitNumber) {
        this.bitNumber = bitNumber;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    private Integer byteNumber;

    /**
     * Get the value of byteNumber
     *
     * @return the value of byteNumber
     */
    public Integer getByteNumber() {
        return byteNumber;
    }

    /**
     * Set the value of byteNumber
     *
     * @param byteNumber new value of byteNumber
     */
    public void setByteNumber(Integer byteNumber) {
        this.byteNumber = byteNumber;
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
        if (!(object instanceof PlcIn)) {
            return false;
        }
        PlcIn other = (PlcIn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    public Integer getModbusAddress(){
        return this.byteNumber * 8  + this.bitNumber + 1;
    }

    @Override
    public String toString() {
        return "ru.npptmk.uray_pressure_reg.model.Input[ id=" + id + " ]";
    }

}
