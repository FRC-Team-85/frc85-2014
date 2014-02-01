/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Catapult {

    private final SpeedController _leftCamMotor;
    private final SpeedController _rightCamMotor;
    private final Solenoid _positionValve;
    private final Encoder _camPosition;
    private final Joystick _leftStick;
    private final Joystick _rightStick;
    
    private boolean isFiring = false;
    
    private final int k_CompleteCamCount = 5000;
    private final double k_CamMotorSpeed = 1;

    
    public Catapult(Joystick leftStick, Joystick rightStick) {
        
        this._leftStick = leftStick;
        this._rightStick = rightStick;
        _leftCamMotor = new Victor(Addresses.CAM_MOTOR_LEFT);
        _rightCamMotor = new Victor(Addresses.CAM_MOTOR_RIGHT);
        _positionValve = new Solenoid(Addresses.CATAPULT_POSITION_VALVE);
        _camPosition = new Encoder(Addresses.CAM_ENCODER_CHANNEL_A, Addresses.CAM_ENCODER_CHANNEL_B);
    }

    public void trussPosition() {
        _positionValve.set(true);
    }

    public void shootingPosition() {
        _positionValve.set(false);
    }

    public void fireCatapult() {
        isFiring = true;
    }

    public void runCatapult() {
        if (isFiring) {
            if (_camPosition.get() >= k_CompleteCamCount) {
                isFiring = false;
                _leftCamMotor.set(0);
                _rightCamMotor.set(0);
                _camPosition.reset();
            } else {
                _leftCamMotor.set(k_CamMotorSpeed);
                _rightCamMotor.set(k_CamMotorSpeed);
            }
        }
    }
}
