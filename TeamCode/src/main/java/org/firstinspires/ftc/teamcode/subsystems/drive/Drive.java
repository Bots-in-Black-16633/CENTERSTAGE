package org.firstinspires.ftc.teamcode.subsystems.drive;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.subsystems.BaseRobot;
import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.vision.AprilTagProcessorWrapper;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class Drive extends MecanumDrive implements SubsystemBase {

    ColorfulTelemetry t = null;
    ElapsedTime timer = null;
//    double headingOffset = 0;
//    double initialHeading = 0;


     static ElapsedTime alignTimer = null;



    public Drive(HardwareMap hardwareMap, Pose2d startPose) {

        super(hardwareMap, startPose);
        imu.resetYaw();
        initialHeading = Math.toDegrees(startPose.heading.log());
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
        double robotTheta = getHeading();
        double diffTheta = Math.toDegrees(targetTheta)- Math.toDegrees(robotTheta);
        if(t!=null)t.addLine("Target " + Math.toDegrees(targetTheta) + " Robot " + Math.toDegrees(robotTheta) + " Difference " + diffTheta);
        xPow = Math.cos(Math.toRadians(diffTheta))*speed;
        yPow = Math.sin(Math.toRadians(diffTheta))*speed;
        rotPow = rotPow*speed;
        rotPow = rotPow*speed;
        if(t !=null){
            t.addLine("XPOW: " + xPow);
            t.addLine("YPOW" + yPow);
            t.addLine("ROT POW" + rotPow);
            t.addLine("DIFF " + Math.toDegrees(diffTheta));
        }

        setDrivePowers(new PoseVelocity2d(new Vector2d(xPow, yPow), rotPow));
        updatePoseEstimate();




    }

    public void drive(double xPow, double yPow, double rotPow){


        double leftFrontPower    =  yPow +xPow +rotPow;
        double rightFrontPower   =  yPow -xPow -rotPow;
        double leftBackPower     =  yPow -xPow +rotPow;
        double rightBackPower    =  yPow +xPow -rotPow;
        leftBack.setPower(leftBackPower);
        leftFront.setPower(leftFrontPower);
        rightBack.setPower(rightBackPower);
        rightFront.setPower(rightFrontPower);
//        setDrivePowers(new PoseVelocity2d(new Vector2d(yPow,xPow),rotPow));
       updatePoseEstimate();

    }
    class SlowBackwards implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            backward(1.5,.5);
            return false;
        }
    }
    class DriveToAprilTag implements Action {
        int id = 0;
        AprilTagDetection tag;
        WebcamName camera;
        ColorfulTelemetry pen;
        Drive drive;
        ElapsedTime notSeenTimer = null;
        boolean closeCameraWhenDone = false;
        public DriveToAprilTag(int color, int zone, WebcamName camera, ColorfulTelemetry pen, Drive drive, boolean closeCameraWhenDone){
            this.camera = camera;
            this.pen = pen;
            this.drive = drive;
            this.closeCameraWhenDone = closeCameraWhenDone;
            if(color == AutoUtil.BLUE){
                if(zone==1)id=1;
                else if(zone==2)id=2;
                else id=3;
            }
            else if(color == AutoUtil.RED){
                if(zone==1)id=4;
                else if(zone==2)id=5;
                else id=6;
            }
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            AprilTagProcessorWrapper.startAprilTagDetection(camera, pen);
            alignTimer = new ElapsedTime();
            while(true){
                if(AprilTagProcessorWrapper.isAtTarget() || alignTimer.seconds() > 10)break;

                double[] powers = AprilTagProcessorWrapper.getSuggestedPower(id, drive,pen);
                if(powers != null){
                    if(notSeenTimer != null)drive(0,0,0);
                    drive(powers[0],powers[1],powers[2]);
                    pen.addLine("FORWARD: " + powers[1]);
                    pen.addLine("STRAFE: " + powers[0]);
                    pen.addLine("TURN" + powers[2]);
                    pen.addLine("Range Error" + powers[3]);
                    pen.addLine("Heading Error" + powers[4]);
                    pen.addLine("Yaw Error" + powers[5]);
                    pen.addLine("HEading" + Math.toDegrees(getHeading()));
                    pen.update();

                    notSeenTimer = null;
                }
                else{
                    if(notSeenTimer == null)notSeenTimer = new ElapsedTime();
                }
                if(notSeenTimer != null && notSeenTimer.seconds() > 5){//turn in one direction
                    //drive(0,0,.3);
                }
                //pen.addLine("NotSeen" + (notSeenTimer!=null?notSeenTimer.seconds():"Null"));

                drawRobot(pen.getPacket().fieldOverlay(), pose);

            }
            if(closeCameraWhenDone)AprilTagProcessorWrapper.endAprilTagDetection();
            drive(0,0,0);
            pen.addLine("FINISHED ALIGNMENT");
            pen.update();
            Actions.runBlocking(new SlowBackwards());
            return false;
        }
    }

    public DriveToAprilTag driveToAprilTag(int color, int zone, WebcamName camera, ColorfulTelemetry pen, boolean closeCameraWhenDone){return new DriveToAprilTag(color, zone, camera, pen, this, closeCameraWhenDone);}
    public DriveToAprilTag driveToAprilTag(int color, int zone, WebcamName camera, ColorfulTelemetry pen){return new DriveToAprilTag(color, zone, camera, pen, this, true);}



    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        this.t = t;
        t.addLine("DRIVE TELEMETRY");
        t.addLine("XPOS: " + pose.position.x);
        t.addLine("YPOS: " + pose.position.y);
        t.addLine("Initital HEading" + initialHeading);
        t.addLine("HEdaing Offset" + headingOffset);
        t.addLine("ROBOT HEADING" + Math.toDegrees(getHeading()));
        t.addLine("OdOHeading: " +Math.toDegrees(pose.heading.log()));
        t.addLine("IMUHeading: " + imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        t.addLine("perp"+ ((ThreeDeadWheelLocalizer)localizer).perp.getPositionAndVelocity().position);
        t.addLine("par0"+ ((ThreeDeadWheelLocalizer)localizer).par0.getPositionAndVelocity().position);
        t.addLine("par1"+ ((ThreeDeadWheelLocalizer)localizer).par1.getPositionAndVelocity().position);
        drawRobot(t.getPacket().fieldOverlay(), pose);

    }

    @Override
    public void periodic() {
        updatePoseEstimate();

    }





    /**
     * @param motorPowers array containg powers for each of he motors [leftFront, leftBack, rightFront, rightBack]
     * @param time
     */
    public void setPowers(double [] motorPowers, double time){
        timer = new ElapsedTime();
        timer.reset();
        while(timer.seconds()< time){
            leftFront.setPower(motorPowers[0]);
            leftBack.setPower(motorPowers[1]);
            rightFront.setPower(motorPowers[2]);
            rightBack.setPower(motorPowers[3]);
        }
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
    }

    public void forward(double time, double power){
        setPowers(new double[]{power,power,power,power}, time);
    }
    public void backward(double time, double power){
        setPowers(new double[]{-power,-power,-power,-power}, time);
    }
    public void right(double time, double power){
        setPowers(new double[]{power, -power, -power, power}, time);
    }
    public void left(double time, double power){
        setPowers(new double[]{-power, power, power, -power}, time);
    }

    public void turnRight(double time, double power){
        setPowers(new double[]{power, power, -power, -power}, time);
    }
    public void turnLeft(double time, double power){
        setPowers(new double[]{-power, -power, power, power}, time);
    }
    public void resetHeading(){
        headingOffset = Math.toDegrees(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
    }




}
