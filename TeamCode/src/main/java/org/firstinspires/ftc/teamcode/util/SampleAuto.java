package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

public abstract class SampleAuto extends LinearOpMode {
    public ColorfulTelemetry pen;
    public ElapsedTime time;
    @Override
    public void runOpMode() throws InterruptedException {
        pen = new ColorfulTelemetry(telemetry, FtcDashboard.getInstance());
        onInit();
        waitForStart();
        onStart();
        onStop();
    }

    /**
     * This method is run once upon the initialization of the Autonomous
     */
    public abstract void onInit();

    /**
     * This method is run once upon start of the Autonomous
     */
    public abstract void onStart();



    /**
     * This method is run if the Autonomous is manually stopped, or 2 seconds before the autonomous timer expires and stops the auto
     */
    public abstract void onStop();
}
