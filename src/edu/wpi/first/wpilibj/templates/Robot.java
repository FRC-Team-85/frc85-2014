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
    DriverStation driverstation;
    Drive drive = new Drive(leftStick, rightStick);
    //Catapult catapult = new Catapult();
    ImageFiltering imageFiltering = new ImageFiltering();
    
    private final int k_FireButton = 2;
    private final int k_IntakeArmSwitch = 4;
    private final int k_TrussSwitch = 6;
    private final int k_FireButtonLED = 1;
    

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    //imageFiltering.runImageFiltering();    
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        setFireButtonLED(getCatapultButton());
        setTrussLED(getTrussSwitch());
        setIntakeLED(getIntakeArmSwitch());
    
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        
    }
   
    public boolean getCatapultButton() {
        driverstation = DriverStation.getInstance();
        return driverstation.getDigitalIn(k_FireButton);
    }

    public boolean getTrussSwitch() {
        driverstation = DriverStation.getInstance();
        return driverstation.getDigitalIn(k_TrussSwitch);
    }

    public boolean getIntakeArmSwitch() {
        driverstation = DriverStation.getInstance();
        return driverstation.getDigitalIn(k_IntakeArmSwitch);
    }

    public void setCamSlowLED(boolean toggle){
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(1, true);
        } else {
            driverstation.setDigitalOut(1, false);
        }
    }
    public void setCamStopLED(boolean toggle) {
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(3, true);
        } else {
            driverstation.setDigitalOut(3, false);
        }
    }

    public void setIntakeLED(boolean toggle) {
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(5, true);
        } else {
            driverstation.setDigitalOut(5, false);
        }
    }

    public void setTrussLED(boolean toggle) {
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(7, true);
        } else {
            driverstation.setDigitalOut(7, false);
        }
    }
    
    public void setFireButtonLED(boolean activationToggle) {
        driverstation = DriverStation.getInstance();
        if (activationToggle){
            driverstation.setDigitalOut(2, true);
        } else {
            driverstation.setDigitalOut(2, false);
        }
    }    
}
    

