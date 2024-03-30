package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.Line;

public class Linkage implements SubsystemBase{


    public HardwareMap hwMap;
    public Servo linkageLeft;
    public Servo linkageRight;
    public Line leftLine = new Line(0, Constants.LinkageConstants.leftLinkageDown, 1, Constants.LinkageConstants.leftLinkageUp);
    public Line rightLine = new Line(0, Constants.LinkageConstants.rightLinkageDown, 1, Constants.LinkageConstants.rightLinkageUp);

    public Linkage(HardwareMap hwMap){
        linkageLeft = hwMap.servo.get("linkageLeft");linkageRight = hwMap.servo.get("linkageRight");
    }

    public void raise(){

        linkageLeft.setPosition(Constants.LinkageConstants.leftLinkageUp);
        linkageRight.setPosition(Constants.LinkageConstants.rightLinkageUp);
    }
    public void lower() {

//        linkageLeft.setPosition(Constants.LinkageConstants.leftLinkageDown);
//        linkageRight.setPosition(Constants.LinkageConstants.rightLinkageDown);
        linkageLeft.setPosition(leftLine.calculate(.1));
        linkageRight.setPosition(rightLine.calculate(.1));
    }

    public void stackLevel(int level)
    {
        if(level==1)
        {
           linkageLeft.setPosition(leftLine.calculate(Constants.LinkageConstants.linkagePixelOne));
            linkageRight.setPosition(rightLine.calculate(Constants.LinkageConstants.linkagePixelOne));

        }
        else if(level==2)
        {
            linkageLeft.setPosition(leftLine.calculate(Constants.LinkageConstants.linkagePixelTwo));
            linkageRight.setPosition(rightLine.calculate(Constants.LinkageConstants.linkagePixelTwo));        }
        else if(level==3)
        {
            linkageLeft.setPosition(leftLine.calculate(Constants.LinkageConstants.linkagePixelThree));
            linkageRight.setPosition(rightLine.calculate(Constants.LinkageConstants.linkagePixelThree));        }
        else if(level==4)
        {
            linkageLeft.setPosition(leftLine.calculate(Constants.LinkageConstants.linkagePixelFour));
            linkageRight.setPosition(rightLine.calculate(Constants.LinkageConstants.linkagePixelFour));        }
        else if(level==5)
        {
            linkageLeft.setPosition(leftLine.calculate(Constants.LinkageConstants.linkagePixelFive));
            linkageRight.setPosition(rightLine.calculate(Constants.LinkageConstants.linkagePixelFive)+.2);        }
    }
    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }
}
