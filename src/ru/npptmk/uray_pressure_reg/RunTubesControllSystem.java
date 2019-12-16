package ru.npptmk.uray_pressure_reg;

import java.io.PrintWriter;
import java.net.InetAddress;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import org.apache.derby.drda.NetworkServerControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.StandardChartTheme;
import ru.npptmk.drivers.manometers.DriverForDM5002M;
import ru.npptmk.tubescontrollsystem.drivers.Controller;
import ru.npptmk.tubescontrollsystem.drivers.ControllerS7;
import ru.npptmk.tubescontrollsystem.drivers.DBHandlerImpl;
import ru.npptmk.tubescontrollsystem.gui.JDialog_SetupIndicator;
import ru.npptmk.tubescontrollsystem.gui.JFrame_Main;
import ru.npptmk.tubescontrollsystem.managers.ShiftManagerImpl;
import ru.npptmk.tubescontrollsystem.managers.TubesRelocator;
import ru.npptmk.tubescontrollsystem.model.Tube;

/**
 * Класс для запуска программы в реальных условиях у заказчика.
 *
 * @author RazumnovAA
 */
public class RunTubesControllSystem {

    private static final Logger logger = LogManager.getLogger("HelloWorld");

    public static void main(String[] args) throws InterruptedException {
        //System.setProperty("log4j.configurationFile", "C:\\log4j.properties");
        JDialog_SetupIndicator indication = new JDialog_SetupIndicator(null, false);
        indication.setMaxValueForProgress(6);
        indication.dropProgress();
        indication.setVisible(true);
        System.setProperty("derby.system.home", System.getProperty("user.home"));
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
            final DBHandlerImpl dbHandler = new DBHandlerImpl("TestTubesControllSystemPU");
            indication.incremetProgress();

            indication.printlnLog("Подключаемся к манометру");
            final DriverForDM5002M manometer = new DriverForDM5002M("/dev/ttyS2");
            indication.incremetProgress();

            indication.printlnLog("Запускаем менеджер смен");
            final ShiftManagerImpl shiftManager = new ShiftManagerImpl(dbHandler);
            indication.incremetProgress();

            indication.printlnLog("Запускаем менеджер труб");
            final TubesRelocator<Tube> tubesRelocator = new TubesRelocator<>(dbHandler);
            indication.incremetProgress();

            indication.printlnLog("Подключаемся к контроллеру");
            final Controller controller = new ControllerS7("localhost", 502);
            indication.incremetProgress();
            indication.setVisible(false);
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(() -> {
                new JFrame_Main(
                        manometer,
                        controller,
                        dbHandler,
                        shiftManager,
                        tubesRelocator)
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
