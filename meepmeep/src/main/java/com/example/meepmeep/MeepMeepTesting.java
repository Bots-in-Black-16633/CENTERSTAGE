package com.example.meepmeep;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.acmerobotics.roadrunner.Pose2d;

import org.jetbrains.annotations.NotNull;


public class MeepMeepTesting {

    public static Pose2d drivePose = new Pose2d(0,0,0);
    public static RoadRunnerBotEntity myBot;
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(500);
         myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
        MeepMeepAutoUtil auto = new MeepMeepAutoUtil(myBot.getDrive());


        myBot.runAction(new SequentialAction(auto.getSpikeAutoAction(MeepMeepAutoUtil.RED, MeepMeepAutoUtil.LEFT, 3), new MeepMeepTesting.resetPoseAction(), auto.getSpikeParkAction(MeepMeepAutoUtil.RED, MeepMeepAutoUtil.LEFT, 3)));
        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();



//        RoadRunnerBotEntity spikeMarkPath = new DefaultBotBuilder(meepMeep)
//                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
//                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14)
//                .followTrajectorySequence(drive -> MeepMeepAutoUtil.getSpikeAutoAction(MeepMeepAutoUtil.RED, MeepMeepAutoUtil.RIGHT, 1, drive,null));
//
//        RoadRunnerBotEntity toBackdropPath = new DefaultBotBuilder(meepMeep).setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14)
//                .followTrajectorySequence(drive -> MeepMeepAutoUtil.getBackdropAutoAction(MeepMeepAutoUtil.RED, MeepMeepAutoUtil.RIGHT, 1, drive, spikeMarkPath.getCurrentTrajectorySequence()));
//
//        RoadRunnerBotEntity pixelStackCycle1 = new DefaultBotBuilder(meepMeep).setConstraints(60,90,Math.toRadians(180), Math.toRadians(180), 14)
//                        .followTrajectorySequence(drive -> MeepMeepAutoUtil.getPixelStackAutoAction(MeepMeepAutoUtil.RED, drive, toBackdropPath.getCurrentTrajectorySequence()));
//
//        RoadRunnerBotEntity pixelStackCycle2 = new DefaultBotBuilder(meepMeep).setConstraints(60,90,Math.toRadians(180), Math.toRadians(180), 14)
//                .followTrajectorySequence(drive -> MeepMeepAutoUtil.getPixelStackAutoAction(MeepMeepAutoUtil.RED, drive, pixelStackCycle1.getCurrentTrajectorySequence()));
//
//        RoadRunnerBotEntity park = new DefaultBotBuilder(meepMeep).setConstraints(60,90,Math.toRadians(180), Math.toRadians(180), 14)
//                .followTrajectorySequence(drive -> MeepMeepAutoUtil.getBackStageParkAutoAction(MeepMeepAutoUtil.RED, MeepMeepAutoUtil.RIGHT,drive, pixelStackCycle2.getCurrentTrajectorySequence()));
//
//
//
//        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
//                .setDarkMode(true)
//                .setBackgroundAlpha(0.95f)
//                .addEntity(spikeMarkPath)
//                .addEntity(toBackdropPath)
//                .addEntity(pixelStackCycle1)
//                .addEntity(pixelStackCycle2)
//                .addEntity(park)
//                .start();


    }

}