package org.firstinspires.ftc.teamcode.subsystems.drive;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

public class Drive extends MecanumDrive implements SubsystemBase {

    ColorfulTelemetry t = null;

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
        if(Math.abs(xPow)<.05 && Math.abs(yPow)<.05) {setDrivePowers(new PoseVelocity2d(new Vector2d(0,0),rotPow*speed)); return;}

        double targetTheta = Math.atan2(Math.toRadians(yPow), Math.toRadians(xPow));
        double robotTheta = Math.toRadians(pose.heading.log());
        double diffTheta = Math.toDegrees(targetTheta)- Math.toDegrees(robotTheta);
        if(t!=null)t.addLine("Target " + Math.toDegrees(targetTheta) + " Robot " + Math.toDegrees(robotTheta) + " Difference " + diffTheta);
        xPow = Math.cos(Math.toRadians(diffTheta))*speed;
        yPow = Math.sin(Math.toRadians(diffTheta))*speed;
        rotPow = rotPow*speed;
        if(t !=null){
            t.addLine("XPOW: " + xPow + "YPOW: " + yPow + "rotPow" + rotPow);
        }

        setDrivePowers(new PoseVelocity2d(new Vector2d(xPow, yPow), rotPow));

    }


    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        this.t = t;
        t.addLine("DRIVE TELEMETRY");
        t.addLine("POSITION: " + pose.toString());
        t.addLine("Yaw" + imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        t.addLine("Pitch" + imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES));
        t.addLine("Roll" + imu.getRobotYawPitchRollAngles().getRoll(AngleUnit.DEGREES));


    }

    @Override
    public void periodic() {
        updatePoseEstimate();


    }
}
