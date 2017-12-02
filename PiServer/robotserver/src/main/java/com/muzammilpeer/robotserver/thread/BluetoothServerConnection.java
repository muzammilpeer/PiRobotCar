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

        byte[] messageByte = new byte[1000];
        int bytesRead = 0;


        try {
            dataOutputStream.write(AppConfig.getInstance().serverWelcomeMessage.getBytes());
            dataOutputStream.flush();
            Log4a.instance.debug("Welcome := " + AppConfig.getInstance().serverWelcomeMessage);

            bytesRead = dataInputStream.read(messageByte);
            String messageRead = new String(messageByte, 0, bytesRead);

            Log4a.instance.debug("Client Information:" + messageRead);
            while (messageRead.equalsIgnoreCase(AppConfig.getInstance().serverClosingMessage) == false) {
                bytesRead = dataInputStream.read(messageByte);
                messageRead = new String(messageByte, 0, bytesRead);
                Log4a.instance.debug("Message Read:" + messageRead);

                dataOutputStream.write(messageRead.getBytes());
                dataOutputStream.flush();
                Log4a.instance.debug("Message Write:" + messageRead);
            }
            dataOutputStream.close();
            dataInputStream.close();
            Log4a.instance.debug("Client shutting down");
        } catch (IOException e) {
            Log4a.instance.debug(e.getMessage());
        }
    }


}
