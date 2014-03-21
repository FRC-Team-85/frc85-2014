/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Catapult {

    public final DigitalInput camLimitStopLeft = new DigitalInput(Addresses.CAM_LIMIT_STOP_LEFT);
    public final DigitalInput camLimitStopRight = new DigitalInput(Addresses.CAM_LIMIT_STOP_RIGHT);
    public final DigitalInput intakeLimit = new DigitalInput(Addresses.INTAKE_LIMIT);
    
    OperatorPanel operatorPanel;
    
    public final SpeedController _leftCamMotor;
    public final SpeedController _rightCamMotor;
    
    private final Solenoid _armValve;
    private final Solenoid _trussValve;
    
    private final double k_CamMotorSpeed = 1.0;
    private final double k_CamMotorSpeedSlow = 0.60;
    private boolean slowSpeedCheck = false;
    private boolean _firing = false;
    
    public final Encoder camEncoder;
    private int encoderCPR = 360;// needs to be tested
    private int camSlowCount = 100;
    private int camStopCount = 215;
    private double scalingSpeed;
    private double camReleaseSpeed = 0.45;
    public int camEncoderCount;
    
    private boolean startup = true;
    
    public Catapult(OperatorPanel operatorPanel) {

        this.operatorPanel = operatorPanel;
        _leftCamMotor = new Victor(Addresses.CAM_MOTOR_LEFT);
        _rightCamMotor = new Victor(Addresses.CAM_MOTOR_RIGHT);
        _armValve = new Solenoid(Addresses.INTAKE_SOLENOID);
        _trussValve = new Solenoid(Addresses.TRUSS_SOLENOID);
        camEncoder = new Encoder(Addresses.CAM_ENCODER_CHANNEL_A, Addresses.CAM_ENCODER_CHANNEL_B);
    }

    public void runCatapult() {
        runDebug();
        runCatapultLED();
        extendArm();
        setTruss();
        setEncoderDirection();
        runEncoderBasedCatapult(operatorPanel.getCatapultButton(), operatorPanel.getIntakeOverrideSwitch(), operatorPanel.getCamEmergencyStopSwitch());
    }
    
    public void catapultInit() {
        camEncoder.start();
        camEncoder.reset();
    }

    /*public void runCam(boolean fire) {
        if (fire) {
            _firing = true;
            slowSpeedCheck = false;
        } else if (camLimitStopLeft.get()) {
            _firing = false;
        }        
        
        if (camLimitStopRight.get()) {
            slowSpeedCheck = true;
        }
        
        if (_firing && intakeLimit.get()) {
            if (slowSpeedCheck) {
                _leftCamMotor.set(k_CamMotorSpeedSlow);
                _rightCamMotor.set(k_CamMotorSpeedSlow);
            } else {
                _leftCamMotor.set(k_CamMotorSpeed);
                _rightCamMotor.set(k_CamMotorSpeed);
            }
        } else {
            _leftCamMotor.set(0.0);
            _rightCamMotor.set(0.0);
        }
    } */

    public void runEncoderBasedCatapult(boolean fire, boolean intakeOverride, boolean emergencyStopOverride) {
        resetCamEncoder();
        if (!emergencyStopOverride) {
            if (intakeLimit.get() || intakeOverride) {
                if (fire) {
                    _leftCamMotor.set(camReleaseSpeed);
                    _rightCamMotor.set(camReleaseSpeed);
                    //startup = false;
                    _firing = true;
                } else if (_firing) {
                    if (camEncoderCount >= 50 && camEncoderCount <= 210) {
                        _leftCamMotor.set(k_CamMotorSpeedSlow);
                        _rightCamMotor.set(k_CamMotorSpeedSlow);
                    } /*else if (camEncoderCount >= camSlowCount) {
                     _leftCamMotor.set(k_CamMotorSpeedSlow);
                     _rightCamMotor.set(k_CamMotorSpeedSlow);
                     }*/ else if (camEncoderCount > 270 || camEncoderCount < 50) {
                        _leftCamMotor.set(1);
                        _rightCamMotor.set(1);
                    } else {
                        _firing = false;
                        _leftCamMotor.set(0.0);
                        _rightCamMotor.set(0.0);
                    }
                } else {
                    _leftCamMotor.set(0.0);
                    _rightCamMotor.set(0.0);
                }
            } else {
                _leftCamMotor.set(0.0);
                _rightCamMotor.set(0.0);
            }
        } else {
            _leftCamMotor.set(0.0);
            _rightCamMotor.set(0.0);
        }
    }
       
    private double scalingCamSpeed() {    
            scalingSpeed = ((1 - 0.45 * (camStopCount - camSlowCount)) / (camEncoderCount / camStopCount));
            return scalingSpeed;
    }
    
    public void resetCamEncoder() {
        if (camLimitStopRight.get()) {
            camEncoder.reset();
        }
    }
    
    public int setEncoderDirection() {
        camEncoderCount = -camEncoder.get();
        return camEncoderCount;
    }

    private void runCatapultLED() {
        operatorPanel.setFireButtonLED(camEncoderCount >= camStopCount);
        operatorPanel.setCamStopLED(camLimitStopLeft.get());
        operatorPanel.setCamSlowLED(camLimitStopRight.get());
        operatorPanel.setIntakeLED(intakeLimit.get());
        operatorPanel.setTrussLED(operatorPanel.getTrussSwitch());
    }

    public void extendArm() {
        if (operatorPanel.getIntakeArmSwitch()) {
            _armValve.set(true);
        } else if (!_firing){
            _armValve.set(false);
        }
    }

    public void setTruss() {
        if (operatorPanel.getTrussSwitch()) {
            _trussValve.set(true);
        } else {
            _trussValve.set(false);
        }
    }

    public boolean getCamLimitStop() {//ready to fire
        return camLimitStopLeft.get();
    }

    public void setMotors(boolean willfire) {
        if (camLimitStopLeft.get() && !willfire) {
            _leftCamMotor.set(0.0);
            _rightCamMotor.set(0.0);
        } else {
            _leftCamMotor.set(k_CamMotorSpeed);
            _rightCamMotor.set(k_CamMotorSpeed);
        }
    }

    public boolean getArmLimit() {
        return intakeLimit.get();
    }

    public void setArmSolenoid(boolean bool) {
        _armValve.set(bool);
    }

    private void runDebug() {
        SmartDashboard.putBoolean("SlowLimit", camLimitStopRight.get());
        SmartDashboard.putBoolean("StopLimit", camLimitStopLeft.get());
        SmartDashboard.putBoolean("IntakeLimit", intakeLimit.get());
        SmartDashboard.putNumber("CamEncoder", camEncoderCount);
    }
}
