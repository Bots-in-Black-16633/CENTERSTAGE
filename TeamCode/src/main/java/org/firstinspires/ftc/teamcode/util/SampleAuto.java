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
        //once the auto has started start the timer
        time = new ElapsedTime();
        while(opModeIsActive() && !isStopRequested()){

            //run onStop 2 seconds before the Autonomous stops
            if(time.seconds()>28)onStop();
            pen.addLine("TIME LEFT: " + (30-time.seconds()));


            loop();
        }
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
     * This method is repeated as long as the Autonomous is active
     */
    public abstract void onLoop();

    /**
     * This method is run if the Autonomous is manually stopped, or 2 seconds before the autonomous timer expires and stops the auto
     */
    public abstract void onStop();
}
