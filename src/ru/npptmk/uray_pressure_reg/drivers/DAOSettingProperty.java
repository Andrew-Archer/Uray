package ru.npptmk.uray_pressure_reg.drivers;

import java.util.List;
import ru.npptmk.uray_pressure_reg.model.SettingProperty;

/**
 *
 * @author RazumnovAA
 */
public interface DAOSettingProperty {

    public List<SettingProperty> getAll();

    public List<SettingProperty> getByName(String name);

    public SettingProperty getById(Long id);

    public SettingProperty reread(SettingProperty settingProperty);

    public SettingProperty createNew(SettingProperty settingProperty);

    public SettingProperty update(SettingProperty settingProperty);
}
