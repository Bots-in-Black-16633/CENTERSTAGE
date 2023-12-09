package org.firstinspires.ftc.teamcode.teleop.comp;


import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Climber;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;
import org.firstinspires.ftc.teamcode.vision.AprilTagProcessorWrapper;

@TeleOp
public class CompetitionTeleop extends SampleTeleop {
    Pose2d startPos = AutoUtil.REDRIGHTSTART;

    double shoulderPos;
    double sliderPos;
    double wristPos;
    volatile GamepadEx g1;
    GamepadEx g2;
    Thread driveLoop;

    volatile boolean volStopRequested = false;




    @Override
    public void onInit() {
        robot = new BaseRobot(hardwareMap, startPos);
        g1 = new GamepadEx(gamepad1);
        g2 = new GamepadEx(gamepad2);

        shoulderPos = Constants.ShoulderConstants.shoulderRest;
        wristPos = Constants.WristConstants.wristRest;
        sliderPos = Constants.SliderConstants.sliderRest;
        robot.shooter.reset();
    }

    @Override
    public void onStart() {
        driveLoop = new Thread(this::runDriveLoop);
        driveLoop.start();

    }

    @Override
    public void onLoop() {
        //A BUTton goes to high outtake
        if(g2.wasJustPressed(GamepadKeys.Button.A)){
            Actions.runBlocking(robot.highOuttake());
            resetPixelSubsystemTrackingVariables();
        }
        //If the Y button is pressed the robot should go back to intake position
        if(g2.wasJustPressed(GamepadKeys.Button.Y)){
            Actions.runBlocking(robot.resetToIntake());

            resetPixelSubsystemTrackingVariables();
            //AprilTagProcessorWrapper.pauseAprilTagDetectionAsync(pen);
        }
        if(g2.wasJustPressed(GamepadKeys.Button.X)){

            if(Math.abs(robot.shoulder.getPosition()-Constants.ShoulderConstants.shoulderOuttake)<.05) Actions.runBlocking(robot.distanceOuttake());
            else Actions.runBlocking(robot.outtake());
            robot.hopper.unLock(Hopper.ALL);
            resetPixelSubsystemTrackingVariables();
        }
        if(g2.wasJustPressed(GamepadKeys.Button.B)){
            robot.shooter.shoot();
        }
        if(g2.wasJustPressed(GamepadKeys.Button.BACK)){
            Actions.runBlocking(robot.traveling());
            resetPixelSubsystemTrackingVariables();
        }



        //HOPPER OUTTAKE
        //IF YOU ARE INTAKING AND THE RIGHT BUMPER IS PRESSED

        if((sliderPos < 20) && robot.hopper.rightHopperSensor.pixelPresent()){robot.hopper.rest(Hopper.RIGHT_HOPPER);robot.hopper.lock(Hopper.RIGHT_HOPPER);}
        else{robot.hopper.unLock(Hopper.RIGHT_HOPPER);}
         if((sliderPos < 20) && robot.hopper.leftHopperSensor.pixelPresent()){robot.hopper.rest(Hopper.LEFT_HOPPER);robot.hopper.lock(Hopper.LEFT_HOPPER);}
         else{robot.hopper.unLock(Hopper.LEFT_HOPPER);}


       if(g2.isDown(GamepadKeys.Button.LEFT_BUMPER)){
            robot.hopper.outtake(Hopper.LEFT_HOPPER);
        }
       else if(!(g2.isDown(GamepadKeys.Button.DPAD_RIGHT)||g2.isDown(GamepadKeys.Button.DPAD_LEFT))){robot.hopper.rest(Hopper.LEFT_HOPPER);}

        if(g2.isDown(GamepadKeys.Button.RIGHT_BUMPER)){
            robot.hopper.outtake(Hopper.RIGHT_HOPPER);
        }
        else if(!(g2.isDown(GamepadKeys.Button.DPAD_RIGHT)||g2.isDown(GamepadKeys.Button.DPAD_LEFT))){robot.hopper.rest(Hopper.RIGHT_HOPPER);}


        //INTTAKE
        if(g2.isDown(GamepadKeys.Button.DPAD_RIGHT) && !robot.hopper.hoppersFull()){robot.intake.setMode(Intake.INTAKE);robot.hopper.intake(Hopper.ALL);}
        else if(g2.isDown(GamepadKeys.Button.DPAD_LEFT)){robot.intake.setMode(Intake.OUTTAKE);if(robot.slider.getPosition()<300){robot.hopper.outtake(Hopper.ALL);}}
        else {
            robot.intake.setMode(Intake.REST);
            if(!g2.isDown(GamepadKeys.Button.RIGHT_BUMPER))robot.hopper.rest(Hopper.RIGHT_HOPPER);
            if(!g2.isDown(GamepadKeys.Button.LEFT_BUMPER))robot.hopper.rest(Hopper.LEFT_HOPPER);
        }


        if(g2.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
            robot.linkage.raise();
        }
        if(g2.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){
            robot.linkage.lower();
        }






        //Manual Fine adjustent controls
        if(Math.abs(g2.getLeftY())>.01) {
            sliderPos += g2.getLeftY() * 100;
            robot.slider.runToPosition(sliderPos);
        }
        if(Math.abs(g2.getRightY())>.01){

            shoulderPos += -1*g2.getRightY()*.05;


        }
        robot.shoulder.setPosition(shoulderPos);

        if(Math.abs((g2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-g2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)))>.01){
            wristPos += (g2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-g2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER))*.05;
        }
        robot.wrist.setPosition(wristPos);


        if(g1.isDown(GamepadKeys.Button.B)){
            robot.climber.setMode(Climber.RAISE);
        }
        else if(g1.isDown(GamepadKeys.Button.X)){
            robot.climber.setMode(Climber.LOWER);
        }
        else if(g1.isDown(GamepadKeys.Button.Y)){
            robot.climber.setMode(Climber.CLIMB);
        }
        else robot.climber.setMode(Climber.REST);

        if (g1.isDown(GamepadKeys.Button.RIGHT_BUMPER)){
            robot.climber.rightClimberServo.setPower(1);
        }
        else if(!(g1.isDown(GamepadKeys.Button.B) || g1.isDown(GamepadKeys.Button.X))){
            robot.climber.rightClimberServo.setPower(0);
        }
        if (g1.isDown(GamepadKeys.Button.LEFT_BUMPER)){
            robot.climber.leftClimberServo.setPower(1);
        }
        else if(!(g1.isDown(GamepadKeys.Button.B) || g1.isDown(GamepadKeys.Button.X))){
            robot.climber.leftClimberServo.setPower(0);
        }




        if(robot.hopper.leftHopperSensor.pixelPresent() || robot.hopper.rightHopperSensor.pixelPresent() ){
            //AprilTagProcessorWrapper.resumeAprilTagDetectionAsync(robot.camera, pen);

        }

        g1.readButtons();
        g2.readButtons();
    }

    @Override
    public void onStop() {
        volStopRequested = true;
        try{
            driveLoop.join();

        }
        catch(InterruptedException e){

        }
        //AprilTagProcessorWrapper.endAprilTagDetection();
    }

    private void resetPixelSubsystemTrackingVariables(){
        wristPos = robot.wrist.getPosition();
        shoulderPos = robot.shoulder.getPosition();
        sliderPos = robot.slider.getPosition();
    }
    public void runDriveLoop(){
        //AprilTagProcessorWrapper.startAprilTagDetectionAsync(robot.camera, pen);
        //AprilTagProcessorWrapper.pauseAprilTagDetectionAsync(pen);
        double[] suggestedPowers = null;
        while(!volStopRequested){
//            if(g1.isDown(GamepadKeys.Button.LEFT_BUMPER) || g1.isDown(GamepadKeys.Button.RIGHT_BUMPER) ){
//                if(g1.isDown(GamepadKeys.Button.LEFT_BUMPER) && g1.isDown(GamepadKeys.Button.RIGHT_BUMPER)){
//                    if(AprilTagProcessorWrapper.getSuggestedPower(2, robot.drive, pen) !=null){
//                        suggestedPowers = AprilTagProcessorWrapper.getSuggestedPower(2, robot.drive, pen);
//                    }
//                    else if(AprilTagProcessorWrapper.getSuggestedPower(5, robot.drive,pen) != null){
//                        suggestedPowers = AprilTagProcessorWrapper.getSuggestedPower(5, robot.drive,pen);
//                    }
//                }
//                else if(g1.isDown(GamepadKeys.Button.LEFT_BUMPER)){
//                    if(AprilTagProcessorWrapper.getSuggestedPower(1, robot.drive,pen) !=null){
//                        suggestedPowers = AprilTagProcessorWrapper.getSuggestedPower(1, robot.drive,pen);
//                    }
//                    else if(AprilTagProcessorWrapper.getSuggestedPower(4, robot.drive,pen) != null){
//                        suggestedPowers = AprilTagProcessorWrapper.getSuggestedPower(4, robot.drive,pen);
//                    }
//                }
//                else if(g1.isDown(GamepadKeys.Button.RIGHT_BUMPER)){
//
//                    if(AprilTagProcessorWrapper.getSuggestedPower(3, robot.drive,pen) !=null){
//                        suggestedPowers = AprilTagProcessorWrapper.getSuggestedPower(3, robot.drive,pen);
//
//                    }
//                    else if(AprilTagProcessorWrapper.getSuggestedPower(5, robot.drive,pen) != null){
//                        suggestedPowers = AprilTagProcessorWrapper.getSuggestedPower(5, robot.drive,pen);
//
//                    }
//                }
//                else{suggestedPowers=null;}
//                if(suggestedPowers==null)robot.drive.driveFieldcentric(-g1.getLeftX(),-g1.getLeftY(), -g1.getRightX(), Math.min(((g1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)>.2)?.5:1), (g1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)>.1)?.2:1));
//                else robot.drive.drive(suggestedPowers[0],suggestedPowers[1],suggestedPowers[2]);
//
//            }
            /**else{**/robot.drive.driveFieldcentric(-g1.getLeftX(),-g1.getLeftY(), -g1.getRightX(), Math.min(((g1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)>.2)?.5:1), (g1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)>.1)?.2:1));/**}**/

            if(g1.wasJustPressed(GamepadKeys.Button.A)){robot.drive.resetHeading();}

            g1.readButtons();
        }



    }
}
