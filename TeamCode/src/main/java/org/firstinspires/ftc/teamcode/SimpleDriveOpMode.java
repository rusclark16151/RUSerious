package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "Simple Drive", group = "Training")
public class SimpleDriveOpMode extends OpMode {

    DcMotor LeftWheel;
    DcMotor RightWheel;
    double powerPower = .5;
    //DriveClass motorFunctions = new DriveClass();

    @Override
    public void init() {
        LeftWheel = hardwareMap.dcMotor.get("topLeft");
        RightWheel = hardwareMap.dcMotor.get("topRight");
        RightWheel.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        LeftWheel.setPower(powerPower);
        RightWheel.setPower(powerPower);
        //motorFunctions.DriveStraight();
    }
}
