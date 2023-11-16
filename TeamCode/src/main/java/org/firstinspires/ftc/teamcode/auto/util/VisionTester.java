package org.firstinspires.ftc.teamcode.auto.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
import org.firstinspires.ftc.teamcode.vision.TeamPropDetector;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;
import org.firstinspires.ftc.vision.VisionPortal;
import org.opencv.core.Rect;


@Autonomous
public class VisionTester extends SampleAuto {

    TeamPropDetector b;
    VisionPortal v;

    WebcamName camera;

    public static final double zone1Border = 200;
    public static final double zone2Border = 400;
    public int redZone = 0;//1,2,or3
    public int blueZone = 0;//1,2,or3

    @Override
    public void onInit() {
        TeamPropPartitionDetector.startPropDetection(hardwareMap.get(WebcamName.class, "camera"), pen);


    }

    @Override
    public void onStart() {
        redZone = TeamPropPartitionDetector.getRedPropZone();
        blueZone = TeamPropPartitionDetector.getBluePropZone();

        //FtcDashboard.getInstance().startCameraStream((CameraStreamSource) b, 0);
        while(!isStopRequested()){
            redZone = TeamPropPartitionDetector.getRedPropZone();
            blueZone = TeamPropPartitionDetector.getBluePropZone();
            pen.addLine("RED ZONE" + redZone);
            pen.addLine("BLUE ZONE" + blueZone);

            pen.update();
        }
        TeamPropPartitionDetector.endPropDetection();

    }


    @Override
    public void onStop() {

    }


}
