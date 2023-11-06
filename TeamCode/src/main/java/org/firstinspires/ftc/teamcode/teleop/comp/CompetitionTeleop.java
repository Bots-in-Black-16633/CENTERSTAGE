package org.firstinspires.ftc.teamcode.teleop.comp;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;

public class CompetitionTeleop extends SampleTeleop {
    Pose2d startPos = new Pose2d(0,0,0);

    double shoulderPos;
    double sliderPos;
    double wristPos;
    GamepadEx g1;
    GamepadEx g2;



    @Override
    public void onInit() {
        robot = new BaseRobot(hardwareMap, startPos);
        g1 = new GamepadEx(gamepad1);
        g2 = new GamepadEx(gamepad2);

        shoulderPos = robot.shoulder.getPosition();
        wristPos = robot.wrist.getPosition();
        sliderPos = robot.slider.getPosition();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onLoop() {

        //If the A button is pressed the robot should go to Traveling Position
        if(g2.wasJustPressed(GamepadKeys.Button.A)){
            robot.toTravelingPosition().run(pen.getPacket());
            resetPixelSubsystemTrackingVariables();
        }

        //If the Y button is pressed the robot should go back to intake position
        if(g2.wasJustPressed(GamepadKeys.Button.Y)){
            robot.resetToIntake().run(pen.getPacket());
            resetPixelSubsystemTrackingVariables();
        }

        if(g2.wasJustPressed(GamepadKeys.Button.X)){
            robot.outtake().run(pen.getPacket());
            resetPixelSubsystemTrackingVariables();
        }

        g1.readButtons();
        g2.readButtons();
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
