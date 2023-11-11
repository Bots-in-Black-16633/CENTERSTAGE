package org.firstinspires.ftc.teamcode.auto.comp;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropDetector;

@Autonomous
public class AutoBlueLeft extends SampleAuto {
    BaseRobot robot;
    int zone;
    double backdropDropOffY = 0;
    double firstEndAngle = 270;
    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.BLUELEFTSTART);
        TeamPropDetector.startPropDetection(hardwareMap, pen);
    }

    @Override
    public void onStart() {
        zone = 2;
        if(zone ==1){backdropDropOffY=36;firstEndAngle=180;}
        else if(zone ==2){backdropDropOffY=32;firstEndAngle=270;}
        else{backdropDropOffY=25;firstEndAngle=0;}
        TeamPropDetector.endPropDetection();
        pen.addLine("ZONE: " + zone);
        pen.update();
        AutoUtil.delay(1);
        Actions.runBlocking(getZone(zone));
        robot.slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
        AutoUtil.delay(1);
        Actions.runBlocking(robot.slowOuttake());
        AutoUtil.delay(1);
        Actions.runBlocking(robot.drive.actionBuilder(new Pose2d(12,50, Math.toRadians(firstEndAngle))).splineToConstantHeading(new Vector2d(12, 60), Math.toRadians(firstEndAngle)).splineToConstantHeading(new Vector2d(35,60), Math.toRadians(270)).splineToLinearHeading(new Pose2d(50, backdropDropOffY, Math.toRadians(180)), Math.toRadians(-90)).splineToConstantHeading(new Vector2d(58, backdropDropOffY), Math.toRadians(180)).build());
        Actions.runBlocking(robot.outtake());
        robot.hopper.outtake(Hopper.ALL);
        AutoUtil.delay(1);
        robot.hopper.rest(Hopper.ALL);

        Actions.runBlocking(robot.resetToIntake());
    }


    @Override
    public void onStop() {

    }
    public Action getZone1(){
        return robot.drive.actionBuilder(AutoUtil.BLUELEFTSTART).splineToLinearHeading(new Pose2d(6.00, 35, Math.toRadians(0)), Math.toRadians(-90.00)).build();
    }
    public Action getZone2(){
        return robot.drive.actionBuilder(AutoUtil.BLUELEFTSTART).lineToY(50).build();
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
