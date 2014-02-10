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
    
    public OperatorPanel(){
        driverstation.getInstance();
        
    }
    
    public void getCatapultButton() {
        driverstation.getDigitalIn(k_Button);
    }

    public void getTrussSwitch() {
        driverstation.getDigitalIn(k_TrussSwitch);
    }

    public void getIntakeArmSwitch() {
        driverstation.getDigitalIn(k_IntakeArmSwitch);
    }

    /*
     LEDSwitch for IntakeArmPosition at the home position
     */
    public void setIntakeLED(boolean toggle) {
        if (toggle = true) {
            driverstation.setDigitalOut(1, true);
        } else {
            driverstation.setDigitalOut(1, false);
        }
    }

    public void setCamLED(boolean toggle) {
        if (toggle = true) {
            driverstation.setDigitalOut(3, true);
        } else {
            driverstation.setDigitalOut(3, false);
        }
    }

    public void setTrussLED(boolean toggle) {
        if (toggle = true) {
            driverstation.setDigitalOut(5, true);
        } else {
            driverstation.setDigitalOut(5, false);
        }
    }
    
}
