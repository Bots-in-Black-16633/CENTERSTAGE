package org.firstinspires.ftc.teamcode.teleop.helper;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;
@TeleOp(name="IntakeTester", group="tester")
public class IntakeTester extends SampleTeleop {


    @Override
    public void onInit() {
        robot = new BaseRobot(hardwareMap);

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onLoop() {
        if(gamepad1.a)robot.intake.setMode(Intake.INTAKE);
        else if(gamepad1.b)robot.intake.setMode(Intake.OUTTAKE);
        else robot.intake.setMode(Intake.REST);
    }

    @Override
    public void onStop() {

    }





}
