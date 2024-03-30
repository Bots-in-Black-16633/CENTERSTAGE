package org.firstinspires.ftc.teamcode.auto.comp.states.BS;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Autonomous(name="BSRRI", group="BS")
public class BSRRI extends SampleAuto {
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
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getBSSStartToBackdrop(AutoUtil.RED, AutoUtil.RIGHT, zone)));
        Actions.runBlocking(robot.outtake());
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        Actions.runBlocking((t)->{AutoUtil.delay(.5);return false;});

        Actions.runBlocking(robot.hopper.hopperOutake());
        Actions.runBlocking((t)->{AutoUtil.delay(.5);return false;});
        Actions.runBlocking(robot.resetToIntake());


        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getBSSBackToSpike(AutoUtil.RED, AutoUtil.RIGHT, zone)));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        Actions.runBlocking(telemetryPacket -> {robot.linkage.raise();AutoUtil.delay(1);return false;});
        Actions.runBlocking(robot.autoGenerator.getBackStageParkAutoAction(AutoUtil.RED, AutoUtil.RIGHT, true));


    }




    @Override
    public void onStop() {

    }

}
