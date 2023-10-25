package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

import java.util.HashMap;

public abstract class SubsystemBase {
    public String name;

    /**
     * Instantiate a Subsystem Base with a name
     * @param name
     */
    public SubsystemBase(String name, HardwareMap hwMap){
        this.name = name;
    }

    /**
     * method which when called prints all the telemetry data asocciated with this particular subsystem
     * @param t
     */
    public abstract void printTelemetry(ColorfulTelemetry t);

    /**
     * method which will be called repeatedly whenever the subsystem is create
     */
    public abstract void periodic();



}
