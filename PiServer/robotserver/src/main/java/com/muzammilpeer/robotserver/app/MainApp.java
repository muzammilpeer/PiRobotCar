package com.muzammilpeer.robotserver.app;

import com.muzammilpeer.robotserver.thread.BluetoothServer;
import com.muzammilpeer.robotserver.utils.Log4a;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class MainApp {

    public MainApp() {
    }

    public static void main(String[] args) {
//
//        try {
//
//            final GpioController gpio = GpioFactory.getInstance();
//            final GpioPinDigitalOutput pinA = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "PinA");
//            final GpioPinDigitalOutput pinB = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "PinB");
//
//            final GpioPinDigitalOutput pinC = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "PinA");
//            final GpioPinDigitalOutput pinD = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "PinB");
//
//            System.out.println("rotate motor clockwise for 3 seconds");
//            pinA.high();
//            pinB.low();
//
//            pinC.high();
//            pinD.low();
//
//            // wait 3 seconds
//            Thread.sleep(3000);
//            System.out.println("rotate motor in oposite derection for 6 seconds");
//            pinA.low();
//            pinB.high();
//
//            pinC.low();
//            pinD.high();
//
//
//            // wait 6 seconds
//            Thread.sleep(6000);
//            // stop motor
//            System.out.println("Stopping motor");
//            pinA.low();
//            pinB.low();
//            pinC.low();
//            pinD.low();
//
//            gpio.shutdown();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


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
