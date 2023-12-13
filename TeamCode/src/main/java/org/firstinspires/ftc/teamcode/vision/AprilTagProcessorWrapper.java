package org.firstinspires.ftc.teamcode.vision;

import static java.lang.Thread.sleep;

import android.graphics.Color;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
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

public  class AprilTagProcessorWrapper {
    static volatile AprilTagProcessor atp;
    static volatile VisionPortal vp;

    static volatile boolean atTarget = false;

    static Thread startStopThread = null;
    public static void startAprilTagDetectionAsync(WebcamName camera, ColorfulTelemetry pen){
        startStopThread = new Thread(() -> AprilTagProcessorWrapper.startAprilTagDetection(camera, pen));
        startStopThread.start();
    }
    public static void resumeAprilTagDetectionAsync(ColorfulTelemetry pen){
        startStopThread = new Thread(()->AprilTagProcessorWrapper.resumeAprilTagDetection(pen));
        startStopThread.start();

    }
    public static void pauseAprilTagDetectionAsync(ColorfulTelemetry pen){
        startStopThread = new Thread(()->AprilTagProcessorWrapper.pauseAprilTagDetection(pen));
        startStopThread.start();

    }



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
        exposureControl.setExposure((long)Constants.VisionConstants.aprilTagExposure, TimeUnit.MILLISECONDS);
        GainControl gainControl = vp.getCameraControl(GainControl.class);
        gainControl.setGain(Constants.VisionConstants.aprilTagGain);
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
            pen.addLine("STATUS " + getStringCameraState(vp.getCameraState()));
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
            double convertedHeading = Math.toDegrees(drive.getHeading());
            if(convertedHeading < 0){
                convertedHeading = 360+convertedHeading;
            }
            double  rangeError      = (desiredTag.ftcPose.range - Constants.DriveConstants.DESIRED_DISTANCE);
            double  headingError    = desiredTag.ftcPose.bearing/**Math.toDegrees(Math.toRadians(convertedHeading)-Math.toRadians(180))**/;
            double  yawError        = desiredTag.ftcPose.yaw;


            // Use the speed and turn "gains" to calculate how we want the robot to move.
            //FORWARD power or YPow
            out[1]=-Range.clip(rangeError * Constants.DriveConstants.APRIL_TAG_SPEED_GAIN, -Constants.DriveConstants.MAX_AUTO_SPEED, Constants.DriveConstants.MAX_AUTO_SPEED);
            //strafe power or xPow
            out[0]=Range.clip(-yawError * Constants.DriveConstants.APRIL_TAG_STRAFE_GAIN, -Constants.DriveConstants.MAX_AUTO_STRAFE, Constants.DriveConstants.MAX_AUTO_STRAFE);

           //TURN Power
            out[2]=- Range.clip(headingError * Constants.DriveConstants.APRIL_TAG_TURN_GAIN, -Constants.DriveConstants.MAX_AUTO_TURN, Constants.DriveConstants.MAX_AUTO_SPEED) ;


            if((Math.abs(out[0]) <=.15 && Math.abs(out[2])<=.15 && Math.abs(out[1])<=.15))atTarget =true;
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
        if(startStopThread != null){
            try {
                startStopThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

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

    public static Pose2d getRobotPoseEstimateAprilTag(int id, ColorfulTelemetry pen) {
        AprilTagDetection desiredTag = getAprilTagInfo(id, pen);
        Vector2d aprilTag = getAprilTagPose(id);
        double  rangeError      = (desiredTag.ftcPose.range - Constants.DriveConstants.DESIRED_DISTANCE);
        double  headingError    = desiredTag.ftcPose.bearing/**Math.toDegrees(Math.toRadians(convertedHeading)-Math.toRadians(180))**/;
        double  yawError        = desiredTag.ftcPose.yaw;

        //TODO make sure the error variables are adjusting the correct axis and in the right direction
        double estimatedX = aprilTag.x+rangeError;
        double estimatedY = aprilTag.y+yawError;

        //This next line is sorta confusing. If I interpreted headingError correctly, it's how far
        //off from facing the april tag straight on we are, which would be when heading is 180.
        double estimatedHeading = Math.toRadians(180)+headingError;
        return new Pose2d(estimatedX, estimatedY, estimatedHeading);
    }

    public static Vector2d getAprilTagPose(int id)
    {
        if(id==1)
        {
            return Constants.VisionConstants.APRIL_TAG_ONE;
        }
        else if(id==2)
        {
            return Constants.VisionConstants.APRIL_TAG_TWO;
        }
        else if(id==3)
        {
            return Constants.VisionConstants.APRIL_TAG_THREE;
        }
        else if(id==4)
        {
            return Constants.VisionConstants.APRIL_TAG_FOUR;
        }
        else if(id==5)
        {
            return Constants.VisionConstants.APRIL_TAG_FIVE;
        }
        else if(id==6)
        {
            return Constants.VisionConstants.APRIL_TAG_SIX;
        }
        return null;
    }
}
