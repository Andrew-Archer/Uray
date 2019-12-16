/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.npptmk.uray_pressure_reg.drivers;

import static ru.npptmk.uray_pressure_reg.drivers.TIAPortalAddress.RegTypes.OUTPUTS;


/**
 *
 * @author RazumnovAA
 */
public class OUTPUTS_LIST {

    /**
     * Лампа включения ПК горит.
     */
    public static final TIAPortalAddress LAMP_PC_ON = new TIAPortalAddress(0, 1, OUTPUTS);
    /**
     * Лампа ошибка горит.
     */
    public static final TIAPortalAddress LAMP_ERROR_ON = new TIAPortalAddress(0, 2, OUTPUTS);
}
