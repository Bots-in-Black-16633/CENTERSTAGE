package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.hardware.motors.CRServo;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Hopper implements SubsystemBase{

    CRServo leftHopper;
    CRServo rightHopper;

    public static final int RIGHT_HOPPER = 1;
    public static final int LEFT_HOPPER =2;
    public static final int ALL = 0;

    public Hopper(HardwareMap hwMap){
        leftHopper = new CRServo(hwMap, "leftHopper");
        rightHopper = new CRServo(hwMap, "rightHopper");
        leftHopper.setInverted(true);
    }

    public void intake(int type){
        if(type ==1 || type ==0)leftHopper.set(Constants.HopperConstants.hopperPower);
        if(type == 2 || type ==0)rightHopper.set(Constants.HopperConstants.hopperPower);
    }

    public void outtake(int type){
       if(type == 0 || type == 1)leftHopper.set(-Constants.HopperConstants.hopperPower);
       if(type == 0 || type == 2)rightHopper.set(-Constants.HopperConstants.hopperPower);
    }
    public void setPower(int type, double power){
        if(type == 0 || type == 1)leftHopper.set(power);
        if(type == 0 || type == 2)rightHopper.set(power);
    }


    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        t.addLine("LEFT HOPPER: " + leftHopper.toString());
        t.addLine("RIGHT HOPPER: " + rightHopper.toString());
    }

    @Override
    public void periodic() {

    }
}
