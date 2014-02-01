/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;



public class Intake {
    
        private final Joystick _leftStick;
        private final Joystick _rightStick;
        private final SpeedController _leftIntakeMotor;
        private final SpeedController _rightIntakeMotor;
        private final Solenoid _positionValve;
        
    public Intake(Joystick leftStick, Joystick rightStick) {
        
        this._leftStick = leftStick;
        this._rightStick = rightStick;
        _leftIntakeMotor = new Victor(Addresses.INTAKE_MOTOR_LEFT);
        _rightIntakeMotor = new Victor(Addresses.INTAKE_MOTOR_RIGHT);
        _positionValve = new Solenoid(Addresses.INPUT_POSITION_VALVE);
    }

    private void extend() {
        _positionValve.set(true);
    }

    private void retract() {
        _positionValve.set(false);
    }

    private void setIntakeSpeed(double speed) {
        _leftIntakeMotor.set(speed);
        _rightIntakeMotor.set(speed);
    }
    
    public void runIntakeSystem(){
        
    }
}
