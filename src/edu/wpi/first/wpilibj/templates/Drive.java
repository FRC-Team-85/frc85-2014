/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*;
/**
 *
 * @author Tiger
 */
public class Drive {
    
    private final SpeedController _frontRightMotor;
    private final SpeedController _frontLeftMotor;
    private final SpeedController _backRightMotor;
    private final SpeedController _backLeftMotor;
    
    private final int FRONTRIGHTVICTOR = 2;
    private final int FRONTLEFTVICTOR = 1;
    private final int BACKRIGHTVICTOR = 4;
    private final int BACKLEFTVICTOR = 3;
    
    private final Joystick _leftStick;
    private final Joystick _rightStick;
    
    private final int LEFTSTICK = 1;
    private final int RIGHTSTICK = 2;
    
    private final Encoder _leftEncoder;
    private final Encoder _rightEncoder;
    
    private final int LEFTENCODERCHANNELA = 1;
    private final int LEFTENCODERCHANNELB = 2;
    private final int RIGHTENCODERCHANNELA = 3;
    private final int RIGHTENCODERCHANNELB = 4;
            
    private double leftMotorOutput;
    private double rightMotorOutput;
    
    private final double DEADBAND = 0.3;
    
    
    public Drive(){
   
        _frontRightMotor = new Victor(FRONTRIGHTVICTOR);
        _frontLeftMotor = new Victor(FRONTLEFTVICTOR);
        _backRightMotor = new Victor(BACKRIGHTVICTOR);
        _backLeftMotor = new Victor(BACKLEFTVICTOR);
        
        _leftStick = new Joystick(LEFTSTICK);
        _rightStick = new Joystick(RIGHTSTICK);
        
        _leftEncoder = new Encoder(LEFTENCODERCHANNELA, LEFTENCODERCHANNELB);
        _rightEncoder = new Encoder(RIGHTENCODERCHANNELA, RIGHTENCODERCHANNELB);
        
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