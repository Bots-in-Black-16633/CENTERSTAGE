package org.firstinspires.ftc.teamcode.teleop.helper;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;
import org.firstinspires.ftc.teamcode.vision.AprilTagProcessorWrapper;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp(name="AprilTagLocalizationTester", group="tester")
public class AprilTagLocalizationTester extends SampleTeleop {
    VisionPortal portal;

    @Override
    public void onInit() {
        robot = new BaseRobot(hardwareMap, new Pose2d(0,0,0));
        AprilTagProcessorWrapper.startAprilTagDetection(robot.camera, pen);

    }

    @Override
    public void onStart() {
    }

    @Override
    public void onLoop() {
        AprilTagProcessorWrapper.getRobotPoseEstimateAprilTag(10, pen);


    }


    @Override
    public void onStop() {

    }
}
