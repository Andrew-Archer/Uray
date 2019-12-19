package ru.npptmk.uray_pressure_reg.managers;

import java.util.List;
import java.util.Map;
import ru.npptmk.uray_pressure_reg.model.Operator;
import ru.npptmk.uray_pressure_reg.model.Shift;
import ru.npptmk.uray_pressure_reg.model.ShiftState;

/**
 * Управляет рабочей сменой.
 *
 * @author RazumnovAA
 */
public interface ShiftManager {

    public void addListener(ShiftManagerListener listener);

    public void addListeners(List<ShiftManagerListener> listeners);

    public void removeListener(ShiftManagerListener listener);

    public void removeListeners(List<ShiftManagerListener> listeners);

    /**
     * Начинает смену.
     * <P>
     * Если текущая смена не была начата, то устанавливает текущее время как
     * время начала смены.
     * <P>
     * Если текущая смена была начата но не была закрыта, то выбрасывает
     * исключение.
     * <P>
     * Если текущая смена закрыта, то создает новую и начинает ее.
     *
     * @return Начатую смену.
     * @throws Exception связанное с тем что вы пытаетесь начать начатую смену.
     */
    public Shift startShift(Map<String, Operator> personal) throws Exception;

    /**
     * Закрывает текущую смену.
     * <P>
     * Если текущая смена открыта, то менеджер её закрывает, устанавливая
     * текущую дату как время завершения смены.
     * <P>
     * Если текущая смена не открыта, то выбрасывает исключение.
     *
     * @return Завершенную смену.
     * @throws Exception связанное с тем, что текущая начатая смена отсутствует.
     */
    public Shift endShift() throws Exception;

    /**
     * Возвращает текущую мену с которой работает менеджер.
     *
     * @return смена с которой работает менеджер.
     */
    public Shift getShift();

    /**
     * Возвращает состояние смены управляемой менеджером.
     *
     * @return текущее состояние управляемой смены.
     */
    public ShiftState getState();
}
