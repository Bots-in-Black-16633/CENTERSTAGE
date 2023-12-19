package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Shooter implements SubsystemBase{


    public HardwareMap hwMap;
    public DcMotor shooter;
    public Shooter(HardwareMap hwMap){
        shooter = hwMap.dcMotor.get("shooter");
    }

    public void shoot(){
        shooter.setPower(Constants.ShooterConstants.shooterSpeed);
    }
    public void rest(){
        shooter.setPower(0);
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
