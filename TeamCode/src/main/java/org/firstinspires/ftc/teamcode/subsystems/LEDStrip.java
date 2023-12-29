package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.RevBlinkinLedDriver;

import org.firstinspires.ftc.teamcode.util.RevBlinkinLedDriver.BlinkinPattern;
import org.firstinspires.ftc.teamcode.util.Constants.ColorSensorWrapperConstants.Pixel;

import java.util.Arrays;

public class LEDStrip implements SubsystemBase{
    RevBlinkinLedDriver strip;
    Pixel left = Pixel.NONE;
    Pixel right = Pixel.NONE;
    static  BlinkinPattern[] patts = Arrays.copyOfRange(BlinkinPattern.values(), 100,120);



    public LEDStrip(HardwareMap hwMap){
        strip = hwMap.get(RevBlinkinLedDriver.class, "led");
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {
        RevBlinkinLedDriver.BlinkinPattern pat;

        if(left==right){
            if(left==Pixel.NONE){
                pat = BlinkinPattern.BLACK;

            }
            else if(left == Pixel.YELLOW){
                pat = BlinkinPattern.YELLOW;

            }
            else if(left == Pixel.GREEN){
                pat = BlinkinPattern.GREEN;

            }
            else if(left == Pixel.PURPLE){
                pat = BlinkinPattern.VIOLET;
            }
            else /**left == Pixel.WHITE**/{
                pat = BlinkinPattern.WHITE;
            }
            return;

        }


        int counter =0;
        for(Pixel left: Pixel.values()){
            for(Pixel right: Pixel.values()){
                if(left==right)continue;
                if(left==this.left && right ==this.right) {
                    pat = patts[counter];
                    return;
                }
                counter++;
            }
        }


    }
}
