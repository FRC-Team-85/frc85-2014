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
    
    private final int FRONTRIGHTVICTOR = 1;
    private final int FRONTLEFTVICTOR = 2;
    private final int BACKRIGHTVICTOR = 3;
    private final int BACKLEFTVICTOR = 4;
    
    private final Joystick _leftStick;
    private final Joystick _rightStick;
    
    private final int LEFTSTICK = 1;
    private final int RIGHTSTICK = 2;
            
    private double leftMotorOutput;
    private double rightMotorOutput;
    
    private final double DEADBAND = 0.2;
    
    
    public Drive(){
   
        _frontRightMotor = new Victor(FRONTRIGHTVICTOR);
        _frontLeftMotor = new Victor(FRONTLEFTVICTOR);
        _backRightMotor = new Victor(BACKRIGHTVICTOR);
        _backLeftMotor = new Victor(BACKLEFTVICTOR);
        
        _leftStick = new Joystick(LEFTSTICK);
        _rightStick = new Joystick(RIGHTSTICK);
        
    }
    
    public void tankDrive(){
        getJoystickY();
        setDeadband();
        setAllMotors();
    }
    
                
    public void getJoystickY(){
        rightMotorOutput = _rightStick.getY();
        leftMotorOutput = _leftStick.getY();
    }
    
    public void setDeadband(){
        if (rightMotorOutput < DEADBAND){
            rightMotorOutput = 0.0;
        }
        if (leftMotorOutput < DEADBAND){
            leftMotorOutput = 0.0;
        }
}
    
    public void setAllMotors(){
        setLeftMotors();
        setRightMotors();
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