package org.firstinspires.ftc.teamcode.auto.comp.states.BSS;

import static org.firstinspires.ftc.teamcode.auto.util.AutoUtil.RED;
import static org.firstinspires.ftc.teamcode.auto.util.AutoUtil.RIGHT;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Autonomous(name="BSSRRI", group="BSS")
public class BSSRRI extends SampleAuto {
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
        //Actions.runBlocking(robot.autoGenerator.getBSSStartToBackdrop(AutoUtil.RED, RIGHT, zone));
        Actions.runBlocking(
                robot.autoGenerator.getBSSStartToBackdropRaw(AutoUtil.RED, RIGHT, zone).afterTime(0, robot.outtake()).build());

        //Actions.runBlocking(robot.outtake());
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());

        Actions.runBlocking(robot.hopper.hopperOutake());
        Actions.runBlocking(robot.resetToIntake());
        Actions.runBlocking(new ParallelAction(robot.autoGenerator.getBSSBackToSpike(AutoUtil.RED, RIGHT, zone)));

        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        Actions.runBlocking(telemetryPacket ->{robot.linkage.raise();return false;});
        AutoUtil.delay(1);
        Actions.runBlocking(robot.autoGenerator.getBSSSpikeToStack(AutoUtil.RED, RIGHT, zone));

        Actions.runBlocking(robot.dragAndSuckStackIntake());
        robot.drive.updatePoseEstimate();
        Actions.runBlocking(robot.outtakeExcessPixels());
        robot.hopper.rest(Hopper.ALL);
        robot.intake.setMode(Intake.OUTTAKE);

        //Actions.runBlocking(robot.autoGenerator.getStackToBackdropAutoAction(AutoUtil.RED, zone));
        //robot.intake.setMode(Intake.REST);

        Actions.runBlocking(robot.drive.actionBuilder(robot.drive.pose)
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(4.39, -10, Math.toRadians(180)), Math.toRadians(0),(pose, path, disp) -> {return 100;}, new ProfileAccelConstraint(-120,120))
                        .afterTime(.5, robot.midOuttake())
                .splineToConstantHeading(new Vector2d(53.5, -34), Math.toRadians(0)));
        Actions.runBlocking(robot.hopper.hopperOutake());
        robot.drive.updatePoseEstimate();
        Actions.runBlocking(robot.resetToIntake());
        Actions.runBlocking(robot.autoGenerator.getBackStageParkAutoAction(AutoUtil.RED, AutoUtil.RIGHT, true));





    }




    @Override
    public void onStop() {

    }

}
