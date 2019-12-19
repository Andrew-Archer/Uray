package ru.npptmk.uray_pressure_reg.drivers;

import java.util.function.Function;
import javax.persistence.EntityManager;

/**
 * Интерфейс для общения с классами содержащими в себе рутинную логику по
 * выполнению запросов к базам данных.
 * <p>
 * Содержит два главных метода для выполнения чтения и записи (удаление тоже
 * считается записью).
 *
 * @author RazumnovAA
 */
public interface DBExecutor {
    public <R> R execRead(Function<EntityManager, R> readLogic);
    public <R> R execWrite(Function<EntityManager, R> writeLogic);
}
