package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Slider implements SubsystemBase{

    DcMotor leader;
    DcMotor follower;
    MotorGroup slider;

    public Slider(HardwareMap hwMap){
        leader = hwMap.dcMotor.get("slider1");
        follower = hwMap.dcMotor.get("slider2");
        follower.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    public void runToPosition(double pos){
        if(pos > Constants.SliderConstants.sliderMaxPosition)pos = Constants.SliderConstants.sliderMaxPosition;
        if(pos < Constants.SliderConstants.sliderMinPosition)pos = Constants.SliderConstants.sliderMinPosition;
        leader.setTargetPosition((int)pos);
        follower.setTargetPosition((int)pos);
        leader.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        follower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leader.setPower(1);
        follower.setPower(1);

    }

    public void reset(){
        leader.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        follower.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leader.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        follower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void set(double power){
        follower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leader.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leader.setPower(power);
        follower.setPower(power);
    }
    public int getPosition(){
        return leader.getCurrentPosition();
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        t.addLine("LEFT SLIDER (LEADER): " + leader.getCurrentPosition());
        t.addLine("RIGHT SLIDER (FOLLOWER)" + follower.getCurrentPosition());

    }

    @Override
    public void periodic() {

    }
}
