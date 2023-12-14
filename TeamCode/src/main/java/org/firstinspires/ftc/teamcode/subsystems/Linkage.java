package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Linkage implements SubsystemBase{


    public HardwareMap hwMap;
    public Servo linkageLeft;
    public Servo linkageRight;
    public Linkage(HardwareMap hwMap){
        linkageLeft = hwMap.servo.get("linkageLeft");
        linkageRight = hwMap.servo.get("linkageRight");
    }

    public void raise(){

        linkageLeft.setPosition(Constants.LinkageConstants.linkageLeftUp);
        linkageRight.setPosition(Constants.LinkageConstants.linkageRightUp);
    }
    public void lower() {

        linkageLeft.setPosition(Constants.LinkageConstants.linkageLeftDown);
        linkageRight.setPosition(Constants.LinkageConstants.linkageRightDown);
    }

    public void stackLevel(int level)
    {
        if(level==1)
        {
            linkageLeft.setPosition(Constants.LinkageConstants.linkageLeftPixelOne);
            linkageRight.setPosition(Constants.LinkageConstants.linkageRightPixelOne);
        }
        else if(level==2)
        {
            linkageLeft.setPosition(Constants.LinkageConstants.linkageLeftPixelTwo);
            linkageRight.setPosition(Constants.LinkageConstants.linkageRightPixelTwo);
        }
        else if(level==3)
        {
            linkageLeft.setPosition(Constants.LinkageConstants.linkageLeftPixelThree);
            linkageRight.setPosition(Constants.LinkageConstants.linkageRightPixelThree);
        }
        else if(level==4)
        {
            linkageLeft.setPosition(Constants.LinkageConstants.linkageLeftPixelFour);
            linkageRight.setPosition(Constants.LinkageConstants.linkageRightPixelFour);
        }
        else if(level==5)
        {
            linkageLeft.setPosition(Constants.LinkageConstants.linkageLeftPixelFive);
            linkageRight.setPosition(Constants.LinkageConstants.linkageRightPixelFive);
        }
    }
    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
