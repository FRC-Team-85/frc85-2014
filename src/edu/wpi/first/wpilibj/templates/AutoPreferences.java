/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Preferences;


public class AutoPreferences {

    public static final String driveDistanceKey = "Drive_Dist";
    public static final String rotationAngleKey = "Rotation_Angle";
    public static final String enableShooterKey = "Enable_Shooter";
    public static final String enableIntakeKey = "Enable_Intake_Arm";
    public static final String prefKey = "_Pref";
    
    double[] autoSettings;
    boolean enableShooterSetting;
    boolean enableIntakeSetting;
    
    Preferences autoPreferences;
    
    public AutoPreferences() {
        autoSettings = new double[2];
        autoPreferences = Preferences.getInstance();
    }
    
    public void getAutoSettings() {
        autoSettings[0] = autoPreferences.getDouble(driveDistanceKey + prefKey, 3);
        autoSettings[1] = autoPreferences.getDouble(rotationAngleKey + prefKey, 180);
        enableShooterSetting = autoPreferences.getBoolean(enableShooterKey + prefKey, false);
        enableIntakeSetting = autoPreferences.getBoolean(enableIntakeKey + prefKey, false);
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
    
    public boolean getShooterSetting() {
        return enableShooterSetting;
    }
    
    public boolean getIntakeSetting() {
        return enableIntakeSetting;
    }
}
