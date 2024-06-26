package org.firstinspires.ftc.teamcode.auto.comp.old.Stack;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;
@Disabled
@Autonomous(name="StackBLueLeft", group="Stack")
public class StackBlueLeft extends SampleAuto {
    BaseRobot robot;
    int zone;

    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.BLUELEFTSTART);
        TeamPropPartitionDetector.startPropDetection(robot.camera, pen);
    }

    @Override
    public void onStart() {
        zone = TeamPropPartitionDetector.getBluePropZone();
        TeamPropPartitionDetector.endPropDetection();
        pen.addLine("ZONE: " + zone);
        pen.update();
        Actions.runBlocking(robot.autoGenerator.getSpikeAutoAction(AutoUtil.BLUE, AutoUtil.LEFT, zone));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();
        Actions.runBlocking(robot.autoGenerator.getBackdropAutoAction(AutoUtil.BLUE, AutoUtil.LEFT, zone));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();
        Actions.runBlocking(robot.drive.driveToAprilTag(AutoUtil.BLUE,zone, robot.camera, pen, false));
        Actions.runBlocking(robot.outtake());
        Actions.runBlocking(robot.hopper.hopperOutake());
        Actions.runBlocking(robot.resetToIntake());
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();
        robot.linkage.raise();//raise the linkage

        Actions.runBlocking(robot.autoGenerator.getBackdropToStackAutoAction(AutoUtil.BLUE));
        //Actions.runBlocking(robot.stackIntake());

       robot.intake.setMode(Intake.OUTTAKE);


        Actions.runBlocking(robot.autoGenerator.getStackToBackdropAutoAction(AutoUtil.BLUE, zone));
        robot.intake.setMode(Intake.REST);

        Actions.runBlocking(robot.highOuttake());
            Actions.runBlocking(robot.hopper.hopperOutake());
            Actions.runBlocking(robot.resetToIntake());

        //Actions.runBlocking(robot.autoGenerator.getBackStageParkAutoAction(AutoUtil.BLUE, AutoUtil.LEFT));



    }


    @Override
    public void onStop() {

    }

}
