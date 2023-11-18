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

@Autonomous(name="BACKDROPRedRight", group="Backdrop")
public class BackdropRedRight extends SampleAuto {
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
        pen.addLine(AutoUtil.RIGHTSIDE + " " + AutoUtil.REDSIDE + " " + zone);
        pen.update();

        Actions.runBlocking(getAuto(zone));
        //Actions.runBlocking(robot.autoGenerator.getBackdropAutoAction(AutoUtil.RIGHTSIDE, AutoUtil.REDSIDE, zone));
        Actions.runBlocking(robot.outtake());
        robot.hopper.outtake(Hopper.ALL);
        AutoUtil.delay(1);
        robot.hopper.rest(Hopper.ALL);
        Actions.runBlocking(robot.resetToIntake());
    }

    public Action getAuto(int zone){
        if(zone == 3){
            //REFLECTED BLUE LEFT
            return robot.drive.actionBuilder(AutoUtil.REDRIGHTSTART)
                    .strafeToConstantHeading(new Vector2d(23.5, -41))
                    .strafeToConstantHeading(new Vector2d(23.5, -45))
                    .strafeToConstantHeading(new Vector2d(32.65, -48.68))
                    .strafeToLinearHeading(new Vector2d(32.65, -39), Math.toRadians(190))
                    .strafeToConstantHeading(new Vector2d(53, -39))
                    .build();
        }
        else if(zone ==2){
            //REFLECTED BLUE LEFT
            return robot.drive.actionBuilder(AutoUtil.REDRIGHTSTART)
                    .strafeToConstantHeading(new Vector2d(10, -38))
                    .strafeToConstantHeading(new Vector2d(10, -40))
                    .strafeToConstantHeading(new Vector2d(22.31, -41.58))
                    .strafeToLinearHeading(new Vector2d(50.30, -45), Math.toRadians(180))
                    .strafeToConstantHeading(new Vector2d(52, -45))
                    .build();
        }
        else {
            /*return robot.drive.actionBuilder(AutoUtil.REDRIGHTSTART)
                    .strafeToConstantHeading(new Vector2d(24.00, -48.00))
                    .strafeToLinearHeading(new Vector2d(11.5, -27), Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(36, -27), Math.toRadians(180.00))
                    .strafeToConstantHeading(new Vector2d(56.5, -28.5))
                    .build();*/
            return robot.drive.actionBuilder(AutoUtil.REDRIGHTSTART)
                    .strafeToConstantHeading(new Vector2d(23.73, -47.66))
                    .strafeToLinearHeading(new Vector2d(11.5, -25), Math.toRadians(-20))
                    .strafeToConstantHeading(new Vector2d(15, -25))
                    .strafeToLinearHeading(new Vector2d(40, -27), Math.toRadians(187))
                    .strafeToConstantHeading(new Vector2d(52, -27))
                    .build();
        }
    }


    @Override
    public void onStop() {

    }

}
