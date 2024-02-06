package org.firstinspires.ftc.teamcode.auto.comp.old.Spike;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Disabled
@Autonomous(name="SPIKEBLueRight", group="Spike")
public class SpikeBlueRight extends SampleAuto {
    BaseRobot robot;
    int zone;

    @Override
    public void onInit() {
        robot  = new BaseRobot(hardwareMap, AutoUtil.BLUERIGHTSTART);
        TeamPropPartitionDetector.startPropDetection(robot.camera, pen);
    }

    @Override
    public void onStart() {
        zone = TeamPropPartitionDetector.getBluePropZone();
        TeamPropPartitionDetector.endPropDetection();
        pen.addLine("ZONE: " + zone);
        pen.update();
        Actions.runBlocking(robot.autoGenerator.getSpikeAutoAction(AutoUtil.BLUE, AutoUtil.RIGHT, zone));
        robot.drive.updatePoseEstimate();
        robot.drive.drawPoseHistory(pen.getPacket().fieldOverlay());
        pen.addLine("POSE: " + robot.drive.pose.position + " Heading "+ robot.drive.pose.heading);
        pen.update();

    }


    @Override
    public void onStop() {

    }


}

