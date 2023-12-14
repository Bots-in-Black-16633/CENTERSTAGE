package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Linkage implements SubsystemBase{


    public HardwareMap hwMap;
    public Servo linkage1;
    public Servo linkage2;
    public Linkage(HardwareMap hwMap){
        linkage1 = hwMap.servo.get("linkage");
        linkage2 = hwMap.servo.get("linkage2");
    }

    public void raise(){

        linkage1.setPosition(Constants.LinkageConstants.linkage1Up);
        linkage2.setPosition(Constants.LinkageConstants.linkage2Up);
    }
    public void lower() {

        linkage1.setPosition(Constants.LinkageConstants.linkage1Down);
        linkage2.setPosition(Constants.LinkageConstants.linkage2Down);
    }

    public void stackLevel(int level)
    {
        if(level==1)
        {
            linkage1.setPosition(Constants.LinkageConstants.linkage1PixelOne);
            linkage2.setPosition(Constants.LinkageConstants.linkage2PixelOne);
        }
        else if(level==2)
        {
            linkage1.setPosition(Constants.LinkageConstants.linkage1PixelTwo);
            linkage2.setPosition(Constants.LinkageConstants.linkage2PixelTwo);
        }
        else if(level==3)
        {
            linkage1.setPosition(Constants.LinkageConstants.linkage1PixelThree);
            linkage2.setPosition(Constants.LinkageConstants.linkage2PixelThree);
        }
        else if(level==4)
        {
            linkage1.setPosition(Constants.LinkageConstants.linkage1PixelFour);
            linkage2.setPosition(Constants.LinkageConstants.linkage2PixelFour);
        }
        else if(level==5)
        {
            linkage1.setPosition(Constants.LinkageConstants.linkage1PixelFive);
            linkage2.setPosition(Constants.LinkageConstants.linkage2PixelFive);
        }
    }
    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
