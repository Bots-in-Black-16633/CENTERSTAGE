package org.firstinspires.ftc.teamcode.teleop.helper;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Pose2d;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

import org.firstinspires.ftc.teamcode.util.SampleTeleop;

@TeleOp(name = "PositionTracker", group = "helper")
public class PositionTracker extends SampleTeleop {
    ColorfulTelemetry pen  = new ColorfulTelemetry(telemetry, FtcDashboard.getInstance());
    public static double DRIVE_SPEED = 1;


    public GamepadEx g1;


    @Override
    public void onInit() {
        g1 = new GamepadEx(gamepad1);
        robot = new BaseRobot(hardwareMap, AutoUtil.REDRIGHTSTART);
        robot.drive.leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.drive.leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.drive.rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.drive.rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onLoop()  {

        pen.addLine("X:"+robot.drive.pose.position.x);
        pen.addLine("Y:"+robot.drive.pose.position.y);
        pen.addLine("HEAding:"+Math.toDegrees(robot.drive.getHeading()));

        pen.update();
        robot.drive.periodic();

    }

    @Override
    public void onStop() {

    }

    private double round(double t){
        return ((int)t*100)/100.0;

    }

}