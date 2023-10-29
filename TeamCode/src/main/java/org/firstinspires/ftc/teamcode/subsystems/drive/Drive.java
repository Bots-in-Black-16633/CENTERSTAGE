package org.firstinspires.ftc.teamcode.subsystems.drive;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

public class Drive extends MecanumDrive implements SubsystemBase {

    public Drive(HardwareMap hardwareMap, Pose2d startPose) {
        super(hardwareMap, startPose);
    }

    /**
     *
     * @param xPow- strafe/right and left movement of robot
     * @param yPow - forward and backward movement o robot
     * @param rotPow - rotational movement of robot
     * @param speed - speed at  which tp run
     */
    public void driveFieldcentric(double xPow, double yPow, double rotPow, double speed){
        double targetTheta = Math.atan2(Math.toRadians(yPow), Math.toRadians(xPow));
        double robotTheta = Math.toRadians(pose.heading.log());
        double diffTheta = Math.toDegrees(targetTheta)- Math.toDegrees(robotTheta);

        xPow = Math.cos(diffTheta)*speed;
        yPow = Math.sin(diffTheta)*speed;
        rotPow = rotPow*speed;

        setDrivePowers(new PoseVelocity2d(new Vector2d(xPow, yPow), rotPow));

    }


    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        t.addLine("DRIVE TELEMETRY");
        t.addLine("POSITION: " + pose.toString());
    }

    @Override
    public void periodic() {

    }
}
