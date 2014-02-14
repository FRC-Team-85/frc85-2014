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
    
    Joystick leftStick = new Joystick(1);
    Joystick rightStick = new Joystick(2);
    DriverStation operatorPanel;
    Drive drive = new Drive(leftStick, rightStick);
    //Catapult catapult = new Catapult();
    ImageFiltering imageFiltering = new ImageFiltering();
    
    

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    imageFiltering.runImageFiltering();    
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    drive.runTankDrive();
    testLED(rightStick.getRawButton(2), 1);
    testLED(rightStick.getRawButton(3),3);
    testLED(rightStick.getRawButton(4),5);
    testLED(rightStick.getRawButton(5),7);
    
    
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        
    }
    
    private void testLED(boolean button, int inputChannel) {
            operatorPanel = DriverStation.getInstance();
            if (button) {
                operatorPanel.setDigitalOut(inputChannel, true);
            } else {
                operatorPanel.setDigitalOut(inputChannel, false);
            }
    }
    
}
