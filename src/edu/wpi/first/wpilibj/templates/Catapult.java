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
    
    private boolean isFiring = false;
    
    private final int FULLCAMCOUNT = 5000;
    private final double CAMMOTORSPEED = 1;
    
    public Catapult(){
       leftCamMotor = new Victor(Addresses.CAM_MOTOR_LEFT);
       rightCamMotor = new Victor(Addresses.CAM_MOTOR_RIGHT);
       positionValve = new Solenoid(Addresses.CATAPULT_POSITION_VALVE);
       camPosition = new Encoder(Addresses.CAM_ENCODER_CHANNEL_A, Addresses.CAM_ENCODER_CHANNEL_B);
       
    }
    public void trussPosition(){
        positionValve.set(true);
    }
    public void shootingPosition(){
        positionValve.set(false);
    }
    public void fireCatapult(){
        isFiring = true;
    }
    public void runCatapult(){
        if (isFiring){
            if (camPosition.get() >= FULLCAMCOUNT){
                isFiring = false;
                leftCamMotor.set(0);
                rightCamMotor.set(0);
                camPosition.reset();
            } else{
                leftCamMotor.set(CAMMOTORSPEED);
                rightCamMotor.set(CAMMOTORSPEED);
            }
            
        } 
    }
}
