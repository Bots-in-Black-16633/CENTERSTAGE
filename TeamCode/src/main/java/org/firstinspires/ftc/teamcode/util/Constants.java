package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;

public final class Constants{
    @Config

    public static final class DriveConstants{
        //Drive Wheel Positions
        // Locations of the wheels relative to the robot center.
        public static Translation2d frontLeftLocation = new Translation2d(0.381, 0.381);
        public static Translation2d frontRightLocation = new Translation2d(0.381, -0.381);
        public static Translation2d backLeftLocation = new Translation2d(-0.381, 0.381);
        public Translation2d backRightLocation = new Translation2d(-0.381, -0.381);

        public IMU.Parameters imuParam = new IMU.Parameters( new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
    }
    @Config
    public static final class SliderConstants{

        public static  double kP = .1;
        public static  double sliderTolerance=20;
        public static  double sliderPower = .5;
        public static int sliderMaxPosition = 3000;
        public static int sliderMinPosition = 0;
        public static int sliderGround = 0;
    }
    @Config
    public static final class ShoulderConstants{

    }@Config
    public static final class IntakeConstants{

    }@Config
    public static final class HopperConstants{
        public static double hopperPower = .5;

    }@Config
    public static final class ClimberConstants{

    }@Config
    public static final class ShooterConstants{

    }
}