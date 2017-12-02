package com.muzammilpeer.robotserver.controller;

import com.muzammilpeer.robotserver.enums.CarMovesEnum;
import com.muzammilpeer.robotserver.model.MotorModel;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;

public class MotorController {

    MotorModel leftMotor;
    MotorModel rightMotor;
    GpioController gpio = GpioFactory.getInstance();


    public MotorController() {
//        System.setProperty("pi4j.linking", "dynamic");
        this.leftMotor = new MotorModel(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "p1"), gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "p2"));
        this.rightMotor = new MotorModel(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "p3"), gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "p4"));
    }


    public void execute(CarMovesEnum move) {
        switch (move) {
            case MOVE_FORWARD: {
                leftMotor.moveForward();
                rightMotor.moveForward();
            }
            break;
            case MOVE_BACKWARD: {
                leftMotor.moveBackward();
                rightMotor.moveBackward();
            }
            break;
            case MOVE_LEFT: {
                leftMotor.moveStop();
                rightMotor.moveForward();
            }
            break;
            case MOVE_RIGHT: {
                leftMotor.moveForward();
                rightMotor.moveStop();

            }
            break;
            case MOVE_STOP: {
                leftMotor.moveStop();
                rightMotor.moveStop();
            }
            break;
            default: {
                leftMotor.moveStop();
                rightMotor.moveStop();
            }
            break;
        }

//        try {
//            Thread.sleep(2*1000);
//            leftMotor.moveStop();
//            rightMotor.moveStop();
//        } catch (InterruptedException e) {
//            Log4a.instance.debug(e.getMessage());
//            leftMotor.moveStop();
//            rightMotor.moveStop();
//        }

    }


}
