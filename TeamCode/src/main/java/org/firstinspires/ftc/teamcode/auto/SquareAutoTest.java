package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.drive.Drive;
import org.firstinspires.ftc.teamcode.util.SampleAuto;
@Autonomous
public class SquareAutoTest extends SampleAuto {
    Drive drive;
    Action squareTest;
    FtcDashboard dash;

    @Override
    public void onInit() {
        drive = new Drive(hardwareMap, new Pose2d(10.50, 67.80, Math.toRadians(267.80)));
        squareTest = drive.actionBuilder(drive.pose).splineTo(new Vector2d(27.31, 16.23), Math.toRadians(0.00)).splineTo(new Vector2d(36.67, 67.61), Math.toRadians(76.02)).build();
        dash = FtcDashboard.getInstance();
    }

    @Override
    public void onStart() {
        squareTest.run( new TelemetryPacket());

    }

    @Override
    public void onLoop() {

    }

    @Override
    public void onStop() {

    }
}
