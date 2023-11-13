package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class AprilTagProcessorWrapper {
    static AprilTagProcessor atp;
    static VisionPortal vp;


    public static void startAprilTagDetection(WebcamName camera){
        atp = new AprilTagProcessor.Builder().build();
        atp.setDecimation(2);
        vp = new VisionPortal.Builder()
                .setCamera(camera)
                .addProcessor(atp)
                .build();
    }

    public static AprilTagDetection getAprilTagInfo(int id){
        return atp.getDetections().stream().filter(tag->tag.id==id).findFirst().orElse(null);
    }
    public static void endAprilTagDetection(){
        vp.close();
    }

}
