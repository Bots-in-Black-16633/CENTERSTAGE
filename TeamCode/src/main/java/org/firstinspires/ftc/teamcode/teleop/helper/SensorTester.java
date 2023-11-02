package org.firstinspires.ftc.teamcode.teleop.helper;

import android.graphics.Color;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
@TeleOp
public class SensorTester extends LinearOpMode {
    public NormalizedColorSensor colorSensor = null;
    //Telemetry telemetry;
    FtcDashboard dash = FtcDashboard.getInstance();
    MultipleTelemetry telemetry;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
        waitForStart();
        while(!isStopRequested() && opModeIsActive()){
            telemetry.addData("RED: ",getRed());
            telemetry.addData("BLUE: ",getBlue());
            telemetry.addData("GREEN: ",getGreen());
            telemetry.addData("INTENSITY: ",getIntensity()*.05);


            telemetry.update();

        }

    }
    // Functions for Color Sensor - July 2021

    public float [] getColorValues() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float [] output = new float [3];
        output[0] = colors.red;
        output[1] = colors.blue;
        output[2] = colors.green;
        return output;
    }

    public double getRed() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        return colors.red;
    }

    public double getGreen() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        return colors.green;
    }

    public double getBlue() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        return colors.blue;
    }

    public float getIntensity() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float[] hsvValues = new float[3];
        Color.colorToHSV(colors.toColor(), hsvValues);
        return hsvValues[1];
    }

    // Added by Mr. Michaud 19 Sep 22
    public float getHue() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float[] hsvValues = new float[3];
        Color.colorToHSV(colors.toColor(), hsvValues);
        return hsvValues[0];
    }
}
