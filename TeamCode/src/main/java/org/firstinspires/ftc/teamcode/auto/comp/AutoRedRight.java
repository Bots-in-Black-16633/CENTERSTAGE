package org.firstinspires.ftc.teamcode.auto.comp;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropDetector;

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
        robot.autoGenerator.getZone(zone).run(pen.getPacket());

    }


    @Override
    public void onStop() {

    }
}
