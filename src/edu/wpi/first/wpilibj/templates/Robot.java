/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

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
    Compressor compressor = new Compressor(Addresses.AIR_COMPRESSOR_PRESSURE_SWITCH, Addresses.AIR_COMPRESSOR_SPIKE);
    TylersCompressor tCompressor = new TylersCompressor(compressor);
    ImageFiltering imageFiltering = new ImageFiltering();
    //Autonomous autonomous = new Autonomous(drive, catapult, imageFiltering);

    public void robotInit(){
        imageFiltering.cameraRingLight.setDirection(Relay.Direction.kForward);
        tCompressor.compressor.setRelayValue(Relay.Value.kForward);
        drive.resetEncoders();
    }
    public void autonomousInit() {
        imageFiltering.cameraRingLight.set(Relay.Value.kOn);
    }

    public void autonomousPeriodic() {
        imageFiltering.runImageFiltering();
        tCompressor.runAirCompressor();
    }

    public void teleopInit() {
        imageFiltering.cameraRingLight.set(Relay.Value.kOn);
        drive.resetEncoders();
    }

    public void teleopPeriodic() {
        tCompressor.compressorDebug();
        tCompressor.runAirCompressor();
        imageFiltering.setCameraLED(leftStick.getRawButton(4), leftStick.getRawButton(5));
        drive.runTankDrive();
        catapult.runCatapult();
    }

    public void teleopDisable() {
        compressor.stop();
        imageFiltering.cameraRingLight.set(Relay.Value.kOff);
    }

    public void testPeriodic() {
    }
}
