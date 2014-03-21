/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Preferences;


public class AutoPreferences {

    public static final String driveDistanceKey = "Drive_Dist";
    public static final String rotationAngleKey = "Rotation_Angle";
    public static final String prefKey = "_Pref";
    
    double[] autoSettings;
    
    Preferences autoPreferences;
    
    public AutoPreferences() {
        autoSettings = new double[2];
        autoPreferences = Preferences.getInstance();
    }
    
    public void getAutoSettings() {
        autoSettings[0] = autoPreferences.getDouble(driveDistanceKey + prefKey, 3);
        autoSettings[1] = autoPreferences.getDouble(rotationAngleKey + prefKey, 180);
    }
    
    public void initAutoPrefs() {
        autoPreferences.save();
    }
    
    public double getDriveDistance() {
        return autoSettings[0];
    }
    
    public double getRotationAngle() {
        return autoSettings[1];
    }
}
