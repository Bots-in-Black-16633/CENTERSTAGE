package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Slider extends SubsystemBase{

    Motor leader;
    Motor follower;
    MotorGroup slider;

    public Slider(HardwareMap hwMap){
        super("Slider", hwMap);
        leader = new Motor(hwMap, "slider1");
        follower = new Motor(hwMap, "slider2");
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
