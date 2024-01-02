package org.firstinspires.ftc.teamcode.teleop.helper;

import static org.firstinspires.ftc.teamcode.subsystems.Shooter.ShooterState.REST;
import static org.firstinspires.ftc.teamcode.subsystems.Shooter.ShooterState.SPINNING;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Shooter;
@TeleOp(name = "Shooter Tester", group = "helper")

public class ShooterTester extends LinearOpMode {

     boolean prevB = false;
    @Override
    public void runOpMode() throws InterruptedException {

        Shooter shooter = new Shooter(hardwareMap);

        waitForStart();

        while(!isStopRequested() && opModeIsActive()){

            if(gamepad1.b && gamepad1.b != prevB){
                if(shooter.getState()==REST){shooter.spinUp();}
                else if(shooter.getState()==SPINNING){shooter.shoot();}
                else shooter.rest();
            }
            prevB = gamepad1.b;

        }
    }
}
