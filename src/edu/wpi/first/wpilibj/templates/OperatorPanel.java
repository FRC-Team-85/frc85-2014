/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class OperatorPanel {
    
    private final int k_Button = 1;
    private final int k_TrussSwitch = 2;
    private final int k_IntakeArmSwitch = 3;
    
    DriverStation driverstation;
    
    public OperatorPanel() {
    }

    public boolean getCatapultButton() {
        DriverStation.getInstance();
        return driverstation.getDigitalIn(k_Button);
    }

    public boolean getTrussSwitch() {
        DriverStation.getInstance();
        return driverstation.getDigitalIn(k_TrussSwitch);
    }

    public boolean getIntakeArmSwitch() {
        DriverStation.getInstance();
        return driverstation.getDigitalIn(k_IntakeArmSwitch);
    }

    public void setCamSlowLED(boolean toggle){
        DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(1, true);
        } else {
            driverstation.setDigitalOut(1, false);
        }
    }
    public void setCamStopLED(boolean toggle) {
        DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(3, true);
        } else {
            driverstation.setDigitalOut(3, false);
        }
    }

    public void setIntakeLED(boolean toggle) {
        DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(5, true);
        } else {
            driverstation.setDigitalOut(5, false);
        }
    }

    public void setTrussLED(boolean toggle) {
        DriverStation.getInstance();
        if (toggle) {
            driverstation.setDigitalOut(7, true);
        } else {
            driverstation.setDigitalOut(7, false);
        }
    }
    
}
