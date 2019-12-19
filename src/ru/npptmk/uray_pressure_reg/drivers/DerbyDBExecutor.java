package ru.npptmk.uray_pressure_reg.drivers;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DerbyDBExecutor implements DBExecutor {

    private static final Logger LOG = Logger.getLogger(DerbyDBExecutor.class.getName());

    public static DerbyDBExecutor getExecutor(String persistenceUnitName) {
        return new DerbyDBExecutor(persistenceUnitName);
    }

    private final EntityManagerFactory emf;
    private EntityManager em;

    /**
     *
     * @param persistenceUnitName
     */
    private DerbyDBExecutor(String persistenceUnitName) {
        this.emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Override
    public <R> R execRead(Function<EntityManager, R> readLogic) {
        R result = null;
        em = emf.createEntityManager();
        try {
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                result = readLogic.apply(em);
                trans.commit();
                return result;
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Не получилось выполнить транзакцию.", ex);
                return null;
            } finally {
                if (trans != null) {
                    if (trans.isActive()) {
                        trans.rollback();
                    }
                }
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Не получилось получить транзакцию.", ex);
            return null;
        } finally {
            if (em != null) {
                if (em.isOpen()) {
                    em.close();
                }
            }
        }
    }

    @Override
    public <R> R execWrite(Function<EntityManager, R> writeLogic) {
        R result = null;
        em = emf.createEntityManager();
        try {
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                result = writeLogic.apply(em);
                trans.commit();
                return result;
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Не получилось выполнить транзакцию.", ex);
                return null;
            } finally {
                if (trans != null) {
                    if (trans.isActive()) {
                        trans.rollback();
                    }
                }
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Не получилось получить транзакцию.", ex);
            return null;
        } finally {
            if (em != null) {
                if (em.isOpen()) {
                    em.close();
                }
            }
        }
    }

}
