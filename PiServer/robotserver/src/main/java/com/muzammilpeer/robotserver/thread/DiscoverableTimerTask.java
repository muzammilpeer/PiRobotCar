package com.muzammilpeer.robotserver.thread;

import com.muzammilpeer.robotserver.utils.Log4a;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import java.util.TimerTask;

public class DiscoverableTimerTask extends TimerTask {


    public DiscoverableTimerTask() {
    }


    public void run() {
        try {
            LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.NOT_DISCOVERABLE);
            LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
        } catch (BluetoothStateException e) {
            Log4a.instance.debug(e.getMessage());
        }
    }

    @Override
    public boolean cancel() {
//        try {
//            LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.NOT_DISCOVERABLE);
//        } catch (BluetoothStateException e) {
//            Log4a.instance.debug(e.getMessage());
//        }

        return super.cancel();
    }
}
