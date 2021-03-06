package ru.npptmk.uray_pressure_reg.drivers;

import java.util.Date;
import java.util.List;
import ru.npptmk.uray_pressure_reg.model.Pipe;
import ru.npptmk.uray_pressure_reg.model.Pipe.Locations;
import ru.npptmk.uray_pressure_reg.model.Shift;

public class DAOPipeImpl implements DAOPipe {

    public static DAOPipeImpl getDAO(DBExecutor dbExecutor) {
        return new DAOPipeImpl(dbExecutor);
    }

    private final DBExecutor dbExecutor;

    private DAOPipeImpl(DBExecutor dbExecutor) {
        this.dbExecutor = dbExecutor;
    }

    @Override
    public synchronized Pipe createNew(Pipe pipe) {
        return dbExecutor.execWrite((em) -> {
            em.persist(pipe);
            return pipe;
        });
    }

    @Override
    public synchronized List<Pipe> getAll() {
        return dbExecutor.execRead((em) -> {
            return em.createNamedQuery("getAllPipes").getResultList();
        });
    }

    @Override
    public synchronized List<Pipe> getByDatePeriod(Date start, Date end) {
        return dbExecutor.execRead((em) -> {
            return em.createNamedQuery("getPipesByDatePeriod")
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();
        });
    }

    @Override
    public synchronized Pipe getById(Long id) {
        return dbExecutor.execRead((em) -> {
            return em.find(Pipe.class, id);
        });
    }

    @Override
    public List<Pipe> getByLocationID(Locations location) {
        return dbExecutor.execRead((em) -> {
            return em.createNamedQuery("getPipesByLocation")
                    .setParameter("location", location)
                    .getResultList();
        });
    }

    @Override
    public synchronized List<Pipe> getByShiftId(Shift shift) {
        return dbExecutor.execRead((em) -> {
            return em.createNamedQuery("getPipesByShiftId")
                    .setParameter("shiftId", shift.getId())
                    .getResultList();
        });
    }

    @Override
    public synchronized Pipe reread(Pipe pipe) {
        if (pipe.getId() != null) {
            return null;
        }
        return dbExecutor.execRead((em) -> {
            return em.find(Pipe.class, pipe.getId());
        });
    }

    @Override
    public synchronized Pipe update(Pipe pipe) {
        return dbExecutor.execWrite((em) -> {
            return em.merge(pipe);
        });
    }

}
