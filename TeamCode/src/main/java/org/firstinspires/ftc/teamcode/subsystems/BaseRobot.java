package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.drive.Drive;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.vision.AprilTagProcessorWrapper;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;
import java.util.List;

public class BaseRobot implements SubsystemBase{
    List<SubsystemBase> subsystems = new ArrayList<SubsystemBase>();
    public Slider slider;
   public Hopper hopper;
   public Wrist wrist;
   public Shoulder shoulder;
   public Intake intake;
   public Climber climber;
    public Drive drive;
    public Shooter shooter;
    public Linkage linkage;
    public volatile WebcamName camera;

    public AutoUtil autoGenerator;
    public BaseRobot(HardwareMap hwMap, Pose2d startPose){
        hopper = new Hopper(hwMap);
        intake = new Intake(hwMap);
        climber = new Climber(hwMap);
        drive = new Drive(hwMap, startPose);
        shoulder = new Shoulder(hwMap);
        wrist = new Wrist(hwMap);
        shooter = new Shooter(hwMap);
        slider = new Slider(hwMap);
        linkage = new Linkage(hwMap);
        autoGenerator = new AutoUtil(drive);
        camera = hwMap.get(WebcamName.class, "camera");


        addSubsystems(intake,  drive, hopper, shoulder, wrist, slider);
    }


    private void addSubsystems(SubsystemBase... bases){
        for(SubsystemBase base: bases){
            subsystems.add(base);
        }
    }
    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        for(SubsystemBase b: subsystems){
            b.printTelemetry(t);
        }
    }

    @Override
    public void periodic() {
        for(SubsystemBase b: subsystems){
            b.periodic();
        }
    }
    class ResetToIntake implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            //set the wrist and shoulder to a safe Position
            wrist.setPosition(Constants.WristConstants.wristSafeBackToIntake);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderSafeBackToIntake);
            AutoUtil.delay(.25);
            slider.runToPosition(Constants.SliderConstants.sliderSafeBackToIntake);

            AutoUtil.delay(.25);


            slider.runToPosition(Constants.SliderConstants.sliderRest);
            while(Math.abs(slider.getPosition()-Constants.SliderConstants.sliderRest) > 5){

            }
            wrist.setPosition(Constants.WristConstants.wristRest);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderRest);
            AutoUtil.delay(.1);
            return false;
        }
    }
    class Outtake implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            hopper.intake(Hopper.ALL);
            intake.setPower(.5);
            if(slider.getPosition() < 200){
                slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
                AutoUtil.delay(.25);
                shoulder.setPosition(Constants.ShoulderConstants.shoulderSafeBackToOuttake);
            }
            intake.setMode(Intake.REST);

            AutoUtil.delay(.25);
            slider.runToPosition(Constants.SliderConstants.sliderOuttake);
            AutoUtil.delay(.25);
            hopper.rest(Hopper.ALL);


            wrist.setPosition(Constants.WristConstants.wristOuttake);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderOuttake);

            while(Math.abs(shoulder.getPosition()- Constants.ShoulderConstants.shoulderOuttake) > .05){

            }
            AutoUtil.delay(1);
            return false;
        }
    }
    class MidOuttake implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            hopper.intake(Hopper.ALL);
            intake.setPower(.5);
            if(slider.getPosition() < 200){
                slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
                AutoUtil.delay(.25);
                shoulder.setPosition(Constants.ShoulderConstants.shoulderSafeBackToOuttake);
            }
            intake.setMode(Intake.REST);

            AutoUtil.delay(.25);
            slider.runToPosition(Constants.SliderConstants.sliderOuttakeMid);
            AutoUtil.delay(.25);
            hopper.rest(Hopper.ALL);


            wrist.setPosition(Constants.WristConstants.wristOuttake);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderOuttake);

            while(Math.abs(shoulder.getPosition()- Constants.ShoulderConstants.shoulderOuttake) > .05){

            }
            AutoUtil.delay(1);
            return false;
        }
    }
    class HighOuttake implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            hopper.intake(Hopper.ALL);
            intake.setPower(.5);

            if(slider.getPosition() < 200){
                slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
                AutoUtil.delay(.25);
                shoulder.setPosition(Constants.ShoulderConstants.shoulderSafeBackToOuttake);
            }
            intake.setMode(Intake.REST);


            AutoUtil.delay(.25);
            slider.runToPosition(Constants.SliderConstants.sliderOuttakeHigh);
            AutoUtil.delay(.25);
            hopper.rest(Hopper.ALL);

            wrist.setPosition(Constants.WristConstants.wristOuttakeHigh);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderOuttakeHigh);
            while(Math.abs(slider.getPosition()-Constants.SliderConstants.sliderOuttakeHigh) > 5){

            }

            return false;
        }
    }
    class PixelStackIntake implements Action {
        ElapsedTime time;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            drive.forward(.5,.5);//drive forward
            linkage.lower();//lower
            time = new ElapsedTime();
            while(time.seconds() < .25){//give the servo a quick second to lower

            }
            drive.backward(.5,.5);//drive backward knocking over the stack
            //intake the pixels while driving forward
            intake.setMode(Intake.INTAKE);
            hopper.intake(Hopper.ALL);
            drive.forward(.7,.3);

            time.reset();//while the hoppers arent full keep intaking or the timeout seconds havent elapsed
            while(!hopper.hoppersFull() && time.seconds() < Constants.IntakeConstants.autoStackIntakeTimeout){
                if(hopper.leftHopperSensor.pixelPresent())hopper.rest(Hopper.LEFT_HOPPER);
                if(hopper.rightHopperSensor.pixelPresent())hopper.rest(Hopper.RIGHT_HOPPER);
            }
            //bring slider up
            intake.setMode(Intake.REST);
            hopper.rest(Hopper.ALL);
            Actions.runBlocking(traveling());



            return false;
        }
    }
    class FirstStackIntake implements Action {
        ElapsedTime time;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            time = new ElapsedTime();
            linkage.raise();
            intake.setMode(Intake.INTAKE);
            hopper.intake(Hopper.ALL);
            drive.forward(.4, .5);
            time.reset();
            while(time.seconds()<1){}
            drive.backward(.5, .5);
            linkage.lower();
            intake.setMode(Intake.OUTTAKE);
            time.reset();
            while(time.seconds()<1){}
            intake.setMode(Intake.INTAKE);
            drive.forward(.5, .5);
            return false;
        }

    }
    class SecondStackIntake implements Action {
        ElapsedTime time;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            time = new ElapsedTime();
            linkage.raise();
            drive.forward(.4, .4);
            intake.setMode(Intake.INTAKE);
            linkage.stackLevel(3);
            time.reset();
            while(time.seconds()<.5){}
            AutoUtil.delay(.5);
            linkage.stackLevel(4);
            time.reset();
            while(time.seconds()<.5){}
            linkage.raise();
            intake.setMode(Intake.REST);
            drive.backward(.4,.4);
            return false;
        }
    }
    class DistanceOuttake implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intake.setPower(.5);
            hopper.intake(Hopper.ALL);

            if(slider.getPosition() < 200){
                slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
                AutoUtil.delay(.25);
                shoulder.setPosition(Constants.ShoulderConstants.shoulderSafeBackToOuttake);
            }
            intake.setMode(Intake.REST);
            hopper.rest(Hopper.ALL);


            AutoUtil.delay(.25);
            slider.runToPosition(Constants.SliderConstants.sliderDistanceDeposit);
            AutoUtil.delay(.25);

            wrist.setPosition(Constants.WristConstants.wristDistanceDeposit);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderDistanceDeposit);
            while(Math.abs(slider.getPosition()-Constants.SliderConstants.sliderDistanceDeposit) > 5){

            }

            return false;
        }
    }

    //Traveling Position
    class TravelingPosition implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            //if we are at rest


            if(Math.abs(slider.getPosition() -Constants.SliderConstants.sliderRest)<10){
                slider.runToPosition(Constants.SliderConstants.sliderTraveling);
                while(slider.getPosition() != Constants.SliderConstants.sliderTraveling)  {
                }
                shoulder.setPosition(Constants.ShoulderConstants.shoulderTraveling);
                wrist.setPosition(Constants.WristConstants.wristTraveling);
                while(Math.abs(shoulder.getPosition()- Constants.ShoulderConstants.shoulderTraveling)>.05){

                }
            }else{
                shoulder.setPosition(Constants.ShoulderConstants.shoulderTraveling);
                wrist.setPosition(Constants.WristConstants.wristTraveling);
                while(Math.abs(shoulder.getPosition()- Constants.ShoulderConstants.shoulderTraveling)>.05){

                }
                slider.runToPosition(Constants.SliderConstants.sliderTraveling);
                while(slider.getPosition() != Constants.SliderConstants.sliderTraveling)  {
                }
            }






            return false;
        }
    }








    public Action resetToIntake(){
        return new ResetToIntake();
    }
    public Action outtake(){return new Outtake();}
    public Action distanceOuttake(){return new DistanceOuttake();}
    public Action highOuttake(){return new HighOuttake();}
    public Action stackIntake(){return new PixelStackIntake();}
    public Action traveling(){return new TravelingPosition();}
    public Action midOuttake(){return new MidOuttake();}
    public Action firstStack(){return new FirstStackIntake();}
    public Action secondStack(){return new SecondStackIntake();}

}
