package org.firstinspires.ftc.teamcode.teleop.helper;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

import java.util.List;

@TeleOp(name = "Claw Tester", group = "helper")
public class ClawTester extends LinearOpMode {
    Claw claw = new Claw(hardwareMap);
    ColorfulTelemetry pen  = new ColorfulTelemetry(telemetry);
    double rightServoPosition = 0;
    double leftServoPosition = 0;




    @Override
    public void runOpMode() throws InterruptedException {
        List<Servo> servos = hardwareMap.getAll(Servo.class);
        for (Servo s:servos){
            telemetry.addLine("Name:"  + s.getDeviceName() + " Port: " + s.getPortNumber());
        }
        telemetry.update();
        waitForStart();


        while(!isStopRequested() && opModeIsActive()){
            rightServoPosition += gamepad1.left_stick_x*.001;
            leftServoPosition += gamepad1.right_stick_x*.001;
            if(rightServoPosition >1)rightServoPosition=1;
            else if(rightServoPosition<0)rightServoPosition=0;
            if(leftServoPosition > 1)leftServoPosition=1;
            else if(leftServoPosition < 0)leftServoPosition=0;

            claw.setPosition(leftServoPosition,rightServoPosition);
            claw.printTelemetry(pen);
        }
    }




}