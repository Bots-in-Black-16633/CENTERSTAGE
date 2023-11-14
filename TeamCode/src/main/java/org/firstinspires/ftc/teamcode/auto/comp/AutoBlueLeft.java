package org.firstinspires.ftc.teamcode.auto.comp;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
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
    double backDropY = 0;
    double firstEndAngle = 270;
    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.BLUELEFTSTART);
        TeamPropDetector.startPropDetection(robot.camera, pen);
    }

    @Override
    public void onStart() {
        zone = 2;
        if(zone ==1){backDropY=36;}
        else if(zone ==2){backDropY=32;}
        else{backDropY=25;}
        TeamPropDetector.endPropDetection();
        pen.addLine("ZONE: " + zone);
        pen.update();
        AutoUtil.delay(1);
        Actions.runBlocking(getZone(zone));
       // robot.slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
        //AutoUtil.delay(1);
        Actions.runBlocking(robot.slowOuttake());
        AutoUtil.delay(1);
//        Actions.runBlocking(robot.drive.actionBuilder(robot.drive.pose)
//                        .splineToConstantHeading(new Vector2d(12, 55), robot.drive.pose.heading)
//                .splineToConstantHeading(new Vector2d(12, 60), robot.drive.pose.heading)
//                .splineToLinearHeading(new Pose2d(35,50, robot.drive.pose.heading.log()), Math.toRadians(0))
//                .splineToLinearHeading(new Pose2d(50, backDropY, Math.toRadians(180)), Math.toRadians(0))
//                .splineToLinearHeading(new Pose2d(58, backDropY, Math.toRadians(180)), Math.toRadians(0)).build());
//        Actions.runBlocking(robot.outtake());
//        robot.hopper.outtake(Hopper.ALL);
//        AutoUtil.delay(1);
//        robot.hopper.rest(Hopper.ALL);
//
//        Actions.runBlocking(robot.resetToIntake());
    }


    @Override
    public void onStop() {

    }
    public Action getZone1(){
        return robot.drive.actionBuilder(AutoUtil.BLUELEFTSTART).lineToX(14).lineToY(55).build();
    }
    public Action getZone2(){
        return robot.drive.actionBuilder(AutoUtil.BLUELEFTSTART).lineToY(42).build();
    }
    public Action getZone3(){
        return robot.drive.actionBuilder(AutoUtil.BLUELEFTSTART).lineToY(55).lineToX(10).build();
    }
    public Action getZone(int zone){
        if(zone==1)return getZone1();
        else if(zone==2)return getZone2();
        else return getZone3();
    }
}
