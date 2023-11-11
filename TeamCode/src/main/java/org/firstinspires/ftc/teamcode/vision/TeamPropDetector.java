package org.firstinspires.ftc.teamcode.vision;

import android.graphics.Canvas;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
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

public class TeamPropDetector implements VisionProcessor {
    Mat hsvThresholdOutputRed = new Mat();
    Mat hsvThresholdOutputBlue = new Mat();


    Mat dilationOutputRed = new Mat();
    Mat dilationOutputBlue = new Mat();

    Mat hierarchy = new Mat();
    ArrayList<MatOfPoint> contoursBlue = new ArrayList<MatOfPoint>();
    ArrayList<MatOfPoint> contoursRed = new ArrayList<MatOfPoint>();

    ArrayList<MatOfPoint> contoursOutputRed = new ArrayList<MatOfPoint>();
    ArrayList<MatOfPoint> contoursOutputBlue = new ArrayList<MatOfPoint>();



    public static Scalar redLower = new Scalar(100,155,100);
    public static Scalar redUpper = new Scalar(255,255,255);
    public static Scalar blueLower = new Scalar(7,111,22);
    public static Scalar blueUpper = new Scalar(30,255,255);
    Rect redUnionRect;
    Rect blueUnionRect;

    public double timesAveraged = 0;
    public static double averageCycle = 30;
    ColorfulTelemetry telemetry;

    public static double ZONE1EDGE = 200;
    public static double ZONE2EDGE = 400;

    public static VisionPortal portal;
    public static WebcamName camera;
    public static TeamPropDetector propDetector;

    public static double redXPos = 0;
    public static double blueXPos = 0;

    public static double redXTimesAveraged = 0;
    public static double blueXTimesAveraged = 0;




    public TeamPropDetector(ColorfulTelemetry telemetry){
        this.telemetry = telemetry;
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        //Convert Color Scheme to HSV and threshold it using an uppre and lower HSV value, turning everything not in the range to black and everything in the range to white
        hsvThreshold(frame, blueLower,blueUpper, hsvThresholdOutputBlue);
        hsvThreshold(frame, redLower, redUpper, hsvThresholdOutputRed);

        // TO -DO Dilate the image to fill in the spots in the found white area. Little hard to explain. Watch this video https://www.youtube.com/watch?v=7-FZBgrW4RE
        Imgproc.dilate(hsvThresholdOutputRed, dilationOutputRed,Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(3,3)));
        Imgproc.dilate(hsvThresholdOutputBlue, dilationOutputBlue,Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(3,3)));

        Core.bitwise_or(dilationOutputBlue, dilationOutputRed, frame);

        double curRedX = getAvergageWhitePixelXPos(dilationOutputRed);
        double curBlueX = getAvergageWhitePixelXPos(dilationOutputBlue);

        if(!Double.isNaN(curRedX)){
            redXPos = (redXPos * (redXTimesAveraged/(redXTimesAveraged+1))) + (curRedX* (1/(redXTimesAveraged+1)));
            redXTimesAveraged++;
        }
        if(!Double.isNaN(curBlueX)){
            blueXPos = (blueXPos * (blueXTimesAveraged/(blueXTimesAveraged+1))) + (curBlueX* (1/(blueXTimesAveraged+1)));
            blueXTimesAveraged++;
        }

        telemetry.addLine("REDXPOS: " + redXPos);
        telemetry.addLine("CURREDX" + curRedX);
        telemetry.addLine("BLUEXPOS: " + blueXPos);
        telemetry.addLine();
        telemetry.update();
        Imgproc.line(frame, new Point(redXPos, 0), new Point(redXPos, frame.rows()-1), new Scalar(255,255,255));
        Imgproc.line(frame, new Point(blueXPos, 0), new Point(blueXPos, frame.rows()-1), new Scalar(255,255,255));

        //    Find Contours
//        findContours(dilationOutputRed, contoursRed);
//        findContours(dilationOutputBlue, contoursBlue);
//
//        //draw the contours
//        Imgproc.drawContours(frame, contoursRed,-1,new Scalar(50,50,50));
//        Imgproc.drawContours(frame, contoursBlue,-1,new Scalar(50,50,50));
//
//
//        //Filter Contours
//        filterContours(contoursRed, frame, contoursOutputRed);
//        filterContours(contoursBlue, frame, contoursOutputBlue);




       // Find Rectangle which covers all the Contours
//        if(contoursOutputBlue.size() >0 && contoursOutputRed.size() > 0){
//            addToAverage(Imgproc.boundingRect(contoursOutputRed.get(0)),Imgproc.boundingRect(contoursOutputBlue.get(0)));
//            Imgproc.rectangle(frame, Imgproc.boundingRect(contoursOutputRed.get(0)), new Scalar(90,90,90));
//            Imgproc.rectangle(frame, Imgproc.boundingRect(contoursOutputBlue.get(0)), new Scalar(90,90,90));
//
//            Imgproc.rectangle(frame, blueUnionRect, new Scalar(255,0,0));
//            Imgproc.rectangle(frame, redUnionRect, new Scalar(255,0,0));
//            telemetry.addLine("NEW BLUE: " + Imgproc.boundingRect(contoursOutputBlue.get(0)).toString());
//            telemetry.addLine("NEW RED: " + Imgproc.boundingRect(contoursOutputRed.get(0)).toString());
//
//        }


        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
    private void hsvThreshold(Mat input, Scalar lower, Scalar upper, Mat out) {
        Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HSV);
        Core.inRange(out, lower, upper, out);
    }
    private void findContours(Mat input, List<MatOfPoint> contours) {
        contours.clear();
        Imgproc.findContours(input, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
    }
    private void filterContours(List<MatOfPoint> inputContours, Mat thresholdedFrame, List<MatOfPoint> output) {
        final MatOfInt hull = new MatOfInt();
        output.clear();
        if(inputContours.size() <1)return;


        double maxPercentFill = 0;
        int maxIndex = 0;
        //operation
        for (int i = 0; i < inputContours.size(); i++) {
            final MatOfPoint contour = inputContours.get(i);
            final Rect bb = Imgproc.boundingRect(contour);
            final double vertices = contour.rows();

            double percentFill = Core.sumElems(thresholdedFrame.submat(bb)).val[0]/bb.area();

            if(percentFill > maxPercentFill ){
                maxIndex = i;
                maxPercentFill = percentFill;
            }
        }
        output.add(inputContours.get(maxIndex));

    }


    public Rect getRedBoundingRect(){
        return redUnionRect;
    }
    public Rect getBlueBoundingRect(){
        return blueUnionRect;
    }
    public void addToAverage(Rect red, Rect blue){

        if(timesAveraged >averageCycle || timesAveraged == 0){
            timesAveraged = 0;
            redUnionRect=red;
            blueUnionRect=blue;
            timesAveraged++;

        }
        else{
            double curRX = redUnionRect.x * (timesAveraged/(timesAveraged+1));
            double curRY = redUnionRect.y* (timesAveraged/(timesAveraged+1));
            double curRWidth = redUnionRect.width * (timesAveraged/(timesAveraged+1));
            double curRHeight = redUnionRect.height* (timesAveraged/(timesAveraged+1));


            double newRX = red.x / (timesAveraged + 1);
            double newRY = red.y / (timesAveraged + 1);
            double newRWidth = red.width / (timesAveraged + 1);
            double newRHeight = red.height / (timesAveraged + 1);

            double avgRX = curRX + newRX;
            double avgRY = curRY + newRY;
            double avgRWidth = curRWidth + newRWidth;
            double avgRHeight = curRHeight + newRHeight;
            redUnionRect = new Rect((int)avgRX ,(int)avgRY, (int)avgRWidth, (int)avgRHeight);

            double curBX = blueUnionRect.x * (timesAveraged/(timesAveraged+1));
            double curBY = blueUnionRect.y* (timesAveraged/(timesAveraged+1));
            double curBWidth = blueUnionRect.width * (timesAveraged/(timesAveraged+1));
            double curBHeight = blueUnionRect.height* (timesAveraged/(timesAveraged+1));


            double newBX = blue.x / (timesAveraged + 1);
            double newBY = blue.y / (timesAveraged + 1);
            double newBWidth = blue.width / (timesAveraged + 1);
            double newBHeight = blue.height / (timesAveraged + 1);

            double avgBX = curBX + newBX;
            double avgBY = curBY + newBY;
            double avgBWidth = curBWidth + newBWidth;
            double avgBHeight = curBHeight + newBHeight;
            blueUnionRect = new Rect((int)avgBX ,(int)avgBY, (int)avgBWidth, (int)avgBHeight);
            timesAveraged++;
        }


        telemetry.addLine("TIME AVG:" + timesAveraged);
        telemetry.addLine("AVG BLUE: " + blueUnionRect.toString());
        telemetry.addLine("AVG Red: " + redUnionRect.toString());
    }

//    public double getAvergageWhitPixXPos(Mat binaryMat){
//        double average = 0;
//        double timesAveraged = 0;
//        for(int i = 0; i < binaryMat.cols(); i++){
//            double numWhitePixelsInColumn = numLongestContinousWhitePixels(binaryMat.col(i));
//
//            double curAverage = average*(timesAveraged/(timesAveraged+numWhitePixelsInColumn));
//            double addAverage = i* (numWhitePixelsInColumn/(numWhitePixelsInColumn+timesAveraged));
//            average = curAverage + addAverage;
//            timesAveraged = timesAveraged + numWhitePixelsInColumn;
//        }
//        telemetry.addLine("AVERAGE XPOS: " + average);
//
//        return average;
//    }
//    public double numLongestContinousWhitePixels(){
//
//    }

    /**
     * Static Methods
     */

    public static void startPropDetection(HardwareMap hwMap, ColorfulTelemetry pen){
         propDetector = new TeamPropDetector(pen);
        camera = hwMap.get(WebcamName.class, "camera");
         portal = VisionPortal.easyCreateWithDefaults(camera, propDetector);

    }
    public static int getRedPropZone(){

        if(redXPos < ZONE1EDGE)return 1;
        else if(redXPos < ZONE2EDGE)return 2;
        else return 3;
    }
    public static int getBluePropZone(){

        if(blueXPos < ZONE1EDGE)return 1;
        else if(blueXPos < ZONE2EDGE)return 2;
        else return 3;
    }
    public static int getZone(double xPOs){
        if(xPOs < ZONE1EDGE)return 1;
        else if(xPOs < ZONE2EDGE)return 2;
        else return 3;
    }


    public static void endPropDetection(){
        portal.close();
    }

    public double getAvergageWhitePixelXPos(Mat binaryMat){
        double average = 0;
        double timesAveraged = 1;
        for(int i = 0; i < binaryMat.cols(); i++){
            double numWhitePixelsInColumn = getGreatestNumContinousWhitePixels(binaryMat, i);
            if(numWhitePixelsInColumn < 20)continue;
            double curAverage = average*(timesAveraged/(timesAveraged+numWhitePixelsInColumn));
            double addAverage = i* (numWhitePixelsInColumn/(numWhitePixelsInColumn+timesAveraged));
            average = curAverage + addAverage;
            timesAveraged = timesAveraged + numWhitePixelsInColumn;
        }

        return average;
    }

    public double getGreatestNumContinousWhitePixels(Mat binaryMatColumn, int col){
        double greatestNumContinous = 0;
        double curNumContinous = 0;
        for(int i = 0; i < binaryMatColumn.rows(); i++){
            double val = binaryMatColumn.get(i,col)[0];

            if(val == 255)greatestNumContinous++;
            if(val !=255)curNumContinous=0;

            if(curNumContinous > greatestNumContinous){
                greatestNumContinous = curNumContinous;
            }

        }
        return greatestNumContinous;

    }


}
