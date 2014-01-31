/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.*;

public class Drive {

    private final SpeedController _frontRightMotor;
    private final SpeedController _frontLeftMotor;
    private final SpeedController _backRightMotor;
    private final SpeedController _backLeftMotor;
    
    private final Joystick _leftStick;
    private final Joystick _rightStick;
    
    private final Encoder _leftEncoder;
    private final Encoder _rightEncoder;
    
    private double leftMotorOutput;
    private double rightMotorOutput;
    private final double k_Deadband = 0.2;

    public Drive() {

        _frontRightMotor = new Victor(Addresses.FRONT_RIGHT_VICTOR);
        _frontLeftMotor = new Victor(Addresses.FRONT_LEFT_VICTOR);
        _backRightMotor = new Victor(Addresses.BACK_RIGHT_VICTOR);
        _backLeftMotor = new Victor(Addresses.BACK_LEFT_VICTOR);

        _leftStick = new Joystick(Addresses.LEFT_STICK);
        _rightStick = new Joystick(Addresses.RIGHT_STICK);

        _leftEncoder = new Encoder(Addresses.LEFT_ENCODER_CHANNEL_A, Addresses.LEFT_ENCODER_CHANNEL_B);
        _rightEncoder = new Encoder(Addresses.RIGHT_ENCODER_CHANNEL_A, Addresses.RIGHT_ENCODER_CHANNEL_B);

    }

    public void runTankDrive() {
        getJoystickY();
        setDeadband();
        setAllMotors();
        runDebug(false);
    }

    private void getJoystickY() {
        rightMotorOutput = calculateLinearOutput(_rightStick.getY());
        leftMotorOutput = calculateLinearOutput(_leftStick.getY());
    }

    private void setDeadband() {
        if (Math.abs(rightMotorOutput) < k_Deadband) {
            rightMotorOutput = 0.0;
        }
        if (Math.abs(leftMotorOutput) < k_Deadband) {
            leftMotorOutput = 0.0;
        }
    }

    private void setAllMotors() {
        setLeftMotors();
        setRightMotors();
    }

    private void setLeftMotors() {
        _frontLeftMotor.set(leftMotorOutput);
        _backLeftMotor.set(leftMotorOutput);
    }

    private void setRightMotors() {
        _frontRightMotor.set(rightMotorOutput);
        _backRightMotor.set(rightMotorOutput);
    }

    private void runDebug(boolean toggle) {
        if (toggle = true){
         System.out.println("frontRight: " + _frontRightMotor.get());
        System.out.println("frontLeft: " + _frontLeftMotor.get());
        System.out.println("backRight: " + _backRightMotor.get());
        System.out.println("backLeft: " + _backLeftMotor.get());   
        }
    }
    
    public double calculateLinearOutput(double output) {
        double x = output;
        
        if (x < 0) {
            x *= -1;
            x = (-3.1199*MathUtils.pow(x, 4) + 4.4664*MathUtils.pow(x, 3) - 
                2.2378*MathUtils.pow(x, 2) - 0.122*x);
        } else {
            x = (3.1199*MathUtils.pow(x, 4) - 4.4664*MathUtils.pow(x, 3) + 
                    2.2378*MathUtils.pow(x, 2) + 0.122*x);
        }   
        return x;
    }
}