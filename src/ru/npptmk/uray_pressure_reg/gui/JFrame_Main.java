package ru.npptmk.uray_pressure_reg.gui;

import java.awt.EventQueue;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import ru.npptmk.drivers.manometers.Manometer;
import ru.npptmk.uray_pressure_reg.drivers.DAOPipe;
import ru.npptmk.uray_pressure_reg.drivers.DAOSettingProperty;
import ru.npptmk.uray_pressure_reg.drivers.DBExecutor;
import ru.npptmk.uray_pressure_reg.managers.ShiftManager;
import ru.npptmk.uray_pressure_reg.managers.ShiftManagerListener;
import ru.npptmk.uray_pressure_reg.model.Pipe;
import ru.npptmk.uray_pressure_reg.model.Shift;
import ru.npptmk.uray_pressure_reg.model.ShiftState;

/**
 *
 * @author RazumnovAA
 */
public class JFrame_Main extends javax.swing.JFrame
        implements
        ShiftManagerListener {

    private final DAOPipe dAOPipe;

    private final DAOSettingProperty dAOSettingProperty;

    private final DBExecutor dbExecutor;
    private final Manometer man1;
    private final Manometer man2;
    private final ShiftManager shiftManager;
    private int pressureTest1State;
    private int pressureTest2State;
    private float pressureToDetectTube;

    /**
     * Creates new form JFrame_Main
     *
     * @param dbExecutor
     * @param man1
     * @param man2
     * @param shiftManager
     * @param dAOSettingProperty
     * @param dAOPipe
     */
    public JFrame_Main(
            DBExecutor dbExecutor,
            Manometer man1,
            Manometer man2,
            ShiftManager shiftManager,
            DAOSettingProperty dAOSettingProperty,
            DAOPipe dAOPipe) {

        initComponents();
        this.dAOPipe = dAOPipe;
        this.dAOSettingProperty = dAOSettingProperty;
        this.dbExecutor = dbExecutor;
        this.shiftManager = shiftManager;
        shiftManager.addListener(this);
        this.man1 = man1;
        jPanel_PressureTest2.setManometer(man1);
        man1.addListener((man, prevValue, currentValue, milliSeconds) -> {
            EventQueue.invokeLater(() -> {
                jPanel_PressureTest2.addPoint(milliSeconds, currentValue, man.toString());
            });
        });
        this.man2 = man2;
        jPanel_PressureTest3.setManometer(man2);
        man2.addListener((man, prevValue, currentValue, milliSeconds) -> {
            EventQueue.invokeLater(() -> {
                jPanel_PressureTest3.addPoint(milliSeconds, currentValue, man.toString());
            });
        });
        try {
            pressureToDetectTube = Float.parseFloat(dAOSettingProperty.getByName("pressureToDetectTube").get(0).getVal());
        } catch (Exception ex) {
            pressureToDetectTube = 0;
        }
        man1.addListener(this::doOnPressureValueChanged1);
        man1.start();
        man2.addListener(this::doOnPressureValueChanged2);
        man2.start();
    }

    public void doOnPressureValueChanged1(Manometer man, Float prevValue, Float currentValue, Long milliSeconds) {
        //Если давление на опрессовке превысило давление регистрации
        if (currentValue > pressureToDetectTube) {
            //Если текущий этап работы регистратора оджидание опрессовки
            if (pressureTest1State == 0) {
                //Очищаем график
                jPanel_PressureTest2.clearDataset();
                //Если по системе учета на опрессовке есть труба
                List<Pipe> pipesOnPressTest = dAOPipe.getByLocationID(0);
                if(!pipesOnPressTest.isEmpty()){
                //Добавляем новый график
                pipesOnPressTest.get(0).
                //Записываем точку графика на в трубу
                //Отрисовываем точку на графике
                //Меняем этап работы на выполение регистрации
                
                } else { //Если по системе учета на опресоовке трубы нет
                    //Создать трубу на опрессовке
                    //Записать в нее точку графика
                    //Отрисовываем точку на графике
                }
                //Меняем этап работы на выполение регистрации
            }else{//Если текущий этап выполнение регистрации
                
            }
        } else {//Если давление ниже давления регистрации
            //Если текущий этап региистрация
            if(){
                //Записываем точку на ргафик и в трубу
                //Записываем время испытания
                //Сохраняем в базу данных
                //Меняем статус на ожидание регистрации
            }
        }
    }
    public void doOnPressureValueChanged2(Manometer man, Float prevValue, Float currentValue, Long milliSeconds) {
        //Если давление на опрессовке превысило давление регистрации
        if (currentValue > pressureToDetectTube) {
            //Если текущий этап работы регистратора оджидание опрессовки
            //Очищаем график
            if () {
                //Если по системе учета на опрессовке есть труба
                //Добавляем новый график
                //Записываем точку графика на в трубу
                //Отрисовываем точку на графике
                //Меняем этап работы на выполение регистрации
                if () {
                } else { //Если по системе учета на опресоовке трубы нет
                    //Создать трубу на опрессовке
                    //Записать в нее точку графика
                    //Отрисовываем точку на графике
                }
                //Меняем этап работы на выполение регистрации
            }else{//Если текущий этап выполнение регистрации
                
            }
        } else {//Если давление ниже давления регистрации
            //Если текущий этап региистрация
            if(){
                //Записываем точку на ргафик и в трубу
                //Записываем время испытания
                //Сохраняем в базу данных
                //Меняем статус на ожидание регистрации
            }
        }
    }

    @Override
    public void doOnShiftChaned(Shift shift) {
        jLabel_ShiftState.setText(shift.getState().toString());
        if (shift.getState().equals(ShiftState.RUNNING)) {
            jButton_SwitchShiftState.setText("Завершить");
        } else {
            jButton_SwitchShiftState.setText("Начать");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_Shift = new javax.swing.JPanel();
        jLabel_ShiftState = new javax.swing.JLabel();
        jButton_SwitchShiftState = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel_PressureTest2 = new ru.npptmk.uray_pressure_reg.gui.JPanel_PressureTest();
        jPanel_PressureTest3 = new ru.npptmk.uray_pressure_reg.gui.JPanel_PressureTest();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel_Shift.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Смена", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        jLabel_ShiftState.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel_ShiftState.setBorder(javax.swing.BorderFactory.createTitledBorder("Состояние"));

        jButton_SwitchShiftState.setText("Начать");
        jButton_SwitchShiftState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SwitchShiftStateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_ShiftLayout = new javax.swing.GroupLayout(jPanel_Shift);
        jPanel_Shift.setLayout(jPanel_ShiftLayout);
        jPanel_ShiftLayout.setHorizontalGroup(
            jPanel_ShiftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ShiftLayout.createSequentialGroup()
                .addComponent(jLabel_ShiftState, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton_SwitchShiftState)
                .addGap(0, 15, Short.MAX_VALUE))
        );
        jPanel_ShiftLayout.setVerticalGroup(
            jPanel_ShiftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ShiftLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton_SwitchShiftState, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel_ShiftState, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jButton2.setText("CLearChart");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("PrintChart");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(708, 708, 708))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_Shift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_PressureTest2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_PressureTest3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_Shift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_PressureTest3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_PressureTest2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_SwitchShiftStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SwitchShiftStateActionPerformed
        if (shiftManager.getState().equals(ShiftState.RUNNING)) {
            try {
                shiftManager.endShift();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "ERROR", ERROR_MESSAGE);
            }
        } else {
            try {
                shiftManager.startShift(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "ERROR", ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton_SwitchShiftStateActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        EventQueue.invokeLater(() -> {
            double x;
            double y;
            double y2;
            for (int i = 0; i < 100; i++) {
                x = i;
                y = Math.pow(x, 4) + 10000;

                jPanel_PressureTest2.addPoint(x, y, "Test");

            }
            for (int i = 0; i < 100; i++) {
                x = i;

                y2 = Math.pow(x, 4);

                jPanel_PressureTest2.addPoint(x, y2, "Test1");
            }

        });

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        EventQueue.invokeLater(() -> {

            jPanel_PressureTest2.clearDataset();

        });
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton_SwitchShiftState;
    private javax.swing.JLabel jLabel_ShiftState;
    private ru.npptmk.uray_pressure_reg.gui.JPanel_PressureTest jPanel_PressureTest2;
    private ru.npptmk.uray_pressure_reg.gui.JPanel_PressureTest jPanel_PressureTest3;
    private javax.swing.JPanel jPanel_Shift;
    // End of variables declaration//GEN-END:variables
}
