package ru.npptmk.uray_pressure_reg.drivers;

import java.util.Date;
import java.util.List;
import ru.npptmk.uray_pressure_reg.model.Shift;
import ru.npptmk.uray_pressure_reg.model.ShiftState;

/**
 *
 * @author RazumnovAA
 */
public interface DAOShift {

    /**
     * Вернуть список всех смен из базы данных.
     *
     * @return список смен найденных в базе данных, или пустой список если
     * ничего не найдено.
     */
    public List<Shift> getAll();

    /**
     * Возвращает список смен за заданный временной интервал испытаний.
     *
     * @param start начало интервала испытаний.
     * @param end конец интервала испытаний.
     * @return найденный список смен, или пустой список если ничего не найдено,
     * или интервала поиска задан некорректно.
     */
    public List<Shift> getByDatePeriod(Date start, Date end);

    /**
     * Возвращает смены на основании их статуса.
     *
     * @param shiftState статус заданный для поиска.
     * @return список смен найденных с заданным статусом, или пустой список,
     * если смен с заданным статусом не найдено.
     */
    public List<Shift> getByState(ShiftState shiftState);

    /**
     * Ищет смену по заданному id.
     *
     * @param id заданный id для поиска.
     * @return найденная смена или null.
     */
    public Shift getById(Long id);

    /**
     * Ищет состояние заданной смены в базе данных.
     *
     * @param shift заданная труба для поиска.
     * @return состояние трубы найденное в базе данных или null, если ничего не
     * найдено.
     */
    public Shift reread(Shift shift);

    public Shift createNew(Shift shift);

    public Shift update(Shift shift);
}
