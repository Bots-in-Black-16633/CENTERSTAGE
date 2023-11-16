package org.firstinspires.ftc.teamcode.vision;

import static java.lang.Thread.sleep;

import android.graphics.Canvas;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TeamPropPartitionDetector implements VisionProcessor {
    Mat hsvThresholdOutputRed = new Mat();
    Mat hsvThresholdOutputBlue = new Mat();

    Mat dilationOutputRed = new Mat();
    Mat dilationOutputBlue = new Mat();

    private static double blueLeftFrame;
    private static double redLeftFrame;
    private static double blueMidFrame;
    private static double redMidFrame;
    private static double blueRightFrame;
    private static double redRightFrame;

    Mat hierarchy = new Mat();


    public static Scalar redLower = new Scalar(100,155,100);
    public static Scalar redUpper = new Scalar(255,255,255);
    public static Scalar blueLower = new Scalar(7,111,22);
    public static Scalar blueUpper = new Scalar(30,255,255);
    ColorfulTelemetry telemetry;

    public static int ZONE1EDGE = 200;
    public static int ZONE2EDGE = 400;


    public static VisionPortal portal;
    public static WebcamName camera;
    public static TeamPropPartitionDetector propDetector;






    public TeamPropPartitionDetector(ColorfulTelemetry telemetry){
        this.telemetry = telemetry;
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    public Object processFrame(Mat frame, long captureTimeNanos) {

        //Convert Color Scheme to HSV and threshold it using an uppre and lower HSV value, turning everything not in the range to black and everything in the range to white
        hsvThreshold(frame, blueLower,blueUpper, hsvThresholdOutputBlue);
        hsvThreshold(frame, redLower, redUpper, hsvThresholdOutputRed);

        // TO -DO Dilate the image to fill in the spots in the found white area. Little hard to explain. Watch this video https://www.youtube.com/watch?v=7-FZBgrW4RE
        Imgproc.erode(hsvThresholdOutputRed, dilationOutputRed, Mat.ones(15,15, CvType.CV_8U));

        Imgproc.erode(hsvThresholdOutputBlue, dilationOutputBlue, Mat.ones(15,15, CvType.CV_8U));
        Imgproc.dilate(dilationOutputBlue, dilationOutputBlue,Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(10, 10)));
        Imgproc.dilate(dilationOutputRed, dilationOutputRed,Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(10, 10)));





        Core.bitwise_or(dilationOutputBlue, dilationOutputRed, frame);


        blueLeftFrame = Core.sumElems(dilationOutputBlue.submat(0,dilationOutputBlue.rows()-1,0,ZONE1EDGE)).val[0];
        blueMidFrame = Core.sumElems(dilationOutputBlue.submat(0, dilationOutputBlue.rows()-1, ZONE1EDGE,ZONE2EDGE)).val[0];
        blueRightFrame = Core.sumElems(dilationOutputBlue.submat(0, dilationOutputBlue.rows()-1, ZONE2EDGE, dilationOutputBlue.cols()-1)).val[0];

        redLeftFrame = Core.sumElems(dilationOutputRed.submat(0,dilationOutputRed.rows()-1,0,ZONE1EDGE)).val[0];
        redMidFrame = Core.sumElems(dilationOutputRed.submat(0, dilationOutputRed.rows()-1, ZONE1EDGE,ZONE2EDGE)).val[0];
        redRightFrame = Core.sumElems(dilationOutputRed.submat(0, dilationOutputRed.rows()-1, ZONE2EDGE, dilationOutputRed.cols()-1)).val[0];



        Imgproc.rectangle(frame, new Rect(0, 0, ZONE1EDGE, frame.rows()), new Scalar(255,255,255));
        Imgproc.rectangle(frame, new Rect(ZONE1EDGE, 0, ZONE2EDGE-ZONE1EDGE, frame.rows()), new Scalar(255,255,255));

        telemetry.addLine("leftFrame: " + blueLeftFrame);
        telemetry.addLine("MidFrame:" + blueMidFrame);
        telemetry.addLine("RightFrame:" + blueRightFrame);
        telemetry.addLine("leftFrame: " + redLeftFrame);
        telemetry.addLine("MidFrame:" + redMidFrame);
        telemetry.addLine("RightFrame:" + redRightFrame);

        telemetry.addLine();
        telemetry.addLine("Blue Zone: " + TeamPropPartitionDetector.getBluePropZone());
        telemetry.addLine("Red Zone: " + TeamPropPartitionDetector.getRedPropZone());

        telemetry.update();

        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
    private void hsvThreshold(Mat input, Scalar lower, Scalar upper, Mat out) {
        Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HSV);
        Core.inRange(out, lower, upper, out);
    }





    /**
     * Static Methods
     */

    public static void startPropDetection(WebcamName camera, ColorfulTelemetry pen){
        propDetector = new TeamPropPartitionDetector(pen);
        TeamPropPartitionDetector.camera = camera;
        portal = VisionPortal.easyCreateWithDefaults(camera, propDetector);
        if (portal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            pen.addData("Camera", "Waiting");
            pen.update();
            while ((portal.getCameraState() != VisionPortal.CameraState.STREAMING)) {

            }
            pen.addData("Camera", "Ready");
            pen.update();
        }
        ExposureControl exposureControl = portal.getCameraControl(ExposureControl.class);
        exposureControl.setMode(ExposureControl.Mode.Manual);
        exposureControl.setExposure((long)20, TimeUnit.MILLISECONDS);
        //GainControl gainControl = portal.getCameraControl(GainControl.class);
        //gainControl.setGain(10);


        }
    public static int getRedPropZone(){
        double max = Math.max(redLeftFrame, Math.max(redMidFrame ,redRightFrame));
        if(max == redLeftFrame)return 1;
        else if(max == redMidFrame)return 2;
        else return 3;
    }
    public static int getBluePropZone(){
        double max = Math.max(blueLeftFrame, Math.max(blueMidFrame ,blueRightFrame));
        if(max == blueLeftFrame)return 1;
        else if(max == blueMidFrame)return 2;
        else return 3;
    }



    public static void endPropDetection(){
        portal.close();
    }


}
