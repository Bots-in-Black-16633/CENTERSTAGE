package org.firstinspires.ftc.teamcode.auto.comp.BSS;

import static org.firstinspires.ftc.teamcode.auto.util.AutoUtil.RED;
import static org.firstinspires.ftc.teamcode.auto.util.AutoUtil.RIGHT;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Autonomous(name="BSSRedRight", group="QuickStack")
public class BSSRedRight extends SampleAuto {
    BaseRobot robot;
    int zone;
    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.REDRIGHTSTART);
        TeamPropPartitionDetector.startPropDetection(robot.camera, pen);
    }

    @Override
    public void onStart() {
        zone = TeamPropPartitionDetector.getRedPropZone();
        TeamPropPartitionDetector.endPropDetection();
        pen.addLine("ZONE: " + zone);
        pen.update();
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getBSSStartToBackdrop(AutoUtil.RED, RIGHT, zone),new SequentialAction(new SleepAction(1), robot.outtake())));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();
        //Actions.runBlocking(robot.outtake());
        Actions.runBlocking(robot.hopper.hopperOutake());
        //Actions.runBlocking(robot.resetToIntake());
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getBSSBackToSpike(AutoUtil.RED, RIGHT, zone), new SequentialAction(new SleepAction(.5),robot.resetToIntake())));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        Actions.runBlocking(robot.autoGenerator.getBSSSpikeToStack(AutoUtil.RED, RIGHT, zone));
        Actions.runBlocking(telemetryPacket -> {robot.linkage.raise();AutoUtil.delay(.5);return false;});
        Actions.runBlocking(robot.dragAndSuckStackIntake());
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getStackToBackdropAutoAction(AutoUtil.RED, zone), new SequentialAction(new SleepAction(2.5), robot.highOuttake())));
        Actions.runBlocking(robot.hopper.hopperOutake());
        robot.drive.updatePoseEstimate();
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getBackdropToStackAutoAction(RED), new SequentialAction(new SleepAction(.5), robot.resetToIntake())));
        Actions.runBlocking(robot.dragAndSuckStackIntake());
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getStackToBackdropAutoAction(AutoUtil.RED, zone), new SequentialAction(new SleepAction(2.5), robot.highOuttake())));
        Actions.runBlocking(robot.hopper.hopperOutake());
        robot.drive.updatePoseEstimate();




    }




    @Override
    public void onStop() {

    }

}
