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
    public WebcamName camera;

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
            if(slider.getPosition() < 200){
                slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
                AutoUtil.delay(.25);
                shoulder.setPosition(Constants.ShoulderConstants.shoulderSafeBackToOuttake);
            }

            AutoUtil.delay(.25);
            slider.runToPosition(Constants.SliderConstants.sliderOuttake);
            AutoUtil.delay(.25);

            wrist.setPosition(Constants.WristConstants.wristOuttake);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderOuttake);

            return false;
        }
    }
    class HighOuttake implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(slider.getPosition() < 200){
                slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
                AutoUtil.delay(.25);
                shoulder.setPosition(Constants.ShoulderConstants.shoulderSafeBackToOuttake);
            }

            AutoUtil.delay(.25);
            slider.runToPosition(Constants.SliderConstants.sliderOuttakeHigh);
            AutoUtil.delay(.25);

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
            linkage.raise();//raise the linkage

            drive.forward(.5,.5);//drive forward
            linkage.lower();//lower
            time = new ElapsedTime();
            while(time.seconds() < .5){//give the servo a quick second to lower

            }
            drive.backward(.5,.5);//drive backward knocking over the stack
            //intake the pixels while driving forward
            intake.setMode(Intake.INTAKE);
            hopper.intake(Hopper.ALL);
            drive.forward(.5,.5);

            time.reset();//while the hoppers arent full keep inaking or the timeout seconds havent elapsed
            while(!hopper.hoppersFull() && time.seconds() < Constants.IntakeConstants.autoStackIntakeTimeout){
                if(hopper.leftHopperSensor.pixelPresent())hopper.rest(Hopper.LEFT_HOPPER);
                if(hopper.rightHopperSensor.pixelPresent())hopper.rest(Hopper.RIGHT_HOPPER);
            }
            //bring slider up
            slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
            //wait for slider to come up
            while(slider.getPosition() != Constants.SliderConstants.sliderSafeBackToOuttake){

            }
                //outtake any extra pixels picked up
            hopper.intake(Hopper.ALL);
            intake.setMode(Intake.OUTTAKE);
            time.reset();//outtake for 2 seconds
            while(time.seconds() < 2){

            }//rest
            hopper.rest(Hopper.ALL);
            intake.setMode(Intake.REST);

            Actions.runBlocking(new ResetToIntake());


            return false;
        }
    }
    class DistanceOuttake implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(slider.getPosition() < 200){
                slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
                AutoUtil.delay(.25);
                shoulder.setPosition(Constants.ShoulderConstants.shoulderSafeBackToOuttake);
            }


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








    public Action resetToIntake(){
        return new ResetToIntake();
    }
    public Action outtake(){return new Outtake();}
    public Action distanceOuttake(){return new DistanceOuttake();}
    public Action highOuttake(){return new HighOuttake();}
    public Action stackIntake(){return new PixelStackIntake();}



}
