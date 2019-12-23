package ru.npptmk.uray_pressure_reg.drivers;

import java.util.List;
import ru.npptmk.uray_pressure_reg.model.SettingProperty;

public class DAOSettingPropertyImpl implements DAOSettingProperty {

    public static DAOSettingPropertyImpl getDAO(DBExecutor dbExecutor) {
        return new DAOSettingPropertyImpl(dbExecutor);
    }

    private final DBExecutor dbExecutor;

    private DAOSettingPropertyImpl(DBExecutor dbExecutor) {
        this.dbExecutor = dbExecutor;
    }

    @Override
    public synchronized SettingProperty createNew(SettingProperty settingProperty) {
        return dbExecutor.execWrite((em) -> {
            em.persist(settingProperty);
            return settingProperty;
        });
    }

    @Override
    public synchronized List<SettingProperty> getAll() {
        return dbExecutor.execRead((em) -> {
            return em.createNamedQuery("getAllProperties").getResultList();
        });
    }

    @Override
    public synchronized SettingProperty getById(Long id) {
        return dbExecutor.execRead((em) -> {
            return em.find(SettingProperty.class, id);
        });
    }

    @Override
    public synchronized List<SettingProperty> getByName(String name) {
        return dbExecutor.execRead((em) -> {
            List<SettingProperty> propertyValues = em.createNamedQuery("getPropertyByName")
                    .setParameter("name", name)
                    .getResultList();
            if (propertyValues.isEmpty()) {
                SettingProperty sp = new SettingProperty();
                sp.setName(name);
                sp.setVal("");
                sp.setValDefault("");
                propertyValues.add(sp);
            }
            return propertyValues;
        });
    }

    @Override
    public synchronized SettingProperty reread(SettingProperty settingProperty) {
        return dbExecutor.execRead((em) -> {
            return em.find(SettingProperty.class, settingProperty.getId());
        });
    }

    @Override
    public synchronized SettingProperty update(SettingProperty settingProperty) {
        return dbExecutor.execWrite((em) -> {
            return em.merge(settingProperty);
        });
    }

}
