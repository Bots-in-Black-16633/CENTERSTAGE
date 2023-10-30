package org.firstinspires.ftc.teamcode.subsystems;


import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

public class Shoulder implements SubsystemBase{

    public Servo shoulder;

    public HardwareMap hwMap;
    public Shoulder(HardwareMap hwMap){
        shoulder = hwMap.servo.get("shoulder");
    }


    public void setPosition(double position){
        shoulder.setPosition(position);
    }
    public double getPosition(){return shoulder.getPosition();}
    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        t.addLine("");
        t.addLine("______SHOULDER____");
        t.addLine("Port: " + shoulder.getPortNumber());
        t.addLine("Pos: " + shoulder.getPosition());

    }

    @Override
    public void periodic() {

    }
}
