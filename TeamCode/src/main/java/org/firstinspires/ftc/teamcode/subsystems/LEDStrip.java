package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

import org.firstinspires.ftc.teamcode.util.Constants.ColorSensorWrapperConstants.Pixel;

import java.util.Arrays;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver.BlinkinPattern;

public class LEDStrip implements SubsystemBase{
    RevBlinkinLedDriver strip;

    BlinkinPattern currentPattern;



    public LEDStrip(HardwareMap hwMap){
        strip = hwMap.get(RevBlinkinLedDriver.class, "led");
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {
        t.addLine("CURRENT LED PATTERN " + currentPattern.name() + " #" + currentPattern.ordinal());
    }

    @Override
    public void periodic() {

    }

    public void updateLEDPattern(Pixel left, Pixel right){

        if (left == right) {//If the right and left hoppers contain the same color, set the strip to soid
            switch (left) {
                case NONE:
                    currentPattern = BlinkinPattern.BLACK;
                    break;
                case YELLOW:
                    currentPattern = BlinkinPattern.YELLOW;
                    break;
                case GREEN:
                    currentPattern = BlinkinPattern.GREEN;
                    break;
                case PURPLE:
                    currentPattern = BlinkinPattern.VIOLET;
                    break;
                case WHITE:
                    currentPattern = BlinkinPattern.WHITE;
                    break;
                default:
                    currentPattern = BlinkinPattern.RED;
            }

        } else {//otherwise select the appropriate split pattern
            int patternIndex = 0;
            outer:
            for (Pixel lleft : Pixel.values()) {//iterate through possible pixel colors in the left Hopper
                for (Pixel lright : Pixel.values()) {//iterate through the possible pixels colors in the right hopper
                    //The split color patterns are assigned id's 0-19 in the order Green, Purple, Yellow, Black, White,
                    // the nested for each loops iterate through every combination in the order they are listed in the BlinkinPattern enum, except when the colors are the sam the loop is skipped because those have a seperate index


                    if (lleft == lright) continue;
                    if (lleft == left && lright == right) {//Once we find the id of the color comibnation in the hoppers
                        currentPattern = BlinkinPattern.values()[patternIndex];//select the pattern  from the
                        break outer;
                    }
                    patternIndex++;
                }
            }
        }
        strip.setPattern(currentPattern);
    }
}
