package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
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
            AutoUtil.delay(.25);
            wrist.setPosition(Constants.WristConstants.wristRest);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderRest);
            AutoUtil.delay(.1);
            return false;
        }
    }
    class Outtake implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
            AutoUtil.delay(.25);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderSafeBackToOuttake);

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
            slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);
            AutoUtil.delay(.25);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderSafeBackToOuttake);

            AutoUtil.delay(.25);
            slider.runToPosition(Constants.SliderConstants.sliderOuttakeHigh);
            AutoUtil.delay(.25);

            wrist.setPosition(Constants.WristConstants.wristOuttakeHigh);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderOuttakeHigh);

            return false;
        }
    }
    class toTravelingPosition implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            wrist.setPosition(Constants.WristConstants.wristTraveling);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderTraveling);
            slider.runToPosition(Constants.SliderConstants.sliderTraveling);
            return false;
        }

    }

    class SlowOuttake implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            hopper.intake(Hopper.ALL);
            intake.setPower(-2);
            AutoUtil.delay(2);
            intake.setMode(Intake.REST);
            hopper.rest(Hopper.ALL);

            return false;
        }
    }

    class DriveToAprilTag implements Action{
        int id = 0;
        final int DESIRED_DISTANCE = 12;
        AprilTagDetection tag;
        final double SPEED_GAIN  =  0.02  ;   //  Forward Speed Control "Gain". eg: Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
        final double STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  eg: Ramp up to 25% power at a 25 degree Yaw error.   (0.25 / 25.0)
        final double TURN_GAIN   =  0.01  ;

        public void setId(int i){this.id = i;}

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            AprilTagProcessorWrapper.startAprilTagDetection(camera);
            while(true){
                tag = AprilTagProcessorWrapper.getAprilTagInfo(id);
                if(tag != null){
                    double  rangeError      = (tag.ftcPose.range - DESIRED_DISTANCE);
                    double  headingError    = tag.ftcPose.bearing;
                    double  yawError        = tag.ftcPose.yaw;
                    double forward, turn, strafe;


                    if(Math.abs(rangeError) < .5)break;
                    // Use the speed and turn "gains" to calculate how we want the robot to move.
                    forward  = Range.clip(rangeError * SPEED_GAIN, -1, 1);
                    turn   = Range.clip(headingError * TURN_GAIN, -1, 1) ;
                    strafe = Range.clip(-yawError * STRAFE_GAIN, -1, 1);
                    drive.drive(forward, strafe, turn);
                }

            }
            return false;
        }
    }

    public Action resetToIntake(){
        return new ResetToIntake();
    }
    public Action outtake(){return new Outtake();}
    public Action highOuttake(){return new HighOuttake();}

    public Action toTravelingPosition(){return new toTravelingPosition();}
    public Action slowOuttake(){
        return new SlowOuttake();
    }

}
