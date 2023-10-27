package org.firstinspires.ftc.teamcode.teleop.helper;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;
@TeleOp(name="HopperTester", group="tester")
public class HopperTester extends SampleTeleop {

    BaseRobot robot;

    @Override
    public void onInit() {
        robot = new BaseRobot(hardwareMap);

    }

    @Override
    public void onLoop() {
        robot.hopper.setPower(Hopper.RIGHT_HOPPER, gamepad1.left_stick_y);
        robot.hopper.setPower(Hopper.LEFT_HOPPER, gamepad1.right_stick_y);
        robot.printTelemetry(pen);
        robot.periodic();
    }

    @Override
    public void onStop() {

    }





}
