package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Intake implements SubsystemBase{

    public static final int INTAKE = 1;
    public static final int OUTTAKE = -1;
    public static final int REST = 0;
    public DcMotor intake;
    public Intake(HardwareMap hwMap){
        intake = hwMap.dcMotor.get("intake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setMode(int mode){
        if(mode == INTAKE)intake.setPower(Constants.IntakeConstants.intakePower);
        else if(mode == OUTTAKE)intake.setPower(-Constants.IntakeConstants.intakePower);
        else if(mode == REST)intake.setPower(0);
    }
    public void setPower(double power){
        intake.setPower(power);
    }


    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
