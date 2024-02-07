package org.firstinspires.ftc.teamcode.auto.util;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PoseMap;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.drive.Drive;
import org.firstinspires.ftc.teamcode.util.Constants;

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
    PoseMap transformation;

    public AutoUtil(Drive drive, PoseMap transformation){
        this.drive = drive;
        startPose = drive.pose;
        if(transformation!= null)this.transformation = transformation;
        else this.transformation = pose -> pose;
    }
    public  Action getBackStageParkAutoAction(int color, int side){
        if(color == AutoUtil.BLUE){
            if(side == AutoUtil.RIGHT){
                return drive.actionBuilder(drive.pose, transformation)
                        .strafeTo(new Vector2d(35,19.5                                          ))
                        .strafeTo(new Vector2d(48, 19.5))

                        .build();
            }
            else if(side == AutoUtil.LEFT){
                return drive.actionBuilder(drive.pose, transformation)
                        .strafeTo(new Vector2d(40,68))
                        .strafeTo(new Vector2d(54, 66))
                        .build();
            }
            else return null;
        }else if(color == AutoUtil.RED){
            if(side == AutoUtil.LEFT){
                return drive.actionBuilder(drive.pose, transformation)
                        .strafeTo(new Vector2d(37,-10))
                        .strafeTo(new Vector2d(48, -10))

                        .build();
            }
            else if(side == AutoUtil.RIGHT){
                return drive.actionBuilder(drive.pose, transformation)
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
                    return drive.actionBuilder(drive.pose, transformation)
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
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-45,-46))
                            .splineToLinearHeading(new Pose2d(-55, -15, Math.toRadians(180)), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(30, -15))
                            .strafeToLinearHeading(new Vector2d(37,-36.5),Math.toRadians(-180))

                            .build();
                }
                //Not tested, probably doesn't work
                else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-36,-44))
                            .strafeTo(new Vector2d(-36,-15))
                            .strafeTo(new Vector2d(12, -15))
                            .strafeToLinearHeading(new Vector2d(40,-12),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(40, -45))
                            .build();

            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(14,-44))
                            .strafeToLinearHeading(new Vector2d(35,-30), Math.toRadians(180))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(12,-44))
                            .strafeToLinearHeading(new Vector2d(40,-44), Math.toRadians(210))

                            .build();
                }
                else return drive.actionBuilder(drive.pose, transformation)
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
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(14,44))
                            .strafeToLinearHeading(new Vector2d(40,44), Math.toRadians(180))

                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(12,44))
                            .strafeToLinearHeading(new Vector2d(35,40), Math.toRadians(180))

                            .build();
                }
                else return drive.actionBuilder(drive.pose, transformation)
                            .strafeToLinearHeading(new Vector2d(37,38), Math.toRadians(180))
                            .build();
            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    /*return drive.actionBuilder(drive.pose, transformation)
                            .strafeToConstantHeading(new Vector2d(-40, 37))
                            .strafeTo(new Vector2d(-34,44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,10))
                            .strafeTo(new Vector2d(12, 10))
                            .strafeToLinearHeading(new Vector2d(48,12), Math.toRadians(230))
                            .build();

                     */
                    //Good
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-36,37))
                            .strafeTo(new Vector2d(-36,15))
                            .strafeToLinearHeading(new Vector2d(40,15),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(40, 47))
                            .build();

                }
                else if(zone==2){
                    //Good
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-36,44))
                            .strafeToConstantHeading(new Vector2d(-50, 44))
                            .splineToLinearHeading(new Pose2d(-50, 12, Math.toRadians(180)), Math.toRadians(250))
                            .strafeTo(new Vector2d(12, 13))
                            .strafeToConstantHeading(new Vector2d(38,14))
                            .strafeToConstantHeading(new Vector2d(38, 45))
                            .build();
                }
                //Good
                else return drive.actionBuilder(drive.pose, transformation)
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
                    return drive.actionBuilder(drive.pose, transformation)
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
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-45,-46))
                            .splineToLinearHeading(new Pose2d(-55, -15, Math.toRadians(180)), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(12, -15))
                            .strafeToLinearHeading(new Vector2d(40,-40),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(50, -40))
                            .build();
                }
                //Not tested, probably doesn't work
                else return drive.actionBuilder(drive.pose, transformation)
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
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(14,-44))
                            .strafeToLinearHeading(new Vector2d(35,-40), Math.toRadians(200))
                            .strafeToConstantHeading(new Vector2d(50, -32))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(12,-44))
                            .strafeToLinearHeading(new Vector2d(40,-44), Math.toRadians(210))
                            .strafeToConstantHeading(new Vector2d(50, -40))
                            .build();
                }
                else return drive.actionBuilder(drive.pose, transformation)
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
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(14,44))
                            .strafeToLinearHeading(new Vector2d(40,44), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(50, 48))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(12,44))
                            .strafeToLinearHeading(new Vector2d(35,40), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(50, 36))
                            .build();
                }
                else return drive.actionBuilder(drive.pose, transformation)
                            .strafeToLinearHeading(new Vector2d(37,38), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(50, 30))
                            .build();
            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    /*return drive.actionBuilder(drive.pose, transformation)
                            .strafeToConstantHeading(new Vector2d(-40, 37))
                            .strafeTo(new Vector2d(-34,44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,10))
                            .strafeTo(new Vector2d(12, 10))
                            .strafeToLinearHeading(new Vector2d(48,12), Math.toRadians(230))
                            .build();

                     */
                    //Good
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-36,37))
                            .strafeTo(new Vector2d(-36,15))
                            .strafeToLinearHeading(new Vector2d(40,15),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(40, 49))
                            .strafeToConstantHeading(new Vector2d(50, 49))
                            .build();

                }
                else if(zone==2){
                    //Good
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-36,44))
                            .strafeToConstantHeading(new Vector2d(-50, 44))
                            .splineToLinearHeading(new Pose2d(-50, 16, Math.toRadians(180)), Math.toRadians(270))
                            .strafeTo(new Vector2d(12, 16))
                            .strafeToConstantHeading(new Vector2d(38,16))
                            .strafeToConstantHeading(new Vector2d(38, 45))
                            .strafeToConstantHeading(new Vector2d(50, 41))
                            .build();
                }
                //Good
                else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-36,44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-36,12))
                            .strafeTo(new Vector2d(12, 13))
                            .strafeToLinearHeading(new Vector2d(40,15), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(40, 35))
                            .strafeToConstantHeading(new Vector2d(50, 35))
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
                    return drive.actionBuilder(REDLEFTSTART, transformation)
                            .strafeToLinearHeading(new Vector2d(-45.5, -40), Math.toRadians(290)).build();
                }
                else if (zone == 2) {
                    //Works
                    return drive.actionBuilder(REDLEFTSTART, transformation)
                            .strafeTo(new Vector2d(-36, -35)).build();
                }
                else {
                    //Not tested but probably works
                    return drive.actionBuilder(REDLEFTSTART, transformation)
                            .strafeToConstantHeading(new Vector2d(-48.07, -48.07))
                            .strafeToLinearHeading(new Vector2d(-32, -36), Math.toRadians(180))
                            .build();
                }
            }
            else if (side == AutoUtil.RIGHT) {
                if (zone == 1) {
                    return drive.actionBuilder(REDRIGHTSTART, transformation)
                            .strafeToConstantHeading(new Vector2d(24, -48))
                            .strafeToLinearHeading(new Vector2d(9, -30), Math.toRadians(0.00))
                            .build();

                }
                else if (zone == 2) {
                    return drive.actionBuilder(REDRIGHTSTART, transformation)
                            .strafeTo(new Vector2d(12, -35)).build();


                } else {
                    return drive.actionBuilder(REDRIGHTSTART, transformation)
                            .strafeToLinearHeading(new Vector2d(19, -40), Math.toRadians(250)).build();
                }
            }
            else return null;
        }
        else if (color == AutoUtil.BLUE) {
            if (side == AutoUtil.LEFT) {
                if (zone == 1) {
                    return drive.actionBuilder(BLUELEFTSTART, transformation)
                            .strafeToLinearHeading(new Vector2d(20, 40), Math.toRadians(110)).build();
                } else if (zone == 2) {
                    return drive.actionBuilder(BLUELEFTSTART, transformation)
                            .strafeTo(new Vector2d(12, 35)).build();
                } else {
                    return drive.actionBuilder(BLUELEFTSTART, transformation)
                            .strafeToConstantHeading(new Vector2d(24.06, 48.51))
                            .strafeToLinearHeading(new Vector2d(7.83, 35.33), Math.toRadians(0))
                            .strafeToConstantHeading(new Vector2d(10, 35.33))
                            .build();
                }
            }
            else if (side == AutoUtil.RIGHT) {
                if (zone == 1) {
                    //Good
                    return drive.actionBuilder(BLUERIGHTSTART, transformation)
                            .strafeToConstantHeading(new Vector2d(-48.00, 48.00))
                            .strafeToLinearHeading(new Vector2d(-35, 37.43), Math.toRadians(180))
                            .build();
                } else if (zone == 2) {
                    //Good
                    return drive.actionBuilder(BLUERIGHTSTART, transformation)
                            .strafeTo(new Vector2d(-36, 35))
                            .build();


                } else {
                    //Not Tested
                    return drive.actionBuilder(BLUERIGHTSTART, transformation)
                            .strafeToLinearHeading(new Vector2d(-44, 38), Math.toRadians(70)).build();
                }

            }
            else return null;
        } else return null;
    }

    public Action getSpikeParkAction(int color, int side, int zone){
        return getSpikeParkAction(color, side, zone, true);
    }

    public  Action getSpikeParkAction(int color, int side, int zone, boolean innerSide) {
        if (color == AutoUtil.RED) {
            if (side == AutoUtil.LEFT) {
                if (zone == 1) {
                    if(innerSide) return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-37, -44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-37, -12))
                            .strafeTo(new Vector2d(58, -12))
                            .build();
                    else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-37, -44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-37, -12))
                            .strafeTo(new Vector2d(44, -12))
                            .strafeTo(new Vector2d(44,-58))
                            .strafeTo(new Vector2d(58,-58))
                            .build();
                } else if (zone == 2) {
                    if(innerSide)return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-50, -50))
                            .splineToConstantHeading(new Vector2d(-55, -12), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(58, -12))
                            .build();
                    else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-50, -50))
                            .splineToConstantHeading(new Vector2d(-55, -12), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(44,-58))
                            .strafeTo(new Vector2d(58,-58)).build();
                } else {
                    if(innerSide) return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-36, -44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-39, -9))
                            .strafeToLinearHeading(new Vector2d(58, -14), Math.toRadians(180))
                            .build();
                    else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-36, -44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-39, -9))
                            .strafeTo(new Vector2d(44,-58))
                            .strafeTo(new Vector2d(58,-58)).build();

                }

            } else if (side == AutoUtil.RIGHT) {
                if (zone == 1) {
                    if(innerSide) return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(14, -44))
                            .strafeToLinearHeading(new Vector2d(14, -60), Math.toRadians(270))
                            .strafeTo(new Vector2d(45, -60))
                            .strafeTo(new Vector2d(45, -14))
                            .strafeTo(new Vector2d(58, -14))
                            .build();
                    else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(14, -44))
                            .strafeToLinearHeading(new Vector2d(14, -60), Math.toRadians(270))
                            .strafeTo(new Vector2d(57, -60))
                            .build();
                } else if (zone == 2) {
                    if(innerSide)return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(12, -44))
                            .strafeTo(new Vector2d(12, -60))
                            .strafeTo(new Vector2d(45, -60))
                            .strafeTo(new Vector2d(45, -14))
                            .strafeTo(new Vector2d(58, -14))
                            .build();
                    else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(12, -44))
                            .strafeTo(new Vector2d(12, -60))
                            .strafeTo(new Vector2d(57, -60))
                            .build();
                } else
                    if(innerSide)return drive.actionBuilder(drive.pose, transformation)
                        .strafeTo(new Vector2d(14, -50))
                        .strafeToLinearHeading(new Vector2d(14, -60), Math.toRadians(270))
                            .strafeTo(new Vector2d(45, -60))
                            .strafeTo(new Vector2d(45, -14))
                            .strafeTo(new Vector2d(58, -14))
                        .build();
                    else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(14, -50))
                            .strafeToLinearHeading(new Vector2d(14, -60), Math.toRadians(270))
                            .strafeTo(new Vector2d(57, -65))
                            .build();


            } else return null;

        } else if (color == AutoUtil.BLUE) {
            if (side == AutoUtil.LEFT) {
                if (zone == 1) {
                    if(innerSide)return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(14, 50))
                            .strafeToLinearHeading(new Vector2d(14, 60), Math.toRadians(90))
                            .strafeTo(new Vector2d(45, 60))
                            .strafeTo(new Vector2d(45, 14))
                            .strafeTo(new Vector2d(58, 14))
                            .build();
                    else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(14, 50))
                            .strafeToLinearHeading(new Vector2d(14, 60), Math.toRadians(90))
                            .strafeTo(new Vector2d(60, 60))
                            .build();
                } else if (zone == 2) {
                    if(innerSide)return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(12, 44))
                            .strafeTo(new Vector2d(12, 60))
                            .strafeTo(new Vector2d(45, 60))
                            .strafeTo(new Vector2d(45, 14))
                            .strafeTo(new Vector2d(58, 14))
                            .build();
                    else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(12, 44))
                            .strafeTo(new Vector2d(12, 60))
                            .strafeTo(new Vector2d(60, 60))
                            .build();
                } else {
                    if (innerSide) return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(14, 44))
                            .strafeToLinearHeading(new Vector2d(14, 60), Math.toRadians(90))
                            .strafeTo(new Vector2d(45, 60))
                            .strafeTo(new Vector2d(45, 14))
                            .strafeTo(new Vector2d(58, 14))
                            .build();
                    else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(14, 44))
                            .strafeToLinearHeading(new Vector2d(14, 60), Math.toRadians(90))
                            .strafeTo(new Vector2d(60, 60))
                            .build();
                }
            } else if (side == AutoUtil.RIGHT) {
                if (zone == 3) {
                    if(innerSide) return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-37, 44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-37, 12))
                            .strafeTo(new Vector2d(44, 12))
                            .strafeTo(new Vector2d(44,58))
                            .strafeTo(new Vector2d(58,58))
                            .build();
                    else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-37, 44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-37, 12))
                            .strafeTo(new Vector2d(58, 12))
                            .build();
                } else if (zone == 2) {
                    if(innerSide)return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-50, 50))
                            .splineToConstantHeading(new Vector2d(-55, 12), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(44, 12))
                            .strafeTo(new Vector2d(44,58))
                            .strafeTo(new Vector2d(58,58))
                            .build();
                    else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-50, 50))
                            .splineToConstantHeading(new Vector2d(-55, 12), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(58, 12))
                            .build();
                } else {
                    if(innerSide)return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-36, 44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-39, 9))
                            .strafeTo(new Vector2d(44, 12))
                            .strafeTo(new Vector2d(44,58))
                            .strafeTo(new Vector2d(58,58))
                            .build();
                    else return drive.actionBuilder(drive.pose, transformation)
                            .strafeTo(new Vector2d(-36, 44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-39, 9))
                            .strafeToLinearHeading(new Vector2d(58, 14), Math.toRadians(180))
                            .build();
                }
            } else return null;
        }
        else return null;
    }


    //Note: getPixelStackAutoAction is vesitigial
    public  Action getPixelStackAutoAction(int color){
        if(color == AutoUtil.RED){
            return drive.actionBuilder(drive.pose, transformation)
                    .splineToLinearHeading(new Pose2d(31.51, -6.30, Math.toRadians(180)), Math.toRadians(180.00))
                    .splineToConstantHeading(new Vector2d(-60.35, -9.93), Math.toRadians(180))
                    .waitSeconds(1)
                    .strafeTo(new Vector2d(50, -12))
                    .build();

        }
        else if(color == AutoUtil.BLUE){
            return drive.actionBuilder(drive.pose, transformation)
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
            return drive.actionBuilder(drive.pose, transformation)
                    .splineToConstantHeading(new Vector2d(26.16, 5.35), Math.toRadians(180.00))
                    .splineToConstantHeading(new Vector2d(-61.69, 12.60), Math.toRadians(180.00))
                    .build();
        }
        else
        {
            return drive.actionBuilder(drive.pose, transformation)
                    .splineToLinearHeading(new Pose2d(26.16, -12, Math.toRadians(180)), Math.toRadians(180.00))
                    .splineToConstantHeading(new Vector2d(-61.69, -16), Math.toRadians(180.00))
                    .build();

        }
    }



    //These paths are pretty sub optimal, if we need to shave some time off the auto
    //Converting these to splines would be a good start.
    public Action getStackToBackdropAutoAction(int color, int zone) {
        if(color==AutoUtil.RED)
        {
            return drive.actionBuilder(drive.pose, transformation)
                    .setReversed(true)
                    .splineToConstantHeading(new Vector2d(4.39, 0), Math.toRadians(0),(pose, path, disp) -> {return 100;}, new ProfileAccelConstraint(-120,120))
                    .splineToConstantHeading(new Vector2d(50, -35), Math.toRadians(0))
//                    .strafeToConstantHeading(new Vector2d(28.80, -12.17))
//                    .strafeToConstantHeading(new Vector2d(53.00, -36.00))
                    .build();


        }
        else
        {
            return drive.actionBuilder(drive.pose, transformation)
                    .strafeToConstantHeading(new Vector2d(28.80, 12.17))
                    .strafeToConstantHeading(new Vector2d(53.00, 36.00))
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
                    return drive.actionBuilder(REDLEFTSTART, transformation)
                            .strafeToConstantHeading(new Vector2d(-47.45, -40.70))
                            .strafeToConstantHeading(new Vector2d(-38.25, -43.98))
                            .splineTo(new Vector2d(-25.16, -11.05), Math.toRadians(27.99))
                            .splineTo(new Vector2d(18.82, -19.84), Math.toRadians(-14.82))
                            .splineToSplineHeading(new Pose2d(50.73, -30.48, Math.toRadians(180.00)), Math.toRadians(17.32))
                            .strafeToConstantHeading(new Vector2d(53, -15))
                            .build();

                }
                else if(zone ==2) {
                    return drive.actionBuilder(REDLEFTSTART, transformation)
                            .strafeToConstantHeading(new Vector2d(-35.90, -34.07))
                            .strafeToConstantHeading(new Vector2d(-50.70, -37.32))
                            .splineTo(new Vector2d(-45.43, -18.86), Math.toRadians(29.52))
                            .splineTo(new Vector2d(10.14, -17.44), Math.toRadians(-21.97))
                            .splineToSplineHeading(new Pose2d(53.00, -36.00, Math.toRadians(-180.00)), Math.toRadians(-11.94))
                            .build();

                }
                else if(zone ==3) {
                    return drive.actionBuilder(new Pose2d(-36.00, -63.00, Math.toRadians(270.00)))
                            .splineTo(new Vector2d(-29.61, -37.32), Math.toRadians(45.00))
                            .strafeToConstantHeading(new Vector2d(-32.86, -38.94))
                            .strafeToLinearHeading(new Vector2d(-45.03, -15.21), Math.toRadians(-180.00))
                            .splineTo(new Vector2d(12.57, -17.04), Math.toRadians(-27.05))
                            .splineTo(new Vector2d(53.00, -42.00), Math.toRadians(315.00))
                            .build();

                }
            }
            else if(side == RIGHT)
            {
                if(zone==1) {
                    return drive.actionBuilder(REDRIGHTSTART, transformation).setReversed(true)
                            .splineToLinearHeading(new Pose2d(9.5, -30.83, Math.toRadians(0.00)), Math.toRadians(180.00))
                            .setReversed(false)
                            .splineToSplineHeading(new Pose2d(53.00, -30.00, Math.toRadians(180.00)), Math.toRadians(0.00))
                            .build();
                }
                else if(zone ==2) {
                    return drive.actionBuilder(REDRIGHTSTART, transformation)
                            .setReversed(true)
                            .splineToSplineHeading(new Pose2d(15.41, -34.48, Math.toRadians(270.00)), Math.toRadians(90.00))
                            .setReversed(false)
                            .splineTo(new Vector2d(53.00, -36.00), Math.toRadians(0.00))
                            .build();

                }
                else if(zone==3) {
                    return drive.actionBuilder(REDRIGHTSTART, transformation)
                            .strafeToConstantHeading(new Vector2d(23.12, -43.00))
                            .splineToSplineHeading(new Pose2d(53.00, -42.00, Math.toRadians(180.00)), Math.toRadians(0.00))
                            .build();

                }
            }
        }
        else if(color==BLUE)
        {
            if(side==LEFT) {
                if(zone==1) {
                    return drive.actionBuilder(BLUELEFTSTART, transformation)
                            .strafeToConstantHeading(new Vector2d(23.12, 43.00))
                            .splineToSplineHeading(new Pose2d(53.00, 42.00, Math.toRadians(180.00)), Math.toRadians(0.00))
                            .build();
                }
                else if(zone ==2) {
                    return drive.actionBuilder(BLUELEFTSTART, transformation)
                            .strafeToConstantHeading(new Vector2d(15.41, 34.48))
                            .splineToSplineHeading(new Pose2d(53.00, 36.00, 180), Math.toRadians(0.00))
                            .build();
                }
                else {
                    return drive.actionBuilder(BLUELEFTSTART, transformation).setReversed(true)
                            .splineToLinearHeading(new Pose2d(9.5, 30.83, Math.toRadians(0.00)), Math.toRadians(180.00))
                            .setReversed(false)
                            .splineToSplineHeading(new Pose2d(53.00, 36, Math.toRadians(180.00)), Math.toRadians(0.00))
                            .build();
                }
            }
            else if(side==RIGHT) {
                if(zone==1) {
                    return drive.actionBuilder(BLUERIGHTSTART, transformation)
                            .splineTo(new Vector2d(-29.61, 37.32), Math.toRadians(45.00))
                            .strafeToConstantHeading(new Vector2d(-32.86, 38.94))
                            .strafeToLinearHeading(new Vector2d(-45.03, 15.21), Math.toRadians(-180.00))
                            .splineTo(new Vector2d(12.57, 17.04), Math.toRadians(-27.05))
                            .splineTo(new Vector2d(53.00, 42.00), Math.toRadians(315.00))
                            .build();
                }
                else if(zone ==2) {
                    return drive.actionBuilder(BLUERIGHTSTART, transformation)
                            .strafeToConstantHeading(new Vector2d(-35.90, 34.07))
                            .strafeToConstantHeading(new Vector2d(-50.70, 37.32))
                            .splineTo(new Vector2d(-45.43, 18.86), Math.toRadians(29.52))
                            .splineTo(new Vector2d(10.14, 17.44), Math.toRadians(-21.97))
                            .splineToSplineHeading(new Pose2d(53.00, 36.00, Math.toRadians(-180.00)), Math.toRadians(-11.94))
                            .build();
                }
                else {
                    return drive.actionBuilder(BLUERIGHTSTART, transformation)
                            .strafeToConstantHeading(new Vector2d(-47.45, 40.70))
                            .strafeToConstantHeading(new Vector2d(-38.25, 43.98))
                            .splineTo(new Vector2d(-25.16, 11.05), Math.toRadians(27.99))
                            .splineTo(new Vector2d(18.82, 19.84), Math.toRadians(-14.82))
                            .splineToSplineHeading(new Pose2d(50.73, 30.48, Math.toRadians(180.00)), Math.toRadians(17.32))
                            .strafeToConstantHeading(new Vector2d(53, 15))
                            .build();
                }
            }
        }
        return null;
    }

    public Action getQuickToSpikeAutoAction(int color, int stack)
    {
        if(color==RED)
        {
            if(stack==3)
            {
                return drive.actionBuilder(drive.pose, transformation)
                        .strafeToConstantHeading(new Vector2d(30.00, -12))
                        .strafeToConstantHeading(new Vector2d(-58.61, -12))
                        .build();

            }
        }
        else if(color==BLUE)
        {
            if(stack==3)
            {
                return drive.actionBuilder(drive.pose, transformation)
                        .strafeToConstantHeading(new Vector2d(30.00, 12))
                        .strafeToConstantHeading(new Vector2d(-55, 12))
                        .build();
            }
        }
        return null;
    }

    //go straight to depositing on the backdrop skipping stack
    public Action getBSSStartToBackdrop(int color, int side, int zone){
        if(color == RED){
            if(side == RIGHT)
            {
                if(zone==1){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeToLinearHeading(new Vector2d(51.8,-29.5), Math.toRadians(180))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeToLinearHeading(new Vector2d(51.8,-36), Math.toRadians(180))
                            .build();
                }
                else return drive.actionBuilder(drive.pose, transformation)
                            .strafeToLinearHeading(new Vector2d(51.8,-40), Math.toRadians(180))
                            .build();
            }
            else return null;

        }
        else if(color == BLUE){
            if(side == LEFT){
                if(zone==3){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeToLinearHeading(new Vector2d(51.8,36), Math.toRadians(180))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeToLinearHeading(new Vector2d(51.8,40), Math.toRadians(180))
                            .build();
                }
                else return drive.actionBuilder(drive.pose, transformation)
                            .strafeToLinearHeading(new Vector2d(51.8,47), Math.toRadians(180))
                            .build();
            }
            else return null;
        }
        else return null;
    }
    public Action getBSSBackToSpike(int color, int side, int zone){
        if(color == RED){
            if(side == RIGHT)
            {
                if(zone==1){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeToConstantHeading(new Vector2d(6,-35))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeToConstantHeading(new Vector2d(22,-26))
                            .build();
                }
                else return drive.actionBuilder(drive.pose, transformation)
                            .strafeToConstantHeading(new Vector2d(29,-35))
                            .build();
            }
            else return null;

        }
        else if(color == BLUE){
            if(side == LEFT){
                if(zone==3){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeToConstantHeading(new Vector2d(10,40))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose, transformation)
                            .strafeToConstantHeading(new Vector2d(22,30))
                            .build();
                }
                else return drive.actionBuilder(drive.pose, transformation)
                            .strafeToConstantHeading(new Vector2d(29,40))
                            .build();
            }
            else return null;
        }
        else return null;
    }
    public Action getBSSSpikeToStack(int color, int side, int zone){
        if(color == RED){
            if(side == RIGHT)
            {
                if(zone==1){
                    return drive.actionBuilder(drive.pose, transformation)
                            .setReversed(true)
                            .splineToConstantHeading(new Vector2d(15, -14), Math.toRadians(90))
                            .splineToLinearHeading(new Pose2d(-55,-14, Math.toRadians(180)), Math.toRadians(180)).build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose, transformation)
                            .setReversed(true)
                            .splineToConstantHeading(new Vector2d(25, -25), Math.toRadians(90))
                            .splineToLinearHeading(new Pose2d(-53.5,-20, Math.toRadians(180)), Math.toRadians(180)).build();
                }
                else return drive.actionBuilder(drive.pose, transformation)
                            .setReversed(true)
                            .splineToConstantHeading(new Vector2d(25, -25), Math.toRadians(90))
                            .splineToLinearHeading(new Pose2d(-53.5,-20, Math.toRadians(180)), Math.toRadians(180)).build();
            }
            else return null;

        }
        else if(color == BLUE){
            if(side == LEFT){
                return null;
            }
            else return null;
        }
        else return null;
    }


    public Pose2d adjustForAprilTag(Pose2d odoPose, Pose2d aprilTagPose)
    {
        double xDifference = odoPose.component1().x-aprilTagPose.component1().x;
        double yDifference = odoPose.component1().y-aprilTagPose.component1().y;

        //TODO If stuff is acting weird, check with cole that this is how you retrieve heading from a pose2d
        double headingDifference = odoPose.heading.component1()-aprilTagPose.heading.component1();
        xDifference*= Constants.VisionConstants.aprilTagPositionWeight;
        yDifference*= Constants.VisionConstants.aprilTagPositionWeight;
        headingDifference*= Constants.VisionConstants.aprilTagHeadingWeight;
        Pose2d pose = new Pose2d(odoPose.position.x+xDifference,
                odoPose.position.y+yDifference,
                odoPose.heading.component1()+headingDifference);
        return pose;
    }





    public static void delay(double t) {
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while (runtime.seconds() < t) {
        }
    }
}
