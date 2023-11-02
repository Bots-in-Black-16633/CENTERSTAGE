package org.firstinspires.ftc.teamcode.teleop.helper;

import androidx.core.math.MathUtils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.drive.Drive;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;


import java.util.List;

@TeleOp(name = "Drive Tester", group = "helper")
public class DriveTester extends LinearOpMode {
    ColorfulTelemetry pen  = new ColorfulTelemetry(telemetry, FtcDashboard.getInstance());
    Drive drive;
    public static Pose2d startPos = new Pose2d(0,0,0);
    public static double DRIVE_SPEED = 1;


    public GamepadEx g1;



    @Override
    public void runOpMode() throws InterruptedException {
        drive = new Drive(hardwareMap, startPos);
        g1 = new GamepadEx(gamepad1);

        waitForStart();

        while(!isStopRequested() && opModeIsActive()){
            pen.addLine("lY: " + g1.getLeftY() + " lX " + g1.getLeftX() + " rX " + g1.getRightX());
           drive.driveFieldcentric(g1.getLeftX(), -g1.getLeftY(), g1.getRightX(), DRIVE_SPEED);
            drive.printTelemetry(pen);
            pen.update();
        }
    }
    private double round(double t){
        return ((int)t*100)/100.0;

    }

}