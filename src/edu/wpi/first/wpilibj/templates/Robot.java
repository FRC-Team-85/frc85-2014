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
    Joystick opStick = new Joystick(Addresses.OP_STICK);
    
    Drive drive = new Drive(leftStick, rightStick);
    Catapult catapult = new Catapult(leftStick, rightStick, opStick);
    Compressor compressor = new Compressor(Addresses.AIR_COMPRESSOR_INPUT, Addresses.AIR_COMPRESSOR_OUTPUT);
    //ImageFiltering imageFiltering = new ImageFiltering();

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousInit(){
        compressor.start();
    }
    public void autonomousPeriodic() {
    //imageFiltering.runImageFiltering();    
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    drive.runTankDrive();
    catapult.runCatapult();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
