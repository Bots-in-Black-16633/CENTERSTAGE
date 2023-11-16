package org.firstinspires.ftc.teamcode.auto.comp.Backdrop;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropDetector;

@Autonomous(group = "backdrop")
public class BackdropBlueLeft extends SampleAuto {
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




    }



    @Override
    public void onStop() {

    }

}
