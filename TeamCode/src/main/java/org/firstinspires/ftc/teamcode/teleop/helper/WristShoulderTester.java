package org.firstinspires.ftc.teamcode.teleop.helper;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;
@TeleOp(name="WristShoulderTester", group="tester")
public class WristShoulderTester extends SampleTeleop {



    public double wristPos;
    public double shoulderPos;

    @Override
    public void onInit() {
        robot = new BaseRobot(hardwareMap, new Pose2d(0,0,0));
        wristPos = robot.wrist.getPosition();
        shoulderPos = robot.shoulder.getPosition();

    }

    @Override
    public void onStart() {


    }

    @Override
    public void onLoop() {

        wristPos -= gamepad1.left_stick_y*.001;
        shoulderPos += gamepad1.right_stick_x*.001;
        if(wristPos > Constants.WristConstants.wristMax)wristPos = Constants.WristConstants.wristMax;
        if(wristPos < Constants.WristConstants.wristMin)wristPos = Constants.WristConstants.wristMin;
        if(shoulderPos > Constants.ShoulderConstants.shoulderMax)shoulderPos=Constants.ShoulderConstants.shoulderMax;
        if(shoulderPos < Constants.ShoulderConstants.shoulderMin)shoulderPos=Constants.ShoulderConstants.shoulderMin;

        robot.wrist.setPosition(wristPos);
        robot.shoulder.setPosition(shoulderPos);
        pen.addLine("wristTarget: " + wristPos);
        pen.addLine("shoulderTarger: " + shoulderPos);
    }

    @Override
    public void onStop() {

    }





}
