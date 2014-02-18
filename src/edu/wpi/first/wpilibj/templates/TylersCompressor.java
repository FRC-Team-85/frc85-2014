/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.Compressor; 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 * @author Ashley
 */
public class TylersCompressor {

    Compressor compressor;

    public TylersCompressor(Compressor compressor) {
        this.compressor = compressor;
    }

    public void runAirCompressor() {
        if (compressor.getPressureSwitchValue()) {
            compressor.stop();
        } else {
            compressor.start();
        }
    }
    
    public void compressorDebug(){
        SmartDashboard.putBoolean("pressureSwitch", compressor.getPressureSwitchValue());
        
    }
}
