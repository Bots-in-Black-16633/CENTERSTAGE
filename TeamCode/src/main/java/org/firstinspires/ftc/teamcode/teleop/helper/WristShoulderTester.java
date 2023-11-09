package org.firstinspires.ftc.teamcode.teleop.helper;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;
@TeleOp(name="WristShoulderTester", group="tester")
public class WristShoulderTester extends SampleTeleop {



    public double wristPos;
    public double shoulderPos;
    public GamepadEx g1;
    @Override
    public void onInit() {
        robot = new BaseRobot(hardwareMap, new Pose2d(0,0,0));
        wristPos = Constants.WristConstants.wristRest;
        shoulderPos = Constants.ShoulderConstants.shoulderRest;
        g1 = new GamepadEx(gamepad1);


    }

    @Override
    public void onStart() {



    }

    @Override
    public void onLoop() {

        wristPos -= gamepad1.left_stick_y*.01;
        shoulderPos += gamepad1.right_stick_y*.01;
        if(wristPos > Constants.WristConstants.wristMax)wristPos = Constants.WristConstants.wristMax;
        if(wristPos < Constants.WristConstants.wristMin)wristPos = Constants.WristConstants.wristMin;
        if(shoulderPos > Constants.ShoulderConstants.shoulderMax)shoulderPos=Constants.ShoulderConstants.shoulderMax;
        if(shoulderPos < Constants.ShoulderConstants.shoulderMin)shoulderPos=Constants.ShoulderConstants.shoulderMin;

        if(g1.wasJustPressed(GamepadKeys.Button.X)){
            wristPos = Constants.WristConstants.wristOuttake;
            shoulderPos = Constants.ShoulderConstants.shoulderOuttake;
        }
        if(g1.wasJustPressed(GamepadKeys.Button.Y)){
            wristPos = Constants.WristConstants.wristRest;
            shoulderPos = Constants.ShoulderConstants.shoulderRest;
        }

        robot.wrist.setPosition(wristPos);
        robot.shoulder.setPosition(shoulderPos);
        pen.addLine("wristTarget: " + wristPos);
        pen.addLine("shoulderTarger: " + shoulderPos);
    }

    @Override
    public void onStop() {

    }





}
