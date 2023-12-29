package com.example.meepmeep;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;


public class MeepMeepAutoUtil {
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
    DriveShim drive;
    Pose2d startPose;

    public MeepMeepAutoUtil(DriveShim drive){
        this.drive = drive;
        startPose = MeepMeepTesting.drivePose;
    }

    /**
     * Actions
     *
     * OLD
     * spikeAutoAction
     * spikePark
     * BackstageParkAutoAction
     * BackdropAutoAction
     *
     *SEMI OLD
     * BackdropAutoActionNoAprilTag
     * stackToBackdrop
     * backdropToStack
     *
     *
     * NeW
     * speedyBackdrop
     *
     *
     *
     *
     *
     *
     *
     *
     */

    public  Action getBackStageParkAutoAction(int color, int side){
        if(color == MeepMeepAutoUtil.BLUE){
            if(side == MeepMeepAutoUtil.RIGHT){
                return drive.actionBuilder(MeepMeepTesting.drivePose)
                        .strafeTo(new Vector2d(35,19.5                                          ))
                        .strafeTo(new Vector2d(48, 19.5))

                        .build();
            }
            else if(side == MeepMeepAutoUtil.LEFT){
                return drive.actionBuilder(MeepMeepTesting.drivePose)

                        .strafeTo(new Vector2d(40,64))
                        .strafeTo(new Vector2d(48, 64))
                        .build();
            }
            else return null;
        }else if(color == MeepMeepAutoUtil.RED){
            if(side == MeepMeepAutoUtil.LEFT){
                return drive.actionBuilder(MeepMeepTesting.drivePose)
                        .strafeTo(new Vector2d(37,-10))
                        .strafeTo(new Vector2d(48, -10))

                        .build();
            }
            else if(side == MeepMeepAutoUtil.RIGHT){
                return drive.actionBuilder(MeepMeepTesting.drivePose)
                        .strafeTo(new Vector2d(45,-60))

                        .strafeTo(new Vector2d(60,-60))

                        .build();
            }
            else return null;
        }
        else return null;
    }

    public  Action getBackdropAutoAction(int color, int side, int zone){
        if(color==MeepMeepAutoUtil.RED){
            if(side == MeepMeepAutoUtil.LEFT){
                if(zone==1){
                    //Works
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
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
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(-45,-46))
                            .splineToLinearHeading(new Pose2d(-55, -15, Math.toRadians(180)), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(30, -15))
                            .strafeToLinearHeading(new Vector2d(37,-36.5),Math.toRadians(-180))

                            .build();
                }
                //Not tested, probably doesn't work
                else return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(-36,-44))
                            .strafeTo(new Vector2d(-36,-15))
                            .strafeTo(new Vector2d(12, -15))
                            .strafeToLinearHeading(new Vector2d(40,-12),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(40, -45))
                            .build();

            }
            else if(side == MeepMeepAutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(14,-44))
                            .strafeToLinearHeading(new Vector2d(35,-30), Math.toRadians(180))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(12,-44))
                            .strafeToLinearHeading(new Vector2d(40,-44), Math.toRadians(210))

                            .build();
                }
                else return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(14,-50))
                            .strafeToLinearHeading(new Vector2d(37,-55), Math.toRadians(180))
                            .strafeTo(new Vector2d(37,-37))

                            .build();
            }
            else return null;
        }
        else if(color ==MeepMeepAutoUtil.BLUE){
            if(side == MeepMeepAutoUtil.LEFT){
                if(zone==1){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(14,44))
                            .strafeToLinearHeading(new Vector2d(40,44), Math.toRadians(180))

                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(12,44))
                            .strafeToLinearHeading(new Vector2d(35,40), Math.toRadians(180))

                            .build();
                }
                else return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeToLinearHeading(new Vector2d(37,38), Math.toRadians(180))
                            .build();
            }
            else if(side == MeepMeepAutoUtil.RIGHT){
                if(zone==1){
                    /*return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeToConstantHeading(new Vector2d(-40, 37))
                            .strafeTo(new Vector2d(-34,44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,10))
                            .strafeTo(new Vector2d(12, 10))
                            .strafeToLinearHeading(new Vector2d(48,12), Math.toRadians(230))
                            .build();

                     */
                    //Good
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(-36,37))
                            .strafeTo(new Vector2d(-36,15))
                            .strafeToLinearHeading(new Vector2d(40,15),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(40, 47))
                            .build();

                }
                else if(zone==2){
                    //Good
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(-36,44))
                            .strafeToConstantHeading(new Vector2d(-50, 44))
                            .splineToLinearHeading(new Pose2d(-50, 12, Math.toRadians(180)), Math.toRadians(250))
                            .strafeTo(new Vector2d(12, 13))
                            .strafeToConstantHeading(new Vector2d(38,14))
                            .strafeToConstantHeading(new Vector2d(38, 45))
                            .build();
                }
                //Good
                else return drive.actionBuilder(MeepMeepTesting.drivePose)
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
        if(color==MeepMeepAutoUtil.RED){
            if(side == MeepMeepAutoUtil.LEFT){
                if(zone==1){
                    //Works
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
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
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(-45,-46))
                            .splineToLinearHeading(new Pose2d(-55, -15, Math.toRadians(180)), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(12, -15))
                            .strafeToLinearHeading(new Vector2d(40,-40),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(50, -40))
                            .build();
                }
                //Not tested, probably doesn't work
                else return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(-36,-44))
                            .strafeTo(new Vector2d(-36,-15))
                            .strafeTo(new Vector2d(12, -15))
                            .strafeToLinearHeading(new Vector2d(40,-12),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(40, -45))
                            .strafeToConstantHeading(new Vector2d(50, -48))
                            .build();

            }
            else if(side == MeepMeepAutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(14,-44))
                            .strafeToLinearHeading(new Vector2d(35,-40), Math.toRadians(200))
                            .strafeToConstantHeading(new Vector2d(50, -32))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(12,-44))
                            .strafeToLinearHeading(new Vector2d(40,-44), Math.toRadians(210))
                            .strafeToConstantHeading(new Vector2d(50, -40))
                            .build();
                }
                else return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(14,-50))
                            .strafeToLinearHeading(new Vector2d(37,-55), Math.toRadians(180))
                            .strafeTo(new Vector2d(37,-37))
                            .strafeToConstantHeading(new Vector2d(50, -48))
                            .build();
            }
            else return null;
        }
        else if(color ==MeepMeepAutoUtil.BLUE){
            if(side == MeepMeepAutoUtil.LEFT){
                if(zone==1){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(14,44))
                            .strafeToLinearHeading(new Vector2d(40,44), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(50, 48))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(12,44))
                            .strafeToLinearHeading(new Vector2d(35,40), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(50, 36))
                            .build();
                }
                else return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeToLinearHeading(new Vector2d(37,38), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(50, 30))
                            .build();
            }
            else if(side == MeepMeepAutoUtil.RIGHT){
                if(zone==1){
                    /*return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeToConstantHeading(new Vector2d(-40, 37))
                            .strafeTo(new Vector2d(-34,44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,10))
                            .strafeTo(new Vector2d(12, 10))
                            .strafeToLinearHeading(new Vector2d(48,12), Math.toRadians(230))
                            .build();

                     */
                    //Good
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(-36,37))
                            .strafeTo(new Vector2d(-36,15))
                            .strafeToLinearHeading(new Vector2d(40,15),Math.toRadians(-180))
                            .strafeToConstantHeading(new Vector2d(40, 45))
                            .strafeToConstantHeading(new Vector2d(50, 48))
                            .build();

                }
                else if(zone==2){
                    //Good
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
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
                else return drive.actionBuilder(MeepMeepTesting.drivePose)
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
        if (color == MeepMeepAutoUtil.RED) {
            if (side == MeepMeepAutoUtil.LEFT) {
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
                    drive.setPoseEstimate(REDLEFTSTART);
                    //Not tested but probably works
                    return drive.actionBuilder(REDLEFTSTART)
                            .strafeToConstantHeading(new Vector2d(-48.07, -48.07))
                            .strafeToLinearHeading(new Vector2d(-32, -36), Math.toRadians(180))
                            .build();
                }
            }
            else if (side == MeepMeepAutoUtil.RIGHT) {
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
        else if (color == MeepMeepAutoUtil.BLUE) {
            if (side == MeepMeepAutoUtil.LEFT) {
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
            else if (side == MeepMeepAutoUtil.RIGHT) {
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
        if(color==MeepMeepAutoUtil.RED){
            if(side == MeepMeepAutoUtil.LEFT){
                if(zone==1){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(-34,-44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,-12))
                            .strafeTo(new Vector2d(60, -12))

                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(-36,-44))
                            .splineToConstantHeading(new Vector2d(-55, -12), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(60, -12))


                            .build();
                }
                else {return drive.actionBuilder(MeepMeepTesting.drivePose)
                        .strafeTo(new Vector2d(-36,-44))
                        .turn(Math.toRadians(20))
                        .strafeTo(new Vector2d(-36,-12))
                        .strafeTo(new Vector2d(60, -12))
                        .build();}

            }
            else if(side == MeepMeepAutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(14,-44))
                            .strafeToLinearHeading(new Vector2d(14, -60), Math.toRadians(270))
                            .strafeTo(new Vector2d(57,-65))

                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(12,-44))
                            .strafeTo(new Vector2d(12,-60))
                            .strafeTo(new Vector2d(57,-65))
                            .build();
                }
                else return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(14,-50))
                            .strafeToLinearHeading(new Vector2d(14, -60), Math.toRadians(270))
                            .strafeTo(new Vector2d(57,-65))

                            .build();
            }
            else return null;
        }
        else if(color ==MeepMeepAutoUtil.BLUE){
            if(side == MeepMeepAutoUtil.LEFT){
                if(zone==1){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(14,50))
                            .strafeToLinearHeading(new Vector2d(14, 60), Math.toRadians(90))
                            .strafeTo(new Vector2d(60,60))

                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(12,44))
                            .strafeTo(new Vector2d(12,60))
                            .strafeTo(new Vector2d(60,60))
                            .build();
                }
                else return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(14,44))
                            .strafeToLinearHeading(new Vector2d(14, 60), Math.toRadians(90))
                            .strafeTo(new Vector2d(60,60))

                            .build();
            }
            else if(side == MeepMeepAutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(-34,44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,10))
                            .strafeTo(new Vector2d(60, 10))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(MeepMeepTesting.drivePose)
                            .strafeTo(new Vector2d(-36,44))
                            .splineToConstantHeading(new Vector2d(-55, 10), Math.toRadians(270))
                            .strafeTo(new Vector2d(60, 10))
                            .build();
                }
                else return drive.actionBuilder(MeepMeepTesting.drivePose)
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



    public Action getBackdropToStackAutoAction(int color) {
        if(color==MeepMeepAutoUtil.BLUE)
        {
            return drive.actionBuilder(MeepMeepTesting.drivePose)
                    .splineTo(new Vector2d(24.75, 13.09), Math.toRadians(192.99))
                    .splineTo(new Vector2d(-50, 10.02), Math.toRadians(179.28))
                    .build();
        }
        else
        {
            return drive.actionBuilder(MeepMeepTesting.drivePose)
                    .splineTo(new Vector2d(24.75, -13.09), Math.toRadians(167.01))
                    .splineTo(new Vector2d(-55, -10.43), Math.toRadians(179.28))
                    .build();

        }
    }



    //These paths are pretty sub optimal, if we need to shave some time off the auto
    //Converting these to splines would be a good start.
    public Action getStackToBackdropAutoAction(int color, int zone) {
        if(color==MeepMeepAutoUtil.RED)
        {
            return drive.actionBuilder(MeepMeepTesting.drivePose)
                    .strafeToConstantHeading(new Vector2d(43.00, -14.20))
                    .strafeToConstantHeading(new Vector2d(48, -40.56))
                    .build();

        }
        else
        {
            return drive.actionBuilder(MeepMeepTesting.drivePose)
                    .strafeToConstantHeading(new Vector2d(43.00, 14.20))
                    .strafeToConstantHeading(new Vector2d(50.30, 40.56))
                    .strafeToConstantHeading(new Vector2d(52, 41))
                    .build();

        }
    }




    public  Action getSpeedyBackdrop(int color, int side, int zone){
        if(color==MeepMeepAutoUtil.RED){
            if(side == MeepMeepAutoUtil.LEFT){
                if(zone==1){
                    return null;
                }
                else if(zone==2){
                    return null;
                }
                else {return null;}

            }
            else if(side == MeepMeepAutoUtil.RIGHT){
                if(zone==1){
                    return null;
                }
                else if(zone==2){
                    return null;
                }
                else return null;
            }
            else return null;
        }
        else if(color ==MeepMeepAutoUtil.BLUE){
            if(side == MeepMeepAutoUtil.LEFT){
                if(zone==1){
                    return null;
                }
                else if(zone==2){
                    return null;
                }
                else return null;
            }
            else if(side == MeepMeepAutoUtil.RIGHT){
                if(zone==1){
                    return null;
                }
                else if(zone==2){
                    return null;
                }
                else return null;
            }
            else return null;
        }
        else return null;
    }



}
