/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {

    private final Joystick leftJoystick;
    private final Joystick rightJoystick;
    private final double DEADBAND = 0.1;
    
    private final SpeedController _leftDriveMotor1;
    private final SpeedController _leftDriveMotor2;
    private final SpeedController _leftDriveMotor3;
    private final SpeedController _rightDriveMotor1;
    private final SpeedController _rightDriveMotor2;
    private final SpeedController _rightDriveMotor3;
    public double leftDriveMotorOutput;
    public double rightDriveMotorOutput;
    
    private final SpeedController _leftIntakeMotor;
    private final SpeedController _rightIntakeMotor;
    private final double INTAKE_SPEED = 1.0;
    
    public final Encoder rightDriveEncoder;
    public final Encoder leftDriveEncoder;
    
    
    public Drive(Joystick leftStick, Joystick rightStick) {

        this.leftJoystick = leftStick;
        this.rightJoystick = rightStick;
        
        leftDriveEncoder = new Encoder(Addresses.LEFT_DRIVE_ENCODER_CHANNEL_A, Addresses.LEFT_DRIVE_ENCODER_CHANNEL_B);
        rightDriveEncoder = new Encoder(Addresses.RIGHT_DRIVE_ENCODER_CHANNEL_A, Addresses.RIGHT_DRIVE_ENCODER_CHANNEL_B);
        _rightDriveMotor1 = new Victor(Addresses.RIGHT_DRIVE_VICTOR1);
        _rightDriveMotor2 = new Victor(Addresses.RIGHT_DRIVE_VICTOR2);
        _rightDriveMotor3 = new Victor(Addresses.RIGHT_DRIVE_VICTOR3);
        _leftDriveMotor1 = new Victor(Addresses.LEFT_DRIVE_VICTOR1);
        _leftDriveMotor2 = new Victor(Addresses.LEFT_DRIVE_VICTOR2);
        _leftDriveMotor3 = new Victor(Addresses.LEFT_DRIVE_VICTOR3);
        _leftIntakeMotor = new Victor(Addresses.INTAKE_MOTOR_LEFT);
        _rightIntakeMotor = new Victor(Addresses.INTAKE_MOTOR_RIGHT);

    }

    public void runDriveInit() {
        resetDriveEncoders();
        startDriveEncoders();
    }
    
    public void runTankDrive() {
        //runDebug();
        getJoystickY(leftJoystick.getTrigger(), rightJoystick.getTrigger());
        setDeadband();
        setHalfSpeed(leftJoystick.getRawButton(2));
        setAllMotors(setDriveOutputScaling(leftDriveMotorOutput), setDriveOutputScaling(rightDriveMotorOutput));
        runIntakeRollers(rightJoystick.getRawButton(2), rightJoystick.getRawButton(3));
    }

    /**
     * Joysticks are reversed for Driver's preference and adjusting for the Intake Side to be the front
     * 
     * @param reverseLeftButton
     * @param reverseRightButton 
     */
    private void getJoystickY(boolean reverseLeftButton, boolean reverseRightButton) {
        if (reverseLeftButton && reverseRightButton){
            rightDriveMotorOutput = -calculateJoystickLinearOutput(rightJoystick.getY());
            leftDriveMotorOutput = -calculateJoystickLinearOutput(leftJoystick.getY());    
        } else {
            leftDriveMotorOutput = calculateJoystickLinearOutput(rightJoystick.getY());
            rightDriveMotorOutput = calculateJoystickLinearOutput(leftJoystick.getY());
        }
    }
    
    private void setDeadband() {
        if (Math.abs(rightDriveMotorOutput) < DEADBAND) {
            rightDriveMotorOutput = 0.0;
        }
        if (Math.abs(leftDriveMotorOutput) < DEADBAND) {
            leftDriveMotorOutput = 0.0;
        }
    }

    private double setDriveOutputScaling(double input) {
        if (input > 0){
            input = ((input - DEADBAND) / (1 - DEADBAND));
            return input;
        } else if (input < 0) {
            input = ((input + DEADBAND) / (1 - DEADBAND));
            return input;
        } else {
            input = 0;
            return input;
        }
            }

    public void setAllMotors(double leftDriveSpeed, double rightDriveSpeed) {
        setLeftMotors(leftDriveSpeed);
        setRightMotors(-rightDriveSpeed);
    }

    private void setLeftMotors(double speed) {
        _leftDriveMotor1.set(speed);
        _leftDriveMotor2.set(speed);
        _leftDriveMotor3.set(speed);
    }

    private void setRightMotors(double speed) {
        _rightDriveMotor1.set(speed);
        _rightDriveMotor2.set(speed);
        _rightDriveMotor3.set(speed);
    }

    private void setHalfSpeed(boolean halfSpeedButton) {
        if (halfSpeedButton) {
            leftDriveMotorOutput = (leftDriveMotorOutput / 2);
            rightDriveMotorOutput = (rightDriveMotorOutput / 2);
        }
    }

    private void runIntakeRollers(boolean releaseButton, boolean intakeButton) {
        if (releaseButton) {
            setIntakeMotors(INTAKE_SPEED);
        } else if (intakeButton) {
            setIntakeMotors(-INTAKE_SPEED);
        } else {    
            setIntakeMotors(0);
        }
    }

    public void setIntakeMotors(double speed) {
        _leftIntakeMotor.set(speed);
        _rightIntakeMotor.set(speed);
    }

    public double getAvgDriveEncValue() {
        double avgEncoderCount;
        avgEncoderCount = (Math.abs(leftDriveEncoder.get() ) + Math.abs(rightDriveEncoder.get() ) ) / 2;
        return avgEncoderCount;
    }

    public void startDriveEncoders() {
        leftDriveEncoder.start();
        rightDriveEncoder.start();
    }

    public void resetDriveEncoders() {
        leftDriveEncoder.reset();
        rightDriveEncoder.reset();
    }
    
    public double calculateJoystickLinearOutput(double output) {
        double x = output;

        if (x < 0) {
            x *= -1;
            x = (-3.1199 * MathUtils.pow(x, 4) + 4.4664 * MathUtils.pow(x, 3)
                    - 2.2378 * MathUtils.pow(x, 2) - 0.122 * x);
        } else {
            x = (3.1199 * MathUtils.pow(x, 4) - 4.4664 * MathUtils.pow(x, 3)
                    + 2.2378 * MathUtils.pow(x, 2) + 0.122 * x);
        }
        return x;
    }
    
    private void runDebug() {
        SmartDashboard.putNumber("leftDriveOutput", leftDriveMotorOutput);
        SmartDashboard.putNumber("rightDriveOutput", rightDriveMotorOutput);
        SmartDashboard.putNumber("leftDriveEncoder", -leftDriveEncoder.get());
        SmartDashboard.putNumber("rightDriveEncoder", rightDriveEncoder.get());
        
    }
}