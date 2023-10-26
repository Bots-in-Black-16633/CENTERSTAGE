package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

import java.util.ArrayList;
import java.util.List;

public class BaseRobot extends SubsystemBase{
    List<SubsystemBase> subsystems = new ArrayList<SubsystemBase>();
    public Slider slider;
   public Hopper hopper;
    public MecanumDrive drive;
    public BaseRobot(HardwareMap hwMap){
        super("BaseRobot", hwMap);
        slider = new Slider(hwMap);
        hopper = new Hopper(hwMap);
        //drive = new MecanumDrive(hwMap, new Pose2d(0,0,0));



        addSubsystems(slider, hopper);
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
}
