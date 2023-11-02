package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.BlockDetection;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionPortalImpl;
import org.opencv.core.Rect;


@Autonomous
public class VisionTester extends SampleAuto {

    BlockDetection b;
    VisionPortal v;
    Rect red;
    Rect blue;
    WebcamName camera;

    public static final double zone1Border = 200;
    public static final double zone2Border = 400;
    public int redZone = 0;//1,2,or3
    public int blueZone = 0;//1,2,or3

    @Override
    public void onInit() {
        b = new BlockDetection(pen);
        camera = hardwareMap.get(WebcamName.class, "camera");
        v = VisionPortal.easyCreateWithDefaults(camera, b);
    }

    @Override
    public void onStart() {
        red = b.getRedBoundingRect();
        blue = b.getBlueBoundingRect();
        if(red.x < zone1Border)redZone=1;
        else if(red.x < zone2Border)redZone=2;
        else redZone = 3;
        if(blue.x < zone1Border)blueZone=1;
        else if(blue.x < zone2Border)blueZone=2;
        else blueZone = 3;

    }

    @Override
    public void onLoop() {


        pen.addLine("RED: "+red.toString());
        pen.addLine("BLUE: "+blue.toString());
        pen.update();



    }

    @Override
    public void onStop() {

    }
}
