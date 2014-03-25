/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Preferences;



public class AutoPreferences {
    
    DriverStationLCD driverStationLCD;
    public static final String driveDistanceKey = "Drive_Dist";
    public static final String prefKey = "_Pref";
    public static final String modeKey = "_Mode";
    
    double driveDistanceSetting;
    int mode;
    
    Preferences autoPreferences;
    
    public AutoPreferences() {
        autoPreferences = Preferences.getInstance();
        driverStationLCD = DriverStationLCD.getInstance();
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
    
    public void displayAutoModes() {
        driverStationLCD.println(DriverStationLCD.Line.kUser1, 1, "Mode 1: Drive Only");
        driverStationLCD.println(DriverStationLCD.Line.kUser2, 1, "Mode 2: Drive then Shoot");
        driverStationLCD.println(DriverStationLCD.Line.kUser3, 1, "Mode 3: Shoot then Drive");
        driverStationLCD.println(DriverStationLCD.Line.kUser4, 1, "Mode 4: Double Ball Auton then Drive");
        driverStationLCD.updateLCD();
    }
}
