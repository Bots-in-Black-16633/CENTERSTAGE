package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Shooter implements SubsystemBase{


    public HardwareMap hwMap;
    public Servo shooter;
    public Shooter(HardwareMap hwMap){
        shooter = hwMap.servo.get("shooter");
    }

    public void shoot(){
        shooter.setPosition(Constants.ShooterConstants.shooterRelease);
    }
    public void reset(){
        shooter.setPosition(Constants.ShooterConstants.shooterHold);
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
