/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.npptmk.uray_pressure_reg.model;

/**
 *
 * @author RazumnovAA
 */
public enum ShiftState {
    /**
     * Смена не начата getBeginning() == null.
     */
    NOT_STARTED,
    /**
     * Смена идет getBeginning() != null и getFinish() == null.
     */
    RUNNING,
    /**
     * Смена закончена getFininsh() == null.
     */
    FINISHED,
    /**
     * Неопределенное состояние смены.
     */
    IN_A_WRONG_STATE
}
