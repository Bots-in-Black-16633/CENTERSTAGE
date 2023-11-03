package org.firstinspires.ftc.teamcode.teleop.helper;

import androidx.core.math.MathUtils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

import java.util.List;

@TeleOp(name = "Tester Teleop", group = "helper")
public class TesterTeleop extends LinearOpMode {
    ColorfulTelemetry pen  = new ColorfulTelemetry(telemetry, FtcDashboard.getInstance());
    BaseRobot robot;


    @Override
    public void runOpMode() throws InterruptedException {
        robot = new BaseRobot(hardwareMap, new Pose2d(0,0,0));



        waitForStart();


        while(!isStopRequested() && opModeIsActive()){
            robot.hopper.setPower(Hopper.RIGHT_HOPPER, gamepad1.left_stick_y);
            robot.hopper.setPower(Hopper.LEFT_HOPPER, gamepad1.right_stick_y);
            robot.printTelemetry(pen);
            robot.periodic();

        }
    }
    private double round(double t){
        return ((int)t*100)/100.0;

    }





}