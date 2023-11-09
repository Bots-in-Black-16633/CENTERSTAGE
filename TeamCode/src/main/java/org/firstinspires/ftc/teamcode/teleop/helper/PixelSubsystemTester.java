package org.firstinspires.ftc.teamcode.teleop.helper;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;
@TeleOp(name="PixelSubsystemTester", group="tester")
public class PixelSubsystemTester extends SampleTeleop {



    public double wristPos;
    public double shoulderPos;
    public double sliderPos;
    public GamepadEx g1;
    @Override
    public void onInit() {
        robot = new BaseRobot(hardwareMap, new Pose2d(0,0,0));
        wristPos = Constants.WristConstants.wristRest;
        shoulderPos = Constants.ShoulderConstants.shoulderRest;
        g1 = new GamepadEx(gamepad1);


    }

    @Override
    public void onStart() {



    }

    @Override
    public void onLoop() {

        wristPos += (g1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-g1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER))*.01;
        shoulderPos += gamepad1.right_stick_x*.01;
        if(wristPos > Constants.WristConstants.wristMax)wristPos = Constants.WristConstants.wristMax;
        if(wristPos < Constants.WristConstants.wristMin)wristPos = Constants.WristConstants.wristMin;
        if(shoulderPos > Constants.ShoulderConstants.shoulderMax)shoulderPos=Constants.ShoulderConstants.shoulderMax;
        if(shoulderPos < Constants.ShoulderConstants.shoulderMin)shoulderPos=Constants.ShoulderConstants.shoulderMin;

        if(g1.wasJustPressed(GamepadKeys.Button.A)){
            wristPos = Constants.WristConstants.wristOuttake;
            shoulderPos = Constants.ShoulderConstants.shoulderOuttake;
            sliderPos = Constants.SliderConstants.sliderOuttake;
        }
        if(g1.wasJustPressed(GamepadKeys.Button.Y)){
            wristPos = Constants.WristConstants.wristRest;
            shoulderPos = Constants.ShoulderConstants.shoulderRest;
            sliderPos = Constants.SliderConstants.sliderRest;

        }
        if(g1.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){
            robot.shooter.shoot();
        }

        sliderPos += gamepad1.left_stick_y*10;
        if(sliderPos > Constants.SliderConstants.sliderMaxPosition)sliderPos = Constants.SliderConstants.sliderMaxPosition;
        if(sliderPos < Constants.SliderConstants.sliderMinPosition)sliderPos = Constants.SliderConstants.sliderMinPosition;
        robot.slider.runToPosition(sliderPos);

        if(g1.isDown(GamepadKeys.Button.X)){
            robot.hopper.outtake(Hopper.LEFT_HOPPER);
        }
        else{
            robot.hopper.rest(Hopper.LEFT_HOPPER);
        }
        if(g1.isDown(GamepadKeys.Button.B)){
            robot.hopper.outtake(Hopper.RIGHT_HOPPER);
        }
        else{
            robot.hopper.rest(Hopper.RIGHT_HOPPER);
        }

        if(g1.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){
            robot.outtake().run(pen.getPacket());
            resetPixelSubsystemTrackingVariables();
        }
        if(g1.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)){
            robot.resetToIntake().run(pen.getPacket());
            resetPixelSubsystemTrackingVariables();
        }

        robot.wrist.setPosition(wristPos);
        robot.shoulder.setPosition(shoulderPos);
        pen.addLine("slider: " + sliderPos);
        pen.addLine("wristTarget: " + wristPos);
        pen.addLine("shoulderTarger: " + shoulderPos);g1.readButtons();
    }

    @Override
    public void onStop() {

    }
    private void resetPixelSubsystemTrackingVariables(){
        wristPos = robot.wrist.getPosition();
        shoulderPos = robot.shoulder.getPosition();
        sliderPos = robot.slider.getPosition();
    }





}
