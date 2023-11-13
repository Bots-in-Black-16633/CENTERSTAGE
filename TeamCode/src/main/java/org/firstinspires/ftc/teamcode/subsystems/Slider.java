package org.firstinspires.ftc.teamcode.subsystems;

import androidx.core.math.MathUtils;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Slider implements SubsystemBase{

    DcMotor leftSlider;
    DcMotor rightSlider;

    public Slider(HardwareMap hwMap){
        leftSlider = hwMap.dcMotor.get("leftSlider");
        rightSlider = hwMap.dcMotor.get("rightSlider");
        leftSlider.setDirection(DcMotorSimple.Direction.REVERSE);
        reset();


    }

    public void runToPosition(double pos){
        pos = MathUtils.clamp(pos, Constants.SliderConstants.sliderMinPosition, Constants.SliderConstants.sliderMaxPosition);
        leftSlider.setTargetPosition((int)pos);
        rightSlider.setTargetPosition((int)pos);
        leftSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlider.setPower(1);
        rightSlider.setPower(1);

    }


    public void reset(){
        leftSlider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSlider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightSlider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void set(double power){
        rightSlider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftSlider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftSlider.setPower(power);
        rightSlider.setPower(power);
    }
    public int getPosition(){
        return leftSlider.getCurrentPosition();
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        t.addLine();
        t.addLine("____SLIDER_____");
        t.addLine("LEFT SLIDER : " + leftSlider.getCurrentPosition());
        t.addLine("RIGHT SLIDER (FOLLOWER)" + rightSlider.getCurrentPosition());

    }

    @Override
    public void periodic() {

    }
}
