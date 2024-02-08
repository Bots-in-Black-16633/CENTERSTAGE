package org.firstinspires.ftc.teamcode.auto.comp.states.S;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;

@Autonomous(name="SBROD", group="S")
public class SBROD extends SampleAuto {
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
        Actions.runBlocking(robot.autoGenerator.getSpikeParkAction(AutoUtil.BLUE, AutoUtil.RIGHT, zone, false));



    }


    @Override
    public void onStop() {

    }


}
