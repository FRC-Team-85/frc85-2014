/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Catapult {

    private final Joystick _leftStick;
    private final Joystick _rightStick;
    //private final SpeedController _leftCamMotor;
    //private final SpeedController _rightCamMotor;
    //private final Solenoid _positionValve;
    //private final Encoder _camPosition;
    private final Relay _airCompressor;
    
    private boolean isFiring = false;
    
    private final int k_CompleteCamCount = 3600;
    private final double k_CamMotorSpeed = .5;

    
    public Catapult(Joystick leftStick, Joystick rightStick) {
        
        this._leftStick = leftStick;
        this._rightStick = rightStick;
        //_leftCamMotor = new Victor(Addresses.CAM_MOTOR_LEFT);
        //_rightCamMotor = new Victor(Addresses.CAM_MOTOR_RIGHT);
        //_positionValve = new Solenoid(Addresses.CATAPULT_POSITION_VALVE);
        //_camPosition = new Encoder(Addresses.CAM_ENCODER_CHANNEL_A, Addresses.CAM_ENCODER_CHANNEL_B);
        _airCompressor = new Relay(Addresses.AIR_COMPRESSOR);
    }
/*
    private void trussPosition() {
        _positionValve.set(true);
    }

    private void shootingPosition() {
        _positionValve.set(false);
    }

    private void fireCatapult() {
        isFiring = true;
    }
*/
    public void runCatapult() {
        
        runAirCompressor();
        /*if (isFiring) {
            if (_camPosition.get() >= k_CompleteCamCount) {
                isFiring = false;
                _leftCamMotor.set(0);
                _rightCamMotor.set(0);
                _camPosition.reset();
            } else {
                _leftCamMotor.set(k_CamMotorSpeed);
                _rightCamMotor.set(k_CamMotorSpeed);
            }
        }*/
       
    }
    
    private void runAirCompressor() {
        if (_leftStick.getRawButton(3)) {
            _airCompressor.set(Relay.Value.kOn);
        }
        if (_leftStick.getRawButton(2)) {
            _airCompressor.set(Relay.Value.kOff);
        }
    }
    
    
}
