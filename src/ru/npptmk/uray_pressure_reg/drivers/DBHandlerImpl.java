package ru.npptmk.uray_pressure_reg.drivers;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import ru.npptmk.tubescontrollsystem.model.Customer;
import ru.npptmk.tubescontrollsystem.model.DEVICES_ID;
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
 * Класс упрощающий работу с базой данных.
 *
 * @author RazumnovAA
 */
public class DBHandlerImpl implements DBHandler {

    private final EntityManagerFactory emf;
    private EntityManager em;

    /**
     *
     * @param persistenceUnitName
     */
    public DBHandlerImpl(String persistenceUnitName) {
        this.emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Override
    public PressureTestCharts findPressureTestChartById(Long id) {
        return executeSelectTask((t) -> {
            return t.find(PressureTestCharts.class, id);
        });
    }

    @Override
    public List<Customer> getAllCustomers() {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getAllCustomers", Customer.class)
                    .getResultList();
        });
    }

    @Override
    public List<DurabilityGroup> getAllDurabilityGroups() {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getAllDurabilityGroups", DurabilityGroup.class)
                    .getResultList();
        });
    }

    @Override
    public List<InputReg> getAllInputRegs() {
        return executeSelectTask((t) -> {
            return em.createNamedQuery("shit", InputReg.class).getResultList();
        });
    }

    @Override
    public List<Operator> getAllOperators() {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getAllOperators", Operator.class).getResultList();
        });
    }

    @Override
    public List<OutputReg> getAllOutputRegs() {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getAllOutputs", OutputReg.class).getResultList();
        });
    }

    @Override
    public List<ShortReg> getAllShortRegs() {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getAllShortRegs", ShortReg.class).getResultList();
        });
    }

    @Override
    public List<TubeType> getAllTubeTypes() {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getAllTubeTypes", TubeType.class).getResultList();
        });
    }

    @Override
    public List<Shift> getAllUnfinishedShifts() {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getAllUnfinishedShifts", Shift.class).getResultList(); //To change body of generated lambdas, choose Tools | Templates.
        });
    }

    @Override
    public List<LastUsedTubeSettings> getLastUsedTubeSettings() {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getLastUsedTubeSettings", LastUsedTubeSettings.class)
                    .getResultList();
        });
    }

    @Override
    public List<Tube> getOutOfLineTubesByShiftId(Long shiftId) {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getOutOfLineTubesByShiftId", Tube.class)
                    .setParameter("shiftId", shiftId)
                    .setParameter("deviceId", DEVICES_ID.OUTWORLD)
                    .getResultList();
        });
    }

    @Override
    public PressureTestCharts getPressureTestChartsById(Long id) {
        return executeSelectTask((t) -> {
            return t.find(PressureTestCharts.class, id);
        });
    }

    @Override
    public List<PrintersSettings> getPrintesSettings() {
        return executeSelectTask((t) -> {
            return t.createNamedQuery(
                    "getAllPrintersSettings",
                    PrintersSettings.class).getResultList();
        });
    }

    @Override
    public List<ProgrammSettings> getProgrammSettings() {
        return executeSelectTask((t) -> {
            return t.createNamedQuery(
                    "getAllProgrammSettings",
                    ProgrammSettings.class).getResultList();
        });
    }

    @Override
    public Tube getTubeById(Long id) {
        return executeSelectTask((t) -> {
            return t.find(Tube.class, id);
        });
    }

    @Override
    public List<Tube> getTubesByDate(Date beginning, Date finish) {
        return executeSelectTask((t) -> {
            return t
                    .createNamedQuery("getTubesByDates", Tube.class)
                    .setParameter("start", beginning)
                    .setParameter("end", finish)
                    .getResultList();
        });
    }

    @Override
    public List<Tube> getTubesByPackNumber(long packId) {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getTubesByPackNumber", Tube.class)
                    .setParameter("packId", packId)
                    .getResultList();
        });
    }

    @Override
    public List<UnitConfig> loadUnitConfigs() {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getAllUnitConfigs", UnitConfig.class).getResultList();
        });
    }

    @Override
    public void removeAllRegs(List<InputReg> inputRegs, List<OutputReg> outputRegs, List<ShortReg> shortRegs) {
        EntityManager em = emf.createEntityManager();
        try {
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();

                inputRegs.forEach((inputReg) -> {
                    em.remove(inputReg);
                });

                outputRegs.forEach((outputReg) -> {
                    em.remove(outputReg);
                });

                shortRegs.forEach((shortReg) -> {
                    em.remove(shortReg);
                });

                trans.commit();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (trans.isActive()) {
                    trans.rollback();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void removeTube(Tube tube) {
        em = emf.createEntityManager();
        try {
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                tube = em.find(Tube.class, tube.getId());
                em.remove(tube);
                trans.commit();
            } finally {
                if (trans.isActive()) {
                    trans.rollback();
                }
            }
        } finally {
            if (!em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Customer saveCustomer(Customer createdCustomer) {
        return executeUpdateQuery((t) -> {
            return t.merge(createdCustomer);
        });
    }

    @Override
    public DurabilityGroup saveDurabilityGroup(DurabilityGroup createdDurabilityGroup) {
        return executeUpdateQuery((t) -> {
            return t.merge(createdDurabilityGroup);
        });
    }

    @Override
    public LastUsedTubeSettings saveLastUsedTubeSettings(LastUsedTubeSettings lastUsedTubeSettings) {
        return executeUpdateQuery((t) -> {
            return t.merge(lastUsedTubeSettings);
        });
    }

    @Override
    public Operator saveOperator(Operator operator) {
        return executeUpdateQuery((t) -> {
            return t.merge(operator);
        });
    }

    @Override
    public PressureTestCharts savePressureTestChart(PressureTestCharts currentPressureTestChart) {
        return executeUpdateQuery((t) -> {
            return t.merge(currentPressureTestChart);
        });
    }

    @Override
    public PrintersSettings savePrintersSettings(PrintersSettings printersSettings) {
        return executeUpdateQuery((t) -> {
            return t.merge(printersSettings);
        });
    }

    @Override
    public ProgrammSettings saveProgrammSettings(ProgrammSettings programmSettings) {
        return executeUpdateQuery((t) -> {
            return t.merge(programmSettings);
        });
    }

    @Override
    public Shift saveShift(Shift shift) {
        return executeUpdateQuery((t) -> {
            return t.merge(shift);
        });
    }

    @Override
    public ThreadType saveThreadType(ThreadType threadType) {
        return executeUpdateQuery((t) -> {
            return t.merge(threadType);
        });
    }

    @Override
    public Tube saveTube(Tube tube) {
        return executeUpdateQuery((t) -> {
            return em.merge(tube);
        });
    }

    @Override
    public TubeType saveTubeType(TubeType createdTubeType) {
        return executeUpdateQuery((t) -> {
            return t.merge(createdTubeType);
        });
    }

    @Override
    public UnitConfig saveUnitConfig(UnitConfig currentConfig) {
        return executeUpdateQuery((t) -> {
            return t.merge(currentConfig);
        });
    }

    @Override
    public void updateAllRegs(List<InputReg> inputRegs, List<OutputReg> outputRegs, List<ShortReg> shortRegs) {
        EntityManager em = emf.createEntityManager();
        try {
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();

                for (int i = 0; i < inputRegs.size(); ++i) {
                    inputRegs.set(i, em.merge(inputRegs.get(i)));
                }

                for (int i = 0; i < outputRegs.size(); ++i) {
                    outputRegs.set(i, em.merge(outputRegs.get(i)));
                }

                for (int i = 0; i < shortRegs.size(); ++i) {
                    shortRegs.set(i, em.merge(shortRegs.get(i)));
                }

                trans.commit();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (trans.isActive()) {
                    trans.rollback();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Позволяет выполнять запросы не беспокоясь об откате транзакции и закрытии
     * EntityManager.
     *
     * @param <R> тип того, что вернет EntityManager после выполнения запроса.
     * @param gettingManipulation код выполняющий запрос на EntityManager.
     * @return то что вернулось после выполнения на gettingManipulation на
     * EntityManager.
     */
    private synchronized <R> R executeSelectTask(Function<EntityManager, R> gettingManipulation) {
        em = emf.createEntityManager();
        try {
            return gettingManipulation.apply(em);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tube> getTubesByShiftId(Long shiftId) {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getTubesByShiftId", Tube.class)
                    .setParameter("shiftId", shiftId).getResultList();
        });
    }

    /**
     * Метод выполняет рутинные действия по работе с EntityManager.
     * <P>
     * Создает и закрывает EntityManager. Запускает выполняет и откатывает
     * транзакцию, если это необходимо.
     *
     * @param savingManipulation манипуляции с EntityManager связанные
     * непосредственно с обеспечением обеспечением постоянства.
     */
    private synchronized <R> R executeUpdateQuery(Function<EntityManager, R> savingManipulation) {
        R result;
        em = emf.createEntityManager();
        try {
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                result = savingManipulation.apply(em);
                trans.commit();
                return result;
            } finally {
                if (trans.isActive()) {
                    trans.rollback();
                }
            }
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Tube> getTubesOnDevice(Long deviceId) {
        return executeSelectTask((t) -> {
            return t.createNamedQuery("getAllTubesOnDevice", Tube.class)
                    .setParameter("deviceId", deviceId)
                    .getResultList();
        });
    }
}
