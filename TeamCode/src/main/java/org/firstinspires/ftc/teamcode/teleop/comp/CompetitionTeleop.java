package org.firstinspires.ftc.teamcode.teleop.comp;


import static org.firstinspires.ftc.teamcode.subsystems.Shooter.ShooterState.REST;
import static org.firstinspires.ftc.teamcode.subsystems.Shooter.ShooterState.SPINNING;
import org.firstinspires.ftc.teamcode.util.Constants.ColorSensorWrapperConstants.Pixel;


import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.checkerframework.checker.signedness.qual.Constant;
import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.Line;
import org.firstinspires.ftc.teamcode.util.SampleTeleop;

@TeleOp
public class CompetitionTeleop extends SampleTeleop {
    Pose2d startPos = AutoUtil.REDRIGHTSTART;
    PIDController t;
    double shoulderPos;
    double sliderPos;
    double wristPos;

    ElapsedTime loopTimes;
    double lastLoops = 0;
    double loops = 0;
    volatile GamepadEx g1;
    GamepadEx g2;
    Thread driveLoop;

    boolean visionHopperLock = true;
    volatile boolean volStopRequested = false;

    boolean wristShoulderAutoAdjust = false;
    boolean prevGuide = false;
    boolean prevX = false;
    boolean prevY = false;


    static public Line wristCalculator = new Line(Constants.SliderConstants.sliderOuttake, Constants.WristConstants.wristOuttake, Constants.SliderConstants.sliderOuttakeHigh, Constants.WristConstants.wristOuttakeHigh);
    static public Line shoulderCalculator = new Line(Constants.SliderConstants.sliderOuttake, Constants.ShoulderConstants.shoulderOuttake, Constants.SliderConstants.sliderOuttakeHigh, Constants.ShoulderConstants.shoulderOuttakeHigh);




    @Override
    public void onInit() {
        robot = new BaseRobot(hardwareMap, startPos);
        g1 = new GamepadEx(gamepad1);
        g2 = new GamepadEx(gamepad2);

        shoulderPos = Constants.ShoulderConstants.shoulderRest;
        wristPos = Constants.WristConstants.wristRest;
        sliderPos = Constants.SliderConstants.sliderRest;
        robot.shooter.rest();
        loopTimes = new ElapsedTime();
    }

    @Override
    public void onStart() {
        driveLoop = new Thread(this::runDriveLoop);
        driveLoop.start();
        loopTimes.reset();

    }

    @Override
    public void onLoop() {


        if(g2.isDown(GamepadKeys.Button.LEFT_BUMPER) && g2.isDown(GamepadKeys.Button.A)){
            robot.slider.reset();
            sliderPos = 0;
        }
        if(g2.isDown(GamepadKeys.Button.LEFT_BUMPER) && g2.isDown(GamepadKeys.Button.X) != prevX){
            if(visionHopperLock){visionHopperLock = false;robot.hopper.disableColorSensor();robot.hopper.unLock(Hopper.ALL);}
            else visionHopperLock = true;
        }
        prevX = g2.isDown(GamepadKeys.Button.X);

        if(g2.isDown(GamepadKeys.Button.LEFT_BUMPER) && g2.isDown(GamepadKeys.Button.Y) != prevY){
            Actions.runBlocking(robot.midOuttake());
            resetPixelSubsystemTrackingVariables();
        }
        prevY = g2.isDown(GamepadKeys.Button.Y);

        pen.addLine("VISION AUTO LOCK: " + visionHopperLock);


        //A BUTton goes to high outtake
        if(!g2.isDown(GamepadKeys.Button.LEFT_BUMPER)&&g2.wasJustPressed(GamepadKeys.Button.A)){
            Actions.runBlocking(robot.highOuttake());
            resetPixelSubsystemTrackingVariables();
        }
        //If the Y button is pressed the robot should go back to intake position
        if(!g2.isDown(GamepadKeys.Button.LEFT_BUMPER)&&g2.wasJustPressed(GamepadKeys.Button.Y)){
            Actions.runBlocking(robot.resetToIntake());
            resetPixelSubsystemTrackingVariables();
            sliderPos = Constants.SliderConstants.sliderRest;

            //AprilTagProcessorWrapper.pauseAprilTagDetectionAsync(pen);
        }
        if(!g2.isDown(GamepadKeys.Button.LEFT_BUMPER)&&g2.wasJustPressed(GamepadKeys.Button.X)){

            if(Math.abs(robot.shoulder.getPosition()-Constants.ShoulderConstants.shoulderOuttake)<.05) Actions.runBlocking(robot.distanceOuttake());
            else Actions.runBlocking(robot.outtake());
            robot.hopper.unLock(Hopper.ALL);
            resetPixelSubsystemTrackingVariables();
        }

        if(!g2.isDown(GamepadKeys.Button.LEFT_BUMPER)&&g2.wasJustPressed(GamepadKeys.Button.B)){
            if(robot.shooter.getState()==REST){robot.shooter.spinUp();}
            else if(robot.shooter.getState()==SPINNING){robot.shooter.shoot();}
            else robot.shooter.rest();
        }

        if(g2.wasJustPressed(GamepadKeys.Button.BACK)){
            if(robot.slider.getPosition()>300){
                robot.wrist.setPosition(Constants.WristConstants.wristAdjustingPositionLow);
                robot.shoulder.setPosition(Constants.ShoulderConstants.shoulderPixelAdjusterLow);
                wristPos = Constants.WristConstants.wristAdjustingPositionLow;
                shoulderPos = Constants.ShoulderConstants.shoulderPixelAdjusterLow;
            }
            else{
                if(wristShoulderAutoAdjust)wristShoulderAutoAdjust=false;
                else wristShoulderAutoAdjust = true;
            }

        }


        pen.addLine("wristShoulderAutoAdjust: " + wristShoulderAutoAdjust);
        pen.addLine("SliderTargetPos" + sliderPos);





        //HOPPER OUTTAKE
        //IF YOU ARE INTAKING AND THE RIGHT BUMPER IS PRESSED

        if((sliderPos < 20) && robot.hopper.getPixelColor(Hopper.RIGHT_HOPPER)!= Pixel.NONE){robot.hopper.rest(Hopper.RIGHT_HOPPER);robot.hopper.lock(Hopper.RIGHT_HOPPER);}
        else{robot.hopper.unLock(Hopper.RIGHT_HOPPER);}
         if((sliderPos < 20) && robot.hopper.getPixelColor(Hopper.LEFT_HOPPER)!= Pixel.NONE){robot.hopper.rest(Hopper.LEFT_HOPPER);robot.hopper.lock(Hopper.LEFT_HOPPER);}
         else{robot.hopper.unLock(Hopper.LEFT_HOPPER);}


       if(g2.isDown(GamepadKeys.Button.RIGHT_BUMPER)){
            robot.hopper.outtake(Hopper.LEFT_HOPPER);
        }
       else if(!(g2.isDown(GamepadKeys.Button.DPAD_RIGHT)||g2.isDown(GamepadKeys.Button.DPAD_LEFT))){robot.hopper.rest(Hopper.LEFT_HOPPER);}

        if(g2.isDown(GamepadKeys.Button.LEFT_BUMPER)){
            robot.hopper.outtake(Hopper.RIGHT_HOPPER);
        }
        else if(!(g2.isDown(GamepadKeys.Button.DPAD_RIGHT)||g2.isDown(GamepadKeys.Button.DPAD_LEFT))){robot.hopper.rest(Hopper.RIGHT_HOPPER);}


        //INTTAKE
        if(g2.isDown(GamepadKeys.Button.DPAD_RIGHT) && !robot.hopper.hoppersFull()){robot.intake.setMode(Intake.INTAKE);robot.hopper.intake(Hopper.ALL);}
        else if(g2.isDown(GamepadKeys.Button.DPAD_LEFT)){robot.intake.setMode(Intake.OUTTAKE);if(robot.slider.getPosition()<300){robot.hopper.outtake(Hopper.ALL);}}
        else {
            robot.intake.setMode(Intake.REST);
            if(!g2.isDown(GamepadKeys.Button.LEFT_BUMPER))robot.hopper.rest(Hopper.RIGHT_HOPPER);
            if(!g2.isDown(GamepadKeys.Button.RIGHT_BUMPER))robot.hopper.rest(Hopper.LEFT_HOPPER);
        }


        if(g2.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
            robot.linkage.raise();
        }
        if(g2.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){
            robot.linkage.lower();
        }

        if(loopTimes.seconds() < 1){
            loops ++;
        }
        else{
            lastLoops = loops;
            loopTimes.reset();
            loops = 0;
        }
        pen.addLine("LPS: " + lastLoops);



        if(sliderPos <1){robot.slider.set(0);pen.addLine("REST");}



        //Manual Fine adjustent controls
        if(Math.abs(g2.getLeftY())>.01) {
            if(g2.isDown(GamepadKeys.Button.LEFT_BUMPER)){
                robot.slider.set(Math.abs(g2.getLeftY())>.3?(g2.getLeftY()/4):0);sliderPos = robot.slider.getPosition();}
            else{
                sliderPos += g2.getLeftY() * 100;
                if(sliderPos <1){robot.slider.set(0);pen.addLine("REST");}
               else robot.slider.runToPosition(sliderPos);
            }

            if(wristShoulderAutoAdjust && sliderPos > 400 && wristPos < Constants.WristConstants.wristOuttake){
                wristPos = wristCalculator.calculate(sliderPos);
                shoulderPos = shoulderCalculator.calculate(sliderPos);
            }


        }
        if(Math.abs(g2.getRightY())>.01){
            shoulderPos += -1*g2.getRightY()*.05;
        }
        robot.shoulder.setPosition(shoulderPos);

        if(Math.abs((g2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-g2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)))>.01){
            wristPos += (g2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-g2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER))*.05;
        }
        robot.wrist.setPosition(wristPos);





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
