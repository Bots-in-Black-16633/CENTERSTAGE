package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.hardware.motors.CRServo;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.teamcode.auto.util.AutoUtil;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Hopper implements SubsystemBase{

    public CRServo leftHopper;
    public CRServo rightHopper;

    public ColorSensorWrapper leftHopperSensor;
    public ColorSensorWrapper rightHopperSensor;

    public static final int RIGHT_HOPPER = 1;
    public static final int LEFT_HOPPER =2;
    public static final int ALL = 0;

    boolean leftLocked = false;
    boolean rightLocked = false;

    public Hopper(HardwareMap hwMap){
        leftHopper = new CRServo(hwMap, "leftHopper");
        rightHopper = new CRServo(hwMap, "rightHopper");

        leftHopperSensor = new ColorSensorWrapper("leftHopperSensor", hwMap);
        rightHopperSensor = new ColorSensorWrapper("rightHopperSensor", hwMap);
        rightHopper.setInverted(true);
    }

   public class HopperOuttake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            outtake(Hopper.ALL);
            AutoUtil.delay(1);
            rest(Hopper.ALL);
            return false;
        }
    }


    public Action hopperOutake(){return new HopperOuttake();}


    public boolean hoppersFull(){
        return leftHopperSensor.pixelPresent() && rightHopperSensor.pixelPresent();
    }



    public void intake(int type){
        setPower(type, Constants.HopperConstants.hopperPower);
    }

    public void outtake(int type){
        setPower(type, -Constants.HopperConstants.hopperPower);
    }
    public void setPower(int type, double power){
        if(!leftLocked && (type == 0 || type == 1))leftHopper.set(power);
        if(!rightLocked && (type == 0 || type == 2))rightHopper.set(power);
    }
    public void lock(int type){
        if(type == 0 || type == 1)leftLocked = true;
        if(type == 0 || type == 2)rightLocked = true;
    }
    public void unLock(int type){
        if(type == 0 || type == 1)leftLocked = false;
        if(type == 0 || type == 2)rightLocked = false;
    }

    public boolean isLocked(int type){
        if(type == 1)return leftLocked;
        if(type == 2)return rightLocked;
        else return leftLocked && rightLocked;

    }



    public void rest(int type){
        setPower(type, 0);
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        t.addLine();
        t.addLine("____HOPPER_____");
        //t.addLine("Left Hopper Port: " + leftHopper.motor.getPortNumber());
        //t.addLine("Right Hopper Port" + rightHopper.motor.getPortNumber());
        t.addLine("LEFT HOPPER: " + leftHopperSensor.pixelPresent());
        t.addLine("RIGHT HOPPER: " + rightHopperSensor.pixelPresent());

        t.addLine("LEFT Sensor: " + leftHopperSensor.toString());
        t.addLine("RIGHT Sensor" + rightHopperSensor.toString());
    }

    @Override
    public void periodic() {

    }
}
