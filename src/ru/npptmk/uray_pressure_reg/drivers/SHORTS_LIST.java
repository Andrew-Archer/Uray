package ru.npptmk.uray_pressure_reg.drivers;

import static ru.npptmk.uray_pressure_reg.drivers.TIAPortalAddress.RegTypes.SHORT;



/**
 * Содержит статические адреса Short регистров.
 *
 * @author RazumnovAA
 */
public class SHORTS_LIST {

    public final static TIAPortalAddress MOVE_FROM_STAND_STATE = new TIAPortalAddress(4, 0, SHORT);
    public final static TIAPortalAddress MOVE_TO_STAND_STATE = new TIAPortalAddress(2, 0, SHORT);
    public final static TIAPortalAddress ERROR = new TIAPortalAddress(0, 0, SHORT);
    public final static TIAPortalAddress CMD = new TIAPortalAddress(6, 0, SHORT);
}
