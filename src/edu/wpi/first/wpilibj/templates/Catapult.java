/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Catapult {

    OperatorPanel operatorPanel;
    
    public final DigitalInput camLimitStopLeft;
    public final DigitalInput camLimitReset;
    public final DigitalInput intakeLimit;
    
    public final SpeedController _leftCamMotor;
    public final SpeedController _rightCamMotor;
    
    public final Encoder camEncoder;
    public int camEncoderCount;
    
    private final Solenoid _armValve;
    private final Solenoid _trussValve;
    
    private final double CAM_RELEASE_SPEED = 0.45;
    private final double CAM_FAST_SPEED = 1.0;
    private final double CAM_SLOW_SPEED = 1.0;
    private boolean firing = false;
    
    
    public Catapult(OperatorPanel operatorPanel) {
        this.operatorPanel = operatorPanel;
        _leftCamMotor = new Victor(Addresses.CAM_MOTOR_LEFT);
        _rightCamMotor = new Victor(Addresses.CAM_MOTOR_RIGHT);
        camLimitStopLeft = new DigitalInput(Addresses.CAM_LIMIT_STOP_LEFT);
        camLimitReset = new DigitalInput(Addresses.CAM_LIMIT_STOP_RIGHT);
        intakeLimit = new DigitalInput(Addresses.INTAKE_LIMITSWITCH);
        _armValve = new Solenoid(Addresses.INTAKE_SOLENOID);
        _trussValve = new Solenoid(Addresses.TRUSS_SOLENOID);
        camEncoder = new Encoder(Addresses.CAM_ENCODER_CHANNEL_A, Addresses.CAM_ENCODER_CHANNEL_B);
    }

    
    public void runCatapult() {
        //runDebug();
        runCatapultLED();
        runIntakeArm();
        runTruss();
        runEncoderBasedCatapult(operatorPanel.getCatapultButton(), operatorPanel.getIntakeOverrideSwitch(), operatorPanel.getCamEmergencyStopSwitch());
    }
    
    public void catapultInit() {
        camEncoder.start();
        camEncoder.reset();
    }

    public void resetFiring() {
        firing = false;
    }
    public void runEncoderBasedCatapult(boolean fire, boolean intakeOverride, boolean emergencyStopOverride) {
        resetCamEncoder();
        setEncoderDirection();
        if (!emergencyStopOverride) {
            if (intakeLimit.get() || intakeOverride) {
                if (fire) {
                    setCamMotors(CAM_RELEASE_SPEED);
                    firing = true;
                } else if (firing) {
                    if (camEncoderCount >= 50 && camEncoderCount <= 205) {
                        setCamMotors(CAM_SLOW_SPEED);
                    } else if (camEncoderCount > 250 || camEncoderCount < 50) {
                        setCamMotors(CAM_FAST_SPEED);
                    } else {
                        firing = false;
                        setCamMotors(0);
                    }
                } else {
                    setCamMotors(0);
                }
            } else {
                setCamMotors(0);
            }
        } else {
            setCamMotors(0);
        }
    }
       
    public boolean isFiring() {
        return firing;
    }
    
    public void setCamMotors(double speed) {
        _leftCamMotor.set(speed);
        _rightCamMotor.set(speed);
    }
        
    public void resetCamEncoder() {
        if (camLimitReset.get()) {
            camEncoder.reset();
        }
    }
    
    public int setEncoderDirection() {
        camEncoderCount = -camEncoder.get();
        return camEncoderCount;
    }

    public void runIntakeArm() {
        if (operatorPanel.getIntakeArmSwitch()) {
            _armValve.set(true);
        } else if (!firing){
            _armValve.set(false);
        }
    }
    
    public boolean getArmLimit() {
        return intakeLimit.get();
    }

    public void setArmSolenoid(boolean toggle) {
        _armValve.set(toggle);
    }

    public void runTruss() {
        if (operatorPanel.getTrussSwitch()) {
            _trussValve.set(true);
        } else {
            _trussValve.set(false);
        }
    }
    
    public void setTrussPostition(boolean toggle) {
        _trussValve.set(toggle);
    }
    
    private void runCatapultLED() {
        operatorPanel.setFireButtonLED(!firing);
        //operatorPanel.setCamStopLED(camLimitStopLeft.get());
        //operatorPanel.setCamSlowLED(camLimitReset.get());
        //operatorPanel.setIntakeLED(intakeLimit.get());
        //operatorPanel.setTrussLED(operatorPanel.getTrussSwitch());
    }

    private void runDebug() {
        SmartDashboard.putBoolean("SlowLimit", camLimitReset.get());
        SmartDashboard.putBoolean("StopLimit", camLimitStopLeft.get());
        SmartDashboard.putBoolean("IntakeLimit", intakeLimit.get());
        SmartDashboard.putNumber("CamEncoder", camEncoderCount);
    }
}
