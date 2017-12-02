package com.muzammilpeer.robotserver.stream;

import java.io.IOException;
import java.io.OutputStream;

public abstract class BluetoothOutputStream extends OutputStream {

    public void writeMessage(String message) throws IOException {
        super.write(message.getBytes());
    }
}
