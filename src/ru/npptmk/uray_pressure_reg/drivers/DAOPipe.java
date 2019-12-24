package ru.npptmk.uray_pressure_reg.drivers;

import java.util.Date;
import java.util.List;
import ru.npptmk.uray_pressure_reg.model.Pipe;
import ru.npptmk.uray_pressure_reg.model.Pipe.Locations;
import ru.npptmk.uray_pressure_reg.model.Shift;

/**
 * Для чтения и записи данных сущности. Для подробностей смотрите конкретную
 * реализацию.
 *
 * @author RazumnovAA
 */
public interface DAOPipe {

    /**
     * Вернуть список всех труб из базы данных.
     *
     * @return список труб найденных в базе данных, или пустой список если
     * ничего не найдено.
     */
    public List<Pipe> getAll();

    /**
     * Вернуть список всех труб находящихся в указанной локации.
     *
     * @param locationID идентификатор локации в которой ищем трубы.
     * @return список труб в указанной локации или пустой список, если ничего не
     * найдено.
     */
    public List<Pipe> getByLocationID(Locations location);

    /**
     * Получить список труб принадлежащих заданной смене.
     *
     * @param shift заданная смена
     * @return список труб найденный по заданной смене или пустой лист, если нет
     * ни одной трубы.
     */
    public List<Pipe> getByShiftId(Shift shift);

    /**
     * Возвращает список труб за заданный временной интервал испытаний.
     *
     * @param start начало интервала испытаний.
     * @param end конец интервала испытаний.
     * @return найденный список труб, или пустой список если ничего не найдено,
     * или интервала поиска задан некорректно.
     */
    public List<Pipe> getByDatePeriod(Date start, Date end);

    /**
     * Ищет трубу по заданному id.
     *
     * @param id заданный id для поиска.
     * @return найденная труба или null.
     */
    public Pipe getById(Long id);

    /**
     * Ищет состояние заданной трубы в базе данных.
     *
     * @param pipe заданная труба для поиска.
     * @return состояние трубы найденное в базе данных или null, если ничего не
     * найдено.
     */
    public Pipe reread(Pipe pipe);

    public Pipe createNew(Pipe pipe);

    public Pipe update(Pipe pipe);
}
