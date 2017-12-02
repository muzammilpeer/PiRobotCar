package com.muzammilpeer.robotserver.app;

import com.muzammilpeer.robotserver.thread.BluetoothServer;
import com.muzammilpeer.robotserver.utils.Log4a;

public class MainApp {


    public static void main(String[] args) {
        Log4a.instance.debug("Starting RobotServer");

        final BluetoothServer server = new BluetoothServer();
        server.startServer();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                Log4a.instance.debug("Stopping RobotServer");
                server.stopServer();
            }
        });
    }

}
