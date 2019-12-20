package ru.npptmk.uray_pressure_reg.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import ru.npptmk.uray_pressure_reg.drivers.DAOShift;
import ru.npptmk.uray_pressure_reg.drivers.DAOShiftImpl;
import ru.npptmk.uray_pressure_reg.drivers.DBExecutor;
import ru.npptmk.uray_pressure_reg.model.Operator;
import ru.npptmk.uray_pressure_reg.model.Shift;
import ru.npptmk.uray_pressure_reg.model.ShiftState;

public class ShiftManagerImpl implements ShiftManager {

    private final DAOShift shiftDAO;

    private static final Logger LOG = Logger.getLogger(ShiftManagerImpl.class.getName());

    Shift managedShift;

    List<ShiftManagerListener> listeners = new ArrayList<>();

    public ShiftManagerImpl(DBExecutor dBExecutor) {
        this.shiftDAO = DAOShiftImpl.getDAO(dBExecutor);
        //Получаем все незавершенные смены
        List<Shift> runningShifts = shiftDAO.getByState(ShiftState.RUNNING);
        //Получаем все неначатые ссмены
        List<Shift> unstartedShifts = shiftDAO.getByState(ShiftState.NOT_STARTED);
        //Если начатые смены не нашлись, создаем новую
        if (runningShifts.isEmpty()) {
            //Если не нашлось неначатых смен тоже
            if (unstartedShifts.isEmpty()) {
                managedShift = new Shift();
                managedShift = shiftDAO.createNew(managedShift);
            } else {
                managedShift = unstartedShifts.get(0);
            }
        } else {//Если смены нашлись, то берем первую незавершенную
            managedShift = runningShifts.get(0);
        }
    }

    @Override
    public void addListener(ShiftManagerListener listener) {
        listeners.add(listener);
        listener.doOnShiftChaned(managedShift);
    }

    @Override
    public void addListeners(List<ShiftManagerListener> listeners) {
        listeners.addAll(listeners);
        notifyListeners();
    }

    @Override
    public synchronized Shift endShift() throws Exception {
        if (managedShift.getState().equals(ShiftState.RUNNING)) {
            managedShift.setFinish(new Date());
            managedShift.setState(ShiftState.FINISHED);
            managedShift = shiftDAO.update(managedShift);
            Shift finishedShift = managedShift;
            managedShift = managedShift = new Shift();
            shiftDAO.createNew(managedShift);
            notifyListeners();
            return finishedShift;
        } else {
            throw new Exception("Попытка завершить смену со статусом \"выполняется\"");
        }
    }

    private void notifyListeners() {
        listeners.forEach((listener) -> {
            listener.doOnShiftChaned(managedShift);
        });
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
    public synchronized Shift startShift(Map<String, Operator> personal) throws Exception {
        if (managedShift == null) {
            LOG.severe("Попытка начать смену null создана новая смена.");
            managedShift = new Shift();
        }
        if (managedShift.getState().equals(ShiftState.NOT_STARTED)) {
            managedShift.setBeginning(new Date());
            managedShift.addAllPersonal(personal);
            managedShift.setState(ShiftState.RUNNING);
            managedShift = shiftDAO.update(managedShift);
            notifyListeners();
            return managedShift;
        } else {
            throw new Exception("Попытка начать смену со статусом \"не начата\"");
        }
    }

}
