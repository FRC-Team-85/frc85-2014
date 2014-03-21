/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    
    boolean mode = false;
    Joystick leftStick = new Joystick(Addresses.LEFT_STICK);
    Joystick rightStick = new Joystick(Addresses.RIGHT_STICK);
    OperatorPanel operatorPanel = new OperatorPanel();
    Drive drive = new Drive(leftStick, rightStick);
    Catapult catapult = new Catapult(operatorPanel);
    TylersCompressor tCompressor = new TylersCompressor();
    ImageFiltering imageFiltering = new ImageFiltering();
    AutoPreferences autoPreferences = new AutoPreferences();
    Autonomous autonomous = new Autonomous(drive, catapult, imageFiltering, autoPreferences);
    

    public void robotInit(){
        imageFiltering.cameraRingLight.setDirection(Relay.Direction.kForward);
        tCompressor.airCompressorInit();
        autoPreferences.initAutoPrefs();
        drive.resetEncoders();
        catapult.catapultInit();
        autonomous.gyro.reset();
    }
    
    public void autonomousInit() {
        imageFiltering.cameraRingLight.set(Relay.Value.kOn);
        autoPreferences.getAutoSettings();
        autonomous.getAutonomousPreferencesData();
        autonomous.runAutonInit();
        tCompressor.runAirCompressor();
        drive.resetEncoders();
        drive.startEncoders();
    }

    public void autonomousPeriodic() {
        imageFiltering.runImageFiltering();
        tCompressor.runAirCompressor();
        autonomous.runAuton();
    }
    
    public void autonomousDisable() {
        drive.resetEncoders();
    }

    public void teleopInit() {
        imageFiltering.cameraRingLight.set(Relay.Value.kOn);
        drive.resetEncoders();
        drive.startEncoders();
        autonomous.gyro.reset();
    }

    public void teleopPeriodic() {
       SmartDashboard.putNumber("Gyro", autonomous.gyro.getAngle());
        drive.runTankDrive();
        tCompressor.compressorDebug();
        tCompressor.runAirCompressor();
        imageFiltering.setCameraLED(leftStick.getRawButton(4), leftStick.getRawButton(5));
        catapult.runCatapult();
    }

    public void teleopDisable() {
        tCompressor.compressor.set(Relay.Value.kOff);
        imageFiltering.cameraRingLight.set(Relay.Value.kOff);
    }

    public void testPeriodic() {
    }
}
