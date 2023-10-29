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
    MecanumDrive drive;
    public static Pose2d startPos = new Pose2d(0,0,0);
    public static double DRIVE_SPEED = 1;
    double robotDegree;
    double gamepadDegree;
    public IMU imu;
    public double offset = 0;

    public GamepadEx g1;



    @Override
    public void runOpMode() throws InterruptedException {
        drive = new MecanumDrive(hardwareMap, startPos);
        g1 = new GamepadEx(gamepad1);

        waitForStart();

        while(!isStopRequested() && opModeIsActive()){
            double xPow;
            double yPow;
            double rotPow;

            // Strafer Mode
            xPow = gamepad1.left_stick_x;
            yPow = -gamepad1.left_stick_y;//inversed to match x y plane coords
            rotPow = gamepad1.right_stick_x;




            double gamepadTheta = Math.atan2(Math.toRadians(yPow), Math.toRadians(xPow));
            double diffTheta = Math.toDegrees(gamepadTheta) - Math.toDegrees(drive.updatePoseEstimate().angVel);
            telemetry.addLine("heading: "+drive.pose.heading.component1());
            telemetry.addLine("Gamepad Target: " + round(yPow) + "/" + round(xPow) + " " + Math.toDegrees(gamepadTheta));

             xPow= Math.cos(Math.toRadians(diffTheta))  *DRIVE_SPEED;
             yPow= Math.sin(Math.toRadians(diffTheta))*DRIVE_SPEED;
             rotPow = rotPow*DRIVE_SPEED;
            if(Math.abs(gamepad1.left_stick_y) > .05 || Math.abs(gamepad1.left_stick_x) >0.05 || Math.abs(gamepad1.right_stick_x) > 0.05)drive.setDrivePowers(new PoseVelocity2d(new Vector2d(xPow, yPow), rotPow));
            else drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0,0),0));
            telemetry.addLine(" X" + xPow + " Y" + yPow + " ROT" + rotPow);
            telemetry.update();

        }
    }
    private double round(double t){
        return ((int)t*100)/100.0;

    }

}