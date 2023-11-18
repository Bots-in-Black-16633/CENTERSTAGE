package org.firstinspires.ftc.teamcode.auto.comp.Backdrop;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Autonomous(name="BACKDROPRedLeft", group="Backdrop")
public class BackdropRedLeft extends SampleAuto {
    BaseRobot robot;
    int zone;
    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.REDLEFTSTART);
        TeamPropPartitionDetector.startPropDetection(robot.camera, pen);
    }

    @Override
    public void onStart() {
        zone = TeamPropPartitionDetector.getRedPropZone();
        TeamPropPartitionDetector.endPropDetection();
        pen.addLine("ZONE: " + zone);
        pen.update();

        Actions.runBlocking(robot.autoGenerator.getBackdropAutoAction(AutoUtil.RED, AutoUtil.LEFT, zone));
        Actions.runBlocking(robot.outtake());
        robot.hopper.outtake(Hopper.ALL);
        AutoUtil.delay(1);
        robot.hopper.rest(Hopper.ALL);
        Actions.runBlocking(robot.resetToIntake());
    }




    @Override
    public void onStop() {

    }

}
