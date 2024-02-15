package org.firstinspires.ftc.teamcode.teleop.comp;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;
@TeleOp
public class PresentationTeleop extends SampleTeleop {
    GamepadEx g1;
    int timesPressed = 0;
    @Override
    public void onInit() {
        g1 = new GamepadEx(gamepad1);
        robot = new BaseRobot(hardwareMap, AutoUtil.REDRIGHTSTART);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onLoop() {
        if(g1.wasJustPressed(GamepadKeys.Button.A)){
            timesPressed++;
            if(timesPressed>5)timesPressed=0;




            if(timesPressed==0) Actions.runBlocking(robot.resetToIntake());
            else if(timesPressed==1)Actions.runBlocking(robot.outtake());
            else if(timesPressed==2)Actions.runBlocking(robot.distanceOuttake());
            else if(timesPressed==3){
                robot.wrist.setPosition(Constants.WristConstants.wristAdjustingPositionLow);
                robot.shoulder.setPosition(Constants.ShoulderConstants.shoulderPixelAdjusterLow);
            }
            else if(timesPressed ==4)Actions.runBlocking(robot.midOuttake());
            else if(timesPressed==5)Actions.runBlocking(robot.highOuttake());
        }


//        if(g1.isDown(GamepadKeys.Button.RIGHT_BUMPER))robot.intake.setMode(Intake.INTAKE);
//        else if(g1.isDown(GamepadKeys.Button.LEFT_BUMPER))robot.intake.setMode(Intake.OUTTAKE);
//        else robot.intake.setMode(Intake.REST);
//
//        if(g1.isDown(GamepadKeys.Button.RIGHT_BUMPER))robot.hopper.outtake(Hopper.ALL);
//        else if(g1.isDown(GamepadKeys.Button.LEFT_BUMPER))robot.hopper.outtake(Hopper.ALL);
//        else robot.hopper.rest(Hopper.ALL);

        if(g1.isDown(GamepadKeys.Button.RIGHT_BUMPER)){
            robot.hopper.outtake(Hopper.LEFT_HOPPER);
        }
        else if(!(g1.isDown(GamepadKeys.Button.DPAD_RIGHT)||g1.isDown(GamepadKeys.Button.DPAD_LEFT))){robot.hopper.rest(Hopper.LEFT_HOPPER);}

        if(g1.isDown(GamepadKeys.Button.LEFT_BUMPER)){
            robot.hopper.outtake(Hopper.RIGHT_HOPPER);
        }
        else if(!(g1.isDown(GamepadKeys.Button.DPAD_RIGHT)||g1.isDown(GamepadKeys.Button.DPAD_LEFT))){robot.hopper.rest(Hopper.RIGHT_HOPPER);}


        //INTTAKE
        if(g1.isDown(GamepadKeys.Button.DPAD_RIGHT) && !robot.hopper.hoppersFull()){robot.intake.setMode(Intake.INTAKE);robot.hopper.intake(Hopper.ALL);}
        else if(g1.isDown(GamepadKeys.Button.DPAD_LEFT)){robot.intake.setMode(Intake.OUTTAKE);if(robot.slider.getPosition()<300){robot.hopper.outtake(Hopper.ALL);}}
        else {
            robot.intake.setMode(Intake.REST);
            if(!g1.isDown(GamepadKeys.Button.LEFT_BUMPER))robot.hopper.rest(Hopper.RIGHT_HOPPER);
            if(!g1.isDown(GamepadKeys.Button.RIGHT_BUMPER))robot.hopper.rest(Hopper.LEFT_HOPPER);
        }

        if((robot.slider.getPosition() < 20) && robot.hopper.getPixelColor(Hopper.RIGHT_HOPPER)!= Constants.ColorSensorWrapperConstants.Pixel.NONE){robot.hopper.rest(Hopper.RIGHT_HOPPER);robot.hopper.lock(Hopper.RIGHT_HOPPER);}
        else{robot.hopper.unLock(Hopper.RIGHT_HOPPER);}
        if((robot.slider.getPosition() < 20) && robot.hopper.getPixelColor(Hopper.LEFT_HOPPER)!= Constants.ColorSensorWrapperConstants.Pixel.NONE){robot.hopper.rest(Hopper.LEFT_HOPPER);robot.hopper.lock(Hopper.LEFT_HOPPER);}
        else{robot.hopper.unLock(Hopper.LEFT_HOPPER);}

        pen.addLine("timesPressed" + timesPressed);

        g1.readButtons();

    }

    @Override
    public void onStop() {

    }
}
