package org.firstinspires.ftc.teamcode.vision;

import static java.lang.Thread.sleep;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.teamcode.subsystems.drive.Drive;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AprilTagProcessorWrapper {
    static AprilTagProcessor atp;
    static VisionPortal vp;

    static volatile boolean atTarget = false;

    final static double MAX_AUTO_SPEED = 0.3;   //  Clip the approach speed to this max value (adjust for your robot)
    final static double MAX_AUTO_STRAFE= 0.2;   //  Clip the approach speed to this max value (adjust for your robot)
    final static double MAX_AUTO_TURN  = 0.2;   //  Clip the turn speed to this max value (adjust for your robot)


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
        ExposureControl exposureControl = vp.getCameraControl(ExposureControl.class);
        if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
            exposureControl.setMode(ExposureControl.Mode.Manual);
        }
        exposureControl.setExposure((long)10, TimeUnit.MILLISECONDS);
        GainControl gainControl = vp.getCameraControl(GainControl.class);
        gainControl.setGain(250);
        atTarget=false;
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
        atTarget=false;
    }

    public static AprilTagDetection getAprilTagInfo(int id, ColorfulTelemetry pen){
        List<AprilTagDetection> currentDetections = atp.getDetections();
        pen.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                pen.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                pen.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                pen.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                pen.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                pen.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                pen.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        pen.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        pen.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        pen.addLine("RBE = Range, Bearing & Elevation");

        return atp.getDetections().stream().filter(tag->tag.id==id).findFirst().orElse(null);
    }
    public static double[] getSuggestedPower(int id, Drive drive, ColorfulTelemetry pen){

        AprilTagDetection desiredTag = getAprilTagInfo(id, pen);
        if(desiredTag == null)return null;
        else{
            double[] out = new double[6];
            double  rangeError      = (desiredTag.ftcPose.range - Constants.DriveConstants.DESIRED_DISTANCE);
            double  headingError    = desiredTag.ftcPose.bearing/**(Math.toRadians(180)-drive.getHeading())**/;
            double  yawError        = desiredTag.ftcPose.yaw;


            // Use the speed and turn "gains" to calculate how we want the robot to move.
            //FORWARD power
            out[1]=-Range.clip(rangeError * Constants.DriveConstants.APRIL_TAG_SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
            //strafe power
            out[0]=Range.clip(-yawError * Constants.DriveConstants.APRIL_TAG_STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);

           //TURN Power
            out[2]= -Range.clip(headingError * Constants.DriveConstants.APRIL_TAG_TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN) ;


            if((Math.abs(rangeError) < 2 && Math.abs(headingError)<10 && Math.abs(yawError)<4)||(Math.abs(out[0]) <=.1 && Math.abs(out[1])<=.1 && Math.abs(out[1])<=.1))atTarget =true;
            else atTarget = false;
            out[3]=rangeError;
            out[4]=headingError;
            out[5]=yawError;
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
