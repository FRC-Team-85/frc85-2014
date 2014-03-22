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

    Drive drive;
    Catapult catapult;
    ImageFiltering imageFilter;
    Encoder rightDriveEncoder;
    Encoder leftDriveEncoder;
    Gyro gyro;
    AutoPreferences autoPreferences;
    
    private boolean hasFired = false;
    private int shotDelayCounter = 0;
    private int driveDelayCounter = 0;
    
    private int totalDistance;
    private double currentDistance = 0;
    private double currentSpeed;
    private final int DRIVE_ENCODER_CPR = 360;
    private final double MAX_DRIVE_SPEED = 0.25;
    
    private double completeAngle;
    private double increaseSpeedAngle;
    private double decreaseSpeedAngle;
    private final double MAX_ROTATE_SPEED = 0.5;

    public Autonomous(Drive drive, Catapult catapult, ImageFiltering imageFiltering, AutoPreferences autoPreferences) {
        this.drive = drive;
        this.catapult = catapult;
        this.imageFilter = imageFiltering;
        this.autoPreferences = autoPreferences;
        gyro = new Gyro(Addresses.GYRO_CHANNEL);
    }

    public void runAutonInit() {
        //gyro.reset();
        drive.resetDriveEncoders();
        drive.startDriveEncoders();
        hasFired = false;
        shotDelayCounter = 0;
        driveDelayCounter = 0;
    }
    
    public void runAuton() {
        selectState();
        //runDebug();
    }

    public void getAutonomousPreferencesData() {
        totalDistance = (int) Math.ceil(((12 * autoPreferences.getDriveDistance()) / (4 * Math.PI)) * DRIVE_ENCODER_CPR);
        if (totalDistance == 0) {
            totalDistance = 1000;
        }
        completeAngle = autoPreferences.getRotationAngle();
        increaseSpeedAngle = completeAngle * (1 / 3);
        decreaseSpeedAngle = completeAngle * (2 / 3);
    }

    public void selectState() {
        //imageFilter.setBlobVariable(imageFilter.getBlob());
        currentDistance = drive.getAvgDriveEncValue();
        if (autoPreferences.getTrussSetting()) {
            catapult.setTrussPostition(true);
        } else {
            catapult.setTrussPostition(false);
        }
        if (!catapult.getArmLimit() && autoPreferences.enableIntakeSetting) {
            //drive.setIntakeMotors(intakeRollerSpeed);
            catapult.setArmSolenoid(true);
            catapult.setCamMotors(0);
        } else if (autoPreferences.getShootFirstSetting()) {
            if (shotDelayCounter > 150) { // 3 sec delay by cycle time, assuming cycle time is 20 millsecs
                runAutoCatapult();
                if (driveDelayCounter > 100) { // 2 sec delay, assuming cycle time is 20 millsecs
                    runAutoDrive();
                } else {
                    driveDelayCounter++;
                }
            } else {
                shotDelayCounter++;
            }
        } else {
            if (driveDelayCounter > 100) { // 2 sec delay, assuming cycle time is 20 millsecs
                if (runAutoDrive() && autoPreferences.getShooterSetting() && catapult.getArmLimit()) {
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
        if (catapult.camEncoderCount > 350) {
            hasFired = true;
        }
        catapult.runEncoderBasedCatapult(!hasFired, false, false);
    }

    public boolean runAutoDrive() {
        if (currentDistance <= totalDistance) {
            currentSpeed = MAX_DRIVE_SPEED;
        } else {
            currentSpeed = 0;
        }
        drive.setAllMotors(currentSpeed, currentSpeed);
        if (currentSpeed == 0 && currentDistance >= totalDistance) {
            return true;
        } else {
            return false;
        }
    }

    public void runAutoRotation() {
        double currentAngle = Math.abs(gyro.getAngle());
        if (currentAngle <= increaseSpeedAngle) {
            currentSpeed = MAX_ROTATE_SPEED * currentAngle / increaseSpeedAngle;
        } else if (currentAngle <= decreaseSpeedAngle) {
            currentSpeed = MAX_ROTATE_SPEED;
        } else if (currentAngle <= completeAngle) {
            currentSpeed = MAX_ROTATE_SPEED * (1 - (currentAngle - decreaseSpeedAngle) / (completeAngle - decreaseSpeedAngle));
        } else {
            currentSpeed = 0;
        }
        drive.setAllMotors(-currentSpeed, currentSpeed);
    }

    public void runDebug() {
        SmartDashboard.putNumber("Gyro", gyro.getAngle());
        SmartDashboard.putNumber("DriveSpeed", currentSpeed);
        System.out.println("CurrentSpeed = " + currentSpeed);
        System.out.println("CurrentDistance = " + currentDistance);
        System.out.println("IsFiring = " + catapult.isFiring());
        System.out.println("Total distance = " + totalDistance);
    }
}
