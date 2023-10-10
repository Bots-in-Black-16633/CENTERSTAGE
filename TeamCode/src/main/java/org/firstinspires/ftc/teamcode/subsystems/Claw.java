package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.util.MathUtils;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Claw extends SubsystemBase{
    //Servos
    ServoEx rightClaw;
    ServoEx leftClaw;

    public Claw(HardwareMap hwMap){
        super("Claw", hwMap);

        //instantiate right and left Servos
        rightClaw = new SimpleServo(hwMap, "leftClaw", 0,360, AngleUnit.DEGREES);
        leftClaw = new SimpleServo(hwMap, "rightClaw", 0, 360, AngleUnit.DEGREES);

    }

    /**
     * Manually set the positions of both servos
     * @param leftClawPosition
     * @param rightClawPosition
     */
    public void setPosition(double leftClawPosition, double rightClawPosition){
        leftClaw.setPosition(MathUtils.clamp(leftClawPosition, 0, 1));
        rightClaw.setPosition(MathUtils.clamp(rightClawPosition, 0, 1));
    }

    /**
     * Manually set the angles of both servos
     */
    public void setAngles(double leftClawAngle, double rightClawAngle){
        leftClaw.turnToAngle(leftClawAngle);
        rightClaw.turnToAngle(rightClawAngle);
    }


    /**
     * Will close the claw around a pixel
     */
    public void closeClaw(){
        rightClaw.turnToAngle(Constants.ClawConstants.rightClawClose);
        leftClaw.turnToAngle(Constants.ClawConstants.leftClawClose);
    }

    /**
     * Will open the claw to a position suitable for intaking pixels
     */
    public void intakeClaw(){
        rightClaw.turnToAngle(Constants.ClawConstants.rightClawIntake);
        leftClaw.turnToAngle(Constants.ClawConstants.leftClawIntake);
    }

    /**
     * Will open the claw wide enough to drop the two pixels
     */
    public void outtakeClaw(){
        rightClaw.turnToAngle(Constants.ClawConstants.rightClawOuttake);
        leftClaw.turnToAngle(Constants.ClawConstants.leftClawOuttake);
    }

    public void printTelemetry(ColorfulTelemetry t){
        t.addLine("RightClaw " + "Pos:" + rightClaw.getPosition() + " Angle:" + rightClaw.getAngle(), ColorfulTelemetry.Orange);
        t.addLine("Left Claw " + "Pos:" + leftClaw.getPosition() + " Angle:" + leftClaw.getAngle(), ColorfulTelemetry.Orange);

    }
}
