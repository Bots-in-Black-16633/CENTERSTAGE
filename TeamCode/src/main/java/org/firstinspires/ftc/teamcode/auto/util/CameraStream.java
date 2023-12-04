package org.firstinspires.ftc.teamcode.auto.util;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name="CameraStream", group="tester")
public class CameraStream extends SampleAuto {
    WebcamName camera;
    VisionPortal portal;

    @Override
    public void onInit() {
        camera = hardwareMap.get(WebcamName.class, "camera");
        portal = VisionPortal.easyCreateWithDefaults(camera);
    }

    @Override
    public void onStart() {

    }


    @Override
    public void onStop() {

    }
}
