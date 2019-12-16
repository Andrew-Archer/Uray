package ru.npptmk.uray_pressure_reg.drivers;

import java.util.Date;
import java.util.List;
import ru.npptmk.tubescontrollsystem.model.Customer;
import ru.npptmk.tubescontrollsystem.model.DurabilityGroup;
import ru.npptmk.tubescontrollsystem.model.InputReg;
import ru.npptmk.tubescontrollsystem.model.LastUsedTubeSettings;
import ru.npptmk.tubescontrollsystem.model.Operator;
import ru.npptmk.tubescontrollsystem.model.OutputReg;
import ru.npptmk.tubescontrollsystem.model.PressureTestCharts;
import ru.npptmk.tubescontrollsystem.model.PrintersSettings;
import ru.npptmk.tubescontrollsystem.model.ProgrammSettings;
import ru.npptmk.tubescontrollsystem.model.Shift;
import ru.npptmk.tubescontrollsystem.model.ShortReg;
import ru.npptmk.tubescontrollsystem.model.ThreadType;
import ru.npptmk.tubescontrollsystem.model.Tube;
import ru.npptmk.tubescontrollsystem.model.TubeType;
import ru.npptmk.tubescontrollsystem.model.UnitConfig;

/**
 * Абстрагирует работу с базой данных.
 *
 * @author RazumnovAA
 */
public interface DBHandler {

    /**
     * Возвращает список принтеров из базы данных.
     *
     * @return список принтеров или пустой список.
     */
    public List<PrintersSettings> getPrintesSettings();

    /**
     * Получить настройки программы из базы данных.
     *
     * @return сохраненные в базе данных настройки.
     */
    public List<ProgrammSettings> getProgrammSettings();

    /**
     * Получает графики опрессовки по заданному Id графиков, которые хранятся в
     * сущности Tube.
     *
     * @param id Id графиков которые необходимо получить.
     * @return найденные графики или null если ничего не найдено.
     */
    public PressureTestCharts getPressureTestChartsById(Long id);

    /**
     * Позволяет получить трубы прошедшие установку за указанную смену.
     *
     * @param shiftId id смены за которую трубы прошли установку.
     * @return трубы прошедшие установку за указанную смену.
     */
    public List<Tube> getOutOfLineTubesByShiftId(Long shiftId);

    /**
     * Получаем трубу из базы по Id.
     *
     * @param id идентификатор искомой трубы.
     * @return найденная труба или null если труба не найдена.
     */
    public Tube getTubeById(Long id);

    /**
     * Возвращает список труб принадлежащих к пачке труб.
     *
     * @param packId идентификатор.
     * @return список труб принадлежащих указанной пачке.
     */
    public List<Tube> getTubesByPackNumber(long packId);

    /**
     * Удаляет трубу по указанному ID.
     *
     * @param tube идентификатор удаляемой трубы.
     */
    public void removeTube(Tube tube);

    /**
     * Сохраняет имена принтеров для печати этикеток.
     *
     * @param printersSettings объект, хранящий имена принтеров для печати.
     * @return объект, хранящий имена принтеров для печати сохраненный в базу с
     * присвоенным id.
     */
    public PrintersSettings savePrintersSettings(PrintersSettings printersSettings);

    /**
     * Сохраняет настройки программы.
     *
     * @param programmSettings настройки для сохранения.
     */
    public ProgrammSettings saveProgrammSettings(ProgrammSettings programmSettings);

    /**
     * Возвращает список труб на устройстве или пустой список если труб не
     * нашлось.
     *
     * @param deviceId список труб найденных на устройстве.
     */
    public List<Tube> getTubesOnDevice(Long deviceId);

    /**
     * Получает список всех заказчиков, имеющихся в базе.
     *
     * @return список заказчиков имеющихся в базе.
     */
    public List<Customer> getAllCustomers();

    /**
     * Возвращает из базы список всех операторов.
     *
     * @return список всех операторов находящихся в базе.
     */
    public List<Operator> getAllOperators();

    /**
     * Возвращает список незавершенных смен, то есть список смен у которых нет
     * время окончания.
     *
     * @return Список незавершенных смен.
     */
    public List<Shift> getAllUnfinishedShifts();

    /**
     * Возвращает трубы проконтролированные в заданном диапазоне дат.
     *
     * @param beginning начальная дата временного интервала.
     * @param finish конечная дата временного интервала.
     * @return список труб проконтролированных за интервал или пустой список.
     */
    public List<Tube> getTubesByDate(Date beginning, Date finish);

    /**
     * Сохраняет заданного оператора в базу данных
     *
     * @param operator оператор для сохранения. Постоянство не обеспечивается,
     * то есть id не задается. Если планируете в дальнейшем использовать этого
     * оператора, то берите значение возвращаемого результата.
     * @return Персистентное состояние сохраненного оператора.
     */
    public Operator saveOperator(Operator operator);

    /**
     * Сохраняет набор графиков базу данных и возвращает персистентную сущность.
     *
     * @param currentPressureTestChart сущность графиков для сохранения.
     * @return персистентная сущность графиков.
     */
    public PressureTestCharts savePressureTestChart(PressureTestCharts currentPressureTestChart);

    /**
     * Получает все трубы за смену.
     *
     * @param shiftId id смены трубы относящиеся к которой необходимо получить.
     * @return список труб найденных за смену.
     */
    public List<Tube> getTubesByShiftId(Long shiftId);

    /**
     * Находит графики опрессовки в базе по ID.
     *
     * @param id по которому необходимо найти графики.
     * @return найденные графики или null если графиков не найдено.
     */
    public PressureTestCharts findPressureTestChartById(Long id);

    /**
     * Сохраняет смену в базу данных.
     *
     * @param shift смена для сохранения,ее постоянство не обеспечивается не
     * обеспечивается. Персистентная сущность возвращается из функции.
     * @return персистентный экземпляр смены.
     */
    public Shift saveShift(Shift shift);

    /**
     * Сохраняет тип резьбы в базу данных.
     *
     * @param threadType тип резьбы для сохранения,ее постоянство не
     * обеспечивается. Персистентная сущность возвращается из функции.
     * @return персистентный экземпляр типа резьбы трубы.
     */
    public ThreadType saveThreadType(ThreadType threadType);

    /**
     * Приводит состояние базы данных в соответствии с переданными аргументами.
     *
     * @param inputRegs список входных регистров которые должны быть в базе.
     * @param outputRegs список выходных регистров которые должны быть в базе.
     * @param shortRegs список Short регистров которые должны быть в базе.
     */
    public void updateAllRegs(
            List<InputReg> inputRegs,
            List<OutputReg> outputRegs,
            List<ShortReg> shortRegs);

    /**
     * Сохраняет трубу базу данных.
     *
     * @param tube труба для сохранения в базу.
     * @return труба соответствующая данным в базе данных.
     */
    public Tube saveTube(Tube tube);

    /**
     * Удаляет все три массива из базы данных
     *
     * @param inputRegs
     * @param shortRegs
     * @param outputRegs
     */
    public void removeAllRegs(
            List<InputReg> inputRegs,
            List<OutputReg> outputRegs,
            List<ShortReg> shortRegs);

    /**
     * Получает список всех групп прочности имеющихся в базе.
     *
     * @return список всех групп прочности имеющихся в базе.
     */
    public List<DurabilityGroup> getAllDurabilityGroups();

    /**
     * Получает список всех входных регистров из базы данных.
     *
     * @return список всех входных регистров или пустой список.
     */
    public List<InputReg> getAllInputRegs();

    /**
     * Получает список всех выходов из базы данных.
     *
     * @return список всех выходов или пустой список.
     */
    public List<OutputReg> getAllOutputRegs();

    /**
     * Получает список всех Short регистров из базы данных.
     *
     * @return список всех Short регистров или пустой список.
     */
    public List<ShortReg> getAllShortRegs();

    /**
     * Получает список всех типов труб имеющихся в базе.
     *
     * @return список всех труб имеющихся в базе.
     */
    public List<TubeType> getAllTubeTypes();

    /**
     * Получает последние использованные настройки типа трубы.
     *
     * @return возвращает лист настроек, так как возможно, что их будет много
     * вариантов.
     */
    public List<LastUsedTubeSettings> getLastUsedTubeSettings();

    /**
     * Загружает список всех настроек установки, имеющихся в базе данных.
     *
     * @return список настроек установки, полученных из базы.
     */
    public List<UnitConfig> loadUnitConfigs();

    /**
     * Сохраняет заказчика в базу данных.
     *
     * @param createdCustomer заказчик для сохранения в базу данных id у него
     * установлен не будет. персистентная сущность будет возвращена из метода.
     * @return персистентная сущность.
     */
    public Customer saveCustomer(Customer createdCustomer);

    /**
     * Сохраняет заданную группу прочности как новую.
     *
     * @param createdDurabilityGroup группа прочности для сохранения как новой.
     */
    public DurabilityGroup saveDurabilityGroup(DurabilityGroup createdDurabilityGroup);

    /**
     * Сохраняет последние использованные настройки типа трубы в базу данных.
     *
     * @param lastSettings последние использованные настройки типа трубы для
     * сохранения в базу.
     */
    public LastUsedTubeSettings saveLastUsedTubeSettings(LastUsedTubeSettings lastSettings);

    /**
     * Сохраняет тип трубы в базу данных как новый.
     *
     * @param createdTubeType тип трубы для сохранения.
     */
    public TubeType saveTubeType(TubeType createdTubeType);

    /**
     * Сохраняет заданные настройки установки как текущие.
     *
     * @param currentConfig настройки установки для сохранения.
     */
    public UnitConfig saveUnitConfig(UnitConfig currentConfig);
}
