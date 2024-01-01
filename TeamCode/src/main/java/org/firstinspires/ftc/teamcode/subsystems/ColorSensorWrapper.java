package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants.ColorSensorWrapperConstants.Pixel;
import org.firstinspires.ftc.teamcode.util.Constants;

import org.opencv.core.Point3;
import org.opencv.core.Scalar;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ColorSensorWrapper implements  SubsystemBase{

    NormalizedColorSensor colorSensor;
    double[] lastDistances = {0,0,0,0,0,0};//PURPLE, gren, yellow, blac whit

    public ColorSensorWrapper(String name, HardwareMap hwMap){
        colorSensor= hwMap.get(NormalizedColorSensor.class, name);
    }


    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }

    public Pixel getPixelPresent(){
        //if the hsv colors from the scanner are different enough form the expected default than a pixel is present
        Scalar curHSVColor = new Scalar(getRed(), getGreen(), getBlue());

        double purpleDistance = distance3D(curHSVColor, Constants.ColorSensorWrapperConstants.purplePixelHSV);
        double greenDistance = distance3D(curHSVColor, Constants.ColorSensorWrapperConstants.greenPixelHSV);
        double yellowDistance = distance3D(curHSVColor, Constants.ColorSensorWrapperConstants.yellowPixelHSV);
        double whiteDistance = distance3D(curHSVColor, Constants.ColorSensorWrapperConstants.whitePixelHSV);
        double emptyDistance = distance3D(curHSVColor, Constants.ColorSensorWrapperConstants.emptyHopperHSV);
        double min = Math.min(purpleDistance, Math.min(greenDistance, Math.min(yellowDistance, Math.min(whiteDistance, emptyDistance))));

        lastDistances = new double[]{purpleDistance, greenDistance, yellowDistance, whiteDistance, emptyDistance};
        if(min == purpleDistance)return Pixel.PURPLE;
        else if(min == greenDistance)return Pixel.GREEN;
        else if (min == yellowDistance)return Pixel.YELLOW;
        else if(min == whiteDistance)return Pixel.WHITE;
        else return Pixel.NONE;
    }
    public boolean isPixelPresent(){
        return getPixelPresent() != Pixel.NONE;
    }
    public double[] getTelemetryData(){
        return lastDistances;
    }

    public double distance3D(Scalar p1, Scalar p2){
        return Math.sqrt(Math.pow(p1.val[0]-p2.val[0],2) + Math.pow(p1.val[1]-p2.val[1],2) + Math.pow(p1.val[2]-p2.val[2], 2));
    }

    public float [] getRGB() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float [] output = new float [3];
        output[0] = colors.red*100;
        output[1] = colors.blue*100;
        output[2] = colors.green*100;
        return output;
    }

    public double getRed() {
       return getRGB()[0];
    }

    public double getGreen() {
        return getRGB()[1];
    }

    public double getBlue() {
        return getRGB()[2];
    }

    public float getIntensity() {
        return getHSVValues()[1];
    }
    public float getValue(){
        return getHSVValues()[2];
    }
    public float getHue() {
        return getHSVValues()[0];
    }
    public float[] getHSVValues(){
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float[] hsvValues = new float[3];
        Color.colorToHSV(colors.toColor(), hsvValues);
        return hsvValues;
    }


    @Override
    public String toString(){
        return "HSV: " + Arrays.toString(getHSVValues()) + "  RGB VALUES: " + Arrays.toString(getRGB());
    }
}
