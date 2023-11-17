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

    public Action getSpikeAutoAction(int side, int color, int zone){
        if(color==AutoUtil.REDSIDE){
            if(side == AutoUtil.LEFTSIDE){
                 if(zone==1){
                     return drive.actionBuilder(REDLEFTSTART)
                             .strafeTo(new Vector2d(-47.66, -38.74))
                             .strafeToLinearHeading(new Vector2d(-48.27, -49.08), Math.toRadians(180.00))
                             .build();
                 }
                 else if(zone==2){
                     return drive.actionBuilder(REDLEFTSTART)
                             .strafeTo(new Vector2d(-36.30, -33.87))
                             .strafeToLinearHeading(new Vector2d(-36.30, -44.21), Math.toRadians(180))
                             .build();
                 }
                 else if(zone == 3){
                     return drive.actionBuilder(REDLEFTSTART)
                             .strafeToConstantHeading(new Vector2d(-48.07, -47.86))
                             .strafeToLinearHeading(new Vector2d(-34.16, -30.07), Math.toRadians(180.00))
                             .strafeToConstantHeading(new Vector2d(-48.47, -30.02))
                             .build();
                 }
            }
            else if(side == AutoUtil.RIGHTSIDE){
                if(zone == 1){


                }
                else if(zone ==2){

                }
                else {

                }
            }
        }
        else if(color ==AutoUtil.BLUESIDE){
            if(side == AutoUtil.LEFTSIDE){
                if(zone ==1){

                }
                else if(zone ==2){

                }else{

                }
            }
            else if(color == AutoUtil.RIGHTSIDE){
                if(zone == 1){
                    drive.actionBuilder(BLUERIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(-48.07, 47.86))
                            .strafeToLinearHeading(new Vector2d(-34.16, 30.07), Math.toRadians(180.00))
                            .strafeToConstantHeading(new Vector2d(-41.52, 30.07))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(BLUERIGHTSTART)
                            .splineTo(new Vector2d(-36.00, 35.00), Math.toRadians(270.00))
                            .strafeToLinearHeading(new Vector2d(-47.55, 43.93), Math.toRadians(180.00))
                            .build();
                }
                else{
                    return drive.actionBuilder(BLUERIGHTSTART)
                            .strafeToLinearHeading(new Vector2d(-47.46, 38.13), Math.toRadians(270.00))
                            .strafeToLinearHeading(new Vector2d(-47.46, 49.08), Math.toRadians(180))
                            .build();
                }
            }
        }
        return null;
    }

    public Action getBackdropAutoAction(int side, int color, int zone){
        if(color==AutoUtil.REDSIDE){
            if(side == AutoUtil.LEFTSIDE){
                if(zone==1){

                }
                else if(zone==2){

                }
                else if(zone == 3){

                }
            }
            else if(side == AutoUtil.RIGHTSIDE){
                if(zone == 1){
                     /*
                    ORIGINAL:
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(12.00, -34.00))
                            .strafeToConstantHeading(new Vector2d(0.00, -34.00))
                            .strafeToLinearHeading(new Vector2d(39.27, -50.11), Math.toRadians(180.00))
                            .strafeToConstantHeading(new Vector2d(50.93, -30.27))
                            .build();
                     */
                    //REFLECTED BLUE LEFT
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(20, -41))
                            .strafeToConstantHeading(new Vector2d(20, -45))
                            .strafeToConstantHeading(new Vector2d(32.65, -48.68))
                            .strafeToLinearHeading(new Vector2d(32.65, -42), Math.toRadians(145))
                            .strafeToConstantHeading(new Vector2d(53, -42))
                            .build();
                }
                else if(zone ==2){
                    /*
                    ORIGINAL
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(11.97, -33.67))
                            .strafeToSplineHeading(new Vector2d(20.48, -41.58), Math.toRadians(180))
                            .splineToSplineHeading(new Pose2d(50.91, -36.71, Math.toRadians(180)), Math.toRadians(0))
                            .build();

                     */
                    //REFLECTED BLUE LEFT
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(12, -37))
                            .strafeToConstantHeading(new Vector2d(12, -39))
                            .strafeToConstantHeading(new Vector2d(22.31, -41.58))
                            .strafeToLinearHeading(new Vector2d(50.30, -37.59), Math.toRadians(150))
                            .build();
                }
                else {
                    /*
                    ORIGINAL
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(23.32, -37.93))
                            .strafeToConstantHeading(new Vector2d(27.99, -49.49))
                            .splineToSplineHeading(new Pose2d(50.70, -43.00, Math.toRadians(180)), Math.toRadians(0.00))
                            .build();
                     */
                    //REFLECTED BLUE LEFT
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(24.00, -48.00))
                            .strafeToLinearHeading(new Vector2d(11.5, -27), Math.toRadians(0))
                            .strafeToLinearHeading(new Vector2d(52.14, -28.5), Math.toRadians(155.00))
                            .strafeToConstantHeading(new Vector2d(56.5, -28.5))
                            .build();
                }
            }
        }
        else if(color ==AutoUtil.BLUESIDE){
            if(side == AutoUtil.LEFTSIDE){
                if(zone ==1){
                    return drive.actionBuilder(BLUELEFTSTART)
                            .strafeToConstantHeading(new Vector2d(20, 41))
                            .strafeToConstantHeading(new Vector2d(20, 45))
                            .strafeToConstantHeading(new Vector2d(32.65, 48.68))
                            .strafeToLinearHeading(new Vector2d(32.65, 42), Math.toRadians(145))
                            .strafeToConstantHeading(new Vector2d(53, 42))
                            .build();
                }
                else if(zone ==2){
                    return drive.actionBuilder(BLUELEFTSTART)
                            .strafeToConstantHeading(new Vector2d(12, 37))
                            .strafeToConstantHeading(new Vector2d(12, 39))
                            .strafeToConstantHeading(new Vector2d(22.31, 41.58))
                            .strafeToLinearHeading(new Vector2d(50.30, 37.59), Math.toRadians(150))
                            .build();
                }else{
                    return drive.actionBuilder(BLUELEFTSTART)
                            .strafeToConstantHeading(new Vector2d(24.00, 48.00))
                            .strafeToLinearHeading(new Vector2d(11.5, 27), Math.toRadians(0))
                            .strafeToLinearHeading(new Vector2d(52.14, 28.5), Math.toRadians(155.00))
                            .strafeToConstantHeading(new Vector2d(56.5, 28.5))
                            .build();
                }
            }
            else if(color == AutoUtil.RIGHTSIDE){
                if(zone == 1){

                }
                else if(zone==2){

                }
                else{

                }
            }
        }
        return null;

    }




    //Experemintal Trajectories: Backdrop from starting on audience side
    public Action getZoneOneRedBackdrop() {
        return drive.actionBuilder(REDLEFTSTART)
                .strafeToConstantHeading(new Vector2d(-44.21, -41.58))
                .strafeToConstantHeading(new Vector2d(-44.21, -44))
                .strafeToConstantHeading(new Vector2d(-35, -44))
                .strafeToConstantHeading(new Vector2d(-35, -37.75))
                .strafeToConstantHeading(new Vector2d(-25.15, -35.29))
                .lineToX(5)
                .strafeToLinearHeading(new Vector2d(51.11, -29.41), Math.toRadians(180))
                .build();

        // Replace with the appropriate return statement
    }

    public Action getZoneTwoRedBackdrop() {
        return drive.actionBuilder(REDLEFTSTART)
                .strafeToConstantHeading(new Vector2d(-36.00, -34.57))
                .strafeToConstantHeading(new Vector2d(-36.00, -38.66))
                .strafeToSplineHeading(new Vector2d(-43.275, -34.57), Math.toRadians(0))
                .splineToSplineHeading(new Pose2d(-43.57, -9.20, Math.toRadians(0.00)), Math.toRadians(48.58))
                .splineTo(new Vector2d(1.02, -7), Math.toRadians(-2.90))
                .splineToSplineHeading(new Pose2d(45.20, -13.30, Math.toRadians(180.00)), Math.toRadians(-1.03))
                .strafeToConstantHeading(new Vector2d(45.20, -36.61))
                .lineToX(53.5)
                .build();

        // Replace with the appropriate return statement
    }

    public Action getZoneThreeRedBackdrop() {
        return drive.actionBuilder(REDLEFTSTART)
                .splineToSplineHeading(new Pose2d(-34.28, -32.25, Math.toRadians(0.00)), Math.toRadians(0.00))
                .strafeToConstantHeading(new Vector2d(-34.07, -2.43))
                .splineToSplineHeading(new Pose2d(11.56, -18.66, Math.toRadians(-25.69)), Math.toRadians(-25.69))
                .splineToSplineHeading(new Pose2d(51.11, -41.78, Math.toRadians(0.00)), Math.toRadians(0.00))
                .build()
                ; // Replace with the appropriate return statement
    }

    // Blue Backdrop Methods
    public Action getZoneOneBlueBackdrop() {
        return drive.actionBuilder(BLUERIGHTSTART)
                .splineToSplineHeading(new Pose2d(-34.28, 32.25, Math.toRadians(360.00)), Math.toRadians(360.00))
                .strafeToConstantHeading(new Vector2d(-34.07, 2.43))
                .splineToSplineHeading(new Pose2d(13.59, 17.85, Math.toRadians(23.74)), Math.toRadians(23.74))
                .splineToSplineHeading(new Pose2d(51.11, 41.78, Math.toRadians(360.00)), Math.toRadians(360.00))
                .build()
                ; // Replace with the appropriate return statement
    }

    public Action getZoneTwoBlueBackdrop() {
        return drive.actionBuilder(BLUERIGHTSTART)
                .splineTo(new Vector2d(-36.91, 34.28), Math.toRadians(269.02))
                .strafeToConstantHeading(new Vector2d(-55.17, 34.28))
                .splineToLinearHeading(new Pose2d(-30.42, -1.83, Math.toRadians(0.00)), Math.toRadians(0.00))
                .splineToSplineHeading(new Pose2d(50.91, 36.10, Math.toRadians(360.00)), Math.toRadians(360.00))
                .build()
                ; // Replace with the appropriate return statement
    }

    public Action getZoneThreeBlueBackdrop() {
        return drive.actionBuilder(BLUERIGHTSTART)
                .splineTo(new Vector2d(-44.21, 41.58), Math.toRadians(246.88))
                .strafeToLinearHeading(new Vector2d(-33.67, 39.75), Math.toRadians(348.91))
                .strafeToLinearHeading(new Vector2d(-25.15, 35.29), Math.toRadians(368.13))
                .splineToSplineHeading(new Pose2d(50.10, 28.19, Math.toRadians(0.00)), Math.toRadians(0.00))
                .build();

    }



    public static void delay(double t) {
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while (runtime.seconds() < t) {
        }
    }
}
