/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    
    private int stage = 0;
    
    private int currentDist;
    private final int dist1 = 288;
    private final int dist2 = 792;
    private final int dist3 = 1080;
    
    private double currentSpeed = 0;
    private final double maxDriveSpeed = 0.6;
    
    private final double intakeRollerSpeed = -1.0;
    
    private final double angle1 = 60;
    private final double angle2 = 120;
    private final double angle3 = 180;
    private final double maxRotateSpeed = 0.5;
    Gyro gyro;
    private double time0, time1, time2, time3;
    
    public Autonomous(Drive drive, Catapult catapult, ImageFiltering imageFiltering) {
        this.drive = drive;
        this.catapult = catapult;
        this.imageFilter = imageFiltering;
        gyro = new Gyro(Addresses.GYRO_CHANNEL);
        time1=1; time2 =2; time3 = 3;
    }

    public void selectState() {
        if (!catapult.getArmLimit()) {
            drive.setIntakeMotors(intakeRollerSpeed);
            catapult.setArmSolenoid(true);
        } else {
            if (imageFilter.getBlob()) {//if the blob is there
                if (timer.get() <= 5.0) {//and 5 sec havent passed
                    timer.stop();
                    runProcess1();//0-5 process2 w/180 
                } else {
                    timer.stop();
                    runProcess2();//5-10
                }
            }
        }
    }
    
    public void runProcess1() {
        switch (stage) {
            case 0:
                if (catapult.camLimitStop.get() && !willFire) {//if ready to fire and done with first fire
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
    
    public void runProcess2() {
        switch (stage) {
            case 0:
                if (catapult.camLimitStop.get() && !willFire) {//if ready to fire and done with first fire
                    stage = 1;
                } else {
                    camMotorControl();//drive
                }
                break;
            case 1:
                haulIt();
                break;
            default:
                break;
        }
    }

    public void camMotorControl() {//fire then set to false
        catapult.setMotors(willFire);
        if (!catapult.camLimitStop.get() && willFire) {
            willFire = false;
        }
    }

    public void haulIt() {
        if (currentDist <= dist1) {
            currentSpeed = maxDriveSpeed * currentDist / dist1;
        } else if (currentDist <= dist2) {
            currentSpeed = maxDriveSpeed;
        } else if (currentDist <= dist3) {
            currentSpeed = maxDriveSpeed * (1 - (currentDist - dist2) / (dist3 - dist2));
        } else {
            currentSpeed = 0;
            stage++;
        }
        drive.setAllMotors(currentSpeed, currentSpeed);
    }
    
    public void vivaLaRevolution() {
        double currentAngle = Math.abs(gyro.getAngle());
        if (currentAngle <= angle1) {
            currentSpeed = maxRotateSpeed * currentAngle / angle1;
        } else if (currentAngle <= angle2) {
            currentSpeed = maxRotateSpeed;
        } else if (currentAngle <= angle3) {
            currentSpeed = maxRotateSpeed * (1 - (currentAngle - angle2) / (angle3 - angle2));
        } else {
            currentSpeed = 0;
            stage++;
        }
        drive.setAllMotors(-currentSpeed, currentSpeed);
    }
    
    public void runAuton() {
        runDebug();
    }
    
    public void runAutonInit() {
        gyro.reset();
        drive.resetEncoders();
        drive.startEncoders();
        timer.reset();
        timer.start();
    }
    
    public void runDebug() {
        SmartDashboard.putNumber("AutonTimer", timer.get());
        SmartDashboard.putNumber("AutoStage", stage);
        SmartDashboard.putNumber("Gyro", gyro.getAngle());
        SmartDashboard.putNumber("DriveSpeed", currentSpeed);
    }
    public void AshleyTimerAuto(){
        double thisTime = timer.get();
        if(thisTime<=time1){
            currentSpeed=maxDriveSpeed*(thisTime/time1);
        } else if(thisTime<=time2){
            currentSpeed=maxDriveSpeed;
        } else if(thisTime<=time3){
            currentSpeed=maxDriveSpeed*(1-(thisTime-time2)/(time3-time2));
        } else if(thisTime>time3){
            vivaLaRevolution();
        }
        if(thisTime<time3){
            drive.setAllMotors(currentSpeed, currentSpeed);
        } 
    }
}
