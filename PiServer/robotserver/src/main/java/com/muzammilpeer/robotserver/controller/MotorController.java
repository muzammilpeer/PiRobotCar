package com.muzammilpeer.robotserver.controller;

import com.muzammilpeer.robotserver.enums.CarMovesEnum;
import com.muzammilpeer.robotserver.model.MotorModel;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;

public class MotorController {

    MotorModel leftMotor;
    MotorModel rightMotor;
    public GpioController gpio = GpioFactory.getInstance();
    CarMovesEnum lastKnownMovingState = CarMovesEnum.MOVE_STOP;

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
            case MOVE_REVERSE: {
                leftMotor.moveReverse();
                rightMotor.moveReverse();
            }
            break;
            case MOVE_FORWARD_LEFT: {
                leftMotor.moveStop();
                rightMotor.moveForward();
            }
            break;
            case MOVE_FORWARD_RIGHT: {
                leftMotor.moveForward();
                rightMotor.moveStop();
            }
            break;
            case MOVE_REVERSE_LEFT: {
                leftMotor.moveStop();
                rightMotor.moveReverse();
            }
            break;
            case MOVE_REVERSE_RIGHT: {
                leftMotor.moveReverse();
                rightMotor.moveStop();
            }
            break;
            case MOVE_STOP: {
                leftMotor.moveStop();
                rightMotor.moveStop();
            }
            break;
            case MOVE_LEFT: {
                if (lastKnownMovingState == CarMovesEnum.MOVE_REVERSE) {
                    leftMotor.moveStop();
                    rightMotor.moveReverse();
                } else {
                    leftMotor.moveStop();
                    rightMotor.moveForward();
                }
            }
            break;
            case MOVE_RIGHT: {
                if (lastKnownMovingState == CarMovesEnum.MOVE_REVERSE) {
                    leftMotor.moveReverse();
                    rightMotor.moveStop();
                } else {
                    leftMotor.moveForward();
                    rightMotor.moveStop();
                }
            }
            break;
            case MOVE_SHUTDOWN: {
                leftMotor.moveStop();
                rightMotor.moveStop();
                gpio.shutdown();
            }
            break;


            default: {
                leftMotor.moveStop();
                rightMotor.moveStop();
            }
            break;
        }

        lastKnownMovingState = move;

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
