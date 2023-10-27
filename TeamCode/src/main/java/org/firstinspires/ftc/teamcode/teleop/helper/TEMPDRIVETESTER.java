package org.firstinspires.ftc.teamcode.teleop.helper;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;

@TeleOp(name = "TEMPORARYDRIVETESTER", group = "helper")
public class TEMPDRIVETESTER extends LinearOpMode {
    MecanumDrive drive;
    public static Pose2d startPos = new Pose2d(0,0,0);
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new MecanumDrive(hardwareMap, startPos);

        waitForStart();

        while(!isStopRequested() && opModeIsActive()){
            double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            drive.leftFront.setPower(y + x + rx);
            drive.leftBack.setPower(y - x + rx);
            drive.rightFront.setPower(y - x - rx);
            drive.rightBack.setPower(y + x - rx);
        }
    }
    private double round(double t){
        return ((int)t*100)/100.0;
    }

}