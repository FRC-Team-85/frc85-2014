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
    //ImageFiltering imageFilter;
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
    private final double MAX_DRIVE_SPEED = 0.23;
    
    private double completeAngle;
    private double increaseSpeedAngle;
    private double decreaseSpeedAngle;
    private final double MAX_ROTATE_SPEED = 0.5;
    
    private int autoState = 0;
    private int rollerDelay = 0;
    
    private int shotCount;

    public Autonomous(Drive drive, Catapult catapult, /*ImageFiltering imageFiltering,*/ AutoPreferences autoPreferences) {
        this.drive = drive;
        this.catapult = catapult;
        //this.imageFilter = imageFiltering;
        this.autoPreferences = autoPreferences;
        //gyro = new Gyro(Addresses.GYRO_CHANNEL);
    }

    public void runAutonInit() {
        //gyro.reset();
        drive.resetDriveEncoders();
        drive.startDriveEncoders();
        catapult.resetFiring();
        drive.setAllMotors(0, 0);
        hasFired = false;
        shotDelayCounter = 0;
        driveDelayCounter = 0;
        rollerDelay = 0;
        shotCount = 0;
        currentSpeed = 0;
    }
    
    public void runAuton() {
        selectState();
        displayModes();
        //runDebug();
    }

    public void getAutonomousPreferencesData() {
        autoState = autoPreferences.getAutoMode();
        totalDistance = (int) Math.ceil(((12 * autoPreferences.getDriveDistance()) / (4 * Math.PI)) * DRIVE_ENCODER_CPR);
        if (totalDistance == 0) {
            totalDistance = 700;
        }
    }

    public void selectState() {
        //imageFilter.setBlobVariable(imageFilter.getBlob());
        //currentDistance = drive.getAvgDriveEncValue();
        currentDistance = Math.abs(drive.leftDriveEncoder.get());// switched to Left Drive Encoder only due to right side being broken
        switch (autoState) {
            case 1://Drive only
                runAutoDrive();
                break;

            case 2://Drive then shoot
                if (!catapult.getArmLimit()) {
                    catapult.setArmSolenoid(true);
                    catapult.setCamMotors(0);
                } else {
                    if (driveDelayCounter > 100) { // 2 sec delay, assuming cycle time is 20 millsecs
                        if (runAutoDrive() && catapult.getArmLimit()) {
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
                break;

            case 3://Shoot then drive
                if (!catapult.getArmLimit()) {
                    catapult.setArmSolenoid(true);
                    catapult.setCamMotors(0);
                } else {
                    if (shotDelayCounter > 100) { // 2 sec delay, assuming cycle time is 20 millsecs
                        runAutoCatapult();
                        if (driveDelayCounter > 100) {
                            runAutoDrive();
                        } else {
                            driveDelayCounter++;
                        }
                    } else {
                        shotDelayCounter++;
                    }
                }
                break;

            case 4://Double shoot then drive
                if (!catapult.getArmLimit()) {
                    catapult.setArmSolenoid(true);
                    catapult.setCamMotors(0);
                } else {
                    if ((shotCount == 0 && shotDelayCounter > 65) || (shotCount > 0 && shotDelayCounter > 65)) { // 2 sec delay, assuming cycle time is 20 millsecs
                        runAutoCatapult();
                        if (!catapult.isFiring()) {
                            if (shotCount == 0) {
                                if (rollerDelay <= 60) {
                                    drive.setIntakeMotors(-1);
                                } else {
                                    //drive.setIntakeMotors(0);
                                    catapult.camEncoder.reset();
                                    hasFired = false;
                                    shotDelayCounter = 0;
                                    shotCount++;
                                    runAutoDrive();
                                }
                                rollerDelay++;
                            } else {
                                //if (driveDelayCounter > 1) {
                                runAutoDrive();
                                //} else {
                                //    driveDelayCounter++;
                                //}
                            }
                        }
                    } else {
                        shotDelayCounter++;
                        if (shotCount > 0)
                        {
                            runAutoDrive();
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    private void runAutoCatapult() {
        if (catapult.camEncoderCount > 350) {
            hasFired = true;
        }
        catapult.runEncoderBasedCatapult(!hasFired, false);
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
    public void displayModes(){
        SmartDashboard.putString("Mode 1: Drive", "Drive");
        SmartDashboard.putString("Mode 2: Drive then shoot", "Drive then shoot");
        SmartDashboard.putString("Mode 3: Shoot then drive", "Shoot then drive");
        SmartDashboard.putString("Mode 4: Double shot then drive", "Double shot then drive");
    }
}
