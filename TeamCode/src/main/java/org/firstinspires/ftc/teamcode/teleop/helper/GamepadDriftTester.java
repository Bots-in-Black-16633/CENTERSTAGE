package org.firstinspires.ftc.teamcode.teleop.helper;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class GamepadDriftTester extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        while(!isStopRequested() && opModeIsActive()){
            telemetry.addLine("A: " + gamepad1.a);
            telemetry.addLine("B: " + gamepad1.b);
            telemetry.addLine("X: " + gamepad1.x);
            telemetry.addLine("Y: " + gamepad1.y);
            telemetry.addLine("LB: " + gamepad1.left_bumper);
            telemetry.addLine("RB: " + gamepad1.right_bumper);

            telemetry.addLine("LT: " + gamepad1.left_trigger);
            telemetry.addLine("RT: " + gamepad1.right_trigger);
            telemetry.addLine("LJX: " + gamepad1.left_stick_x);
            telemetry.addLine("LJY: " + gamepad1.left_stick_y);
            telemetry.addLine("RJX: " + gamepad1.right_stick_x);
            telemetry.addLine("RJY: " + gamepad1.right_stick_y);
            telemetry.update();

        }
    }
}
