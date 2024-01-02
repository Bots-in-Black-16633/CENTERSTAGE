package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.arcrobotics.ftclib.util.InterpLUT;

import org.checkerframework.checker.units.qual.C;
import org.opencv.core.Scalar;

public final class Constants{
    @Config

    public static final class DriveConstants{
        public static IMU.Parameters imuParam = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.UP));
        public static  double APRIL_TAG_SPEED_GAIN  =  0.022  ;   //  Forward Speed Control "Gain". eg: Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
        public static  double APRIL_TAG_STRAFE_GAIN =  0.015 ;   //  Strafe Speed Control "Gain".  eg: Ramp up to 25% power at a 25 degree Yaw error.   (0.25 / 25.0)
        public static  double APRIL_TAG_TURN_GAIN   =  0.01;   //  Turn Control "Gain".  eg: Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)
        public static double MAX_AUTO_SPEED = 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
        public static double MAX_AUTO_STRAFE= 0.5;   //  Clip the approach speed to this max value (adjust for your robot)
        public static double MAX_AUTO_TURN  = 0.3;   //  Clip the turn speed to this max value (adjust for your robot)

        public static double DESIRED_DISTANCE = 1.0;

    }
    @Config
    public static final class SliderConstants{

        public static  double kP = .1;
        public static  double sliderTolerance=20;
        public static  double sliderPower = 1;
        public static int sliderMaxPosition = 3000;
        public static int sliderMinPosition = 0;

        public static double sliderRest = 0;
        public static double sliderTraveling = 150;
        public static double sliderOuttake = 1080;//outtke row1
        public static double sliderOuttakeHigh = 2360;//outtke row1

        public static double sliderSafeBackToIntake = 300;

        public static double sliderSafeBackToOuttake = 728;

        public static double backdropRowConstant = 200;

        public static double sliderDistanceDeposit = 800;//low distanc

        public static double sliderOuttakeMid = 1300;
    }
    @Config
    public static final class ShoulderConstants{
        public static double shoulderMin = 0;
        public static double shoulderMax = 1;

        public static double shoulderRest = .13;
        public static double shoulderTraveling = .11;
        public static double shoulderOuttake = .355;//.444

        public static double shoulderOuttakeHigh = .355;

        public static double shoulderSafeBackToIntake = .09;

        public static double shoulderSafeBackToOuttake = .104;
        public static double shoulderDistanceDeposit = .477;
    }
    @Config
    public static final class WristConstants{
        public static double wristMax =1;
        public static double wristMin = 0;

        public static double wristRest = .621;//.621
        public static double wristTraveling = .821; //+.2
        public static double wristOuttake = .606;//low outtae - .015
        public static double wristOuttakeHigh = .606;//low outtae - .015

        public static double wristSafeBackToIntake = .791; //+.17

        public static double wristSafeBackToOuttake = .801;//+.18
        public static double wristDistanceDeposit = .585;//-.036
        public static double wristAdjustingPosition = .291;
    }
    @Config
    public static final class IntakeConstants{
        public static double intakePower = 1;

        public static int autoStackIntakeTimeout=2;
    }@Config
    public static final class HopperConstants{
        public static double hopperPower = 1;

    }@Config
    public static final class ClimberConstants{
        public static double climberPower = 1;

    }@Config
    public static final class ShooterConstants{
        public static double shooterSpeed =-1;
        public static double kickerSpeed =1;

        //TODO add these values
        //Put speed that worked first in a pair of points, then the distance
        public static double [][] testedDistances = new double[][] {};

    }
    @Config
    public static final class LinkageConstants{
        public static double linkageLeftUp = .64;
        public static double linkageLeftDown = .44;

        public static double linkageRightUp = .37;
        public static double linkageRightDown = .56;

        //TODO tune these positions

        //LINKAGE 1 POSITIONS
        public static double  linkageLeftPixelOne = LinkageConstants.linkageLeftDown;
        public static double  linkageLeftPixelTwo = .51;
        public static double linkageLeftPixelThree = .54;
        public static double linkageLeftPixelFour = .59;
        public static double linkageLeftPixelFive = LinkageConstants.linkageLeftUp;

        //LINKAGE 2 POSITIONS
        public static double  linkageRightPixelOne = LinkageConstants.linkageRightDown;
        public static double  linkageRightPixelTwo = .55;
        public static double linkageRightPixelThree = .48;
        public static double linkageRightPixelFour = .43;
        public static double linkageRightPixelFive = LinkageConstants.linkageRightUp;
    }
    @Config
    public static final class ColorSensorWrapperConstants{
        public static Scalar emptyHopperHSV = new Scalar(.08,.1,.13);

        public static Scalar purplePixelHSV = new Scalar(0,0,0);
        public static Scalar greenPixelHSV = new Scalar(0,0,0);
        public static Scalar yellowPixelHSV = new Scalar(0,0,0);
        public static Scalar whitePixelHSV = new Scalar(0,0,0);
        public static double maxDistanceFromEmptyHopperColor = 1;
        public enum Pixel{
             GREEN, PURPLE, YELLOW, NONE, WHITE;
        }
    }
    public static final class VisionConstants {
        public final static Vector2d APRIL_TAG_ONE = new Vector2d(60, 41.4);
        public final static Vector2d APRIL_TAG_TWO = new Vector2d(60, 35.25);
        public final static Vector2d APRIL_TAG_THREE = new Vector2d(60, 29);
        public final static Vector2d APRIL_TAG_FOUR = new Vector2d(60, -29);
        public final static Vector2d APRIL_TAG_FIVE = new Vector2d(60, -35.25);
        public final static Vector2d APRIL_TAG_SIX = new Vector2d(60, -41.4);
        public final static Vector2d APRIL_TAG_SEVEN = new Vector2d(-72, -40);
        public final static Vector2d APRIL_TAG_EIGHT = new Vector2d(-72, -36);
        public final static Vector2d APRIL_TAG_NINE = new Vector2d(-72, 36);
        public final static Vector2d APRIL_TAG_TEN = new Vector2d(-72, 40);



        public static long partitionExposure = 30;
        public static int partitionGain = 0;

        public static long aprilTagExposure = 5;
        public static int aprilTagGain = 0;

        //TODO tune these
        public static double aprilTagPositionWeight = 1;
        public static double aprilTagHeadingWeight = 1;
    }


}