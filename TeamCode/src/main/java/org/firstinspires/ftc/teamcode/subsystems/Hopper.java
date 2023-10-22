package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.hardware.motors.CRServo;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

public class Hopper extends SubsystemBase{

    CRServo leftHopper;
    CRServo rightHopper;

    public Hopper(HardwareMap hwMap){
        super("Hopper", hwMap);
        leftHopper = new CRServo(hwMap, "leftHopper");
        rightHopper = new CRServo(hwMap, "rightHopper");
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
