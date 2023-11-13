package org.firstinspires.ftc.teamcode.auto.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropDetector;
import org.firstinspires.ftc.vision.VisionPortal;
import org.opencv.core.Rect;


@Autonomous
public class VisionTester extends SampleAuto {

    TeamPropDetector b;
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
        TeamPropDetector.startPropDetection(hardwareMap, pen);


    }

    @Override
    public void onStart() {
        redZone = TeamPropDetector.getRedPropZone();
        blueZone = TeamPropDetector.getBluePropZone();

        //FtcDashboard.getInstance().startCameraStream((CameraStreamSource) b, 0);
        while(!isStopRequested()){
            redZone = TeamPropDetector.getRedPropZone();
            blueZone = TeamPropDetector.getBluePropZone();
            pen.addLine("RED ZONE" + redZone);
            pen.addLine("BLUE ZONE" + blueZone);

            pen.update();
        }
        TeamPropDetector.endPropDetection();

    }


    @Override
    public void onStop() {

    }
}
