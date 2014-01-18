/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Person
 */
public class Intake {
    
    
        private final SpeedController leftIntakeMotor;
        private final SpeedController rightIntakeMotor;
        private final Solenoid positionValve;
        
    
    
    public Intake(){
        //Create SpeedControllers,cylinders and Encoders here
        leftIntakeMotor = new Victor(Addresses.INTAKE_MOTOR_LEFT);
        rightIntakeMotor = new Victor(Addresses.INTAKE_MOTOR_RIGHT);
        positionValve = new Solenoid(Addresses.INPUT_POSITION_VALVE);
    }
    public void extend(){
        positionValve.set(true);
    }
    public void retract(){
        positionValve.set(false);
    }
    public void setIntakeSpeed(double speed){
        leftIntakeMotor.set(speed);
        rightIntakeMotor.set(speed);
    }
}
