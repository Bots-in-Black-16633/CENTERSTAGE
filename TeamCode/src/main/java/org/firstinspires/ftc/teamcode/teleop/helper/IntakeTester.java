package org.firstinspires.ftc.teamcode.teleop.helper;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;
@TeleOp(name="IntakeTester", group="tester")
public class IntakeTester extends SampleTeleop {


    @Override
    public void onInit() {
        robot = new BaseRobot(hardwareMap, new Pose2d(0,0,0));

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onLoop() {
        if(gamepad1.a){robot.intake.setMode(Intake.INTAKE);robot.hopper.intake(Hopper.ALL);}
        else if(gamepad1.b){robot.intake.setMode(Intake.OUTTAKE);robot.hopper.outtake(Hopper.ALL);}
        else {robot.intake.setMode(Intake.REST);robot.hopper.rest(Hopper.ALL);}
    }

    @Override
    public void onStop() {

    }





}
