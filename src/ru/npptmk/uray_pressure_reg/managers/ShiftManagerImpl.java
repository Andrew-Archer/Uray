package ru.npptmk.uray_pressure_reg.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import ru.npptmk.uray_pressure_reg.model.Operator;
import ru.npptmk.uray_pressure_reg.model.Shift;
import ru.npptmk.uray_pressure_reg.model.ShiftState;

public class ShiftManagerImpl implements ShiftManager {

    private static final Logger LOG = Logger.getLogger(ShiftManagerImpl.class.getName());

    Shift managedShift;

    List<ShiftManagerListener> listeners = new ArrayList<>();

    @Override
    public void addListener(ShiftManagerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void addListeners(List<ShiftManagerListener> listeners) {
        listeners.addAll(listeners);
    }

    @Override
    public Shift endShift() throws Exception {
        if (managedShift.getState().equals(ShiftState.RUNNING)) {
            managedShift.setFinish(new Date());
            managedShift.setState(ShiftState.FINISHED);
            Shift finishedShift = managedShift;
            managedShift = new Shift();
            return finishedShift;
        } else {
            throw new Exception("Попытка завершить смену со статусом \"выполняется\"");
        }
    }

    @Override
    public Shift getShift() {
        return managedShift;
    }

    @Override
    public ShiftState getState() {
        return managedShift.getState();
    }

    @Override
    public void removeListener(ShiftManagerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void removeListeners(List<ShiftManagerListener> listeners) {
        listeners.removeAll(listeners);
    }

    @Override
    public Shift startShift(Map<String, Operator> personal) throws Exception {
        if (managedShift == null) {
           throw new Exception("Попытка начать null смену ");
        }
        if (managedShift.getState().equals(ShiftState.NOT_STARTED)) {
            managedShift.addAllPersonal(personal);
            managedShift.setState(ShiftState.RUNNING);
            return managedShift;
        } else {
            throw new Exception("Попытка начать смену со статусом \"не начата\"");
        }
    }

}
