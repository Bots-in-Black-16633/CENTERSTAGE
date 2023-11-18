package org.firstinspires.ftc.teamcode.auto.util;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.drive.Drive;

import java.util.Vector;

@Config
public class AutoUtil {
//Hi Cole! -Russ
    //Field is 12ft by 12 ft; Grid looks like this

    /**              72 in 90 degrees
     *                     |
     *        Blue Righ    /  Blue Left
     *                     |
     *                     |
 *180 deg_______________________________________ 72 in  0 degress
     *                     |
     *          Red Left   | Red Right
     *                     |
     *
     *                     270 degrees
     */
    public static  Pose2d REDRIGHTSTART = new Pose2d(12.00, -63, Math.toRadians(270));
    public static  Pose2d REDLEFTSTART = new Pose2d(-36.00, -63, Math.toRadians(270));
    public static  Pose2d BLUERIGHTSTART = new Pose2d(-36, 63, Math.toRadians(90));
    public static  Pose2d BLUELEFTSTART = new Pose2d(12.00, 63, Math.toRadians(90));


    public static final int BLUE =0;
    public static final int RED = 1;

    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    Drive drive;
    Pose2d startPose;

    public AutoUtil(Drive drive){
        this.drive = drive;
        startPose = drive.pose;
    }

    public Action getBackStageParkAutoAction(int color, int side, int zone){
        return null;
    }

    public Action getSpikeMarkParkAutoAction(int color, int side, int zone){
        return null;
    }

    public Action getSpikeAutoAction(int color, int side, int zone){
        if(color==AutoUtil.RED){
            if(side == AutoUtil.LEFT){
                 if(zone==1){
                     return null;

                 }
                 else if(zone==2){
                     return null;

                 }
                 else {
                     return null;

                 }
            }
            else if(side == AutoUtil.RIGHT){
                if(zone == 1){

                    return null;

                }
                else if(zone ==2){
                    return null;

                }
                else {
                    return null;

                }
            }
            else return null;
        }
        else if(color ==AutoUtil.BLUE){
            if(side == AutoUtil.LEFT){
                if(zone ==1){
                    return null;

                }
                else if(zone ==2){
                    return null;

                }else{
                    return null;

                }
            }
            else if(side == AutoUtil.RIGHT){
                if(zone == 1){
                    return null;
                }
                else if(zone==2){
                    return null;

                }
                else{
                    return null;

                }
            }
            else return null;
        }
        else return null;
    }

    public Action getBackdropAutoAction(int color, int side, int zone){
        if(color==AutoUtil.RED){
            if(side == AutoUtil.LEFT){
                if(zone==1){
                    return null;
                }
                else if(zone==2){
                    return null;
                }
                else return null;

            }
            else if(side == AutoUtil.RIGHT){
                if(zone == 1){
                    return null;
                }
                else if(zone ==2){
                    return null;
                }
                else {
                    return null;
                }
            }
            else return null;
        }
        else if(color ==AutoUtil.BLUE){
            if(side == AutoUtil.LEFT){
                if(zone ==1){
                    return null;
                }
                else if(zone ==2){
                   return null;
                }else{
                    return null;
                }
            }
            else if(side == AutoUtil.RIGHT){
                if(zone == 1){
                    return null;
                }
                else if(zone==2){
                    return null;
                }
                else{
                    return null;
                }
            }
            else return null;
        }
        else return null;
    }

    public Action getPixelStackAutoAction(int color, int side, int zone){
        return null;
    }








    public static void delay(double t) {
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while (runtime.seconds() < t) {
        }
    }
}
