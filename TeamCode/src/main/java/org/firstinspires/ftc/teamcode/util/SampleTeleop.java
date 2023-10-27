package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class SampleTeleop extends LinearOpMode {
    public ColorfulTelemetry pen;
    @Override
    public void runOpMode() throws InterruptedException {
        pen = new ColorfulTelemetry(telemetry, FtcDashboard.getInstance());
        onInit();
        waitForStart();
        while(opModeIsActive() && !isStopRequested()){
            loop();
        }
        onStop();
    }


    public abstract void onInit();
    public abstract void onLoop();
    public abstract void onStop();
}
