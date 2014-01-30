/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ImageFiltering {
    
    
    NetworkTable networkTable = NetworkTable.getTable("SmartDashboard");
    private boolean blob = false;
   
    /*
     * Gets Blobs from Roborealm and checks for hot goal and switches the variable "blob" if true
     */
    private void getBlob() {
        try {
            if (networkTable.getBoolean("Blob") == true) {
                blob = true;
            } else {
                blob = false;
            }
        } catch (Exception ex) {
            SmartDashboard.putString("Exception", "Network Tables Not Found");
        }
        SmartDashboard.putBoolean("Blob", blob);
    }
    
    private void runDebug(){
        SmartDashboard.putBoolean("RoboRealm Blob", networkTable.getBoolean("Blob"));
    }
    
    public void runImageFiltering(){
        getBlob();
        runDebug();
    }
}
