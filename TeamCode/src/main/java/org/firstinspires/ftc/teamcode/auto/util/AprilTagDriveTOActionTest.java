package org.firstinspires.ftc.teamcode.auto.comp.Spike;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.AprilTagProcessorWrapper;
import org.firstinspires.ftc.teamcode.vision.TeamPropDetector;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Autonomous(name="AprilTagDriveTOActionTest")
public class AprilTagDriveTOActionTest extends SampleAuto {
    BaseRobot robot;
    int zone;

    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.REDRIGHTSTART);
        TeamPropPartitionDetector.startPropDetection(robot.camera, pen);
    }

    @Override
    public void onStart() {
        zone = TeamPropPartitionDetector.getBluePropZone();
        TeamPropPartitionDetector.endPropDetection();
        pen.addLine("ZONE: " + zone);
        pen.addLine("REMINDER THIS ONLY WORKS FOR ZONE 3 RED RIGHT");
        pen.update();
        Actions.runBlocking(robot.drive.driveToAprilTag(AutoUtil.RED, 3, robot.camera, pen));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        while(opModeIsActive()){

        }


    }


    @Override
    public void onStop() {
        AprilTagProcessorWrapper.endAprilTagDetection();
    }


}

