package org.firstinspires.ftc.teamcode.teleop.helper;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "BlinkinTester", group = "helper")

public class BlinkinTester extends LinearOpMode {
    RevBlinkinLedDriver strip;
    RevBlinkinLedDriver.BlinkinPattern cur;
    boolean dUP = false;
    boolean dDown = false;


    int patternIndex = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        strip = hardwareMap.get(RevBlinkinLedDriver.class, "led");



        waitForStart();

        while(!isStopRequested() && opModeIsActive()){

            if(gamepad1.dpad_up && gamepad1.dpad_up != dUP){
                patternIndex++;
            }
            dUP = gamepad1.dpad_up;

            if(gamepad1.dpad_down && gamepad1.dpad_down != dDown){
                patternIndex--;
            }
            dDown = gamepad1.dpad_down;

            if(gamepad1.a){
                patternIndex=0;
            }

            cur = RevBlinkinLedDriver.BlinkinPattern.values()[patternIndex];
            strip.setPattern(cur);

            telemetry.addLine("PatternIndex" + patternIndex);
            telemetry.addLine("Pattern" + cur.name());
            telemetry.update();






        }
    }
}
