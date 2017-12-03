package com.muzammilpeer.robotserver.thread;

import com.muzammilpeer.robotserver.config.AppConfig;
import com.muzammilpeer.robotserver.controller.MotorController;
import com.muzammilpeer.robotserver.enums.CarMovesEnum;
import com.muzammilpeer.robotserver.utils.Log4a;

import javax.microedition.io.StreamConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BluetoothServerConnection implements Runnable {

    private BluetoothServer bluetoothServer = null;
    private StreamConnection streamConnection = null;

    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;

    MotorController motorController = new MotorController();

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

                if (messageRead.contains("forward")) {
                    motorController.execute(CarMovesEnum.MOVE_FORWARD);
                    Log4a.instance.debug("MOVE_FORWARD:");
                } else if (messageRead.contains("reverse")) {
                    motorController.execute(CarMovesEnum.MOVE_REVERSE);
                    Log4a.instance.debug("MOVE_REVERSE:");
                } else if (messageRead.contains("forward_left")) {
                    motorController.execute(CarMovesEnum.MOVE_FORWARD_LEFT);
                    Log4a.instance.debug("MOVE_FORWARD_LEFT:");
                } else if (messageRead.contains("forward_right")) {
                    motorController.execute(CarMovesEnum.MOVE_FORWARD_RIGHT);
                    Log4a.instance.debug("MOVE_FORWARD_RIGHT:");
                } else if (messageRead.contains("reverse_left")) {
                    motorController.execute(CarMovesEnum.MOVE_REVERSE_LEFT);
                    Log4a.instance.debug("MOVE_REVERSE_LEFT:");
                } else if (messageRead.contains("reverse_right")) {
                    motorController.execute(CarMovesEnum.MOVE_REVERSE_RIGHT);
                    Log4a.instance.debug("MOVE_REVERSE_RIGHT:");
                } else if (messageRead.contains("stop")) {
                    motorController.execute(CarMovesEnum.MOVE_STOP);
                    Log4a.instance.debug("MOVE_STOP:");
                }

                dataOutputStream.write(messageRead.getBytes());
                dataOutputStream.flush();
                Log4a.instance.debug("Message Write:" + messageRead);
            }

            motorController.execute(CarMovesEnum.MOVE_SHUTDOWN);
            dataOutputStream.close();
            dataInputStream.close();
            Log4a.instance.debug("Client shutting down");
        } catch (IOException e) {
            motorController.execute(CarMovesEnum.MOVE_SHUTDOWN);
            Log4a.instance.debug(e.getMessage());
        }

    }


}
