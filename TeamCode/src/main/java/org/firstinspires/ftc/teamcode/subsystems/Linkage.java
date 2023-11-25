package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Linkage implements SubsystemBase{


    public HardwareMap hwMap;
    public Servo linkage;
    public Linkage(HardwareMap hwMap){
        linkage = hwMap.servo.get("linkage");
    }

    public void raise(){
        linkage.setPosition(Constants.LinkageConstants.linkageUp);
    }
    public void lower(){
        linkage.setPosition(Constants.LinkageConstants.linkageDown);
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
