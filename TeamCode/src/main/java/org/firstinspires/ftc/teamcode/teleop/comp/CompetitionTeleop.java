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
    volatile GamepadEx g1;
    GamepadEx g2;



    @Override
    public void onInit() {
        robot = new BaseRobot(hardwareMap, startPos);
        g1 = new GamepadEx(gamepad1);
        g2 = new GamepadEx(gamepad2);

        shoulderPos = Constants.ShoulderConstants.shoulderRest;
        wristPos = Constants.WristConstants.wristRest;
        sliderPos = Constants.SliderConstants.sliderRest;
    }

    @Override
    public void onStart() {
        Thread driveLoop = new Thread(this::runDriveLoop);
        driveLoop.start();

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
            robot.hopper.unLock(Hopper.ALL);
            resetPixelSubsystemTrackingVariables();
        }
        if(g2.wasJustPressed(GamepadKeys.Button.B)){
            robot.shooter.shoot();
        }

        if(g2.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)){
            sliderPos += Constants.SliderConstants.backdropRowConstant;
        }
        else if(g2.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)){
            sliderPos -= Constants.SliderConstants.backdropRowConstant;
        }

        //HOPPER OUTTAKE
        //IF YOU ARE INTAKING AND THE RIGHT BUMPER IS PRESSED
        if((sliderPos < 100) && g2.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            if(robot.hopper.isLocked(Hopper.LEFT_HOPPER))robot.hopper.unLock(Hopper.LEFT_HOPPER);
            else{
                robot.hopper.rest(Hopper.LEFT_HOPPER);
                robot.hopper.lock(Hopper.LEFT_HOPPER);
            }

        }
        else if(g2.isDown(GamepadKeys.Button.LEFT_BUMPER)){
            robot.hopper.outtake(Hopper.LEFT_HOPPER);
        }
        else if(!(g2.isDown(GamepadKeys.Button.DPAD_RIGHT)||g2.isDown(GamepadKeys.Button.DPAD_LEFT))){robot.hopper.rest(Hopper.LEFT_HOPPER);}

        if((sliderPos < 100) && g2.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            if(robot.hopper.isLocked(Hopper.RIGHT_HOPPER))robot.hopper.unLock(Hopper.RIGHT_HOPPER);
            else{
                robot.hopper.rest(Hopper.RIGHT_HOPPER);
                robot.hopper.lock(Hopper.RIGHT_HOPPER);
            }
        }
        else if(g2.isDown(GamepadKeys.Button.RIGHT_BUMPER)){
            robot.hopper.outtake(Hopper.RIGHT_HOPPER);
        }
        else if(!(g2.isDown(GamepadKeys.Button.DPAD_RIGHT)||g2.isDown(GamepadKeys.Button.DPAD_LEFT))){robot.hopper.rest(Hopper.RIGHT_HOPPER);}

        //INTTAKE
        if(g2.isDown(GamepadKeys.Button.DPAD_RIGHT)){robot.intake.setMode(Intake.INTAKE);robot.hopper.intake(Hopper.ALL);}
        else if(g2.isDown(GamepadKeys.Button.DPAD_LEFT)){robot.intake.setMode(Intake.OUTTAKE);robot.hopper.outtake(Hopper.ALL);}
        else {
            robot.intake.setMode(Intake.REST);
            if(!g2.isDown(GamepadKeys.Button.RIGHT_BUMPER))robot.hopper.rest(Hopper.RIGHT_HOPPER);
            if(!g2.isDown(GamepadKeys.Button.LEFT_BUMPER))robot.hopper.rest(Hopper.LEFT_HOPPER);
        }

        if(g2.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
            sliderPos += Constants.SliderConstants.backdropRowConstant;
            robot.slider.runToPosition(sliderPos);
        }
        if(g2.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){
            sliderPos -= Constants.SliderConstants.backdropRowConstant;
            robot.slider.runToPosition(sliderPos);
        }






//        //Manual Fine adjustent controls
        if(Math.abs(g2.getLeftY())>.01){
            sliderPos += g2.getLeftY()*10;
            robot.slider.runToPosition(sliderPos);
        }
        if(Math.abs(g2.getRightY())>.01){
            shoulderPos += -1*g2.getRightY()*.01;
            robot.shoulder.setPosition(shoulderPos);

        }
        if(Math.abs((g2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-g2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)))>.01){
            wristPos += (g2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-g2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER))*.01;
            robot.wrist.setPosition(wristPos);
        }
        telemetry.addLine("TRIGGERS: " + Math.abs((g2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-g2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER))));



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
    public void runDriveLoop(){
        while(true){
            robot.drive.driveFieldcentric(g1.getLeftX(),g1.getLeftY(), -g1.getRightX(), 1-(g1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)*.5));
            if(g1.wasJustPressed(GamepadKeys.Button.A)){robot.drive.resetHeading();}

            g1.readButtons();
        }


    }
}
