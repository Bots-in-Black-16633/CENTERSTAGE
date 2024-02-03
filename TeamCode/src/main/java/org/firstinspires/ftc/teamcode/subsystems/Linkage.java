package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Linkage implements SubsystemBase{


    public HardwareMap hwMap;
    public Servo linkage;
    public Linkage(HardwareMap hwMap){
        linkage = hwMap.servo.get("linkage");
    }

    public void raise(){

        linkage.setPosition(Constants.LinkageConstants.linkageUp);
    }
    public void lower() {

        linkage.setPosition(Constants.LinkageConstants.linkageDown);
    }

    public void stackLevel(int level)
    {
        if(level==1)
        {
            linkage.setPosition(Constants.LinkageConstants.linkagePixelOne);
        }
        else if(level==2)
        {
            linkage.setPosition(Constants.LinkageConstants.linkagePixelTwo);
        }
        else if(level==3)
        {
            linkage.setPosition(Constants.LinkageConstants.linkagePixelThree);
        }
        else if(level==4)
        {
            linkage.setPosition(Constants.LinkageConstants.linkagePixelFour);
        }
        else if(level==5)
        {
            linkage.setPosition(Constants.LinkageConstants.linkagePixelFive);
        }
    }
    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
