/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.npptmk.uray_pressure_reg.managers;

import ru.npptmk.uray_pressure_reg.model.Shift;



/**
 * Слушатель менеджера смен который будет уведомляться об изменении состояния
 * смены контролируемой менеджером.
 *
 * @author RazumnovAA
 */
public interface ShiftManagerListener {

    /**
     * Метод вызываемый у слушателя изменений состояния смены.
     *
     * @param shift смена состояние которой было изменено.
     */
    public void doOnShiftChaned(Shift shift);

}
