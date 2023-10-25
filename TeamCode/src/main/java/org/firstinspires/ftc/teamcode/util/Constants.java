package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.geometry.Translation2d;

public final class Constants{
    @Config

    public static final class DriveConstants{
        //Drive Wheel Positions
        // Locations of the wheels relative to the robot center.
        public static final Translation2d frontLeftLocation = new Translation2d(0.381, 0.381);
        public static final Translation2d frontRightLocation = new Translation2d(0.381, -0.381);
        public static final Translation2d backLeftLocation = new Translation2d(-0.381, 0.381);
        public Translation2d backRightLocation = new Translation2d(-0.381, -0.381);
    }
    @Config
    public static final class SliderConstants{

        public static final double kP = .5;
        public static final double sliderPower = .5;
        public static final int sliderMaxPosition = 3000;
        public static final int sliderMinPosition = 0;
        public static final int sliderGround = 0;
    }
    @Config
    public static final class ShoulderConstants{

    }@Config
    public static final class IntakeConstants{

    }@Config
    public static final class HopperConstants{

    }@Config
    public static final class ClimberConstants{

    }@Config
    public static final class ShooterConstants{

    }
}