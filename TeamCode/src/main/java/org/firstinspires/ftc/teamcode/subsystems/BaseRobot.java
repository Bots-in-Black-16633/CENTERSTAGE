package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.PoseMap;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
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
import org.firstinspires.ftc.teamcode.teleop.comp.CompetitionTeleop;
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
    public Drive drive;
    public Shooter shooter;
    public Linkage linkage;
    public Gate gate;
    public volatile WebcamName camera;

    public AutoUtil autoGenerator;
    public ElapsedTime timeout = new ElapsedTime();
    public final static double timeOutSec = 1;
    public BaseRobot(HardwareMap hwMap, Pose2d startPose, PoseMap transformation){
        hopper = new Hopper(hwMap);
        intake = new Intake(hwMap);
        drive = new Drive(hwMap, startPose);
        shoulder = new Shoulder(hwMap);
        wrist = new Wrist(hwMap);
        shooter = new Shooter(hwMap);
        slider = new Slider(hwMap);
        linkage = new Linkage(hwMap);
        autoGenerator = new AutoUtil(drive, transformation);
        camera = hwMap.get(WebcamName.class, "camera");


        addSubsystems(intake,  drive, hopper, shoulder, wrist, slider);
    }
    public BaseRobot(HardwareMap hwMap, Pose2d startPose){
        this(hwMap,startPose,null);
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

            drive.updatePoseEstimate();
            slider.runToPosition(Constants.SliderConstants.sliderRest);
            timeout.reset();
            while(Math.abs(slider.getPosition()-Constants.SliderConstants.sliderRest) > 20 && timeout.seconds() < 3){
                drive.updatePoseEstimate();
            }

            shoulder.setPosition(Constants.ShoulderConstants.shoulderRest);
            wrist.setPosition(Constants.WristConstants.wristRest);
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
                wrist.setPosition(Constants.WristConstants.wristSafeBackToIntake);

            }
            intake.setMode(Intake.REST);

            AutoUtil.delay(.25);
            slider.runToPosition(Constants.SliderConstants.sliderOuttake);
            drive.updatePoseEstimate();

            wrist.setPosition(Constants.WristConstants.wristOuttake);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderOuttake);
            hopper.rest(Hopper.ALL);
            timeout.reset();
            while(Math.abs(shoulder.getPosition()- Constants.ShoulderConstants.shoulderOuttake) > .05 && timeout.seconds() < timeOutSec){
                drive.updatePoseEstimate();
            }
            timeout.reset();
            while(Math.abs(slider.getPosition()- Constants.SliderConstants.sliderOuttake) > 20 && timeout.seconds() < timeOutSec){
                drive.updatePoseEstimate();
            }
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
                wrist.setPosition(Constants.WristConstants.wristSafeBackToOuttake);
            }
            intake.setMode(Intake.REST);

            AutoUtil.delay(.25);
            slider.runToPosition(Constants.SliderConstants.sliderOuttakeMid);
            drive.updatePoseEstimate();



            wrist.setPosition(CompetitionTeleop.wristCalculator.calculate(Constants.SliderConstants.sliderOuttakeMid));
            shoulder.setPosition(CompetitionTeleop.shoulderCalculator.calculate(Constants.SliderConstants.sliderOuttakeMid));
            AutoUtil.delay(.25);
            hopper.rest(Hopper.ALL);
            timeout.reset();
            while(Math.abs(shoulder.getPosition()- CompetitionTeleop.shoulderCalculator.calculate(Constants.SliderConstants.sliderOuttakeMid)) > .05 && timeout.seconds() < timeOutSec){
                drive.updatePoseEstimate();

            }
            timeout.reset();
            while(Math.abs(slider.getPosition()- Constants.SliderConstants.sliderOuttakeMid) > 20 && timeout.seconds() < timeOutSec){
                drive.updatePoseEstimate();

            }
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
            timeout.reset();
            while(Math.abs(slider.getPosition()-Constants.SliderConstants.sliderOuttakeHigh) > 5 && timeout.seconds() < timeOutSec){

            }

            return false;
        }
    }
    class DragAndSuckStackIntake implements Action {
        ElapsedTime time;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            drive.forward(.5,.5);//drive forward
            linkage.stackLevel(4);//lower
            AutoUtil.delay(.25);
            drive.backward(.3,.5);//drive backward knocking over the stack
            //intake the pixels while driving forward
            intake.setMode(Intake.INTAKE);
            hopper.intake(Hopper.ALL);
            drive.forward(.7,.4);

            time.reset();//while the hoppers arent full keep intaking or the timeout seconds havent elapsed
            while(!hopper.hoppersFull() && time.seconds() < Constants.IntakeConstants.autoStackIntakeTimeout){
                if(hopper.leftHopperSensor.isPixelPresent())hopper.rest(Hopper.LEFT_HOPPER);
                if(hopper.rightHopperSensor.isPixelPresent())hopper.rest(Hopper.RIGHT_HOPPER);
            }
            //bring slider up
            intake.setMode(Intake.REST);
            hopper.rest(Hopper.ALL);
            drive.updatePoseEstimate();
            return false;
        }
    }
    class offTheTopStackIntake implements Action {
        ElapsedTime time;

        int stackLevel;
        public offTheTopStackIntake(int stackLevel){
            this.stackLevel=stackLevel;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            wrist.setPosition(Constants.WristConstants.wristRest);
            time = new ElapsedTime();
            linkage.stackLevel(stackLevel);
            drive.forward(.4, .4);
            intake.setMode(Intake.INTAKE);
           hopper.intake(Hopper.ALL);
           time.reset();
            while(!hopper.hoppersFull() && time.seconds() < Constants.IntakeConstants.autoStackIntakeTimeout){
                if(hopper.leftHopperSensor.isPixelPresent())hopper.rest(Hopper.LEFT_HOPPER);
                if(hopper.rightHopperSensor.isPixelPresent())hopper.rest(Hopper.RIGHT_HOPPER);
                wrist.setPosition(Constants.WristConstants.wristRest);

//                if(((int)time.seconds())%2==0)intake.setMode(Intake.INTAKE);
//                else intake.setMode(Intake.OUTTAKE);
            }
            intake.setMode(Intake.REST);
            drive.backward(.4,.4);
            drive.updatePoseEstimate();

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
                slider.runToPosition(Constants.SliderConstants.sliderTraveling+100);
                while(slider.getPosition() != Constants.SliderConstants.sliderTraveling+100)  {
                }
                shoulder.setPosition(Constants.ShoulderConstants.shoulderTraveling);
                wrist.setPosition(Constants.WristConstants.wristTraveling);
                while(Math.abs(shoulder.getPosition()- Constants.ShoulderConstants.shoulderTraveling)>.05){

                }
                slider.runToPosition(Constants.SliderConstants.sliderTraveling);

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



    class OuttakeCustom implements Action{


        public double sliderPos;

        public OuttakeCustom(double sliderPos){
            this.sliderPos = sliderPos;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            hopper.intake(Hopper.ALL);
            intake.setPower(.5);
            if(slider.getPosition() < 200){
                slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
                AutoUtil.delay(.25);
                wrist.setPosition(Constants.WristConstants.wristSafeBackToOuttake);
            }
            intake.setMode(Intake.REST);

            AutoUtil.delay(.25);
            slider.runToPosition(sliderPos);



            wrist.setPosition(CompetitionTeleop.wristCalculator.calculate(sliderPos));
            shoulder.setPosition(CompetitionTeleop.shoulderCalculator.calculate(sliderPos));
            AutoUtil.delay(.25);
            hopper.rest(Hopper.ALL);
            timeout.reset();
            while(Math.abs(shoulder.getPosition()- CompetitionTeleop.shoulderCalculator.calculate(sliderPos)) > .05 && timeout.seconds() < timeOutSec){

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
    public Action traveling(){return new TravelingPosition();}
    public Action midOuttake(){return new MidOuttake();}
    public Action dragAndSuckStackIntake(){return new DragAndSuckStackIntake();}
    public Action offTheTopStackIntake(int stackLevel){return new offTheTopStackIntake(stackLevel);}

    public Action customOuttake(double sliderPos){return new OuttakeCustom(sliderPos);}

    public Action outtakeExcessPixels(){
        return new SequentialAction(traveling(), (p)-> {intake.setMode(Intake.OUTTAKE);return false;}, new SleepAction(1.5), (p)-> {intake.setMode(Intake.REST);return false;});
    }
}
