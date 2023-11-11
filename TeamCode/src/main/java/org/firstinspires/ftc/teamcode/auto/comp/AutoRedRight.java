package org.firstinspires.ftc.teamcode.auto.comp;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropDetector;

@Autonomous
public class AutoRedRight extends SampleAuto {
    BaseRobot robot;
    int zone;
    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.REDRIGHTSTART);
        TeamPropDetector.startPropDetection(hardwareMap, pen);
    }

    @Override
    public void onStart() {
        zone = TeamPropDetector.getRedPropZone();
        TeamPropDetector.endPropDetection();
        pen.addLine("ZONE: " + zone);
        pen.update();
        robot.autoGenerator.delay(.5);
        Actions.runBlocking(getZone(zone));
        Actions.runBlocking(robot.slowOuttake());

    }


    @Override
    public void onStop() {

    }
    public Action getZone1(){
        return robot.drive.actionBuilder(AutoUtil.BLUELEFTSTART).splineToLinearHeading(new Pose2d(6.00, 35, Math.toRadians(0)), Math.toRadians(-90.00)).build();
    }
    public Action getZone2(){
        return robot.drive.actionBuilder(AutoUtil.BLUELEFTSTART).lineToY(47).build();
    }
    public Action getZone3(){
        return robot.drive.actionBuilder(AutoUtil.BLUELEFTSTART).splineToLinearHeading(new Pose2d(14.00, 35, Math.toRadians(180)), Math.toRadians(-90.00)).build();
    }
    public Action getZone(int zone){
        if(zone==1)return getZone1();
        else if(zone==2)return getZone2();
        else return getZone3();
    }
}
