package com.muzammilpeer.robotserver.stream;

import java.io.IOException;
import java.io.InputStream;

public abstract class BluetoothInputStream extends InputStream {

    public void readMessage(String message) throws IOException {
        super.read(message.getBytes());
    }
}
