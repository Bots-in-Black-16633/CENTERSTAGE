package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Climber implements SubsystemBase{

    public DcMotor climber;
    public CRServo leftClimberServo;
    public CRServo rightClimberServo;

    public static final int RAISE = 0;
    public static final int LOWER = 1;
    public static final int REST = 2;
    public Climber(HardwareMap hwMap){
        climber = hwMap.dcMotor.get("climber");
        leftClimberServo = hwMap.crservo.get("leftClimberServo");
        rightClimberServo = hwMap.crservo.get("rightClimberServo");
        climber.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    public void setMode(int mode){
        if(mode == RAISE){climber.setPower(Constants.ClimberConstants.climberPower);leftClimberServo.setPower(Constants.ClimberConstants.climberPower);rightClimberServo.setPower(Constants.ClimberConstants.climberPower);}
        else if(mode == LOWER) {climber.setPower(-Constants.ClimberConstants.climberPower);leftClimberServo.setPower(-Constants.ClimberConstants.climberPower);rightClimberServo.setPower(-Constants.ClimberConstants.climberPower);}
        else if(mode == REST) {climber.setPower(0);leftClimberServo.setPower(0);rightClimberServo.setPower(0);}
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
