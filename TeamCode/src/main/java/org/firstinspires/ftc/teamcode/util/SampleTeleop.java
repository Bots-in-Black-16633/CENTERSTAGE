package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;

public abstract class SampleTeleop extends LinearOpMode {
    public volatile ColorfulTelemetry pen;
    public volatile BaseRobot robot;
    @Override
    public void runOpMode() throws InterruptedException {
        pen = new ColorfulTelemetry(telemetry, FtcDashboard.getInstance());
        onInit();
        waitForStart();
        onStart();
        while(opModeIsActive() && !isStopRequested()){
            onLoop();
            telemetry();
        }
        onStop();
    }

    /**
     * This method is run once upon the initialization of the Teleop
     */
    public abstract void onInit();

    /**
     * This method is run once upon start of the Teleop
     */
    public abstract void onStart();

    /**
     * This method is repeated as long as the Teleop is active
     */
    public abstract void onLoop();

    /**
     * This method is run if the Teleop is manually stopped
     */
    public abstract void onStop();
    private void telemetry(){
        robot.printTelemetry(pen);
        robot.periodic();
        pen.update();
    }
}
