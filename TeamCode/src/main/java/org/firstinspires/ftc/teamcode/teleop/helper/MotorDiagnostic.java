package org.firstinspires.ftc.teamcode.teleop.helper;

import androidx.core.math.MathUtils;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

import java.util.List;

@TeleOp(name = "Motor Diagnostic", group = "helper")
public class MotorDiagnostic extends LinearOpMode {
    ColorfulTelemetry pen  = new ColorfulTelemetry(telemetry);
    List<DcMotor> motors;
    int curMotor = 0;
    DcMotor cur = null;
    double curMotorPosition = 0;
    private boolean prevDUP;
    private boolean prevDDOWN;
    private boolean encoderControl = false;
    private boolean prevA = false;
    private boolean prevT = false;


    @Override
    public void runOpMode() throws InterruptedException {


        motors = hardwareMap.getAll(DcMotor.class);
        for (DcMotor m:motors){
            telemetry.addLine("Name:"  + m.getDeviceName() + " Port: " + m.getPortNumber()  + " Position: " + round(m.getCurrentPosition()));
        }
        telemetry.update();
        waitForStart();


        while(!isStopRequested() && opModeIsActive()){
            //get all the motors from the control hub
            motors = hardwareMap.getAll(DcMotor.class);

            //if there are no motors
            if(motors.size()==0){
                pen.addLine("NO motorS FOUND");
            }
            else{//otherwise
                //Print out Info about all the motors
                for (int i = 0; i < motors.size(); i++){
                    DcMotor m = motors.get(i);
                    pen.addLine((i==curMotor?"!ENABLED! ":"disabled ")+ "---Name:"  + m.getDeviceName() + "---Port: " + m.getPortNumber() + "---Position: " + m.getCurrentPosition());
                }

                //SELECTING motor
                if(gamepad1.dpad_up || gamepad1.dpad_down){
                    if(gamepad1.dpad_up && gamepad1.dpad_up !=prevDUP){
                        curMotor -=1;
                    }
                    if(gamepad1.dpad_down && gamepad1.dpad_down !=prevDDOWN){
                        curMotor+=1;
                    }

                    if(curMotor > motors.size()-1 && curMotor!=0){curMotor=0;}
                    else if(curMotor < 0){curMotor =motors.size()-1;}

                    if(curMotor <motors.size() && curMotor >=0 && motors.get(curMotor)!=null){
                        cur.setPower(0);//set power of previous motor to 0
                        cur = motors.get(curMotor);//grab the new robot
                        curMotorPosition = cur.getCurrentPosition();//set the targetPos to be the current position of the motor
                    }

                }
                prevDUP = gamepad1.dpad_up;
                prevDDOWN = gamepad1.dpad_down;

                //MOVING Motor
                if(encoderControl){
                    curMotorPosition += gamepad1.left_stick_y*.0005;
                    curMotorPosition = MathUtils.clamp(curMotorPosition, 0, 1);
                    if(cur != null){cur.setTargetPosition((int)curMotorPosition);cur.setPower(1);}
                }
                else{
                    cur.setPower(gamepad1.left_stick_y*.5);
                }

                if(gamepad1.a && gamepad1.a != prevA){
                    if(encoderControl){
                        encoderControl = false;
                    }
                    else{
                        encoderControl = true;
                        curMotorPosition = cur.getCurrentPosition();
                    }
                }
                prevA = gamepad1.a;

                pen.addLine("TARGET: " + curMotorPosition);
                pen.update();

            }

        }
    }
    private double round(double t){
        return ((int)t*100)/100.0;

    }





}