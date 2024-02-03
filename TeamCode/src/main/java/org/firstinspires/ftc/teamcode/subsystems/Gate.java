package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Gate implements SubsystemBase{
    Servo gate;

    public Gate(HardwareMap hwMap){
        gate = hwMap.servo.get("gate");
    }

    public void setPosition(double position){
        gate.setPosition(position);
    }
    public void open(){
        gate.setPosition(Constants.GateConstant.gateOpen);
    }
    public void close(){
        gate.setPosition(Constants.GateConstant.gateClose);
    }

    public double getPosition(){return gate.getPosition();}
    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        t.addLine();
        t.addLine("_____GATE_____");
        t.addLine("Port" + gate.getPortNumber());
        t.addLine("Position" + gate.getPosition());
    }

    @Override
    public void periodic() {

    }
}
