package org.firstinspires.ftc.teamcode.auto.comp.Spike;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropDetector;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Autonomous(name="SPIKERedLeft", group="Spike")
public class SpikeRedLeft extends SampleAuto {
    BaseRobot robot;
    int zone;
    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.BLUELEFTSTART);
        TeamPropPartitionDetector.startPropDetection(robot.camera, pen);
    }

    @Override
    public void onStart() {
        zone = TeamPropPartitionDetector.getRedPropZone();
        TeamPropPartitionDetector.endPropDetection();
        pen.addLine("ZONE: " + zone);
        pen.update();

        Actions.runBlocking(getAuto(zone));

    }
    public Action getAuto(int zone){
        if(zone==1){
            return robot.drive.actionBuilder(AutoUtil.BLUELEFTSTART)
                    .strafeToConstantHeading(new Vector2d(22.5, 41))
                    .strafeToConstantHeading(new Vector2d(22.5, 45))
                    .build();
        }
        else if(zone==2){
//            return robot.drive.actionBuilder(AutoUtil.BLUELEFTSTART)
//                    .strafeToConstantHeading(new Vector2d(12, 63))
//                    .strafeToConstantHeading(new Vector2d(12, 45))
//                    .build();
            return robot.drive.actionBuilder(new Pose2d(0, 0, 90))
                    .strafeToConstantHeading(new Vector2d(-7, -26.5))
                    .strafeToConstantHeading(new Vector2d(-7, -15))
                    .build();
        }
        else {
            return robot.drive.actionBuilder(AutoUtil.BLUELEFTSTART)
                    .strafeToConstantHeading(new Vector2d(24.00, 48.00))
                    .strafeToLinearHeading(new Vector2d(13.5, 25), Math.toRadians(0))
                    .strafeToConstantHeading(new Vector2d(16, 25))
                    .build();
        }
    }


    @Override
    public void onStop() {

    }

}
