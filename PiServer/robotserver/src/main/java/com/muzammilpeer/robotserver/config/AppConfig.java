package com.muzammilpeer.robotserver.config;

import javax.bluetooth.UUID;

public class AppConfig {
    private static AppConfig ourInstance = new AppConfig();

    public static AppConfig getInstance() {
        return ourInstance;
    }

    private AppConfig() {
    }

    UUID serverUUID = new UUID("0000110100001000800000805F9B34FB", false);
    String serverName = "robotserver";
    String hostname = "localhost";

    public String serverCompleteUrl = "btspp://" + hostname + ":" + serverUUID.toString() + ";name=" + serverName;

    public long bluetoothDiscoverableDuration = 179000;
    public long bluetoothDiscoverableThreadDelay = 0;

    public boolean isServerDiscoverable = true;

    public int threadPoolSize = 4;

    public String serverWelcomeMessage = "welcome to robot controller server";
    public String serverClosingMessage = "stopServer";

    public int motorMoveDuration = 1;


}
