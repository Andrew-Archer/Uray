package ru.npptmk.uray_pressure_reg.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author RazumnovAA
 */
@Entity
@Table(name = "MB_DATA_PIECE")
public class MBDataPiece implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer bitNumber;
    private Integer byteNumber;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MBDataPieceType type;
    private Integer val;

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MBDataPiece)) {
            return false;
        }
        MBDataPiece other = (MBDataPiece) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Integer getBitNumber() {
        return bitNumber;
    }

    public void setBitNumber(Integer bitNumber) {
        this.bitNumber = bitNumber;
    }

    public Integer getByteNumber() {
        return byteNumber;
    }

    public void setByteNumber(Integer byteNumber) {
        this.byteNumber = byteNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getModbusAddress() {
        if (type.equals(MBDataPieceType.SHORT)) {
            return byteNumber / 2;
        } else {
            return byteNumber * 8 + bitNumber + 1;
        }
    }
    public MBDataPieceType getType() {
        return type;
    }
    public void setType(MBDataPieceType type) {
        this.type = type;
    }
    public Integer getVal() {
        return val;
    }
    public void setVal(Integer val) {
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
        return "ru.npptmk.uray_pressure_reg.model.Output[ id=" + id + " ]";
    }

}
