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
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Autonomous(name="BSSRRO", group="BSS")
public class BSSRRO extends SampleAuto {
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
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getBSSStartToBackdrop(AutoUtil.RED, RIGHT, zone)));
        Actions.runBlocking(robot.outtake());
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();
        //Actions.runBlocking(robot.outtake());
        Actions.runBlocking(robot.hopper.hopperOutake());
        Actions.runBlocking(robot.resetToIntake());
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getBSSBackToSpike(AutoUtil.RED, RIGHT, zone)));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        Actions.runBlocking(telemetryPacket ->{robot.linkage.raise();return false;});
        AutoUtil.delay(1);
        Actions.runBlocking(robot.autoGenerator.getBSSSpikeToStack(AutoUtil.RED, RIGHT, zone));
        Actions.runBlocking(telemetryPacket -> {robot.linkage.raise();AutoUtil.delay(.5);return false;});
        //Actions.runBlocking(robot.offTheTopStackIntake(5));
        Actions.runBlocking(robot.dragAndSuckStackIntake());
        Actions.runBlocking(robot.outtakeExcessPixels());
        robot.hopper.rest(Hopper.ALL);
        robot.intake.setMode(Intake.OUTTAKE);
        robot.drive.updatePoseEstimate();
        robot.intake.setMode(Intake.OUTTAKE);
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getStackToBackdropAutoAction(AutoUtil.RED, zone)));
        robot.intake.setMode(Intake.REST);
        Actions.runBlocking(robot.midOuttake());

        Actions.runBlocking(robot.hopper.hopperOutake());
        robot.drive.updatePoseEstimate();
        Actions.runBlocking(robot.resetToIntake());
        Actions.runBlocking(robot.autoGenerator.getBackStageParkAutoAction(AutoUtil.RED, AutoUtil.RIGHT, false));





    }




    @Override
    public void onStop() {

    }

}
