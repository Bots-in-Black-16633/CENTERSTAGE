package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

public class Slider extends SubsystemBase{

    Motor slider1;
    Motor slider2;
    MotorGroup slider;

    public Slider(HardwareMap hwMap){
        super("Slider", hwMap);
        slider1 = new Motor(hwMap, "slider1");
        slider2 = new Motor(hwMap, "slider2");
        slider = new MotorGroup(slider1, slider2);


    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
