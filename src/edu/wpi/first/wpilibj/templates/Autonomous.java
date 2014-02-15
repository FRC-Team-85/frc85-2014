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
    private double currentSpeed, mSpeed, intakeRollerSpeed;
    private double angle1, angle2, angle3;
    Gyro gyro;
    
    public Autonomous(Drive drive, Catapult catapult, ImageFiltering imageFiltering){
            this.drive = drive;
            this.catapult = catapult;
            this.imageFilter = imageFiltering;
            rightDriveEncoder = new Encoder(Addresses.RIGHT_DRIVE_ENCODER_CHANNEL_A, Addresses.RIGHT_DRIVE_ENCODER_CHANNEL_B);
            leftDriveEncoder = new Encoder(Addresses.LEFT_DRIVE_ENCODER_CHANNEL_A, Addresses.LEFT_DRIVE_ENCODER_CHANNEL_B);
            stage = 0;
            rightDriveEncoder.reset();
            leftDriveEncoder.reset();
            timer.reset();
            timer.start();
            dist1 = 288;
            dist2 = 792;
            dist3 = 1080;
            mSpeed = 0.6;
            angle1 = 60;
            angle2 = 120;
            angle3 = 180;
            intakeRollerSpeed = -1.0;
            gyro = new Gyro(Addresses.GYRO_CHANNEL);
            gyro.reset();
    }
    public void selectState(){
        getEncoderValues();
        if(!catapult.getArmLimit()){
            drive.setIntakeMotors(intakeRollerSpeed);
            catapult.setArmSolenoid(true);
        } else {
            if(imageFilter.getBlob()){//if the blob is there
                if(timer.get() <= 5.0){//and 5 sec havent passed
                    timer.stop();
                    runProcess1();//0-5 process2 w/180                    
                } else {
                    timer.stop();
                    runProcess2();//5-10
                }
            }
        }
    }
    
    public void runProcess1(){
       switch(stage){
            case 0:
                if(catapult.camLimitStop.get() && !willFire){//if ready to fire and done with first fire
                    stage = 1;
                }
                camMotorControl();//drive
                break;
            case 1:
                haulIt();
                break;
            case 2:
                vivaLaRevolution();
            default:
                break;
        }
    }
    
    public void runProcess2(){
        switch(stage){
            case 0:
                if(catapult.camLimitStop.get() && !willFire){//if ready to fire and done with first fire
                    stage = 1;
                }
                camMotorControl();//drive
                break;
            case 1:
                haulIt();
                break;
            default:
                break;
        }

    }
    public void camMotorControl(){//fire then set to false
            catapult.setMotors(willFire);
            if(!catapult.camLimitStop.get() && willFire){
                willFire = false;
            }
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
    
    public void vivaLaRevolution(){
        double currentAngle = Math.abs(gyro.getAngle());
        if(currentAngle <= angle1){
            currentSpeed = mSpeed * currentAngle / angle1;
        } else if(currentAngle <= angle2){
            currentSpeed = mSpeed;
        } else if(currentAngle <= angle3){
            currentSpeed = mSpeed * (1 - (currentAngle-angle2)/(angle3-angle2));
        } else {
            currentSpeed = 0;
            stage++;
        }
    }
}