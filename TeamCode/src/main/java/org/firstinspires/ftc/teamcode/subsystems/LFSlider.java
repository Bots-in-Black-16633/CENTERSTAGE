package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class LFSlider implements SubsystemBase{

    Motor leader;
    Motor follower;
    MotorGroup slider;

    public LFSlider(HardwareMap hwMap){
        leader = new Motor(hwMap, "leftSlider");
        follower = new Motor(hwMap, "rightSlider");
        leader.setInverted(true);
        slider = new MotorGroup(leader, follower);
        slider.setPositionCoefficient(Constants.SliderConstants.kP);

    }

    public void runToPosition(double pos){
        if(pos > Constants.SliderConstants.sliderMaxPosition)pos = Constants.SliderConstants.sliderMaxPosition;
        if(pos < Constants.SliderConstants.sliderMinPosition)pos = Constants.SliderConstants.sliderMinPosition;
        slider.setRunMode(Motor.RunMode.PositionControl);
        slider.setTargetPosition((int)pos);
        slider.set(Constants.SliderConstants.sliderPower);
    }

    public void reset(){
        slider.resetEncoder();
    }
    public void set(double power){
        slider.setRunMode(Motor.RunMode.RawPower);
        slider.set(power);
    }
    public int getPosition(){
        return leader.getCurrentPosition();
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        t.addLine("LEFT SLIDER (LEADER): " + leader.getCurrentPosition());
        t.addLine("RIGHT SLIDER (FOLLOWER)" + follower.getCurrentPosition());
        t.addLine("kP" + slider.getPositionCoefficient());

    }

    @Override
    public void periodic() {

    }
}