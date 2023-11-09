package org.firstinspires.ftc.teamcode.teleop.comp;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;
@TeleOp
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

        //Switch betw
//        if(g2.wasJustPressed(GamepadKeys.Button.A)){
//            robot.toTravelingPosition().run(pen.getPacket());
//            resetPixelSubsystemTrackingVariables();
//        }
        //If the Y button is pressed the robot should go back to intake position
        if(g2.wasJustPressed(GamepadKeys.Button.Y)){
            robot.resetToIntake().run(pen.getPacket());
            resetPixelSubsystemTrackingVariables();
        }
        if(g2.wasJustPressed(GamepadKeys.Button.X)){
            robot.outtake().run(pen.getPacket());
            resetPixelSubsystemTrackingVariables();
        }
        if(g2.wasJustPressed(GamepadKeys.Button.B)){
            robot.shooter.shoot();
        }

        if(g2.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
            sliderPos += Constants.SliderConstants.backdropRowConstant;
        }
        else if(g2.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){
            sliderPos -= Constants.SliderConstants.backdropRowConstant;
        }

        //HOPPER OUTTAKE
        if(g2.isDown(GamepadKeys.Button.LEFT_BUMPER)){
            robot.hopper.outtake(Hopper.LEFT_HOPPER);
        }
        else robot.hopper.rest(Hopper.RIGHT_HOPPER);
        if (g2.isDown(GamepadKeys.Button.RIGHT_BUMPER)) {
            robot.hopper.outtake(Hopper.RIGHT_HOPPER);
        }
        else robot.hopper.rest(Hopper.RIGHT_HOPPER);

        //INTTAKE
        if(g2.isDown(GamepadKeys.Button.DPAD_UP)){robot.intake.setMode(Intake.INTAKE);robot.hopper.intake(Hopper.ALL);}
        else if(g2.isDown(GamepadKeys.Button.DPAD_DOWN)){robot.intake.setMode(Intake.OUTTAKE);robot.hopper.outtake(Hopper.ALL);}
        else {
            robot.intake.setMode(Intake.REST);
            if(!g2.isDown(GamepadKeys.Button.RIGHT_BUMPER))robot.hopper.rest(Hopper.RIGHT_HOPPER);
            if(!g2.isDown(GamepadKeys.Button.LEFT_BUMPER))robot.hopper.rest(Hopper.LEFT_HOPPER);
        }




        //Manual Fine adjustent controls
        sliderPos += -1*g2.getLeftY()*.01;
        shoulderPos += -1*g2.getRightY()*.01;
        wristPos += g2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-g2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)*.01;

        robot.drive.driveFieldcentric(g1.getLeftX(),g1.getLeftY(), g1.getRightX(), 1);


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
