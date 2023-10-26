package org.firstinspires.ftc.teamcode.teleop.helper;

import androidx.core.math.MathUtils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.ColorfulTelemetry;

import java.util.List;

@TeleOp(name = "Servo Diagnostic", group = "helper")
public class ServoDiagnostic extends LinearOpMode {
    ColorfulTelemetry pen  = new ColorfulTelemetry(telemetry, FtcDashboard.getInstance());
    List<Servo> servos;
    int curServo = 0;
    Servo cur = null;
    double curServoPosition = 0;
    private boolean prevDUP;
    private boolean prevDDOWN;


    @Override
    public void runOpMode() throws InterruptedException {


        servos = hardwareMap.getAll(Servo.class);
        for (Servo s:servos){
            telemetry.addLine("Name:"  + s.getDeviceName() + " Port: " + s.getPortNumber()  + " Position: " + round(s.getPosition()));
        }
        telemetry.update();
        waitForStart();


        while(!isStopRequested() && opModeIsActive()){
            //get all the servos from the control hub
            servos = hardwareMap.getAll(Servo.class);

            //if there are no servos
            if(servos.size()==0){
                pen.addLine("NO SERVOS FOUND");
            }
            else{//otherwise
                //Print out Info about all the servos
                for (int i = 0; i < servos.size(); i++){
                    Servo s = servos.get(i);
                    pen.addLine((i==curServo?"!ENABLED! ":"disabled ")+ "---Name:"  + s.getDeviceName() + "---Port: " + s.getPortNumber() + "---Position: " + s.getPosition());
                }

                //SELECTING SERVO
                if(gamepad1.dpad_up || gamepad1.dpad_down){
                    if(gamepad1.dpad_up && gamepad1.dpad_up !=prevDUP){
                        curServo -=1;
                    }
                    if(gamepad1.dpad_down && gamepad1.dpad_down !=prevDDOWN){
                        curServo+=1;
                    }

                    if(curServo > servos.size()-1 && curServo!=0){curServo=0;}
                    else if(curServo < 0){curServo =servos.size()-1;}

                    if(curServo <servos.size() && curServo >=0 && servos.get(curServo)!=null){
                        cur = servos.get(curServo);
                        curServoPosition = cur.getPosition();
                    }

                }
                prevDUP = gamepad1.dpad_up;
                prevDDOWN = gamepad1.dpad_down;

                //MOVING SERVO
                curServoPosition += gamepad1.left_stick_y*.0005;
                curServoPosition = MathUtils.clamp(curServoPosition, 0, 1);
                if(cur != null){cur.setPosition(curServoPosition);}
                pen.addLine("TARGET: " + curServoPosition);
                pen.update();

            }

        }
    }
    private double round(double t){
        return ((int)t*100)/100.0;

    }





}