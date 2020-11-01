package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "Johnny Test", group = "Examples")
public class jabbaTest_hub extends LinearOpMode {
    DcMotor motor1;
    DcMotor motor2;
    DcMotor lift;
    Servo servo1;

    @Override
    public void runOpMode() throws InterruptedException {
        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");
        lift = hardwareMap.dcMotor.get("lift");
        servo1 = hardwareMap.servo.get("servo1");

        waitForStart();
        motor1.setPower(1);
        motor2.setPower(1);
        sleep(1000);
    }
}
