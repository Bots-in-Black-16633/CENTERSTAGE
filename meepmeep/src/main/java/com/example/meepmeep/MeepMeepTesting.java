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
      drive.trajectorySequenceBuilder(new Pose2d(12.00, 63.00, Math.toRadians(90.00)))
                                .lineToConstantHeading(new Vector2d(12.00, 34.00))
                                .lineToConstantHeading(new Vector2d(0.00, 34.00))
                                .lineTo(new Vector2d(0, 36))
                                .lineToLinearHeading(new Pose2d(39.27, 50.11, Math.toRadians(180.00)))
                                .lineToConstantHeading(new Vector2d(50.52, 27.61))
                                .build()
                );
                        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}