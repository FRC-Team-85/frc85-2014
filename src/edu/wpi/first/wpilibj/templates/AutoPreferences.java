/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Preferences;


public class AutoPreferences {

    public static final String driveDistanceKey = "Drive_Dist";
    public static final String prefKey = "_Pref";
    public static final String modeKey = "_Mode";
    
    double driveDistanceSetting;
    int mode;
    
    Preferences autoPreferences;
    
    public AutoPreferences() {
        autoPreferences = Preferences.getInstance();
    }
    
    public void getAutoSettings() {
        driveDistanceSetting = autoPreferences.getDouble(driveDistanceKey + prefKey, 3);
        mode = autoPreferences.getInt(modeKey + prefKey, 0);
        
    }
    
    public void initAutoPrefs() {
        autoPreferences.save();
    }
    
    public double getDriveDistance() {
        return driveDistanceSetting;
    }
}
