package org.firstinspires.ftc.teamcode.teleop.helper;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;
@TeleOp(name="IntakeTester", group="tester")
public class IntakeTester extends SampleTeleop {

    private int level = 0;
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

        //Linkage Stuff
        if(gamepad1.dpad_up){robot.linkage.stackLevel(1);}
        else if(gamepad1.dpad_left){robot.linkage.stackLevel(2);}
        else if(gamepad1.dpad_right){robot.linkage.stackLevel(3);}
        else if(gamepad1.dpad_down){robot.linkage.stackLevel(4);}
        else if(gamepad1.x){robot.linkage.raise();}
        else if(gamepad1.y){robot.linkage.lower();}
    }

    @Override
    public void onStop() {

    }





}
