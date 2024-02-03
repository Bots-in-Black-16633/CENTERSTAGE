package org.firstinspires.ftc.teamcode.teleop.helper;

import android.graphics.Color;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensorWrapper;
import org.firstinspires.ftc.teamcode.util.Constants;

import java.util.Arrays;
@TeleOp(name = "Sensor Tester", group = "helper")
public class SensorTester extends LinearOpMode {
    public ColorSensorWrapper leftSensor = null;
    public ColorSensorWrapper rightSensor = null;
    public RevColorSensorV3 distance = null;

    //Telemetry telemetry;
    FtcDashboard dash = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {

        leftSensor = new ColorSensorWrapper( "leftHopperSensor",hardwareMap);
        rightSensor = new ColorSensorWrapper( "rightHopperSensor",hardwareMap);
        distance= hardwareMap.get(RevColorSensorV3.class, "distanceSensor");
        waitForStart();
        while(!isStopRequested() && opModeIsActive()){
            telemetry.addData("left HSV: ",Arrays.toString(leftSensor.getHSVValues()));
            telemetry.addData("left RGB: ",Arrays.toString(leftSensor.getRGB()));

            telemetry.addData("right HSV: ",Arrays.toString(rightSensor.getHSVValues()));
            telemetry.addData("right RGB: ",Arrays.toString(rightSensor.getRGB()));
            telemetry.addLine("Distance: " + distance.getDistance(DistanceUnit.MM));

            telemetry.addLine("____HOPPER_____");

            telemetry.addLine("LEFT HOPPER: " + leftSensor.getPixelPresent().name());
            telemetry.addLine("     VAlue" + Arrays.toString(leftSensor.getTelemetryData()));
            telemetry.addLine("RIGHT HOPPER: " + rightSensor.getPixelPresent().name());
            telemetry.addLine("     VAlue" + Arrays.toString(rightSensor.getTelemetryData()));
            telemetry.update();

        }

    }

}
