package ru.npptmk.uray_pressure_reg.drivers;

/**
 * Класс для хранения адресов TIAPortala.
 *
 * @author RazumnovAA
 */
public class TIAPortalAddress {

    private Integer bitNumber;
    private Integer byteNumber;
    private RegTypes type;

    public TIAPortalAddress(
            Integer byteNumber,
            Integer bitNumber,
            RegTypes type) {
        this.byteNumber = byteNumber;
        this.bitNumber = bitNumber;
        this.type = type;
    }

    public Integer getBitNumber() {
        return bitNumber;
    }

    public int getModbusAddress() {
        switch (getType()) {
            case INPUTS:
            case OUTPUTS:
                return getByteNumber() * 8 + getBitNumber();
            case SHORT:
                return getByteNumber() * 5;
        }
        return 0;
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

    public RegTypes getType() {
        return type;
    }

    public void setType(RegTypes type) {
        this.type = type;
    }

    public static enum RegTypes {
        /**
         * Дискретный вход.
         */
        INPUTS,
        /**
         * Дискретный выход.
         */
        OUTPUTS,
        /**
         * Short регистр.
         */
        SHORT
    }

    @Override
    public String toString() {
        String prefix = "Не указано";
        switch (this.getType()) {
            case INPUTS:
                prefix = "%I";
                break;
            case OUTPUTS:
                prefix = "%Q";
                break;
            case SHORT:
                prefix = "";
        }
        StringBuilder toReturn = new StringBuilder();
        toReturn.append(prefix);
        toReturn.append(getByteNumber());
        toReturn.append(".");
        toReturn.append(getBitNumber());
        toReturn.append(" ");
        return toReturn.toString();
    }
}
