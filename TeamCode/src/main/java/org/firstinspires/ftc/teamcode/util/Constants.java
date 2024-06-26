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

        public static double sliderOuttakeMid = 1365;
    }
    @Config
    public static final class ShoulderConstants{
        public static double shoulderMin = 0;
        public static double shoulderMax = 1;

        public static double shoulderRest = .224;
        public static double shoulderTraveling = .11;
        public static double shoulderOuttake = .44;//.305

        public static double shoulderOuttakeHigh = .4073;

        public static double shoulderSafeBackToIntake = .163;

        public static double shoulderSafeBackToOuttake = .104;
        public static double shoulderDistanceDeposit = .53;
        public static double shoulderPixelAdjusterLow= .625;
    }
    @Config
    public static final class WristConstants{
        public static double wristMax =1;
        public static double wristMin = 0;

        public static double wristRest = .544;//.498;//.621
        public static double wristTraveling = .67; //+.2
        public static double wristOuttake = .500;//.411;//rest - .33
        public static double wristOuttakeHigh = .479;//.4061;//rest - .09

        //update our hardare, turn down brightness

        public static double wristSafeBackToIntake = .544+.17;//.82; //+.17

        public static double wristSafeBackToOuttake = .544+.18;//.65;//.+.18
        public static double wristDistanceDeposit = .484;//.400;//-.036
        public static double wristAdjustingPositionLow = .442;//.363;//-.33
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
        public static double shooterSpeed =-.5;//constants -.43
        public static double kickerSpeed =1;

        //TODO add these values
        //Put speed that worked first in a pair of points, then the voltage
        public static double [][] testedVoltages = new double[][] {{12.27,-.39},{13.58,-.36}};

    }
    @Config
    public static final class GateConstant{
        public static double gateOpen =-0;
        public static double gateClose =1;



    }
    @Config
    public static final class LinkageConstants{



        //TODO tune these positions

        //LINKAGE 1 POSITIONS
        public static double  linkagePixelOne =.1;
        public static double  linkagePixelTwo = .28;
        public static double linkagePixelThree = .46;
        public static double linkagePixelFour = .6;
        public static double linkagePixelFive = .76;


        //New Linkage Positions

        //Right Linkag

        public static double rightLinkageUp = .87;
        public static double rightLinkageDown = .367;

        public static double leftLinkageUp = .42;
        public static double leftLinkageDown = .79;
    }
    @Config
    public static final class ColorSensorWrapperConstants{
        public static Scalar emptyHopperHSV = new Scalar(0,0,0);

        public static Scalar purplePixelHSV = new Scalar(210,.5,0);
        public static Scalar greenPixelHSV = new Scalar(120,.6,0);
        public static Scalar yellowPixelHSV = new Scalar(90,.6,0);
        public static Scalar whitePixelHSV = new Scalar(160,.4,0);

        public static Scalar emptyLeftHSV = new Scalar(0,0,0);

        public static Scalar purpleLeftHSV = new Scalar(210,.5,0);
        public static Scalar greenLeftHSV = new Scalar(120,.6,0);
        public static Scalar yellowLeftHSV = new Scalar(90,.6,0);
        public static Scalar whiteLeftHSV = new Scalar(160,.4,0);


        public static Scalar emptyRightHSV = new Scalar(0,0,0);

        public static Scalar purpleRightHSV = new Scalar(210,.5,0);
        public static Scalar greenRightHSV = new Scalar(120,.6,0);
        public static Scalar yellowRightHSV = new Scalar(90,.6,0);
        public static Scalar whiteRightHSV = new Scalar(160,.4,0);


        public static Scalar emptyHopperRGB = new Scalar(0.08392,.14801252,.155642);
        public static Scalar purplePixelRGB = new Scalar(.91554,1.6449,1.2573);
        public static Scalar greenPixelRGB = new Scalar(.58747,.74464,1.471);
        public static Scalar yellowPixelRGB = new Scalar(1.04219,.500495,1.54726);
        public static Scalar whitePixelRGB = new Scalar(1.8219,3.1006,3.31884);


        public static Scalar leftEmptyHopperRGB = new Scalar(.07935, .1358, .1465);
        public static Scalar rightEmptyHopperRGB = new Scalar(.14191, .20752, .2411);
        public static Scalar leftGreenRGB = new Scalar(.2121, .3235, .622568);
        public static Scalar rightGreenRGB = new Scalar(.48676, .61952, 1.268);

        public static Scalar leftPurpleRGB = new Scalar(.4715, 1.03, .7614);
        public static Scalar rightPurpleRGB = new Scalar(1.05, 1.9867, 1.4496);
        public static Scalar leftYellowRGB = new Scalar(.4715, 1.03, .7614);//untuned
        public static Scalar rightYellowRGB = new Scalar(1.05, 1.9867, 1.4496);//untuned
        public static Scalar leftWhiteRGB = new Scalar(.4715, 1.03, .7614);//untunes
        public static Scalar rightWhiteRGB = new Scalar(1.05, 1.9867, 1.4496);//untuned

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