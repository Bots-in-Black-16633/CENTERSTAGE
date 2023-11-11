package org.firstinspires.ftc.teamcode.auto.util;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.drive.Drive;
@Config
public class AutoUtil {

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
    public static  Pose2d REDRIGHTSTART = new Pose2d(12.00, -71.5, Math.toRadians(90));
    public static  Pose2d REDLEFTSTART = new Pose2d(-12.00, -71.5, Math.toRadians(90));
    public static  Pose2d BLUERIGHTSTART = new Pose2d(-12.00, 71.5, Math.toRadians(270));
    public static  Pose2d BLUELEFTSTART = new Pose2d(12.00, 71.5, Math.toRadians(270));


    public static final int BLUESIDE =0;
    public static final int REDSIDE = 1;

    public static final int RIGHTSIDE = 0;
    public static final int LEFTSIDE = 0;

    Drive drive;
    Pose2d startPose;
    int sideColor = 0;
    int side = 0;
    public AutoUtil(Drive drive){
        this.drive = drive;
        startPose = drive.pose;
        if(startPose.position.y > 0)sideColor = BLUESIDE;
        else sideColor = REDSIDE;
        if(startPose.position.x < 0){
            if(sideColor == REDSIDE)side = LEFTSIDE;
            else side = RIGHTSIDE;
        }
        else{
            if(sideColor == REDSIDE)side = RIGHTSIDE;
            else side = LEFTSIDE;
        }

    }


//    public Action getBlueToBackdrop(int zone){
//       return drive.actionBuilder(getZone(zone))
//
//                .splineToLinearHeading(new Pose2d(12.00, 1*36.29, Math.toRadians(-30.00)), Math.toRadians(-90.00))
//                .lineToY(67.42)
//                .splineToLinearHeading(new Pose2d(51.95, 36.67, Math.toRadians(1.97)), Math.toRadians(-37.34)).build();
//    }
    public Action getRedToBackdrop(){
        return drive.actionBuilder(new Pose2d(12.00, -71.5, Math.toRadians(90)))
                .splineToLinearHeading(new Pose2d(12.00, -1*36.29, Math.toRadians(45)), Math.toRadians(90))
                .lineToY( -67.42)
                .splineToLinearHeading(new Pose2d(51.95, -36.67, Math.toRadians(1.97)), Math.toRadians(0)).build();
    }

    public static void delay(double t) {
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while (runtime.seconds() < t) {
        }
    }
}
