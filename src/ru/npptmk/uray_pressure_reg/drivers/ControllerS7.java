package ru.npptmk.uray_pressure_reg.drivers;

import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.procimg.SimpleRegister;
import com.ghgande.j2mod.modbus.util.BitVector;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import static ru.npptmk.tubescontrollsystem.drivers.INPUTS_LIST.*;
import static ru.npptmk.tubescontrollsystem.drivers.SHORTS_LIST.*;
import ru.npptmk.tubescontrollsystem.drivers.TIAPortalAddress.RegTypes;

public class ControllerS7 implements Controller {

    private Boolean isConnected;

    private final int firstInputAddress = TUBE_BEFORE_STAND.getModbusAddress();
    private final int inputsToReadCount = 8;
    private final int firstShortRegAddress = 0;
    private final int shortRegsCount = 8;
    private final Object threadLock;
    private BitVector inPutsValues = new BitVector(inputsToReadCount);
    private Register[] shortRegsValues = new Register[shortRegsCount];

    private Map<TIAPortalAddress, Integer> inputsIndex;
    private Map<TIAPortalAddress, Integer> shortRegsIndex;
    private String controllerAddress;
    private int port;
    private Thread tCPMasterThread;
    private List<ControllerStateListener> listeners;

    private ModbusTCPMaster modbusTCPMaster;

    /**
     * Конструктор по умолчанию устанавливает адрес для опроса контроллера
     * 192.168.0.240, а порт 502.
     *
     */
    public ControllerS7() {
        this("192.168.0.241", 502);
        this.isConnected = false;
    }

    /**
     *
     * @param controllerAddress IP адрес контролера
     * @param port
     */
    public ControllerS7(String controllerAddress, int port) {
        this.isConnected = false;
        listeners = new ArrayList<>();
        this.threadLock = new Object();
        this.controllerAddress = controllerAddress;
        this.port = port;
        inputsIndex = new Hashtable<>();
        shortRegsIndex = new Hashtable<>();
        modbusTCPMaster = new ModbusTCPMaster(controllerAddress, port);
        inputsIndex.put(TUBE_BEFORE_STAND, 0);
        inputsIndex.put(TUBE_AFTER_PRESSURE_TEST, 1);
        inputsIndex.put(TUBE_AFTER_STAND, 3);

        shortRegsIndex.put(ERROR, 0);
        shortRegsIndex.put(MOVE_TO_STAND_STATE, 1);
        shortRegsIndex.put(MOVE_FROM_STAND_STATE, 2);
        shortRegsIndex.put(CMD, 3);

        for (int i = 0; i < 8; i++) {
            shortRegsValues[i] = new SimpleRegister(0);
        }
    }

    @Override
    public void addConnectionStateListener(ControllerStateListener listener) {
        listeners.add(listener);
        listener.onControllerChanedState(isConnected);
    }

    @Override
    public <T> T getRegisterValue(Integer byteNumber, Integer bitNumber, RegTypes regType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T getRegisterValue(TIAPortalAddress address) {
        switch (address.getType()) {
            case INPUTS:
                return (T) Boolean.valueOf(inPutsValues.getBit(inputsIndex.get(address)));
            case SHORT:
                return (T) Integer.valueOf(shortRegsValues[shortRegsIndex.get(address)].getValue());
        }
        return null;
    }

    @Override
    public <T> void setRegisterValue(Integer byteNumber, Integer bitNumber, T value, RegTypes regType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> void setRegisterValue(TIAPortalAddress address, int value) {
        switch (address.getType()) {
            case SHORT:
                shortRegsValues[shortRegsIndex.get(address)]
                        .setValue(value);
                try {
                    modbusTCPMaster.writeMultipleRegisters(0, shortRegsValues);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }

    private void notifyAllListenersOfState(Boolean isConnected) {
        listeners.forEach((listener) -> {
            listener.onControllerChanedState(isConnected);
        });
    }

    @Override
    public void start() {
        if (modbusTCPMaster == null) {
            modbusTCPMaster = new ModbusTCPMaster(controllerAddress, port);
        }

        if (tCPMasterThread != null) {
            tCPMasterThread.interrupt();
        }
        tCPMasterThread = new Thread(() -> {
            synchronized (threadLock) {
                try {
                    modbusTCPMaster.connect();
                    isConnected = Boolean.TRUE;
                    notifyAllListenersOfState(isConnected);
                    while (true) {
                        //Читаем из контроллера
                        inPutsValues = modbusTCPMaster
                                .readInputDiscretes(firstInputAddress, 8);
                        shortRegsValues = modbusTCPMaster
                                .readMultipleRegisters(firstShortRegAddress, 4);
                        threadLock.wait(200);
                    }
                } catch (Exception ex) {
                    modbusTCPMaster.disconnect();
                    isConnected = Boolean.FALSE;
                    notifyAllListenersOfState(isConnected);
                    ex.printStackTrace();
                }
            }
        });
        tCPMasterThread.start();

    }

    @Override
    public void stop() {
        try {
            if (!modbusTCPMaster.isReconnecting()) {
                modbusTCPMaster.disconnect();
                isConnected = Boolean.FALSE;
                notifyAllListenersOfState(isConnected);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
