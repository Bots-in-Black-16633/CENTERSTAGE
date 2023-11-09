package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Climber implements SubsystemBase{

    public DcMotor climber;
    public CRServo leftClimberServo;
    public CRServo rightClimberServo;

    public static final int RAISE = 0;
    public static final int LOWER = 1;
    public static final int REST = 2;
    public static final int CLIMB = 3;
    public static final int UNCLIMB = 4;
    public Climber(HardwareMap hwMap){
        climber = hwMap.dcMotor.get("climber");
        leftClimberServo = hwMap.crservo.get("leftClimberServo");
        rightClimberServo = hwMap.crservo.get("rightClimberServo");
        climber.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        climber.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    public void setMode(int mode){
        if(mode == RAISE){leftClimberServo.setPower(Constants.ClimberConstants.climberPower);rightClimberServo.setPower(Constants.ClimberConstants.climberPower);}
        else if(mode == LOWER) {leftClimberServo.setPower(-Constants.ClimberConstants.climberPower);rightClimberServo.setPower(-Constants.ClimberConstants.climberPower);}
        else if(mode == REST) {climber.setPower(0);leftClimberServo.setPower(0);rightClimberServo.setPower(0);}
        else if(mode == CLIMB){
            climber.setPower(Constants.ClimberConstants.climberPower);
        }
        else if(mode == UNCLIMB){
            climber.setPower(-Constants.ClimberConstants.climberPower);
        }
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
