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
    Encoder rightDriveEncoder;
    Encoder leftDriveEncoder;
    Gyro gyro;
    AutoPreferences autoPreferences;
    
    private int stage = 1;
    
    private boolean hasFired = false;
    
    private final int driveEncoderCPR = 360;
    private int totalDistance;
    private double currentDist = 0;
    
    private double currentSpeed;
    private final double maxDriveSpeed = 0.25;
    
    private double completeAngle;
    private double increaseSpeedAngle;
    private double decreaseSpeedAngle;
    private final double maxRotateSpeed = 0.5;
    
    private int shotDelayCounter = 0;
    private int driveDelayCounter = 0;
    
    public Autonomous(Drive drive, Catapult catapult, ImageFiltering imageFiltering, AutoPreferences autoPreferences) {
        this.drive = drive;
        this.catapult = catapult;
        this.imageFilter = imageFiltering;
        this.autoPreferences = autoPreferences;
        gyro = new Gyro(Addresses.GYRO_CHANNEL);
        
    }

    public void getAutonomousPreferencesData() {
        totalDistance = (int)Math.ceil(((12 * autoPreferences.getDriveDistance()) / (4 * Math.PI)) * driveEncoderCPR);
        if (totalDistance == 0) {
            totalDistance = 1000;
        }
        completeAngle = autoPreferences.getRotationAngle();
        increaseSpeedAngle = completeAngle * (1/3);
        decreaseSpeedAngle = completeAngle * (2/3);
    }
   
    public void selectState() {
        //imageFilter.setBlobVariable(imageFilter.getBlob());
        currentDist = drive.getEncoderValues();
        if (!catapult.getArmLimit() && autoPreferences.enableIntakeSetting) {
            //drive.setIntakeMotors(intakeRollerSpeed);
            catapult.setArmSolenoid(true);
            catapult._leftCamMotor.set(0);
            catapult._rightCamMotor.set(0);
        } else {
            if (driveDelayCounter > 100) {
                if (haulIt() && autoPreferences.getShooterSetting() && catapult.getArmLimit()) {
                    if (shotDelayCounter > 50) { // 1 sec delay by cycle time, assuming cycle time is 20 millsecs
                        runAutoCatapult();
                    } else {
                        shotDelayCounter++;
                    }
                }
            } else {
                driveDelayCounter++;
            }
        }
    }
    
    
    private void runAutoCatapult() {
        if (catapult.camEncoderCount > 300){
           hasFired = true;
        }
        runAutoCamControl(!hasFired);
    }
    
        public void runAutoCamControl(boolean fire) {
                catapult.runEncoderBasedCatapult(fire, false, false);
        }

    public boolean haulIt() {
       if (currentDist <= totalDistance) {
            currentSpeed = maxDriveSpeed;
        } else {
            currentSpeed = 0;   
        }

        drive.setAllMotors(-currentSpeed, -currentSpeed);
        if (currentSpeed == 0 && currentDist >= totalDistance){
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
        //runDebug();
    }
    
    public void runAutonInit() {
        //gyro.reset();
        drive.resetEncoders();
        drive.startEncoders();
        hasFired = false;
        shotDelayCounter = 0;
        driveDelayCounter = 0;
    }
    
    public void runDebug() {
        SmartDashboard.putNumber("AutoStage", stage);
        SmartDashboard.putNumber("Gyro", gyro.getAngle());
        SmartDashboard.putNumber("DriveSpeed", currentSpeed);
        System.out.println("CurrentSpeed = " + currentSpeed);
        System.out.println("CurrentDistance = " + currentDist);
        System.out.println("IsFiring = " + catapult.getIsFiring());
        System.out.println("Total distance = " + totalDistance);
        
    }
}
