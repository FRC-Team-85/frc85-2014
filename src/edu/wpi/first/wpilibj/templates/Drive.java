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

    private final SpeedController _leftDriveMotor1;
    private final SpeedController _leftDriveMotor2;
    private final SpeedController _leftDriveMotor3;
    private final SpeedController _rightDriveMotor1;
    private final SpeedController _rightDriveMotor2;
    private final SpeedController _rightDriveMotor3;
    private final Encoder rightDriveEncoder;
    private final Encoder leftDriveEncoder;
    private final Joystick _leftStick;
    private final Joystick _rightStick;
    private double leftDriveMotorOutput;
    private double rightDriveMotorOutput;
    private final double k_Deadband = 0.2;

    public Drive(Joystick leftStick, Joystick rightStick) {

        this._leftStick = leftStick;
        this._rightStick = rightStick;
        
        rightDriveEncoder = new Encoder(Addresses.RIGHT_DRIVE_ENCODER_CHANNEL_A, Addresses.RIGHT_DRIVE_ENCODER_CHANNEL_B);
        leftDriveEncoder = new Encoder(Addresses.LEFT_DRIVE_ENCODER_CHANNEL_A, Addresses.LEFT_DRIVE_ENCODER_CHANNEL_B);
        _rightDriveMotor1 = new Victor(Addresses.RIGHT_DRIVE_VICTOR1);
        _rightDriveMotor2 = new Victor(Addresses.RIGHT_DRIVE_VICTOR2);
        _rightDriveMotor3 = new Victor(Addresses.RIGHT_DRIVE_VICTOR3);
        _leftDriveMotor1 = new Victor(Addresses.LEFT_DRIVE_VICTOR1);
        _leftDriveMotor2 = new Victor(Addresses.LEFT_DRIVE_VICTOR2);
        _leftDriveMotor3 = new Victor(Addresses.LEFT_DRIVE_VICTOR3);

    }

    public void runTankDrive() {
        getJoystickY();
        setDeadband();
        setHalfSpeed(_leftStick.getRawButton(1), _rightStick.getRawButton(1)); //Joystick Triggers
        setAllMotors();
        runDebug(false);
    }

    private void getJoystickY() {
        rightDriveMotorOutput = calculateLinearOutput(_rightStick.getY());
        leftDriveMotorOutput = -(calculateLinearOutput(_leftStick.getY()));
    }

    private void setDeadband() {
        if (Math.abs(rightDriveMotorOutput) < k_Deadband) {
            rightDriveMotorOutput = 0.0;
        }
        if (Math.abs(leftDriveMotorOutput) < k_Deadband) {
            leftDriveMotorOutput = 0.0;
        }
    }

    private void setAllMotors() {
        setLeftMotors();
        setRightMotors();
    }

    private void setLeftMotors() {
        _leftDriveMotor1.set(leftDriveMotorOutput);
        _leftDriveMotor2.set(leftDriveMotorOutput);
        _leftDriveMotor3.set(leftDriveMotorOutput);
    }

    private void setRightMotors() {
        _rightDriveMotor1.set(rightDriveMotorOutput);
        _rightDriveMotor2.set(rightDriveMotorOutput);
        _rightDriveMotor3.set(rightDriveMotorOutput);
    }

    private void runDebug(boolean toggle) {
        if (toggle = true) {
            SmartDashboard.putNumber("LeftJoystickInput", _leftStick.getY());
            SmartDashboard.putNumber("RightJoystickInput", _rightStick.getY());
            SmartDashboard.putNumber("LeftOutput", leftDriveMotorOutput);
            SmartDashboard.putNumber("RightOutput", rightDriveMotorOutput);
            SmartDashboard.putNumber("rightMotor1", _rightDriveMotor1.get());
            SmartDashboard.putNumber("rightMotor2", _rightDriveMotor2.get());
            SmartDashboard.putNumber("rightMotor3", _rightDriveMotor3.get());
            SmartDashboard.putNumber("leftMotor1", _leftDriveMotor1.get());
            SmartDashboard.putNumber("leftMotor2", _leftDriveMotor2.get());
            SmartDashboard.putNumber("leftMotor3", _leftDriveMotor3.get());
        }
    }

    public double calculateLinearOutput(double output) {
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

    private void setHalfSpeed(boolean button, boolean button2) {
        if (button == true || button2 == true) {
            leftDriveMotorOutput = (leftDriveMotorOutput / 2);
            rightDriveMotorOutput = (rightDriveMotorOutput / 2);
        }
    }
}