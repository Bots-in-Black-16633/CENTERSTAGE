package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.arcrobotics.ftclib.util.InterpLUT;
import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;
import org.firstinspires.ftc.teamcode.util.Constants;

public class Shooter implements SubsystemBase{


    public HardwareMap hwMap;
    public DcMotor shooter;
    public CRServo kicker;
    public InterpLUT shooterVals;
    public enum ShooterState{
        SPINNING,SHOOTING,REST
    }
    ShooterState state ;

    public Shooter(HardwareMap hwMap){
        shooter = hwMap.dcMotor.get("shooter");
        kicker = hwMap.crservo.get("kicker");
        state = ShooterState.REST;
        for(int i = 0; i< Constants.ShooterConstants.testedDistances.length; i++)
        {
            shooterVals.add(Constants.ShooterConstants.testedDistances[i][0], Constants.ShooterConstants.testedDistances[i][1]);
            shooterVals.createLUT();
        }
    }
    public void spinUp(){
        shooter.setPower(Constants.ShooterConstants.shooterSpeed);
        state = ShooterState.SPINNING;
    }

    public void spinUpToDistance(double d)
    {
        shooter.setPower(shooterVals.get(d));
        state=ShooterState.SPINNING;
    }
    public void shoot(){
        kicker.setPower(Constants.ShooterConstants.kickerSpeed);
        state=ShooterState.SHOOTING;
    }
    public void rest(){
        kicker.setPower(0);
        shooter.setPower(0);
        state = ShooterState.REST;
    }
    public ShooterState getState(){
        return state;
    }

    @Override
    public void printTelemetry(ColorfulTelemetry t) {

    }

    @Override
    public void periodic() {

    }


}
