/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*;
/**
 *
 * @author Tyler
 */
public class Autonomous {
    public boolean willFire = true;
    Drive drive;
    Catapult catapult;
    ImageFiltering imageFilter;
    Timer timer;
    Encoder rightDriveEncoder;
    Encoder leftDriveEncoder;
    
    public Autonomous(Drive drive, Catapult catapult, ImageFiltering imageFilter){
            this.drive = drive;
            this.catapult = catapult;
            this.imageFilter = imageFilter;
            rightDriveEncoder = new Encoder(Addresses.RIGHT_DRIVE_ENCODER_CHANNEL_A, Addresses.RIGHT_DRIVE_ENCODER_CHANNEL_B);
            leftDriveEncoder = new Encoder(Addresses.LEFT_DRIVE_ENCODER_CHANNEL_A, Addresses.LEFT_DRIVE_ENCODER_CHANNEL_B);
            rightDriveEncoder.reset();
            leftDriveEncoder.reset();
            timer.start();
    }
    public void selectState(){
        if(imageFilter.getBlob()){//if the blob is there
            if(timer.get() <= 5.0){//and 5 sec havent passed
                runProcess1();//0-5 process2 w/180
            } else {
                runProcess2();//5-10
            }
        }
        
        /**if(imageFilter.getBlob() && !hasFired){
            runProcess1();
        } else {
            runProcess2();
        }**/
    }
    public void runProcess1(){
       //runProcess2();
        //180
    }
    public void runProcess2(){
        
        if(catapult.getLimit() && !willFire){//if ready to fire and done with first fire
            //drive
        } else{
            camMotorControl();//fire
        }
    }
    public void camMotorControl(){//fire then set to false
            catapult.setMotors(willFire);
            willFire = false;
    }
}