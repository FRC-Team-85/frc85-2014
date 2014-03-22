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
    public static final String shootFirstKey = "Shoot_First";
    public static final String prefKey = "_Pref";
    
    double driveDistanceSetting;
    double rotationSetting;
    boolean enableShooterSetting;
    boolean enableIntakeSetting;
    boolean shootFirstSetting;
    
    Preferences autoPreferences;
    
    public AutoPreferences() {
        autoPreferences = Preferences.getInstance();
    }
    
    public void getAutoSettings() {
        driveDistanceSetting = autoPreferences.getDouble(driveDistanceKey + prefKey, 3);
        rotationSetting = autoPreferences.getDouble(rotationAngleKey + prefKey, 180);
        enableShooterSetting = autoPreferences.getBoolean(enableShooterKey + prefKey, false);
        enableIntakeSetting = autoPreferences.getBoolean(enableIntakeKey + prefKey, false);
        shootFirstSetting = autoPreferences.getBoolean(shootFirstKey + prefKey, true);
    }
    
    public void initAutoPrefs() {
        autoPreferences.save();
    }
    
    public double getDriveDistance() {
        return driveDistanceSetting;
    }
    
    public double getRotationAngle() {
        return rotationSetting;
    }
    
    public boolean getShooterSetting() {
        return enableShooterSetting;
    }
    
    public boolean getIntakeSetting() {
        return enableIntakeSetting;
    }
    
    public boolean getShootFirstSetting() {
        return shootFirstSetting;
    }
}
