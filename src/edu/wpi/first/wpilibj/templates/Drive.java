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
    
    private final Joystick _leftStick;
    private final Joystick _rightStick;
    
            
    private double leftMotorOutput;
    private double rightMotorOutput;
    
    private boolean canDrive = true;
    
    private final double _deadband = 0.0;
    
    public Drive(SpeedController frontRightMotor, SpeedController frontLeftMotor,
            SpeedController backRightMotor, SpeedController backLeftMotor, 
            Joystick leftStick, Joystick rightStick){
        _frontRightMotor = frontRightMotor;
        _frontLeftMotor = frontLeftMotor;
        _backRightMotor = backRightMotor;
        _backLeftMotor = backLeftMotor;
        
        _leftStick = leftStick;
        _rightStick = rightStick;
        
    }
    
    public void tankDrive(){
        makeSureYouCanDrive();
        if (canDrive){
        getJoystickY();
        setDeadband();
        setAllMotors();
        }
    }
    
    public void makeSureYouCanDrive(){
        if (_rightStick.getTrigger()){
                canDrive = !true;
            }
        if (_leftStick.getTrigger()){
                canDrive = !false;
        }
    }
                
    public void getJoystickY(){
        rightMotorOutput = _rightStick.getY();
        leftMotorOutput = _leftStick.getY();
    }
    
    public void setDeadband(){
        if (rightMotorOutput < _deadband){
            rightMotorOutput = 0.0;
        }
        if (leftMotorOutput < _deadband){
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