package com.example.meepmeep;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Arclength;
import com.acmerobotics.roadrunner.MecanumKinematics;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TankKinematics;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
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
//        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(54,-34, Math.toRadians(180)))
//                .splineToConstantHeading(new Vector2d(26.16, -5.35), Math.toRadians(180.00))
//                .splineToConstantHeading(new Vector2d(-61.69, -12.60), Math.toRadians(180.00)).build());
               myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-38.01, 38, Math.toRadians(270)))
                               .setReversed(true)
                       .splineToConstantHeading(new Vector2d(35.33, 58), Math.toRadians(0))
                       .strafeToLinearHeading(new Vector2d(40, 23), Math.toRadians(-180))
                       .strafeTo(new Vector2d(40, 27))
                       .strafeToConstantHeading(new Vector2d(50, 32)).build());


        //myBot.runAction(auto.getQuickBackdropAutoAction(MeepMeepAutoUtil.RED, MeepMeepAutoUtil.LEFT, 3));
        /**test here**/
        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();







    }

}