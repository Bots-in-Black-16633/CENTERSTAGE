package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

import java.util.HashMap;

public interface SubsystemBase {



    /**
     * method which when called prints all the telemetry data asocciated with this particular subsystem
     * @param t
     */
    void printTelemetry(ColorfulTelemetry t);

    /**
     * method which will be called repeatedly whenever the subsystem is create
     */
    void periodic();



}
