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
         myBot = new DefaultBotBuilder(meepMeep).setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 14.27).build();
        MeepMeepAutoUtil auto = new MeepMeepAutoUtil(myBot.getDrive());
        /** test here**/
        myBot.runAction(auto.getSpikeAutoAction(MeepMeepAutoUtil.RED, MeepMeepAutoUtil.LEFT, 3));
        /**test here**/
        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();







    }

}