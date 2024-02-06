package org.firstinspires.ftc.teamcode.auto.comp.states.BS;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Autonomous(name="SBRLOD", group="BS")

public class SBRLOD extends SampleAuto {
    BaseRobot robot;
    int zone;

    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.REDLEFTSTART);
        TeamPropPartitionDetector.startPropDetection(robot.camera, pen);
    }

    @Override
    public void onStart() {
        zone = TeamPropPartitionDetector.getBluePropZone();
        TeamPropPartitionDetector.endPropDetection();
        pen.addLine("ZONE: " + zone);
        pen.update();
        Actions.runBlocking(robot.autoGenerator.getSpikeAutoAction(AutoUtil.RED, AutoUtil.LEFT, zone));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());

        Actions.runBlocking(robot.autoGenerator.getBackdropAutoActionNoAprilTag(AutoUtil.RED, AutoUtil.LEFT, zone));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());

        Actions.runBlocking(robot.outtake());
        Actions.runBlocking(robot.hopper.hopperOutake());
        Actions.runBlocking(robot.resetToIntake());
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());

        Actions.runBlocking(robot.autoGenerator.getBackStageParkAutoAction(AutoUtil.RED, AutoUtil.LEFT));

    }


    @Override
    public void onStop() {

    }
}
