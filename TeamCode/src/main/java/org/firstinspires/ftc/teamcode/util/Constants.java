package org.firstinspires.ftc.teamcode.util;

import com.arcrobotics.ftclib.geometry.Translation2d;

public final class Constants{
    public static final class ClawConstants{
        //Intake Positions
        public static final double leftClawIntake = 0;
        public static final double rightClawIntake = 0;

        //Outtake Positions
        public static final double leftClawOuttake = 0;
        public static final double rightClawOuttake = 0;

        //Close Positions
        public static final double leftClawClose = 0;
        public static final double rightClawClose  =0;
    }
    public static final class DriveConstants{
        //Drive Wheel Positions
        // Locations of the wheels relative to the robot center.
        Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381);
        Translation2d m_frontRightLocation = new Translation2d(0.381, -0.381);
        Translation2d m_backLeftLocation = new Translation2d(-0.381, 0.381);
        Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381);
    }
}