package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "autoCall", group = "Example")
public class AutoCall extends LinearOpMode {
    //bye
    DriveClass driveClass = new DriveClass();

    @Override
    public void runOpMode() throws InterruptedException {
        driveClass.bottomLeft = hardwareMap.dcMotor.get("bottomLeft");
        driveClass.bottomRight = hardwareMap.dcMotor.get("bottomRight");
        driveClass.topLeft = hardwareMap.dcMotor.get("topLeft");
        driveClass.topRight = hardwareMap.dcMotor.get("topRight");
        driveClass.topLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveClass.bottomLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        driveClass.Drive(1, 1, 1, 1);

        sleep(1000);

        driveClass.motorStop();

        //turn
        driveClass.Drive(0.5, -0.5, .5, -.5);

        sleep(1000);

        driveClass.motorStop();

        driveClass.Drive(1, 1, 1, 1);

        sleep(1000);

        driveClass.motorStop();


    }
}
