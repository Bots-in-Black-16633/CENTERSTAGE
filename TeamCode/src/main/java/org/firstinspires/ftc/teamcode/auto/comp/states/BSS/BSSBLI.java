package org.firstinspires.ftc.teamcode.auto.comp.states.BSS;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Autonomous(name="BSSBLI", group="BSS")
public class BSSBLI extends SampleAuto {
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
        Actions.runBlocking(robot.outtake());
        Actions.runBlocking(robot.hopper.hopperOutake());
        Actions.runBlocking(robot.resetToIntake());


        robot.drive.updatePoseEstimate();

        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getBSSBackToSpike(AutoUtil.BLUE, AutoUtil.LEFT, zone)));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        Actions.runBlocking(telemetryPacket ->{robot.linkage.raise();return false;});
        AutoUtil.delay(1);
        Actions.runBlocking(robot.autoGenerator.getBSSSpikeToStack(AutoUtil.BLUE, AutoUtil.LEFT, zone));
        Actions.runBlocking(telemetryPacket -> {robot.linkage.raise();AutoUtil.delay(.52);return false;});
        Actions.runBlocking(robot.dragAndSuckStackIntake());
        robot.drive.updatePoseEstimate();
        Actions.runBlocking(robot.outtakeExcessPixels());
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getStackToBackdropAutoAction(AutoUtil.BLUE, zone)));
        Actions.runBlocking(robot.midOuttake());
        Actions.runBlocking(robot.hopper.hopperOutake());
        robot.drive.updatePoseEstimate();
        Actions.runBlocking(robot.resetToIntake());
        Actions.runBlocking(robot.drive.actionBuilder(robot.drive.pose).strafeTo(new Vector2d(55,1)).build());




    }




    @Override
    public void onStop() {

    }

}
