package org.firstinspires.ftc.teamcode.teleop.helper;

import android.widget.ToggleButton;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.subsystems.Slider;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

import java.util.Set;


@TeleOp(name = "MotorGroup Diagnostic", group = "helper")
public class MotorGroupDiagnostic extends LinearOpMode {
    ColorfulTelemetry pen  = new ColorfulTelemetry(telemetry);
    Slider s;
    double sliderPos = 0;
    boolean encoderControl = false;

    GamepadEx g1;



    @Override
    public void runOpMode() throws InterruptedException {
        s = new Slider(hardwareMap);
        s.reset();

        g1 = new GamepadEx(gamepad1);

        g1.getGamepadButton(GamepadKeys.Button.A).whenActive(()->switchEncoderControl());


        waitForStart();


        while(!isStopRequested() && opModeIsActive()){

            if(encoderControl){
                sliderPos += gamepad1.left_stick_y*1;
                if(sliderPos > Constants.SliderConstants.sliderMaxPosition)sliderPos = Constants.SliderConstants.sliderMaxPosition;
                if(sliderPos < Constants.SliderConstants.sliderMinPosition)sliderPos = Constants.SliderConstants.sliderMinPosition;
                s.runToPosition(sliderPos);
                pen.addLine("SLIDER TARGET:"  + sliderPos);
            }
            else{
                s.set(gamepad1.left_stick_y*.5);
            }
            pen.addLine("ENCODER MODE: " + encoderControl);
            if(g1.wasJustPressed(GamepadKeys.Button.A)){
                switchEncoderControl();
            }

            g1.readButtons();


            s.printTelemetry(pen);
            pen.update();
        }
    }

    public void switchEncoderControl(){
        if(encoderControl){
            encoderControl = false;
        }
        else{
            encoderControl = true;
            sliderPos = s.getPosition();
        }
    }





}