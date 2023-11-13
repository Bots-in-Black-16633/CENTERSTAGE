package org.firstinspires.ftc.teamcode.auto.comp;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropDetector;

@Autonomous
public class AutoRedRightBackdrop extends SampleAuto {
    BaseRobot robot;
    int zone;
    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.REDRIGHTSTART);
        TeamPropDetector.startPropDetection(hardwareMap, pen);
    }
    double backDropY = 0;
    double firstEndAngle = 270;


    @Override
    public void onStart() {
        zone = TeamPropDetector.getRedPropZone();
        TeamPropDetector.endPropDetection();
        pen.addLine("ZONE: " + zone);
        pen.update();

        if (zone == 1) {
            backDropY = 45;
        } else if (zone == 2) {
            backDropY = 49;
        } else {
            backDropY = 57;
        }

        AutoUtil.delay(1);
        Actions.runBlocking(robot.drive.actionBuilder(new Pose2d(12, -71.5, Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(12, -55), robot.drive.pose.heading)
                .splineToConstantHeading(new Vector2d(12, -60), robot.drive.pose.heading)
                .splineToLinearHeading(new Pose2d(35,-50, robot.drive.pose.heading.log()), Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(50, -backDropY, Math.toRadians(180)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(58, -backDropY, Math.toRadians(180)), Math.toRadians(0))
                .lineToX(60)
                .build());
        Actions.runBlocking(robot.outtake());
        AutoUtil.delay(1);
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
