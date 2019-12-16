package ru.npptmk.uray_pressure_reg.drivers;

import ru.npptmk.tubescontrollsystem.drivers.TIAPortalAddress.RegTypes;

/**
 * Интерфейс обмена данными с контроллером Siemens по протоколу Modbus.
 *
 * @author RazumnovAA
 */
public interface Controller {

    public interface ControllerStateListener {

        /**
         * Метод вызываемый у зарегистрированных слушателей при изменении
         * состояния подключения к контроллеру.
         *
         * @param isConnected true - если подключение есть и false, если
         * подключение отсутствует.
         */
        public void onControllerChanedState(Boolean isConnected);
    }

    /**
     * Добавляет слушателя состояния подключения к контроллеру.
     *
     * @param listener слушатель для добавления в список уведомления.
     */
    public void addConnectionStateListener(ControllerStateListener listener);

    /**
     * Задает состояние дискретных выходов. Задание дискретного выхода
     * происходит при следующей отправки данных в контроллер, а не в момент
     * вызова данного метода.
     *
     * @param <T> тип задаваемого значения
     * @param byteNumber номер байта по адресации TIAPortal.
     * @param bitNumber номер бита по адресации TIAPortal.
     * @param value значение состояния для присвоения дискретному выходу.
     * @param regType тип регистра.
     */
    public <T> void setRegisterValue(
            Integer byteNumber,
            Integer bitNumber,
            T value,
            RegTypes regType);

    /**
     * Задает состояние дискретных выходов. Задание дискретного выхода
     * происходит при следующей отправки данных в контроллер, а не в момент
     * вызова данного метода.
     *
     * @param <T> тип задаваемого значения
     * @param address объект адреса TIA PORTAL.
     * @param value значение состояния для присвоения дискретному выходу.
     */
    public <T> void setRegisterValue(
            TIAPortalAddress address,
            int value);

    /**
     * Возвращает последнее прочитанное состояние регистра.
     *
     * @param <T>
     * @param byteNumber номер байта по адресации TIAPortal.
     * @param bitNumber номер бита по адресации TIAPortal.
     * @param regType тип регистра от которого получаем значение.
     * @return последнее прочитанное состояние регистра.
     */
    public <T> T getRegisterValue(
            Integer byteNumber,
            Integer bitNumber,
            RegTypes regType
    );

    /**
     * Возвращает последнее прочитанное состояние регистра.
     *
     * @param <T> тип возвращаемого значения.
     * @param address объект адреса TIA PORTAL.
     * @return последнее прочитанное состояние регистра.
     */
    public <T> T getRegisterValue(
            TIAPortalAddress address
    );

    public void start();

    public void stop();

}
