package ru.npptmk.uray_pressure_reg.gui;

import java.awt.EventQueue;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import ru.npptmk.drivers.manometers.Manometer;
import ru.npptmk.uray_pressure_reg.drivers.DAOPipe;
import ru.npptmk.uray_pressure_reg.drivers.DAOSettingProperty;
import ru.npptmk.uray_pressure_reg.drivers.DBExecutor;
import ru.npptmk.uray_pressure_reg.managers.ShiftManager;
import ru.npptmk.uray_pressure_reg.managers.ShiftManagerListener;
import ru.npptmk.uray_pressure_reg.model.Graph;
import ru.npptmk.uray_pressure_reg.model.Pipe;
import ru.npptmk.uray_pressure_reg.model.Pipe.Locations;
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
    private Pipe currentPress1Pipe;
    private Pipe currentPress2Pipe;

    private final DBExecutor dbExecutor;
    private final Manometer man1;
    private final Manometer man2;
    private final ShiftManager shiftManager;
    private Integer pressureTest1State = 0;
    private Integer pressureTest2State = 0;
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
        jPanel_PressureTest1.setManometer(man1);
        man1.addListener((man, prevValue, currentValue, milliSeconds) -> {
            EventQueue.invokeLater(() -> {
                jPanel_PressureTest1.addPoint(milliSeconds / 1000f, currentValue, "One");
            });
        });
        this.man2 = man2;
        jPanel_PressureTest2.setManometer(man2);
        man2.addListener((man, prevValue, currentValue, milliSeconds) -> {
            EventQueue.invokeLater(() -> {
                jPanel_PressureTest2.addPoint(milliSeconds / 1000f, currentValue, "Two");
            });
        });
        try {
            pressureToDetectTube = Float.parseFloat(dAOSettingProperty.getByName("pressureToDetectTube").get(0).getVal());
        } catch (Exception ex) {
            pressureToDetectTube = 0;
        }
        man1.addListener(this::doOnPressureValueChanged);
        man1.setName("man1");
        man1.start();
        man2.addListener(this::doOnPressureValueChanged);
        man2.setName("man2");
        man2.start();
    }
    
    private void loadPreviousResults(){
    }

    public void doOnPressureValueChanged(Manometer man, Float prevValue, Float currentValue, Long milliSeconds) {
        Pipe currentPipe;
        JPanel_PressureTest currentPanel;
        Integer currentPressState;
        Locations currentLocation;
        switch (man.getName()) {
            case "man1":
                currentPipe = currentPress1Pipe;
                currentPanel = jPanel_PressureTest1;
                currentPressState = pressureTest1State;
                currentLocation = Locations.PRESS_TEST_1;
                break;
            default:
                currentPipe = currentPress2Pipe;
                currentPanel = jPanel_PressureTest2;
                currentPressState = pressureTest1State;
                currentLocation = Locations.PRESS_TEST_2;
        }
        //Если давление на опрессовке превысило давление регистрации
        if (currentValue >= pressureToDetectTube) {
            //Если текущий этап работы регистратора оджидание опрессовки
            if (currentPressState == 0) {
                //Очищаем график
                currentPanel.clearDataset();
                //Если по системе учета на опрессовке есть труба
                List<Pipe> pipesOnPressTest = dAOPipe.getByLocationID(currentLocation);
                if (!pipesOnPressTest.isEmpty()) {
                    //Добавляем новый график
                    currentPipe = pipesOnPressTest.get(pipesOnPressTest.size() - 1);
                    currentPipe.getGraphs().add(new Graph());
                } else { //Если по системе учета на опресоовке трубы нет
                    //Создать трубу на опрессовке
                    currentPipe = new Pipe();
                    currentPipe.setShiftId(shiftManager.getShift().getId());
                    //Помещаем трубу на опрессовку
                    currentPipe.setLocationID(currentLocation);
                    //Создаем график
                    currentPipe.getGraphs().add(new Graph());
                    currentPipe = dAOPipe.createNew(currentPipe);
                    //Добавляем трубу в таблицу
                    currentPanel.addPipe(currentPipe.getId().toString(), currentLocation.toString());
                }
                //Меняем этап работы на выполение регистрации
                currentPressState = 1;
            } else {//Если текущий этап выполнение регистрации

            }
            //Записываем точку графика на в трубу
            currentPipe.getLastGraph().addPoint(milliSeconds / 1000f, currentValue);
        } else {//Если давление ниже давления регистрации
            //Если текущий этап региистрация
            if (currentPressState == 1) {
                //Записываем точку на ргафик и в трубу
                currentPipe.getLastGraph().addPoint(milliSeconds / 1000f, currentValue);
                //Записываем время испытания
                currentPipe.setTestDate(new Date());
                //Сохраняем в базу данных
                currentPipe = dAOPipe.update(currentPipe);
                //Меняем статус на ожидание регистрации
                currentPressState = 0;
            } else {//Если текущий этап ожидание регистрации
                //Ничего делать не надо, все ок.
            }
        }
        switch (man.getName()) {
            case "man1":
                currentPress1Pipe = currentPipe;
                jPanel_PressureTest1 = currentPanel;
                pressureTest1State = currentPressState;
                break;
            default:
                currentPress2Pipe = currentPipe;
                jPanel_PressureTest2 = currentPanel;
                pressureTest1State = currentPressState;
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
        jPanel_PressureTest1 = new ru.npptmk.uray_pressure_reg.gui.JPanel_PressureTest();
        jPanel_PressureTest2 = new ru.npptmk.uray_pressure_reg.gui.JPanel_PressureTest();

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_Shift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_PressureTest1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_PressureTest2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_Shift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_PressureTest2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_PressureTest1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_SwitchShiftState;
    private javax.swing.JLabel jLabel_ShiftState;
    private ru.npptmk.uray_pressure_reg.gui.JPanel_PressureTest jPanel_PressureTest1;
    private ru.npptmk.uray_pressure_reg.gui.JPanel_PressureTest jPanel_PressureTest2;
    private javax.swing.JPanel jPanel_Shift;
    // End of variables declaration//GEN-END:variables
}
