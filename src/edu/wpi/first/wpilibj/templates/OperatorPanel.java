/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class OperatorPanel {
    
    
    private final int CAM_SLOW_LED = 1;
    private final int CAM_STOP_LED = 3;
    private final int INTAKE_LED = 5;
    private final int TRUSS_LED = 7;
    
    private final int FIRE_BUTTON_LED = 2;
    private final int FIRE_BUTTON = 2;
    
    private final int INTAKE_TOGGLE_SWITCH = 4;
    private final int TRUSS_TOGGLE_SWITCH = 6;
    private final int CAM_EMERGENCY_STOP_TOGGLE_SWITCH = 1;
    
    DriverStation driverstation;
    
    public OperatorPanel() {
    }

    public boolean getCatapultButton() {
           driverstation = DriverStation.getInstance();
        return !driverstation.getDigitalIn(FIRE_BUTTON);
    }
    
    public boolean getCamEmergencyStopSwitch() {
        driverstation = DriverStation.getInstance();
        return driverstation.getDigitalIn(CAM_EMERGENCY_STOP_TOGGLE_SWITCH);
    }

    public boolean getTrussSwitch() {
        driverstation = DriverStation.getInstance();
        return driverstation.getDigitalIn(TRUSS_TOGGLE_SWITCH);
    }

    public boolean getIntakeArmSwitch() {
        driverstation = DriverStation.getInstance();
        return driverstation.getDigitalIn(INTAKE_TOGGLE_SWITCH);
    }

    public void setCamSlowLED(boolean toggle){
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(CAM_SLOW_LED, true);
        } else {
            driverstation.setDigitalOut(CAM_SLOW_LED, false);
        }
    }
    public void setCamStopLED(boolean toggle) {
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(CAM_STOP_LED, true);
        } else {
            driverstation.setDigitalOut(CAM_STOP_LED, false);
        }
    }

    public void setIntakeLED(boolean toggle) {
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(INTAKE_LED, true);
        } else {
            driverstation.setDigitalOut(INTAKE_LED, false);
        }
    }

    public void setTrussLED(boolean toggle) {
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(TRUSS_LED, true);
        } else {
            driverstation.setDigitalOut(TRUSS_LED, false);
        }
    }
    
    public void setFireButtonLED(boolean toggle) { // Spike is normally on
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(FIRE_BUTTON_LED, false);
        } else {
            driverstation.setDigitalOut(FIRE_BUTTON_LED, true);
        }
    }
}
