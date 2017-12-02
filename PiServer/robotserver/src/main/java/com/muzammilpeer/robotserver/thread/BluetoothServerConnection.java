package com.muzammilpeer.robotserver.thread;

import com.muzammilpeer.robotserver.config.AppConfig;
import com.muzammilpeer.robotserver.utils.Log4a;

import javax.microedition.io.StreamConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BluetoothServerConnection implements Runnable {

    public final static int OPEN_COMMAND_CODE = 0xff;
    public final static int CLOSE_COMMAND_CODE = 0xee;


    private BluetoothServer bluetoothServer = null;
    private StreamConnection streamConnection = null;

    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;

    private BluetoothServerConnection() {
    }

    public BluetoothServerConnection(BluetoothServer bluetoothServer, StreamConnection streamConnection) {
        this.bluetoothServer = bluetoothServer;
        this.streamConnection = streamConnection;
    }

    private void openBothStreams() {

        try {
            dataInputStream = new DataInputStream(this.streamConnection.openInputStream());
            dataOutputStream = new DataOutputStream(this.streamConnection.openOutputStream());

        } catch (IOException e) {
            Log4a.instance.debug(e.getMessage());
        }
    }

    public void run() {
        openBothStreams();

        try {
            dataOutputStream.writeUTF(AppConfig.getInstance().serverWelcomeMessage);
            String messageRead = dataInputStream.readUTF();
            while (messageRead.equalsIgnoreCase(AppConfig.getInstance().serverClosingMessage) == false) {
                messageRead = dataInputStream.readUTF();
                dataOutputStream.writeUTF(messageRead);
                dataOutputStream.flush();
                Log4a.instance.debug(messageRead);
            }
            dataOutputStream.close();
            dataInputStream.close();
        } catch (IOException e) {
            Log4a.instance.debug(e.getMessage());
        }
    }


}
