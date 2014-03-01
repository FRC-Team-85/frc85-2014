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

    Joystick leftStick = new Joystick(Addresses.LEFT_STICK);
    Joystick rightStick = new Joystick(Addresses.RIGHT_STICK);
    OperatorPanel operatorPanel = new OperatorPanel();
    Drive drive = new Drive(leftStick, rightStick);
    Catapult catapult = new Catapult(operatorPanel);
    TylersCompressor tCompressor = new TylersCompressor();
    ImageFiltering imageFiltering = new ImageFiltering();
    Autonomous autonomous = new Autonomous(drive, catapult, imageFiltering);

    public void robotInit(){
        imageFiltering.cameraRingLight.setDirection(Relay.Direction.kForward);
        tCompressor.airCompressorInit();
        drive.resetEncoders();
        autonomous.gyro.reset();
    }
    public void autonomousInit() {
        imageFiltering.cameraRingLight.set(Relay.Value.kOn);
        tCompressor.runAirCompressor();
        drive.resetEncoders();
        drive.startEncoders();
    }

    public void autonomousPeriodic() {
        imageFiltering.runImageFiltering();
        tCompressor.runAirCompressor();
    }

    public void teleopInit() {
        imageFiltering.cameraRingLight.set(Relay.Value.kOn);
        drive.resetEncoders();
        drive.startEncoders();
        autonomous.gyro.reset();
        catapult.catapultInit();
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
