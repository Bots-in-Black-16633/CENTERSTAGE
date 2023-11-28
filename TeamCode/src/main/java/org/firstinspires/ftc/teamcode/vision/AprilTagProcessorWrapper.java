package org.firstinspires.ftc.teamcode.vision;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class AprilTagProcessorWrapper {
    static AprilTagProcessor atp;
    static VisionPortal vp;

    static boolean atTarget = false;

    final static double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
    final static double MAX_AUTO_STRAFE= 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
    final static double MAX_AUTO_TURN  = 0.3;   //  Clip the turn speed to this max value (adjust for your robot)


    public static void startAprilTagDetection(WebcamName camera, ColorfulTelemetry pen){
        atp = new AprilTagProcessor.Builder().build();
        atp.setDecimation(2);
        vp = new VisionPortal.Builder()
                .setCamera(camera)
                .addProcessor(atp)
                .build();
        while(vp.getCameraState() != VisionPortal.CameraState.STREAMING) {
            pen.addLine("Waiting for Camera to Come Online");
            pen.addLine("STATUS" + getStringCameraState(vp.getCameraState()));
            pen.update();
        }
    }
    public static void pauseAprilTagDetection(ColorfulTelemetry pen){

        vp.stopStreaming();
        while(vp.getCameraState() != VisionPortal.CameraState.CAMERA_DEVICE_READY) {
            pen.addLine("Waiting for Camera to Pause");
            pen.addLine("STATUS" + getStringCameraState(vp.getCameraState()));
            pen.update();
        }
    }
    public static void resumeAprilTagDetection(ColorfulTelemetry pen){
        if(vp!=null)vp.resumeStreaming();
        while(vp.getCameraState() != VisionPortal.CameraState.STREAMING) {
            pen.addLine("Waiting for Camera to Resume Streaming");
            pen.addLine("STATUS" + getStringCameraState(vp.getCameraState()));
            pen.update();
        }
    }

    public static AprilTagDetection getAprilTagInfo(int id){
        return atp.getDetections().stream().filter(tag->tag.id==id).findFirst().orElse(null);
    }
    public static double[] getSuggestedPower(int id){

        AprilTagDetection desiredTag = getAprilTagInfo(id);
        if(desiredTag == null)return null;
        else{
            double[] out = new double[3];
            double  rangeError      = (desiredTag.ftcPose.range - Constants.DriveConstants.DESIRED_DISTANCE);
            double  headingError    = desiredTag.ftcPose.bearing;
            double  yawError        = desiredTag.ftcPose.yaw;
            if(Math.abs(rangeError) < .5)atTarget =true;
            else atTarget = false;


            // Use the speed and turn "gains" to calculate how we want the robot to move.
            //FORWARD power
            out[1]=-Range.clip(rangeError * Constants.DriveConstants.APRIL_TAG_SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
            //strafe power
            out[0]=Range.clip(-yawError * Constants.DriveConstants.APRIL_TAG_STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);

           //TURN Power
            out[2]= -Range.clip(headingError * Constants.DriveConstants.APRIL_TAG_TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;

            return out;
        }
    }
    public static boolean isAtTarget(){
        return atTarget;
    }
    public static void endAprilTagDetection(){
        vp.close();
    }
    public static String getStringCameraState(VisionPortal.CameraState state){
        if(state == VisionPortal.CameraState.STREAMING)return "Streaming";
        else if(state == VisionPortal.CameraState.CAMERA_DEVICE_READY)return "CAMERA DEVICE READY";
        else if(state == VisionPortal.CameraState.CAMERA_DEVICE_CLOSED)return "CAMERA DEVICE CLOSED";
        else if(state == VisionPortal.CameraState.CLOSING_CAMERA_DEVICE)return "CLOSING CAMERA DEVICE";
        else if(state == VisionPortal.CameraState.ERROR)return "ERROR";
        else if(state == VisionPortal.CameraState.OPENING_CAMERA_DEVICE)return "OPENING CAMERA DEVICE";
        else if(state == VisionPortal.CameraState.STARTING_STREAM)return "STARTING STREAM";
        else if(state == VisionPortal.CameraState.STOPPING_STREAM)return "STOPPING STREAM";
        else return "UNKNOWN";







    }

}
