package com.muzammilpeer.robotserver.thread;

import com.muzammilpeer.robotserver.config.AppConfig;
import com.muzammilpeer.robotserver.utils.Log4a;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BluetoothServer extends Thread {

    private ExecutorService threadPool = Executors.newFixedThreadPool(AppConfig.getInstance().threadPoolSize);

    private boolean runningServer = true;
    private StreamConnectionNotifier streamConnectionNotifier = null;
    private StreamConnection streamConnection = null;

    DiscoverableTimerTask discoverableTimerTask = null;
    Timer discoverableTimer = null;

    public BluetoothServer() {
    }

    public void startServerDiscovery() {
        try {
            LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
            discoverableTimer = new Timer();
            discoverableTimerTask = new DiscoverableTimerTask();
            discoverableTimer.schedule(discoverableTimerTask, AppConfig.getInstance().bluetoothDiscoverableThreadDelay, AppConfig.getInstance().bluetoothDiscoverableDuration);
        } catch (BluetoothStateException e) {
            Log4a.instance.debug(e.getMessage());
        }
    }

    public void stopServerDiscovery() {
        if (discoverableTimerTask != null)
            discoverableTimerTask.cancel();

        if (discoverableTimer != null)
            discoverableTimer.cancel();

        discoverableTimer = null;
        discoverableTimerTask = null;
    }


    public boolean isServerRunning() {
        return runningServer;
    }

    public void stopServer() {
        runningServer = false;
        //check discover was on or not then stop it
        if (AppConfig.getInstance().isServerDiscoverable == true) {
            stopServerDiscovery();
        }
        //close server socket
        closeServerSocket();
        this.threadPool.shutdown();
    }

    public void startServer() {
        this.start();
    }

    private void openServerSocket() {
        try {

            LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
            streamConnectionNotifier = (StreamConnectionNotifier) Connector.open(AppConfig.getInstance().serverCompleteUrl);
            //bluetooth discoverable
            if (AppConfig.getInstance().isServerDiscoverable == true) {
                startServerDiscovery();
            }
        } catch (IOException e) {
            Log4a.instance.debug(e.getMessage());
        }
    }

    private void closeServerSocket() {
        try {
            if (streamConnection != null)
                streamConnection.close();

            if (streamConnectionNotifier != null)
                streamConnectionNotifier.close();
        } catch (IOException e) {
            Log4a.instance.debug(e.getMessage());
        }
    }


    @Override
    public synchronized void start() {
        runningServer = true;
        super.start();
    }

    @Override
    public void interrupt() {
        runningServer = false;
        //check discover was on or not then stop it
        if (AppConfig.getInstance().isServerDiscoverable) {
            stopServerDiscovery();
        }
        //close server socket
        closeServerSocket();

        super.interrupt();
    }

    @Override
    public void run() {
        super.run();
        //open Server socket
        openServerSocket();
        System.out.println("Server started");
        Log4a.instance.debug("Server started");

        while (isServerRunning() == true) {
            //check for connection notification
            try {
                Log4a.instance.debug("Waiting for connection...");
                System.out.println("Waiting for connection...");
                streamConnection = streamConnectionNotifier.acceptAndOpen();
                System.out.println("Client Connected");
                Log4a.instance.debug("Client Connected");
                LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
                this.threadPool.execute(new BluetoothServerConnection(this, streamConnection));
            } catch (IOException e) {
                Log4a.instance.debug(e.getMessage());
            }
        }
        System.out.println("Server shutting down");
        Log4a.instance.debug("Server shutting down");
        stopServer();

    }


}
