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
public class Catapult {

    private final SpeedController leftCamMotor;
    private final SpeedController rightCamMotor;
    private final Solenoid positionValve;
    private final Encoder camPosition;
    
    public Catapult(){
       leftCamMotor = new Victor(Addresses.CAM_MOTOR_LEFT);
       rightCamMotor = new Victor(Addresses.CAM_MOTOR_RIGHT);
       positionValve = new Solenoid(Addresses.CATAPULT_POSITION_VALVE);
       camPosition = new Encoder(Addresses.CAM_ENCODER_CHANNEL_A, Addresses.CAM_ENCODER_CHANNEL_B);
       
    }
}
