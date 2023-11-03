package org.firstinspires.ftc.teamcode.teleop.helper;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Climber;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;
@TeleOp(name="ClimberTester", group="tester")
public class ClimberTester extends SampleTeleop {


    @Override
    public void onInit() {
        robot = new BaseRobot(hardwareMap, new Pose2d(0,0,0));

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onLoop() {
        if(gamepad1.a)robot.climber.setMode(Climber.RAISE);
        else if(gamepad1.b)robot.climber.setMode(Climber.LOWER);
        else robot.climber.setMode(Climber.REST);
    }

    @Override
    public void onStop() {

    }





}
