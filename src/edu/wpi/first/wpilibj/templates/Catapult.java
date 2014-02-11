/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Catapult {

    private final DigitalInput limitswitch = new DigitalInput(Addresses.CAM_LIMITSWITCH);
    
    private final Joystick _leftStick;
    private final Joystick _rightStick;
    private final Joystick opStick;
    private final SpeedController _leftCamMotor;
    private final SpeedController _rightCamMotor;
    private final Solenoid _armValve;
    private final Solenoid _trussValve;
    
    private final double k_CamMotorSpeed = .65;

    
    public Catapult(Joystick leftStick, Joystick rightStick, Joystick opStick) {
        
        this.opStick = opStick;
        this._leftStick = leftStick;
        this._rightStick = rightStick;
        _leftCamMotor = new Victor(Addresses.CAM_MOTOR_LEFT);
        _rightCamMotor = new Victor(Addresses.CAM_MOTOR_RIGHT);
        _armValve = new Solenoid(Addresses.LEFT_SOLENOID);
        _trussValve = new Solenoid(Addresses.RIGHT_SOLENOID);
    }

    public void runCatapult(){
        runCam(_rightStick.getRawButton(5));
        extendArm();
        setTruss();
    }
    
    public void runCam(boolean fire) {
        if(/*limitswitch.get() && */!fire)/**Button subject to change**/ {
            _leftCamMotor.set(0.0);
            _rightCamMotor.set(0.0);
        } else {
            _leftCamMotor.set(k_CamMotorSpeed);
            _rightCamMotor.set(k_CamMotorSpeed);
        }
    }
    public void extendArm(){
        if(_rightStick.getRawButton(2)){
            _armValve.set(true);
        } else {
            _armValve.set(false);
        }
    }
    public void setTruss() {
        if(_rightStick.getRawButton(3)) {
            _trussValve.set(true);
        } else {
            _trussValve.set(false);
        }
    }
    
}