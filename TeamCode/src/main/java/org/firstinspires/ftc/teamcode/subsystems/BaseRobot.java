package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.drive.Drive;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

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

    public AutoUtil autoGenerator;
    public BaseRobot(HardwareMap hwMap, Pose2d startPose){
        hopper = new Hopper(hwMap);
        intake = new Intake(hwMap);
        //climber = new Climber(hwMap);
        drive = new Drive(hwMap, startPose);
        shoulder = new Shoulder(hwMap);
        wrist = new Wrist(hwMap);
        shooter = new Shooter(hwMap);
        autoGenerator = new AutoUtil(drive);


        addSubsystems(intake,  drive, hopper, shoulder, wrist);
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
            slider.runToPosition(Constants.SliderConstants.sliderSafeBackToIntake);

            AutoUtil.delay(1);

            wrist.setPosition(Constants.WristConstants.wristRest);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderRest);
            slider.runToPosition(Constants.SliderConstants.sliderRest);
            return false;
        }
    }
    class Outtake implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            wrist.setPosition(Constants.WristConstants.wristSafeBackToOuttake);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderSafeBackToOuttake);
            slider.runToPosition(Constants.SliderConstants.sliderSafeBackToOuttake);

            AutoUtil.delay(1);

            wrist.setPosition(Constants.WristConstants.wristOuttake);
            shoulder.setPosition(Constants.ShoulderConstants.shoulderOuttake);
            slider.runToPosition(Constants.SliderConstants.sliderOuttake);
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

    public Action resetToIntake(){
        return new ResetToIntake();
    }
    public Action outtake(){return new Outtake();}
    public Action toTravelingPosition(){return new toTravelingPosition();}

}
