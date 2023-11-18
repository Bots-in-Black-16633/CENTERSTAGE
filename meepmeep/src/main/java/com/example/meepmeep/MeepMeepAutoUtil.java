package com.example.meepmeep;

import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;

public class MeepMeepAutoUtil {
    public static  Pose2d REDRIGHTSTART = new Pose2d(12.00, -63, Math.toRadians(270));
    public static Pose2d REDLEFTSTART = new Pose2d(-36.00, -63, Math.toRadians(270));
    public static  Pose2d BLUERIGHTSTART = new Pose2d(-36, 63, Math.toRadians(90));
    public static  Pose2d BLUELEFTSTART = new Pose2d(12.00, 63, Math.toRadians(90));


    public static final int BLUE =0;
    public static final int RED = 1;

    public static final int RIGHT = 0;
    public static final int LEFT = 1;

    public static TrajectorySequence getBackStageParkAutoAction(int color, int side, int zone){
        return null;
    }

    public static TrajectorySequence getSpikeMarkParkAutoAction(int color, int side, int zone, DriveShim drive, TrajectorySequence prevPath){
        if(color==MeepMeepAutoUtil.RED){
            if(side == MeepMeepAutoUtil.LEFT){
                if(zone==1){
                    return drive.trajectorySequenceBuilder(prevPath.end()).waitSeconds(prevPath.duration())
                            .lineToLinearHeading(new Pose2d(-32, 10, Math.toRadians(90)))
                            .build();
                }
                else if(zone==2){
                    return null;
                }
                else return null;

            }
            else if(side == MeepMeepAutoUtil.RIGHT){
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
        else if(color ==MeepMeepAutoUtil.BLUE){
            if(side == MeepMeepAutoUtil.LEFT){
                if(zone ==1){
                    return null;
                }
                else if(zone ==2){
                    return null;
                }else{
                    return null;
                }
            }
            else if(side == MeepMeepAutoUtil.RIGHT){
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

    public static TrajectorySequence getSpikeAutoAction(int color, int side, int zone, DriveShim drive, DriveShim startPose){
        if(color==MeepMeepAutoUtil.RED){
            if(side == MeepMeepAutoUtil.LEFT){
                if(zone==1){
                   return drive.trajectorySequenceBuilder(REDLEFTSTART)
                           .lineToLinearHeading(new Pose2d(-40,-40, Math.toRadians(290))).build();
                }
                else if(zone==2){
                    return drive.trajectorySequenceBuilder(REDLEFTSTART)
                            .lineTo(new Vector2d(-36,-35)).build();
                }
                else {
                    return drive.trajectorySequenceBuilder(REDLEFTSTART)
                            .lineToLinearHeading(new Pose2d(-32,-40, Math.toRadians(250))).build();
                }
            }
            else if(side == MeepMeepAutoUtil.RIGHT){
                if(zone==1){
                    return drive.trajectorySequenceBuilder(REDRIGHTSTART)
                            .lineToLinearHeading(new Pose2d(8,-40, Math.toRadians(290))).build();
                }
                else if(zone==2){
                    return drive.trajectorySequenceBuilder(REDRIGHTSTART)
                            .lineTo(new Vector2d(12,-40)).build();


                }
                else {
                    return drive.trajectorySequenceBuilder(REDRIGHTSTART)
                            .lineToLinearHeading(new Pose2d(16,-40, Math.toRadians(250))).build();
                }
            }
            else return null;
        }
        else if(color ==MeepMeepAutoUtil.BLUE) {
            if (side == MeepMeepAutoUtil.LEFT) {
                if (zone == 1) {
                    return drive.trajectorySequenceBuilder(BLUELEFTSTART)
                            .lineToLinearHeading(new Pose2d(16, 40, Math.toRadians(110))).build();
                } else if (zone == 2) {
                    return drive.trajectorySequenceBuilder(BLUELEFTSTART)
                            .lineTo(new Vector2d(12, 35)).build();
                } else {
                    return drive.trajectorySequenceBuilder(BLUELEFTSTART)
                            .lineToLinearHeading(new Pose2d(8, 40, Math.toRadians(70))).build();
                }
            } else if (side == MeepMeepAutoUtil.RIGHT) {
                if (zone == 1) {
                    return drive.trajectorySequenceBuilder(BLUERIGHTSTART)
                            .lineToLinearHeading(new Pose2d(-32, 40, Math.toRadians(110))).build();
                } else if (zone == 2) {
                    return drive.trajectorySequenceBuilder(BLUERIGHTSTART)
                            .lineTo(new Vector2d(-36, 40)).build();


                } else {
                    return drive.trajectorySequenceBuilder(BLUERIGHTSTART)
                            .lineToLinearHeading(new Pose2d(-40, 40, Math.toRadians(70))).build();
                }

            }
            else return null;
        }
        else return null;
    }

    public static TrajectorySequence getBackdropAutoAction(int color, int side, int zone, DriveShim robot, DriveShim startPose){
        if(color==MeepMeepAutoUtil.RED){
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
        else if(color ==MeepMeepAutoUtil.BLUE){
            if(side == MeepMeepAutoUtil.LEFT){
                if(zone ==1){
                    return null;
                }
                else if(zone ==2){
                    return null;
                }else{
                    return null;
                }
            }
            else if(side == MeepMeepAutoUtil.RIGHT){
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

    public static TrajectorySequence getPixelStackAutoAction(int color, int side, int zone){
        return null;
    }
}
