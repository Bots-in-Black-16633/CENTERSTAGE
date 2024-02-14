package org.firstinspires.ftc.teamcode.auto.comp.states.BSS;

import static org.firstinspires.ftc.teamcode.auto.util.AutoUtil.RED;
import static org.firstinspires.ftc.teamcode.auto.util.AutoUtil.RIGHT;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Autonomous(name="BSSBLO", group="BSS")
public class BSSBLO extends SampleAuto {
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
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getBSSStartToBackdrop(AutoUtil.BLUE, AutoUtil.LEFT, zone)));
        Actions.runBlocking(robot.resetToIntake());
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();
        Actions.runBlocking(robot.hopper.hopperOutake());
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getBSSBackToSpike(AutoUtil.BLUE, AutoUtil.LEFT, zone), new SequentialAction(new SleepAction(.5),robot.resetToIntake())));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        Actions.runBlocking(telemetryPacket ->{robot.linkage.raise();return false;});
        Actions.runBlocking(robot.autoGenerator.getBSSSpikeToStack(AutoUtil.BLUE, AutoUtil.LEFT, zone));
        Actions.runBlocking(telemetryPacket -> {robot.linkage.raise();AutoUtil.delay(.5);return false;});
        Actions.runBlocking(robot.dragAndSuckStackIntake());
        robot.drive.updatePoseEstimate();
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getStackToBackdropAutoAction(AutoUtil.BLUE, zone), new SequentialAction( robot.outtakeExcessPixels(), new SleepAction(2), robot.midOuttake())));
        Actions.runBlocking(robot.hopper.hopperOutake());
        robot.drive.updatePoseEstimate();
        Actions.runBlocking(robot.resetToIntake());
        Actions.runBlocking(robot.autoGenerator.getBackStageParkAutoAction(AutoUtil.BLUE, AutoUtil.LEFT, false));




    }




    @Override
    public void onStop() {

    }

}
