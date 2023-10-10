package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

public class Drive extends SubsystemBase{

    MecanumDrive drive;
    Motor leftRear;
    Motor leftFront;
    Motor rightRear;
    Motor rightFront;

    public Drive(HardwareMap hwMap){
        super("MecanumDrive", hwMap);
        leftRear = new Motor(hwMap, "leftRear", Motor.GoBILDA.RPM_312);
        leftFront = new Motor(hwMap, "leftFront", Motor.GoBILDA.RPM_312);
        rightRear = new Motor(hwMap, "rightRear", Motor.GoBILDA.RPM_312);
        rightFront = new Motor(hwMap, "rightFront", Motor.GoBILDA.RPM_312);
        drive = new MecanumDrive(leftFront, rightFront, leftRear, rightRear);

    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }
}
