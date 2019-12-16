package ru.npptmk.uray_pressure_reg.drivers;

import static ru.npptmk.uray_pressure_reg.drivers.TIAPortalAddress.RegTypes.INPUTS;

/**
 * Список адресов дискретных входов для получения данных от контроллера.
 *
 * @author RazumnovAA
 */
public class INPUTS_LIST {

    /**
     * Оптический датчик на рольганге за опрессовкой в состоянии ture.
     */
    public static final TIAPortalAddress TUBE_AFTER_PRESSURE_TEST = new TIAPortalAddress(0, 3, INPUTS);
    /**
     * Оптический датчик на рольганге перед стеллажом в состоянии true.
     */
    public static final TIAPortalAddress TUBE_BEFORE_STAND = new TIAPortalAddress(0, 2, INPUTS);
    /**
     * Датчик на рольганге за стеллажом в состоянии ture.
     */
    public static final TIAPortalAddress TUBE_AFTER_STAND = new TIAPortalAddress(0, 5, INPUTS);
}
