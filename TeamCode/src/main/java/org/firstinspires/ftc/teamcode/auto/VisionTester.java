package org.firstinspires.ftc.teamcode.auto;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.BlockDetection;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionPortalImpl;
import org.opencv.core.Rect;


public class VisionTester extends SampleAuto {

    BlockDetection b;
    VisionPortal v;
    Rect r;
    WebcamName camera;
    @Override
    public void onInit() {
        b = new BlockDetection(pen);
        camera = hardwareMap.get(WebcamName.class, "camera");
        v = VisionPortal.easyCreateWithDefaults(camera, b);
    }

    @Override
    public void onStart() {
        r = b.getBoundingRect();

    }

    @Override
    public void onLoop() {
        pen.addLine("X:"+r.x);
        pen.addLine("Y:"+r.y);
        pen.addLine("W:"+r.width);
        pen.addLine("H:"+r.height);
        pen.update();


    }

    @Override
    public void onStop() {

    }
}
