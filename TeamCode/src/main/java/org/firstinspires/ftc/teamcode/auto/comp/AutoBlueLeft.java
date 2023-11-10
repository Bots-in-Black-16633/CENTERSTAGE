package org.firstinspires.ftc.teamcode.auto.comp;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropDetector;

@Autonomous
public class AutoBlueLeft extends SampleAuto {
    BaseRobot robot;
    int zone;
    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.BLUELEFTSTART);
        TeamPropDetector.startPropDetection(hardwareMap, pen);
    }

    @Override
    public void onStart() {
//        zone = TeamPropDetector.getRedPropZone();
//        TeamPropDetector.endPropDetection();
//        pen.addLine("ZONE: " + zone);
//        pen.update();
        robot.autoGenerator.delay(.5);
        Actions.runBlocking(robot.autoGenerator.getZone(1));
        robot.intake.setMode(Intake.OUTTAKE);
        AutoUtil.delay(.1);
        robot.intake.setMode(Intake.REST);

    }


    @Override
    public void onStop() {

    }
}
