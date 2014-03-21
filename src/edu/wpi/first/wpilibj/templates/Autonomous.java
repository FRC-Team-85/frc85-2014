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
    Gyro gyro;
    AutoPreferences autoPreferences;
    
    private int stage = 1;
    
    private boolean hasFired = false;
    
    private final int driveEncoderCPR = 360;
    private int totalDistance;
    private int currentDist;
    private int increaseSpeedDistance;
    private int decreaseSpeedDistance;
    
    private double currentSpeed = 0;
    private final double maxDriveSpeed = 0.6;
    
    private final double intakeRollerSpeed = -1.0;
    
    private double completeAngle;
    private double increaseSpeedAngle;
    private double decreaseSpeedAngle;
    private final double maxRotateSpeed = 0.5;
    private double time1, time2, time3;
    
    private final int autoSwitichCount = 300;// needs to be found by testing, needs to be changed
    
    public Autonomous(Drive drive, Catapult catapult, ImageFiltering imageFiltering, AutoPreferences autoPreferences) {
        this.drive = drive;
        this.catapult = catapult;
        this.imageFilter = imageFiltering;
        this.autoPreferences = autoPreferences;
        gyro = new Gyro(Addresses.GYRO_CHANNEL);
        time1 = 1; time2 = 2; time3 = 3;
        
    }

    public void getAutonomousPreferencesData() {
        totalDistance = (int)Math.ceil(((12 * autoPreferences.getDriveDistance()) / (4 * Math.PI)) * driveEncoderCPR);
        increaseSpeedDistance = (int)(totalDistance * (1/3));
        decreaseSpeedDistance = (int)(totalDistance * (2/3));
        completeAngle = autoPreferences.getRotationAngle();
        increaseSpeedAngle = completeAngle * (1/3);
        decreaseSpeedAngle = completeAngle * (2/3);
    }
    
    public void selectState() {
        imageFilter.setBlobVariable(imageFilter.getBlob());
        if (!catapult.getArmLimit()) {
            drive.setIntakeMotors(intakeRollerSpeed);
            catapult.setArmSolenoid(true);
        } else {
            if (haulIt()) {
                if (imageFilter.blob || timer.get() > 5) {
                    runAutoCatapult();
                }
            }
        }
    }
    
    
    private void runAutoCatapult() {
        if (catapult.camLimitStopRight.get()){
            hasFired = true;
        } 
        runAutoCamControl(!hasFired);
    }
    
    
    public void runProcess1() {
        switch (stage) {
            case 1:
                haulIt();
                break;
            case 2:
                vivaLaRevolution();
                break;
            default:
                break;
        }
    }
    
    public void runProcess2() {
        switch (stage) {
            case 1:
                haulIt();
                break;
            default:
                break;
        }
    }
    
        public void runAutoCamControl(boolean fire) {
                catapult.runEncoderBasedCatapult(fire, false, false);
        }

    public void camMotorControl() {//fire then set to false
        catapult.setMotors(willFire);
        if (!catapult.camLimitStopLeft.get() && willFire) {
            willFire = false;
        }
    }

    public boolean haulIt() {
        if (currentDist <= increaseSpeedDistance) {
            currentSpeed = maxDriveSpeed * currentDist / increaseSpeedDistance;
        } else if (currentDist <= decreaseSpeedDistance) {
            currentSpeed = maxDriveSpeed;
        } else if (currentDist <= totalDistance) {
            currentSpeed = maxDriveSpeed * (1 - (currentDist - decreaseSpeedDistance) / (totalDistance - decreaseSpeedDistance));
        } else {
            currentSpeed = 0;   
        }
        drive.setAllMotors(currentSpeed, currentSpeed);
        if (currentSpeed == 0){
            return true;
        } else {
            return false;
        }
    }
    
    public void vivaLaRevolution() {
        double currentAngle = Math.abs(gyro.getAngle());
        if (currentAngle <= increaseSpeedAngle) {
            currentSpeed = maxRotateSpeed * currentAngle / increaseSpeedAngle;
        } else if (currentAngle <= decreaseSpeedAngle) {
            currentSpeed = maxRotateSpeed;
        } else if (currentAngle <= completeAngle) {
            currentSpeed = maxRotateSpeed * (1 - (currentAngle - decreaseSpeedAngle) / (completeAngle - decreaseSpeedAngle));
        } else {
            currentSpeed = 0;
            stage++;
        }
        drive.setAllMotors(-currentSpeed, currentSpeed);
    }
    
    public void runAuton() {
        selectState();
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
    public void AshleyTimerAuto() {
        double thisTime = timer.get();
        if (thisTime <= time1) {
            currentSpeed = maxDriveSpeed * (thisTime / time1);
        } else if (thisTime <= time2) {
            currentSpeed = maxDriveSpeed;
        } else if (thisTime <= time3) {
            currentSpeed = maxDriveSpeed * (1 - (thisTime - time2) / (time3 - time2));
        } else if (thisTime > time3) {
            vivaLaRevolution();
        }
        if (thisTime < time3) {
            drive.setAllMotors(currentSpeed, currentSpeed);
        }
    }
}
