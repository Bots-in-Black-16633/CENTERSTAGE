package org.firstinspires.ftc.teamcode.auto.util;



import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.vision.TeamPropPartitionDetector;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;



@TeleOp(name="CameraExpGainTester", group = "Concept")
public class CameraExposureGainTester extends LinearOpMode
{


    private VisionPortal visionPortal;               // Used to manage the video source.
    private AprilTagProcessor aprilTag;              // Used for managing the AprilTag detection process.
    private TeamPropPartitionDetector partitionDetector;

    public ColorfulTelemetry pen;


    private int exposureTime = 0 ;
    private int gain = 0;

    public GamepadEx g1;
    @Override public void runOpMode()
    {
       pen = new ColorfulTelemetry(telemetry, FtcDashboard.getInstance());
        g1 = new GamepadEx(gamepad1);


        // Initialize the Apriltag Detection process
        initAprilTagAndPartition();

        exposureTime = (int)visionPortal.getCameraControl(ExposureControl.class).getExposure(TimeUnit.MILLISECONDS);
        gain = visionPortal.getCameraControl(GainControl.class).getGain();

        // Wait for driver to press start
        pen.addData("Camera preview on/off", "3 dots, Camera Stream");
        pen.addData(">", "Touch Play to start OpMode");
        pen.update();
        waitForStart();

        while (opModeIsActive())
        {

            if(g1.wasJustPressed(GamepadKeys.Button.DPAD_UP)){
                exposureTime +=1;
                setManualExposure(exposureTime,gain);
            }
            if(g1.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){
                exposureTime-=1;
                setManualExposure(exposureTime,gain);
            }
            if(g1.wasJustPressed(GamepadKeys.Button.Y)){
                gain+=1;
                setManualExposure(exposureTime,gain);
            }
            if(g1.wasJustPressed(GamepadKeys.Button.A)){
                gain-=1;
                setManualExposure(exposureTime,gain);

            }
            if(g1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
                exposureTime-=5;
                setManualExposure(exposureTime,gain);

            }
            if(g1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)){
                exposureTime += 5;
                setManualExposure(exposureTime,gain);
            }

            pen.addLine("EXPOSURE:" + exposureTime);
            pen.addLine("GAIN: " + gain);
            telemetryAprilTag();



          g1.readButtons();
        }
    }

    private void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
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
        }

        // Add "key" information to pen
        pen.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        pen.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        pen.addLine("RBE = Range, Bearing & Elevation");


        telemetry.addLine("leftFrame: " + partitionDetector.blueLeftFrame);
        telemetry.addLine("MidFrame:" + partitionDetector.blueMidFrame);
        telemetry.addLine("RightFrame:" + partitionDetector.blueRightFrame);
        telemetry.addLine("leftFrame: " + partitionDetector.redLeftFrame);
        telemetry.addLine("MidFrame:" + partitionDetector.redMidFrame);
        telemetry.addLine("RightFrame:" + partitionDetector.redRightFrame);

        telemetry.addLine();
        telemetry.addLine("Blue Zone: " + TeamPropPartitionDetector.getBluePropZone());
        telemetry.addLine("Red Zone: " + TeamPropPartitionDetector.getRedPropZone());

        telemetry.update();

    }


    /**
     * Initialize the AprilTag processor.
     */
    private void initAprilTagAndPartition() {
        // Create the AprilTag processor by using a builder.
        aprilTag = new AprilTagProcessor.Builder().build();
        partitionDetector = new TeamPropPartitionDetector(pen);

        // Adjust Image Decimation to trade-off detection-range for detection-rate.
        // eg: Some typical detection data using a Logitech C920 WebCam
        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second
        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second
        // Note: Decimation can be changed on-the-fly to adapt during a match.
        aprilTag.setDecimation(2);

        // Create the vision portal by using a builder.
            visionPortal = new VisionPortal.Builder()
                    .setCamera(hardwareMap.get(WebcamName.class, "camera"))
                    .addProcessor(aprilTag)
                    .addProcessor(partitionDetector)
                    .build();
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            pen.addData("Camera", "Waiting");
            pen.update();
            while (!isStopRequested() && (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                sleep(20);
            }
            pen.addData("Camera", "Ready");
            pen.update();
        }


    }



    /*
     Manually set the camera gain and exposure.
     This can only be called AFTER calling initAprilTag(), and only works for Webcams;
    */
    private void setManualExposure(int exposureMS, int gain) {
        // Wait for the camera to be open, then use the controls

        if (visionPortal == null) {
            return;
        }

        // Make sure camera is streaming before we try to set the exposure controls
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            pen.addData("Camera", "Waiting");
            pen.update();
            while (!isStopRequested() && (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                sleep(20);
            }
            pen.addData("Camera", "Ready");
            pen.update();
        }

        // Set camera controls unless we are stopping.
        if (!isStopRequested())
        {
            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                sleep(50);
            }
            exposureControl.setExposure((long)exposureMS, TimeUnit.MILLISECONDS);
            sleep(20);
            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            sleep(20);
        }
    }
}