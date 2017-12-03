package com.muzammilpeer.robotserver.model;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;

public class MotorModel {

    private GpioPinPwmOutput pintPWM;
    private GpioPinDigitalOutput pin1;
    private GpioPinDigitalOutput pin2;

    private MotorModel() {
    }

    public MotorModel(GpioPinPwmOutput pintPWM, GpioPinDigitalOutput pin1, GpioPinDigitalOutput pin2) {
        this.pintPWM = pintPWM;
        this.pin1 = pin1;
        this.pin2 = pin2;

        this.pintPWM.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        this.pin1.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        this.pin2.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);

    }

    public MotorModel(GpioPinDigitalOutput pin1, GpioPinDigitalOutput pin2) {
        this.pin1 = pin1;
        this.pin2 = pin2;
        this.pin1.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        this.pin2.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
    }

    public void moveForward() {
        pin1.high();
        pin2.low();
    }

    public void moveReverse() {
        pin1.low();
        pin2.high();
    }

    public void moveStop() {
        pin1.low();
        pin2.low();
    }
}
