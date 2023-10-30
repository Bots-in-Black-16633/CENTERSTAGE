package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

public class Wrist implements SubsystemBase{
    Servo wrist;

    public Wrist(HardwareMap hwMap){
        wrist = hwMap.servo.get("wrist");
    }

    public void setPosition(double position){
        wrist.setPosition(position);
    }
    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        t.addLine();
        t.addLine("_____WRIST_____");
        t.addLine("Port" + wrist.getPortNumber());
        t.addLine("Position" + wrist.getPosition());
    }

    @Override
    public void periodic() {

    }
}
