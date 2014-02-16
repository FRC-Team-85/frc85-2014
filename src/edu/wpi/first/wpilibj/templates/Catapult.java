/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Catapult {

    public final DigitalInput camLimitStop = new DigitalInput(Addresses.CAM_LIMIT_STOP);
    public final DigitalInput camLimitSlow = new DigitalInput(Addresses.CAM_LIMIT_SLOW);
    public final DigitalInput intakeLimit = new DigitalInput(Addresses.INTAKE_LIMIT);
    
    OperatorPanel operatorPanel;
    
    private final SpeedController _leftCamMotor;
    private final SpeedController _rightCamMotor;
    
    private final Solenoid _armValve;
    private final Solenoid _trussValve;
    
    private final double k_CamMotorSpeed = 0.75;
    private final double k_CamMotorSpeedSlow = 0.6;
    
    public Catapult(OperatorPanel operatorPanel) {

        this.operatorPanel = operatorPanel;
        _leftCamMotor = new Victor(Addresses.CAM_MOTOR_LEFT);
        _rightCamMotor = new Victor(Addresses.CAM_MOTOR_RIGHT);
        _armValve = new Solenoid(Addresses.INTAKE_SOLENOID);
        _trussValve = new Solenoid(Addresses.TRUSS_SOLENOID);
    }

    public void runCatapult() {
        runDebug();
        runCatapultLED();
        extendArm();
        setTruss();
        runCam(operatorPanel.getCatapultButton());
    }

    public void runCam(boolean fire) {
        if (intakeLimit.get()) {
            if (camLimitStop.get() && !fire) {
                _leftCamMotor.set(0.0);
                _rightCamMotor.set(0.0);
            } else if (camLimitSlow.get()) {
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
    }

    private void runCatapultLED() {
        operatorPanel.setFireButtonLED(operatorPanel.getCatapultButton());
        operatorPanel.setCamStopLED(camLimitStop.get());
        operatorPanel.setCamSlowLED(camLimitSlow.get());
        operatorPanel.setIntakeLED(intakeLimit.get());
        operatorPanel.setTrussLED(operatorPanel.getTrussSwitch());
    }

    public void extendArm() {
        if (operatorPanel.getIntakeArmSwitch()) {
            _armValve.set(true);
        } else {
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
        return camLimitStop.get();
    }

    public void setMotors(boolean willfire) {
        if (camLimitStop.get() && !willfire) {
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
        SmartDashboard.putBoolean("SlowLimit", camLimitSlow.get());
        SmartDashboard.putBoolean("StopLimit", camLimitStop.get());
        SmartDashboard.putBoolean("IntakeLimit", intakeLimit.get());
    }
}
