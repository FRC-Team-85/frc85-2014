/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
/**
 *
 * @author Tiger
 */
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
    
    private final double DEADBAND = 0.3;
    
    public Drive(){
   
        _frontRightMotor = new Victor(Addresses.FRONT_RIGHT_VICTOR);
        _frontLeftMotor = new Victor(Addresses.FRONT_LEFT_VICTOR);
        _backRightMotor = new Victor(Addresses.BACK_RIGHT_VICTOR);
        _backLeftMotor = new Victor(Addresses.BACK_LEFT_VICTOR);
        
        _leftStick = new Joystick(Addresses.LEFT_STICK);
        _rightStick = new Joystick(Addresses.RIGHT_STICK);
        
        _leftEncoder = new Encoder(Addresses.LEFT_ENCODER_CHANNEL_A, Addresses.LEFT_ENCODER_CHANNEL_B);
        _rightEncoder = new Encoder(Addresses.RIGHT_ENCODER_CHANNEL_A, Addresses.RIGHT_ENCODER_CHANNEL_B);
        
    }
    
    public void tankDrive(){
        getJoystickY();
        setDeadband();
        setAllMotors();
    }
    
                
    public void getJoystickY(){
        rightMotorOutput = -_rightStick.getY();
        leftMotorOutput = _leftStick.getY();
        //System.out.println("left: " + leftMotorOutput);
        //System.out.println("right: " + rightMotorOutput);
    }
    
    public void setDeadband(){
        if (Math.abs(rightMotorOutput) < DEADBAND){
            rightMotorOutput = 0.0;
        }
        if (Math.abs(leftMotorOutput) < DEADBAND){
            leftMotorOutput = 0.0;
        }
        //System.out.println("left: " + leftMotorOutput);
        //System.out.println("right: " + rightMotorOutput);
}
    
    public void setAllMotors(){
        setLeftMotors();
        setRightMotors();
        //System.out.println("frontRight: " + _frontRightMotor.get());
        //System.out.println("frontLeft: " + _frontLeftMotor.get());
        //System.out.println("backRight: " + _backRightMotor.get());
        //System.out.println("backLeft: " + _backLeftMotor.get());
    }
    
    public void setLeftMotors(){
        _frontLeftMotor.set(leftMotorOutput);
        _backLeftMotor.set(leftMotorOutput);
    }
    
    public void setRightMotors(){
        _frontRightMotor.set(rightMotorOutput);
        _backRightMotor.set(rightMotorOutput);
    }
           
}