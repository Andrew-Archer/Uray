package ru.npptmk.uray_pressure_reg;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import org.apache.derby.drda.NetworkServerControl;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.StandardChartTheme;
import ru.npptmk.drivers.manometers.Manometer;
import ru.npptmk.drivers.manometers.ManometerTestImpl;
import ru.npptmk.uray_pressure_reg.drivers.DAOPipe;
import ru.npptmk.uray_pressure_reg.drivers.DAOPipeImpl;
import ru.npptmk.uray_pressure_reg.drivers.DAOSettingProperty;
import ru.npptmk.uray_pressure_reg.drivers.DAOSettingPropertyImpl;
import ru.npptmk.uray_pressure_reg.drivers.DBExecutor;
import ru.npptmk.uray_pressure_reg.drivers.DerbyDBExecutor;
import ru.npptmk.uray_pressure_reg.gui.JDialog_SetupIndicator;
import ru.npptmk.uray_pressure_reg.gui.JFrame_Main;
import ru.npptmk.uray_pressure_reg.managers.ShiftManager;
import ru.npptmk.uray_pressure_reg.managers.ShiftManagerImpl;

/**
 * Класс для запуска программы в реальных условиях у заказчика.
 *
 * @author RazumnovAA
 */
public class Run {

    private static final Logger LOG = Logger.getLogger("HelloWorld");

    public static void main(String[] args) throws InterruptedException, IOException {
        JDialog_SetupIndicator indication = new JDialog_SetupIndicator(null, false);
        indication.setMaxValueForProgress(2);
        indication.dropProgress();
        indication.setVisible(true);
        //LogManager.getLogManager().readConfiguration(new FileInputStream("logger.properties"));
        System.setProperty("derby.system.home", "db");
        System.out.printf("derby.system.home = %s%n", System.getProperty("user.home"));
        System.setProperty("java.net.preferIPv4Stack", "true");
        //Устанавливает старую тему при запуске для JFreeCharts,
        //так как JasperReports делает это при открытии и создается
        //эффект хаотичной смены оформления.
        //Если это вам ни о чем не говорит, то просто выполните эту строчку
        //перед созданием GUI в программах использующих JFreeCharts и JasperReports
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        try {
            indication.printlnLog("Запуск базы данных");
            final NetworkServerControl server = new NetworkServerControl(InetAddress.getByName("0.0.0.0"), 1527);
            server.start(new PrintWriter(System.out));
            indication.incremetProgress();

            indication.printlnLog("Подключаемся к базе данных");
            final DBExecutor dbExecutor = DerbyDBExecutor.getExecutor("UrayPressureRegistrationPU");
            indication.incremetProgress();

            indication.printlnLog("Подключаемся к манометру1");
            final Manometer manometer1 = new ManometerTestImpl();
            indication.incremetProgress();

            indication.printlnLog("Подключаемся к манометру");
            final Manometer manometer2 = new ManometerTestImpl();
            indication.incremetProgress();

            indication.printlnLog("Запускаем менеджер смен");
            final ShiftManager shiftManager = new ShiftManagerImpl(dbExecutor);
            indication.incremetProgress();
            
            indication.printlnLog("Запускаем менеджер смен");
            final DAOSettingProperty dAOSettingProperty = DAOSettingPropertyImpl.getDAO(dbExecutor);
            indication.incremetProgress();
            
            indication.printlnLog("Запускаем менеджер смен");
            final DAOPipe dAOPipe = DAOPipeImpl.getDAO(dbExecutor);
            indication.incremetProgress();

            /*indication.printlnLog("Подключаемся к контроллеру");
            final Controller controller = new ControllerS7("localhost", 502);
            indication.incremetProgress();*/
            indication.setVisible(false);
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(() -> {
                new JFrame_Main(dbExecutor, manometer1, manometer2, shiftManager, dAOSettingProperty, dAOPipe)
                        .setVisible(true);
            });
        } catch (Exception ex) {
            indication.setVisible(false);
            JOptionPane.showMessageDialog(
                    null,
                    ex.getMessage()
                    + "\nПрограмма будет закрыта",
                    "Ошибка",
                    ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
