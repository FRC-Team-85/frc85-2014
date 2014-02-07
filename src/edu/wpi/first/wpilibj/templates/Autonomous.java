/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*;
/**
 *
 * @author Tyler
 */
public class Autonomous {
    Drive drive;
    Catapult catapult;
    ImageFiltering imageFilter;
    public Autonomous(Drive drive, Catapult catapult, ImageFiltering imageFilter){
            this.drive = drive;
            this.catapult = catapult;
            this.imageFilter = imageFilter;
    }
    public void selectState(){
        
        if(imageFilter.getBlob()){
            runProcess1();
        } else {
            runProcess2();
        }
    }
    public void runProcess1(){
        
    }
    public void runProcess2(){
        
    }
}
