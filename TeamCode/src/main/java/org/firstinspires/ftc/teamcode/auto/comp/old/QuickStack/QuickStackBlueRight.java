package org.firstinspires.ftc.teamcode.auto.comp.old.QuickStack;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;
@Disabled

@Autonomous(name="QuickStackBlueRight", group="QuickStack")
public class QuickStackBlueRight extends SampleAuto {
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
        Actions.runBlocking(robot.autoGenerator.getQuickBackdropAutoAction(AutoUtil.BLUE, AutoUtil.RIGHT, zone));
        robot.drive.backward(.2, .5);
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();
        Actions.runBlocking(robot.outtake());
        Actions.runBlocking(robot.hopper.hopperOutake());
        Actions.runBlocking(robot.resetToIntake());
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();
        Actions.runBlocking(robot.autoGenerator.getQuickToSpikeAutoAction(AutoUtil.BLUE, 3));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();

        //Actions.runBlocking(robot.firstStack());

        Actions.runBlocking(robot.autoGenerator.getStackToBackdropAutoAction(AutoUtil.BLUE, zone));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();

        Actions.runBlocking(robot.outtake());
        Actions.runBlocking(robot.hopper.hopperOutake());
        Actions.runBlocking(robot.resetToIntake());

        Actions.runBlocking(robot.autoGenerator.getQuickToSpikeAutoAction(AutoUtil.BLUE, 3));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();

        //Actions.runBlocking(robot.secondStack());

        Actions.runBlocking(robot.autoGenerator.getStackToBackdropAutoAction(AutoUtil.BLUE, zone));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();

        Actions.runBlocking(robot.outtake());
        Actions.runBlocking(robot.hopper.hopperOutake());
        Actions.runBlocking(robot.resetToIntake());

        Actions.runBlocking(robot.autoGenerator.getBackStageParkAutoAction(AutoUtil.BLUE, zone));

    }


    @Override
    public void onStop() {

    }

}
