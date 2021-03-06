/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.npptmk.uray_pressure_reg.gui;

import java.awt.EventQueue;

/**
 *
 * @author RazumnovAA
 */
public class JDialog_SetupIndicator extends javax.swing.JDialog {

    public JDialog_SetupIndicator(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jProgressBar1.setStringPainted(true);
    }

    @Override
    public void setVisible(boolean b) {
        setLocationRelativeTo(null);
        super.setVisible(b);
    }

    /**
     * Устанавливает максимальное значение для индикатора.
     *
     * @param maxValue максимальное значение прогресса.
     */
    public void setMaxValueForProgress(int maxValue) {
        EventQueue.invokeLater(() -> {
            jProgressBar1.setMaximum(maxValue);
        });
    }

    /**
     * Увеличивает индикацию прогресса на 1.
     */
    public void incremetProgress() {
        EventQueue.invokeLater(() -> {
            jProgressBar1.setValue(jProgressBar1.getValue() + 1);
        });
    }

    /**
     * Сбрасывает индикацию прогресса в 0.
     */
    public void dropProgress() {
        EventQueue.invokeLater(() -> {
            jProgressBar1.setValue(0);
        });
    }

    /**
     * Записывает строку в лог и переходит на новую строку.
     *
     * @param aString строка для вывода в лог.
     */
    public void printlnLog(String aString) {
        EventQueue.invokeLater(() -> {
            jProgressBar1.setString(aString);
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ЗАПУСК СИСТЕМЫ УЧЕТА ТРУБ");

        jProgressBar1.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
