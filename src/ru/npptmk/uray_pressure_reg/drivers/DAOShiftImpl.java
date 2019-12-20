package ru.npptmk.uray_pressure_reg.drivers;

import java.util.Date;
import java.util.List;
import ru.npptmk.uray_pressure_reg.model.Shift;
import ru.npptmk.uray_pressure_reg.model.ShiftState;

public class DAOShiftImpl implements DAOShift {

    public static DAOShiftImpl getDAO(DBExecutor dbExecutor) {
        return new DAOShiftImpl(dbExecutor);
    }

    private final DBExecutor dbExecutor;

    private DAOShiftImpl(DBExecutor dbExecutor) {
        this.dbExecutor = dbExecutor;
    }

    @Override
    public synchronized Shift createNew(Shift shift) {
        return dbExecutor.execWrite((em) -> {
            em.persist(shift);
            return shift;
        });
    }

    @Override
    public synchronized List<Shift> getAll() {
        return dbExecutor.execRead((em) -> {
            return em.createNamedQuery("getAllShifts").getResultList();
        });
    }

    @Override
    public synchronized List<Shift> getByDatePeriod(Date start, Date end) {
        return dbExecutor.execRead((em) -> {
            return em.createNamedQuery("getShiftsByDatePeriod").getResultList();
        });
    }

    @Override
    public synchronized Shift getById(Long id) {
        return dbExecutor.execRead((em) -> {
            return em.find(Shift.class, id);
        });
    }

    @Override
    public synchronized List<Shift> getByState(ShiftState state) {
        return dbExecutor.execRead((em) -> {
            return em.createNamedQuery("getAllShiftsByState")
                    .setParameter("state", state)
                    .getResultList();
        });
    }

    @Override
    public synchronized Shift reread(Shift shift) {
        return dbExecutor.execRead((em) -> {
            return em.find(Shift.class, shift.getId());
        });
    }

    @Override
    public synchronized Shift update(Shift shift) {
        return dbExecutor.execWrite((em) -> {
            return em.merge(shift);
        });
    }

}
