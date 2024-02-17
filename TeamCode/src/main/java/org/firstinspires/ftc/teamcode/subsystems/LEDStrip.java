package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

import org.firstinspires.ftc.teamcode.util.Constants.ColorSensorWrapperConstants.Pixel;

import java.util.Arrays;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver.BlinkinPattern;

public class LEDStrip implements SubsystemBase{
    RevBlinkinLedDriver strip;
    RevBlinkinLedDriver strip2;

    BlinkinPattern currentPattern;
    BlinkinPattern currentPatter2;



    public LEDStrip(HardwareMap hwMap){

        strip = hwMap.get(RevBlinkinLedDriver.class, "led");//left
        strip2 = hwMap.get(RevBlinkinLedDriver.class, "led2");//right
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        t.addLine("CURRENT LED PATTERN " + currentPattern.name() + " #" + currentPattern.ordinal());
    }

    @Override
    public void periodic() {

    }
    public void set(BlinkinPattern blinkinPattern){
        strip.setPattern(blinkinPattern);

    }
    public void updateLEDPattern(Pixel left, Pixel right){
        if(left==Pixel.GREEN)strip.setPattern(BlinkinPattern.GREEN);
        else if(left == Pixel.PURPLE)strip.setPattern(BlinkinPattern.VIOLET);
        else if(left == Pixel.YELLOW)strip.setPattern(BlinkinPattern.YELLOW);
        else if(left == Pixel.WHITE)strip.setPattern(BlinkinPattern.WHITE);

        if(right==Pixel.GREEN)strip2.setPattern(BlinkinPattern.GREEN);
        else if(right == Pixel.PURPLE)strip2.setPattern(BlinkinPattern.VIOLET);
        else if(right == Pixel.YELLOW)strip2.setPattern(BlinkinPattern.YELLOW);
        else if(right == Pixel.WHITE)strip2.setPattern(BlinkinPattern.WHITE);
    }

//    public void updateLEDPattern(Pixel left, Pixel right){
//
//        if (left == right) {//If the right and left hoppers contain the same color, set the strip to soid
//            switch (left) {
//                case NONE:
//                    currentPattern = BlinkinPattern.BLACK;
//                    break;
//                case YELLOW:
//                    currentPattern = BlinkinPattern.YELLOW;
//                    break;
//                case GREEN:
//                    currentPattern = BlinkinPattern.GREEN;
//                    break;
//                case PURPLE:
//                    currentPattern = BlinkinPattern.VIOLET;
//                    break;
//                case WHITE:
//                    currentPattern = BlinkinPattern.WHITE;
//                    break;
//                default:
//                    currentPattern = BlinkinPattern.RED;
//            }
//
//        } else {//otherwise select the appropriate split pattern
//            int patternIndex = 0;
//            outer:
//            for (Pixel lleft : Pixel.values()) {//iterate through possible pixel colors in the left Hopper
//                for (Pixel lright : Pixel.values()) {//iterate through the possible pixels colors in the right hopper
//                    //The split color patterns are assigned id's 0-19 in the order Green, Purple, Yellow, Black, White,
//                    // the nested for each loops iterate through every combination in the order they are listed in the BlinkinPattern enum, except when the colors are the sam the loop is skipped because those have a seperate index
//
//
//                    if (lleft == lright) continue;
//                    if (lleft == left && lright == right) {//Once we find the id of the color comibnation in the hoppers
//                        currentPattern = BlinkinPattern.values()[patternIndex];//select the pattern  from the
//                        break outer;
//                    }
//                    patternIndex++;
//                }
//            }
//        }
//        strip.setPattern(currentPattern);
//    }
}
