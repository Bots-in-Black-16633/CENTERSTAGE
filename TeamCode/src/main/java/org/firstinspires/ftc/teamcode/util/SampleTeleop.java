package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class SampleTeleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        init();
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
