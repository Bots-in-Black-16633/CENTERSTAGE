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
    public  Action getBackStageParkAutoAction(int color, int side){
        if(color == AutoUtil.BLUE){
            if(side == AutoUtil.RIGHT){
                return drive.actionBuilder(drive.pose)
                        .strafeTo(new Vector2d(35,19.5                                          ))
                        .strafeTo(new Vector2d(48, 19.5))

                        .build();
            }
            else if(side == AutoUtil.LEFT){
                return drive.actionBuilder(drive.pose)

                        .strafeTo(new Vector2d(40,64))
                        .strafeTo(new Vector2d(48, 64))
                        .build();
            }
            else return null;
        }else if(color == AutoUtil.RED){
            if(side == AutoUtil.LEFT){
                return drive.actionBuilder(drive.pose)
                        .strafeTo(new Vector2d(37,-10))
                        .strafeTo(new Vector2d(48, -10))

                        .build();
            }
            else if(side == AutoUtil.RIGHT){
                return drive.actionBuilder(drive.pose)
                        .strafeTo(new Vector2d(45,-60))

                        .strafeTo(new Vector2d(60,-60))

                        .build();
            }
            else return null;
        }
        else return null;
    }

    public  Action getBackdropAutoAction(int color, int side, int zone){
        if(color==AutoUtil.RED){
            if(side == AutoUtil.LEFT){
                if(zone==1){
                    //Works
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,-44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,-15))
                            .strafeTo(new Vector2d(12, -15))
                            .strafeToLinearHeading(new Vector2d(40,-15),Math.toRadians(-180))
                            .strafeTo(new Vector2d(40, -30))
                            .build();
                }
                else if(zone==2){
                    //Works
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-45,-46))
                            .splineToLinearHeading(new Pose2d(-55, -15, Math.toRadians(180)), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(30, -15))
                            .strafeToLinearHeading(new Vector2d(37,-36.5),Math.toRadians(-180))

                            .build();
                }
                //Not tested, probably doesn't work
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,-44))
                            .strafeTo(new Vector2d(-36,-15))
                            .strafeTo(new Vector2d(12, -15))
                            .strafeToLinearHeading(new Vector2d(40,-12),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(40, -45))
                            .build();

            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,-44))
                            .strafeToLinearHeading(new Vector2d(35,-30), Math.toRadians(180))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(12,-44))
                            .strafeToLinearHeading(new Vector2d(40,-44), Math.toRadians(210))

                            .build();
                }
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,-50))
                            .strafeToLinearHeading(new Vector2d(37,-55), Math.toRadians(180))
                            .strafeTo(new Vector2d(37,-40))

                            .build();
            }
            else return null;
        }
        else if(color ==AutoUtil.BLUE){
            if(side == AutoUtil.LEFT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,44))
                            .strafeToLinearHeading(new Vector2d(40,44), Math.toRadians(180))

                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(12,44))
                            .strafeToLinearHeading(new Vector2d(35,40), Math.toRadians(180))

                            .build();
                }
                else return drive.actionBuilder(drive.pose)
                            .strafeToLinearHeading(new Vector2d(37,38), Math.toRadians(180))
                            .build();
            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    /*return drive.actionBuilder(drive.pose)
                            .strafeToConstantHeading(new Vector2d(-40, 37))
                            .strafeTo(new Vector2d(-34,44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,10))
                            .strafeTo(new Vector2d(12, 10))
                            .strafeToLinearHeading(new Vector2d(48,12), Math.toRadians(230))
                            .build();

                     */
                    //Good
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,37))
                            .strafeTo(new Vector2d(-36,15))
                            .strafeToLinearHeading(new Vector2d(40,15),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(40, 47))
                            .build();

                }
                else if(zone==2){
                    //Good
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,44))
                            .strafeToConstantHeading(new Vector2d(-50, 44))
                            .splineToLinearHeading(new Pose2d(-50, 12, Math.toRadians(180)), Math.toRadians(250))
                            .strafeTo(new Vector2d(12, 13))
                            .strafeToConstantHeading(new Vector2d(38,14))
                            .strafeToConstantHeading(new Vector2d(38, 45))
                            .build();
                }
                //Good
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-36,12))
                            .strafeTo(new Vector2d(12, 13))
                            .strafeToLinearHeading(new Vector2d(40,15), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(40, 35))
                            .build();
            }
            else return null;
        }
        else return null;
    }
    public  Action getBackdropAutoActionNoAprilTag(int color, int side, int zone){
        if(color==AutoUtil.RED){
            if(side == AutoUtil.LEFT){
                if(zone==1){
                    //Works
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-34,-44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,-15))
                            .strafeTo(new Vector2d(12, -15))
                            .strafeToLinearHeading(new Vector2d(40,-23),Math.toRadians(-180))
                            .strafeTo(new Vector2d(40, -27))
                            .strafeToConstantHeading(new Vector2d(50, -32))
                            .build();
                }
                else if(zone==2){
                    //Works
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-45,-46))
                            .splineToLinearHeading(new Pose2d(-55, -15, Math.toRadians(180)), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(12, -15))
                            .strafeToLinearHeading(new Vector2d(40,-40),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(50, -40))
                            .build();
                }
                //Not tested, probably doesn't work
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,-44))
                            .strafeTo(new Vector2d(-36,-15))
                            .strafeTo(new Vector2d(12, -15))
                            .strafeToLinearHeading(new Vector2d(40,-12),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(40, -45))
                            .strafeToConstantHeading(new Vector2d(50, -48))
                            .build();

            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,-44))
                            .strafeToLinearHeading(new Vector2d(35,-40), Math.toRadians(200))
                            .strafeToConstantHeading(new Vector2d(50, -32))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(12,-44))
                            .strafeToLinearHeading(new Vector2d(40,-44), Math.toRadians(210))
                            .strafeToConstantHeading(new Vector2d(50, -40))
                            .build();
                }
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,-50))
                            .strafeToLinearHeading(new Vector2d(37,-55), Math.toRadians(180))
                            .strafeTo(new Vector2d(37,-37))
                            .strafeToConstantHeading(new Vector2d(50, -48))
                            .build();
            }
            else return null;
        }
        else if(color ==AutoUtil.BLUE){
            if(side == AutoUtil.LEFT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,44))
                            .strafeToLinearHeading(new Vector2d(40,44), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(50, 48))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(12,44))
                            .strafeToLinearHeading(new Vector2d(35,40), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(50, 36))
                            .build();
                }
                else return drive.actionBuilder(drive.pose)
                            .strafeToLinearHeading(new Vector2d(37,38), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(50, 30))
                            .build();
            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    /*return drive.actionBuilder(drive.pose)
                            .strafeToConstantHeading(new Vector2d(-40, 37))
                            .strafeTo(new Vector2d(-34,44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,10))
                            .strafeTo(new Vector2d(12, 10))
                            .strafeToLinearHeading(new Vector2d(48,12), Math.toRadians(230))
                            .build();

                     */
                    //Good
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,37))
                            .strafeTo(new Vector2d(-36,15))
                            .strafeToLinearHeading(new Vector2d(40,15),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(40, 45))
                            .strafeToConstantHeading(new Vector2d(50, 48))
                            .build();

                }
                else if(zone==2){
                    //Good
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,44))
                            .strafeToConstantHeading(new Vector2d(-50, 44))
                            .splineToLinearHeading(new Pose2d(-50, 16, Math.toRadians(180)), Math.toRadians(270))
                            .strafeTo(new Vector2d(12, 16))
                            .strafeToConstantHeading(new Vector2d(38,16))
                            .strafeToConstantHeading(new Vector2d(38, 45))
                            .strafeToConstantHeading(new Vector2d(50, 36))
                            .build();
                }
                //Good
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-36,12))
                            .strafeTo(new Vector2d(12, 13))
                            .strafeToLinearHeading(new Vector2d(40,15), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(40, 35))
                            .strafeToConstantHeading(new Vector2d(50, 30))
                            .build();
            }
            else return null;
        }
        else return null;
    }
    public  Action getSpikeAutoAction(int color, int side, int zone) {
        if (color == AutoUtil.RED) {
            if (side == AutoUtil.LEFT) {
                if (zone == 1) {
                    //Works
                    return drive.actionBuilder(REDLEFTSTART)
                            .strafeToLinearHeading(new Vector2d(-45.5, -40), Math.toRadians(290)).build();
                }
                else if (zone == 2) {
                    //Works
                    return drive.actionBuilder(REDLEFTSTART)
                            .strafeTo(new Vector2d(-36, -35)).build();
                }
                else {
                    //Not tested but probably works
                    return drive.actionBuilder(REDLEFTSTART)
                            .strafeToConstantHeading(new Vector2d(-48.07, -48.07))
                            .strafeToLinearHeading(new Vector2d(-32, -36), Math.toRadians(180))
                            .build();
                }
            }
            else if (side == AutoUtil.RIGHT) {
                if (zone == 1) {
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(24, -48))
                            .strafeToLinearHeading(new Vector2d(9, -30), Math.toRadians(0.00))
                            .build();

                }
                else if (zone == 2) {
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeTo(new Vector2d(12, -35)).build();


                } else {
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeToLinearHeading(new Vector2d(19, -40), Math.toRadians(250)).build();
                }
            }
            else return null;
        }
        else if (color == AutoUtil.BLUE) {
            if (side == AutoUtil.LEFT) {
                if (zone == 1) {
                    return drive.actionBuilder(BLUELEFTSTART)
                            .strafeToLinearHeading(new Vector2d(20, 40), Math.toRadians(110)).build();
                } else if (zone == 2) {
                    return drive.actionBuilder(BLUELEFTSTART)
                            .strafeTo(new Vector2d(12, 35)).build();
                } else {
                    return drive.actionBuilder(BLUELEFTSTART)
                            .strafeToConstantHeading(new Vector2d(24.06, 48.51))
                            .strafeToLinearHeading(new Vector2d(7.83, 35.33), Math.toRadians(0))
                            .strafeToConstantHeading(new Vector2d(10, 35.33))
                            .build();
                }
            }
            else if (side == AutoUtil.RIGHT) {
                if (zone == 1) {
                    //Good
                    return drive.actionBuilder(BLUERIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(-48.00, 48.00))
                            .strafeToLinearHeading(new Vector2d(-35, 37.43), Math.toRadians(180))
                            .build();
                } else if (zone == 2) {
                    //Good
                    return drive.actionBuilder(BLUERIGHTSTART)
                            .strafeTo(new Vector2d(-36, 35))
                            .build();


                } else {
                    //Not Tested
                    return drive.actionBuilder(BLUERIGHTSTART)
                            .strafeToLinearHeading(new Vector2d(-44, 38), Math.toRadians(70)).build();
                }

            }
            else return null;
        } else return null;
    }


    public  Action getSpikeParkAction(int color, int side, int zone){
        if(color==AutoUtil.RED){
            if(side == AutoUtil.LEFT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-34,-44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,-12))
                            .strafeTo(new Vector2d(60, -12))

                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,-44))
                            .splineToConstantHeading(new Vector2d(-55, -12), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(60, -12))


                            .build();
                }
                else {return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,-44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-36,-12))
                            .strafeTo(new Vector2d(60, -12))
                            .build();}

            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,-44))
                            .strafeToLinearHeading(new Vector2d(14, -60), Math.toRadians(270))
                            .strafeTo(new Vector2d(57,-65))

                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(12,-44))
                            .strafeTo(new Vector2d(12,-60))
                            .strafeTo(new Vector2d(57,-65))
                            .build();
                }
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,-50))
                            .strafeToLinearHeading(new Vector2d(14, -60), Math.toRadians(270))
                            .strafeTo(new Vector2d(57,-65))

                            .build();
            }
            else return null;
        }
        else if(color ==AutoUtil.BLUE){
            if(side == AutoUtil.LEFT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,50))
                            .strafeToLinearHeading(new Vector2d(14, 60), Math.toRadians(90))
                            .strafeTo(new Vector2d(60,60))

                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(12,44))
                            .strafeTo(new Vector2d(12,60))
                            .strafeTo(new Vector2d(60,60))
                            .build();
                }
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,44))
                            .strafeToLinearHeading(new Vector2d(14, 60), Math.toRadians(90))
                            .strafeTo(new Vector2d(60,60))

                            .build();
            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-34,44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,10))
                            .strafeTo(new Vector2d(60, 10))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,44))
                            .splineToConstantHeading(new Vector2d(-55, 10), Math.toRadians(270))
                            .strafeTo(new Vector2d(60, 10))
                            .build();
                }
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-36,10))
                            .strafeTo(new Vector2d(60, 10))

                            .build();
            }
            else return null;
        }
        else return null;
    }


    //Note: getPixelStackAutoAction is vesitigial
    public  Action getPixelStackAutoAction(int color){
        if(color == AutoUtil.RED){
            return drive.actionBuilder(drive.pose)
                    .splineToLinearHeading(new Pose2d(31.51, -6.30, Math.toRadians(180)), Math.toRadians(180.00))
                    .splineToConstantHeading(new Vector2d(-60.35, -9.93), Math.toRadians(180))
                    .waitSeconds(1)
                    .strafeTo(new Vector2d(50, -12))
                    .build();

        }
        else if(color == AutoUtil.BLUE){
            return drive.actionBuilder(drive.pose)
                    .splineToLinearHeading(new Pose2d(31.51, 6.30, Math.toRadians(180)), Math.toRadians(180.00))
                    .splineToConstantHeading(new Vector2d(-60.35, 9.93), Math.toRadians(180))
                    .waitSeconds(1)
                    .strafeTo(new Vector2d(50, 12))
                    .build();
        }
        else return null;
    }

    public Action getBackdropToStackAutoAction(int color) {
        if(color==AutoUtil.BLUE)
        {
            return drive.actionBuilder(drive.pose)
                    .splineTo(new Vector2d(24.75, 13.09), Math.toRadians(192.99))
                    .splineTo(new Vector2d(-50, 10.02), Math.toRadians(179.28))
                    .build();
        }
        else
        {
            return drive.actionBuilder(drive.pose)
                    .splineTo(new Vector2d(24.75, -13.09), Math.toRadians(167.01))
                    .splineTo(new Vector2d(-55, -10.43), Math.toRadians(179.28))
                    .build();

        }
    }



    //These paths are pretty sub optimal, if we need to shave some time off the auto
    //Converting these to splines would be a good start.
    public Action getStackToBackdropAutoAction(int color, int zone) {
        if(color==AutoUtil.RED)
        {
            return drive.actionBuilder(drive.pose)
                    .strafeToConstantHeading(new Vector2d(43.00, -14.20))
                    .strafeToConstantHeading(new Vector2d(48, -40.56))
                    .build();

        }
        else
        {
            return drive.actionBuilder(drive.pose)
                    .strafeToConstantHeading(new Vector2d(43.00, 14.20))
                    .strafeToConstantHeading(new Vector2d(50.30, 40.56))
                    .strafeToConstantHeading(new Vector2d(52, 41))
                    .build();

        }
    }


    public Action getQuickBackdropAutoAction(int color, int side, int zone)
    {
        if(color==RED)
        {
            if(side==LEFT)
            {
                if(zone==1) {
                    return drive.actionBuilder(REDLEFTSTART)
                            .strafeToConstantHeading(new Vector2d(-47.45, -40.70))
                            .strafeToConstantHeading(new Vector2d(-38.25, -43.98))
                            .splineTo(new Vector2d(-25.16, -11.05), Math.toRadians(27.99))
                            .splineTo(new Vector2d(18.82, -19.84), Math.toRadians(-14.82))
                            .splineToSplineHeading(new Pose2d(50.73, -30.48, Math.toRadians(180.00)), Math.toRadians(17.32))
                            .strafeToConstantHeading(new Vector2d(48.5, -15))
                            .build();

                }
                else if(zone ==2) {
                    return drive.actionBuilder(REDLEFTSTART)
                            .strafeToConstantHeading(new Vector2d(-35.90, -34.07))
                            .strafeToConstantHeading(new Vector2d(-50.70, -37.32))
                            .splineTo(new Vector2d(-45.43, -18.86), Math.toRadians(29.52))
                            .splineTo(new Vector2d(10.14, -17.44), Math.toRadians(-21.97))
                            .splineToSplineHeading(new Pose2d(50.00, -36.00, Math.toRadians(-180.00)), Math.toRadians(-11.94))
                            .build();

                }
                else if(zone ==3) {
                    return drive.actionBuilder(new Pose2d(-36.00, -63.00, Math.toRadians(270.00)))
                            .splineTo(new Vector2d(-29.61, -37.32), Math.toRadians(45.00))
                            .strafeToConstantHeading(new Vector2d(-32.86, -38.94))
                            .strafeToLinearHeading(new Vector2d(-45.03, -15.21), Math.toRadians(-180.00))
                            .splineTo(new Vector2d(12.57, -17.04), Math.toRadians(-27.05))
                            .splineTo(new Vector2d(50.00, -42.00), Math.toRadians(315.00))
                            .build();

                }
            }
            else if(side == RIGHT)
            {
                if(zone==1) {
                    return drive.actionBuilder(REDRIGHTSTART).setReversed(true)
                            .splineToSplineHeading(new Pose2d(10.55, -30.83, Math.toRadians(0.00)), Math.toRadians(180.00))
                            .setReversed(false)
                            .splineToSplineHeading(new Pose2d(50.00, -30.00, Math.toRadians(180.00)), Math.toRadians(0.00))
                            .build();

                }
                else if(zone ==2) {
                    return drive.actionBuilder(REDRIGHTSTART)
                            .setReversed(true)
                            .splineToSplineHeading(new Pose2d(15.41, -34.48, Math.toRadians(270.00)), Math.toRadians(90.00))
                            .setReversed(false)
                            .splineTo(new Vector2d(50.00, -36.00), Math.toRadians(0.00))
                            .build();

                }
                else if(zone==3) {
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(23.12, -43.00))
                            .splineToSplineHeading(new Pose2d(50.00, -42.00, Math.toRadians(180.00)), Math.toRadians(0.00))
                            .build();

                }
            }
        }
        else if(color==BLUE)
        {
            if(side==LEFT) {
                if(zone==1) {

                }
                else if(zone ==2) {

                }
                else {

                }
            }
            else if(side==RIGHT) {
                if(zone==1) {
                    return drive.actionBuilder(BLUERIGHTSTART)
                            .splineTo(new Vector2d(-29.61, 37.32), Math.toRadians(45.00))
                            .strafeToConstantHeading(new Vector2d(-32.86, 38.94))
                            .strafeToLinearHeading(new Vector2d(-45.03, 15.21), Math.toRadians(-180.00))
                            .splineTo(new Vector2d(12.57, 17.04), Math.toRadians(-27.05))
                            .splineTo(new Vector2d(50.00, 42.00), Math.toRadians(315.00))
                            .build();
                }
                else if(zone ==2) {
                    return drive.actionBuilder(BLUERIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(-35.90, 34.07))
                            .strafeToConstantHeading(new Vector2d(-50.70, 37.32))
                            .splineTo(new Vector2d(-45.43, 18.86), Math.toRadians(29.52))
                            .splineTo(new Vector2d(10.14, 17.44), Math.toRadians(-21.97))
                            .splineToSplineHeading(new Pose2d(50.00, 36.00, Math.toRadians(-180.00)), Math.toRadians(-11.94))
                            .build();
                }
                else {
                    return drive.actionBuilder(BLUERIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(-47.45, 40.70))
                            .strafeToConstantHeading(new Vector2d(-38.25, 43.98))
                            .splineTo(new Vector2d(-25.16, 11.05), Math.toRadians(27.99))
                            .splineTo(new Vector2d(18.82, 19.84), Math.toRadians(-14.82))
                            .splineToSplineHeading(new Pose2d(50.73, 30.48, Math.toRadians(180.00)), Math.toRadians(17.32))
                            .strafeToConstantHeading(new Vector2d(48.5, 15))
                            .build();
                }
            }
        }
        return null;
    }







    public static void delay(double t) {
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while (runtime.seconds() < t) {
        }
    }
}
