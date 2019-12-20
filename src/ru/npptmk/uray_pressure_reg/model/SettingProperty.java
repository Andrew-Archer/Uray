package ru.npptmk.uray_pressure_reg.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author RazumnovAA
 */
@Entity
@Table(name = "SETTING_PROPERTY")
@NamedQueries({
    @NamedQuery(name = "getAllProperties", query = "SELECT a FROM SettingProperty a")
    ,
    @NamedQuery(name = "getPropertyByName", query = "SELECT a FROM SettingProperty a WHERE a.name = :name")
})
public class SettingProperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "VAL_DEFAULT")
    private String valDefault;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String val;

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SettingProperty)) {
            return false;
        }
        SettingProperty other = (SettingProperty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValDefault() {
        return valDefault;
    }

    public void setValDefault(String valDefault) {
        this.valDefault = valDefault;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "ru.npptmk.uray_pressure_reg.model.ProgrammSetting[ id=" + id + " ]";
    }

}
