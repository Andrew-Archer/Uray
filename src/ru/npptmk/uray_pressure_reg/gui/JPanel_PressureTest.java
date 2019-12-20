package ru.npptmk.uray_pressure_reg.gui;

/**
 *
 * @author RazumnovAA
 */
public class JPanel_PressureTest extends javax.swing.JPanel {

    /**
     * Creates new form JPanel_PressureTest
     */
    public JPanel_PressureTest() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chartPanel_RealTimePressureGraph1 = new ru.npptmk.uray_pressure_reg.gui.ChartPanel_RealTimePressureGraph();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        javax.swing.GroupLayout chartPanel_RealTimePressureGraph1Layout = new javax.swing.GroupLayout(chartPanel_RealTimePressureGraph1);
        chartPanel_RealTimePressureGraph1.setLayout(chartPanel_RealTimePressureGraph1Layout);
        chartPanel_RealTimePressureGraph1Layout.setHorizontalGroup(
            chartPanel_RealTimePressureGraph1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 442, Short.MAX_VALUE)
        );
        chartPanel_RealTimePressureGraph1Layout.setVerticalGroup(
            chartPanel_RealTimePressureGraph1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 242, Short.MAX_VALUE)
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "№", "ID", "Позиция"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chartPanel_RealTimePressureGraph1, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chartPanel_RealTimePressureGraph1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void addPipe(String ID, String position) {

    }
    
    public void removePipe(String ID, String position){
        
    }
    
    public void updatePipe(String ID, String position){
        
    }
    
    public void clearChart(){
        chartPanel_RealTimePressureGraph1.clearDataSet();
    }
    
    public void addPoint(Float x, Float y){
        chartPanel_RealTimePressureGraph1.addPoint();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ru.npptmk.uray_pressure_reg.gui.ChartPanel_RealTimePressureGraph chartPanel_RealTimePressureGraph1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
