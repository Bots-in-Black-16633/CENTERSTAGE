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
                        .strafeTo(new Vector2d(50,12))


                        .build();
            }
            else if(side == AutoUtil.LEFT){
                return drive.actionBuilder(drive.pose)
                        .strafeTo(new Vector2d(45,60))

                        .strafeTo(new Vector2d(60,60))

                        .build();
            }
            else return null;
        }else if(color == AutoUtil.RED){
            if(side == AutoUtil.LEFT){
                return drive.actionBuilder(drive.pose)
                        .strafeTo(new Vector2d(60,-12))


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
                            .strafeTo(new Vector2d(-34,-44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,-16))
                            .strafeTo(new Vector2d(12, -18))
                            .strafeToLinearHeading(new Vector2d(40,-23),Math.toRadians(-180))
                            .strafeTo(new Vector2d(40, -50))
                            .build();
                }
                else if(zone==2){
                    //Works
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-45,-46))
                            .splineToConstantHeading(new Vector2d(-55, -14), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(12, -17))
                            .strafeToLinearHeading(new Vector2d(40,-50),Math.toRadians(-180))

                            .build();
                }
                //Not tested, probably doesn't work
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,-44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-36,-12))
                            .strafeTo(new Vector2d(12, -12))
                            .strafeToLinearHeading(new Vector2d(48,-12),Math.toRadians(-180))
                            .build();

            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,-44))
                            .strafeToLinearHeading(new Vector2d(35,-40), Math.toRadians(190))
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
                            .strafeToLinearHeading(new Vector2d(37,-55), Math.toRadians(230))

                            .build();
            }
            else return null;
        }
        else if(color ==AutoUtil.BLUE){
            if(side == AutoUtil.LEFT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,44))
                            .strafeToLinearHeading(new Vector2d(40,44), Math.toRadians(150))

                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(12,44))
                            .strafeToLinearHeading(new Vector2d(35,40), Math.toRadians(158))

                            .build();
                }
                else return drive.actionBuilder(drive.pose)
                            .strafeToLinearHeading(new Vector2d(37,44), Math.toRadians(130))
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
                            .strafeToConstantHeading(new Vector2d(-50, 37.12))
                            .strafeToConstantHeading(new Vector2d(-50, 10.55))
                            .strafeToConstantHeading(new Vector2d(29, -24))
                            .strafeToConstantHeading(new Vector2d(29.00, 0))
                            .build();

                }
                else if(zone==2){
                    //Good
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,44))
                            .strafeToConstantHeading(new Vector2d(-50, 44))
                            .splineToConstantHeading(new Vector2d(-50, 10), Math.toRadians(270))
                            .strafeTo(new Vector2d(12, 0))
                            .strafeToLinearHeading(new Vector2d(38,0), Math.toRadians(180))
                            .strafeToConstantHeading(new Vector2d(38, 35))
                            .build();
                }
                //Good
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-36,12))
                            .strafeTo(new Vector2d(12, 12))
                            .strafeToLinearHeading(new Vector2d(40,12), Math.toRadians(180))
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
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-34,-44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,-12))
                            .strafeTo(new Vector2d(12, -12))
                            .strafeToLinearHeading(new Vector2d(48,-12),Math.toRadians(-180))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,-44))
                            .splineToConstantHeading(new Vector2d(-55, -12), Math.toRadians(90.00))
                            .strafeTo(new Vector2d(12, -12))
                            .strafeToLinearHeading(new Vector2d(48,-12),Math.toRadians(-180))

                            .build();
                }
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,-44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-36,-12))
                            .strafeTo(new Vector2d(12, -12))
                            .strafeToLinearHeading(new Vector2d(48,-12),Math.toRadians(-180))
                            .build();

            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,-44))
                            .strafeToLinearHeading(new Vector2d(35,-40), Math.toRadians(190))
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
                            .strafeToLinearHeading(new Vector2d(37,-55), Math.toRadians(230))

                            .build();
            }
            else return null;
        }
        else if(color ==AutoUtil.BLUE) {
            if(side == AutoUtil.LEFT) {
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(12,44))
                            .strafeToLinearHeading(new Vector2d(40,40), Math.toRadians(158))
                            .strafeToConstantHeading(new Vector2d(53, 40))

                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(12,40))
                            .strafeToLinearHeading(new Vector2d(40,35), Math.toRadians(158))
                            .strafeToConstantHeading(new Vector2d(53, 35))
                            .build();
                }
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(14,56))
                            .strafeToLinearHeading(new Vector2d(37,59), Math.toRadians(130))

                            .build();
            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-34,44))
                            .turn(Math.toRadians(-20))
                            .strafeTo(new Vector2d(-36,10))
                            .strafeTo(new Vector2d(12, 10))
                            .strafeToLinearHeading(new Vector2d(48,12), Math.toRadians(230))
                            .build();
                }
                else if(zone==2){
                    return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,44))
                            .splineToConstantHeading(new Vector2d(-55, 10), Math.toRadians(270))
                            .strafeTo(new Vector2d(12, 10))
                            .strafeToLinearHeading(new Vector2d(48,10), Math.toRadians(230))
                            .build();
                }
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-36,10))
                            .strafeTo(new Vector2d(12, 10))
                            .strafeToLinearHeading(new Vector2d(48,10), Math.toRadians(230))
                            .build();
            }
            else return null;
        }
        else return null;
    }
    public  Action getSpikeAutoAction(int color, int side, int zone){
        if(color==AutoUtil.RED){
            if(side == AutoUtil.LEFT){
                if(zone==1){
                    //Works
                    return drive.actionBuilder(REDLEFTSTART)
                            .strafeToLinearHeading(new Vector2d(-42,-40), Math.toRadians(290)).build();
                }
                else if(zone==2){
                    //Works
                    return drive.actionBuilder(REDLEFTSTART)
                            .strafeTo(new Vector2d(-36,-35)).build();
                }
                else {
                    //Not tested but probably works
                    drive.actionBuilder(REDLEFTSTART)
                            .strafeToConstantHeading(new Vector2d(-48.07, -48.07))
                            .strafeToLinearHeading(new Vector2d(-31.23, -36.91), Math.toRadians(38.84))
                            .build();
                }
            }
            else if(side == AutoUtil.RIGHT){
                if(zone==1){
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(24, -48))
                            .strafeToLinearHeading(new Vector2d(11, -30), Math.toRadians(0.00))
                            .build();

                }
                else if(zone==2){
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeTo(new Vector2d(12,-35)).build();


                }
                else {
                    return drive.actionBuilder(REDRIGHTSTART)
                            .strafeToLinearHeading(new Vector2d(19,-40), Math.toRadians(250)).build();
                }
            }
            else return null;
        }
        else if(color ==AutoUtil.BLUE) {
            if (side == AutoUtil.LEFT) {
                if (zone == 1) {
                    return drive.actionBuilder(BLUELEFTSTART)
                            .strafeToLinearHeading(new Vector2d(18.5, 40), Math.toRadians(110)).build();
                } else if (zone == 2) {
                    return drive.actionBuilder(BLUELEFTSTART)
                            .strafeTo(new Vector2d(12, 35)).build();
                } else {
                    return drive.actionBuilder(BLUELEFTSTART)
                            .strafeToConstantHeading(new Vector2d(24.06, 48.51))
                            .strafeToLinearHeading(new Vector2d(7.83, 35.33), Math.toRadians(20))
                            .strafeToLinearHeading(new Vector2d(38.39, 44.69), Math.toRadians(130.00))
                            .build();
                }
            } else if (side == AutoUtil.RIGHT) {
                if (zone == 1) {
                    //Good
                    return drive.actionBuilder(BLUERIGHTSTART)
                            .strafeToConstantHeading(new Vector2d(-48.00, 48.00))
                            .strafeToLinearHeading(new Vector2d(-34, 37.43), Math.toRadians(147.99))
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
        }
        else return null;
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
                else return drive.actionBuilder(drive.pose)
                            .strafeTo(new Vector2d(-36,-44))
                            .turn(Math.toRadians(20))
                            .strafeTo(new Vector2d(-36,-12))
                            .strafeTo(new Vector2d(60, -12))
                            .build();

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








    public static void delay(double t) {
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while (runtime.seconds() < t) {
        }
    }
}
