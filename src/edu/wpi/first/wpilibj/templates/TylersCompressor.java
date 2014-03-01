/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*; 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class TylersCompressor {

    Relay compressor;
    DigitalInput pressureSwitch;
    
    public TylersCompressor() {
        compressor = new Relay(Addresses.AIR_COMPRESSOR_SPIKE);
        pressureSwitch = new DigitalInput(Addresses.AIR_COMPRESSOR_PRESSURE_SWITCH); 
    }

    public void airCompressorInit(){
        compressor.setDirection(Relay.Direction.kForward);
    }
    
    public void runAirCompressor() {
        if (pressureSwitch.get()) {
            compressor.set(Relay.Value.kOff);
        } else {
            compressor.set(Relay.Value.kOn);
        }
    }
    
    public void compressorDebug(){
        SmartDashboard.putBoolean("pressureSwitch", pressureSwitch.get());
        
    }
}
