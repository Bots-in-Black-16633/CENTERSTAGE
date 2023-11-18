package com.example.meepmeep;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Vector;


public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                                drive.trajectorySequenceBuilder(new Pose2d(12.00, -63.00, Math.toRadians(270.00)))
                                        .lineToConstantHeading(new Vector2d(23.73, -47.66))
                                        .lineToLinearHeading(new Pose2d(9.82, -32.93, Math.toRadians(0.00)))
                                        .lineToLinearHeading(new Pose2d(44.80, -29.25, Math.toRadians(180.00)))
                                        .splineToConstantHeading(new Vector2d(53.39, -29.25), Math.toRadians(3.24))
                                        .build());

                        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}