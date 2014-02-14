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
    private int stage;
    private int currentDist, dist1, dist2, dist3;
    private double currentSpeed, mSpeed;
    
    public Autonomous(Drive drive, Catapult catapult, ImageFiltering imageFilter){
            this.drive = drive;
            this.catapult = catapult;
            this.imageFilter = imageFilter;
            rightDriveEncoder = new Encoder(Addresses.RIGHT_DRIVE_ENCODER_CHANNEL_A, Addresses.RIGHT_DRIVE_ENCODER_CHANNEL_B);
            leftDriveEncoder = new Encoder(Addresses.LEFT_DRIVE_ENCODER_CHANNEL_A, Addresses.LEFT_DRIVE_ENCODER_CHANNEL_B);
            stage=0;
            rightDriveEncoder.reset();
            leftDriveEncoder.reset();
            timer.start();
            dist1 = 200;
            dist2 = 550;
            dist3 = 750;
            mSpeed = .6;
    }
    public void selectState(){
        getEncoderValues();
        
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
        switch(stage){
            case 0:
                if(catapult.getLimit() && !willFire){//if ready to fire and done with first fire
                    stage = 1;
                }
                camMotorControl();//drive
                break;
            case 1:
                haulIt();
                break;
            case 2:
                
        }

    }
    public void camMotorControl(){//fire then set to false
            catapult.setMotors(willFire);
            if(!catapult.getLimit() && willFire)
                willFire = false;
    }
    public void haulIt(){
        if(currentDist<=dist1){
            currentSpeed = mSpeed * currentDist / dist1;
        } else if(currentDist <=dist2){
            currentSpeed = mSpeed;
        } else if(currentDist <= dist3){
            currentSpeed = mSpeed*(1-(currentDist-dist2)/(dist3-dist2));
        } else {
            currentSpeed = 0;
            stage++;
        }
        drive.setAllMotors(currentSpeed);
    }
    public void getEncoderValues(){
        currentDist = (leftDriveEncoder.get() + rightDriveEncoder.get()) / 2;
    }
}