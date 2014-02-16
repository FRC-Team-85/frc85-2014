/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class OperatorPanel {
    
    
    private final int k_CamSlowLED = 1;
    private final int k_CamStopLED = 3;
    private final int k_IntakeLED = 5;
    private final int k_TrussLED = 7;
    
    private final int k_FireButtonLED = 1;
    private final int k_FireButton = 2;
    
    private final int k_IntakeArmSwitch = 4;
    private final int k_TrussSwitch = 6;
    
    DriverStation driverstation;
    
    public OperatorPanel() {
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
            driverstation.setDigitalOut(k_CamSlowLED, true);
        } else {
            driverstation.setDigitalOut(k_CamSlowLED, false);
        }
    }
    public void setCamStopLED(boolean toggle) {
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(k_CamStopLED, true);
        } else {
            driverstation.setDigitalOut(k_CamStopLED, false);
        }
    }

    public void setIntakeLED(boolean toggle) {
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(k_IntakeLED, true);
        } else {
            driverstation.setDigitalOut(k_IntakeLED, false);
        }
    }

    public void setTrussLED(boolean toggle) {
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(k_TrussLED, true);
        } else {
            driverstation.setDigitalOut(k_TrussLED, false);
        }
    }
    
    public void setFireButtonLED(boolean toggle) {
        driverstation = DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(k_FireButtonLED, true);
        } else {
            driverstation.setDigitalOut(k_FireButtonLED, false);
        }
    }
    
}
