package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.GyroEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveKinematics;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveOdometry;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Drive extends SubsystemBase{

    MecanumDrive mecDrive;
    MecanumDriveOdometry odometry;
    Motor leftRear;
    Motor leftFront;
    Motor rightRear;
    Motor rightFront;

    RevIMU imu;

    public Drive(HardwareMap hwMap){
        super("MecanumDrive", hwMap);
        leftRear = new Motor(hwMap, "leftRear", Motor.GoBILDA.RPM_312);
        leftFront = new Motor(hwMap, "leftFront", Motor.GoBILDA.RPM_312);
        rightRear = new Motor(hwMap, "rightRear", Motor.GoBILDA.RPM_312);
        rightFront = new Motor(hwMap, "rightFront", Motor.GoBILDA.RPM_312);
        mecDrive = new MecanumDrive(leftFront, rightFront, leftRear, rightRear);
        imu = new RevIMU(hwMap, "imu");

    }

    /**
     * sets the drive, strafe, and rotational powers of the robot
     * @param forward
     * @param strafe
     * @param rotational
     * @param fieldcentric
     */
    public void drive(double forward, double strafe, double rotational, boolean fieldcentric){

    }

    public void freeze(){
        drive(0,0,0,true);
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
