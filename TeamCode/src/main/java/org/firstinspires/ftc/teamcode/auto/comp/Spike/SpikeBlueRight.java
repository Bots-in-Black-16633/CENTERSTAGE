package org.firstinspires.ftc.teamcode.auto.comp.Spike;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropDetector;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Autonomous(name="SPIKEBLueRight", group="Spike")
public class SpikeBlueRight extends SampleAuto {
    BaseRobot robot;
    int zone;

    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.BLUERIGHTSTART);
        TeamPropPartitionDetector.startPropDetection(robot.camera, pen);
    }

    @Override
    public void onStart() {
        zone = TeamPropPartitionDetector.getBluePropZone();

        TeamPropPartitionDetector.endPropDetection();
        pen.addLine("ZONE: " + zone);
        pen.update();
        Actions.runBlocking(getZone(zone));
    }


    @Override
    public void onStop() {

    }
    public Action getZone(int zone) {
        if (zone == 1) {
            robot.drive.actionBuilder(AutoUtil.BLUERIGHTSTART)
                    .strafeToConstantHeading(new Vector2d(-48.07, 47.86))
                    .strafeToLinearHeading(new Vector2d(-34.16, 30.07), Math.toRadians(180.00))
                    .strafeToConstantHeading(new Vector2d(-41.52, 30.07))
                    .build();
        } else if (zone == 2) {
            return robot.drive.actionBuilder(AutoUtil.BLUERIGHTSTART)
                    .splineTo(new Vector2d(-36.00, 35.00), Math.toRadians(270.00))
                    .strafeToLinearHeading(new Vector2d(-47.55, 43.93), Math.toRadians(180.00))
                    .build();
        } else {
            return robot.drive.actionBuilder(AutoUtil.BLUERIGHTSTART)
                    .strafeToLinearHeading(new Vector2d(-47.46, 38.13), Math.toRadians(270.00))
                    .strafeToLinearHeading(new Vector2d(-47.46, 49.08), Math.toRadians(180))
                    .build();
        }
        return null;
    }

}

