package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = " ExampleA uto", group = "Example")
public class AutoTest extends LinearOpMode {

    DcMotor leftMotortop;
    DcMotor rightMotortop;
    DcMotor leftMotorbottom;
    DcMotor rightMotorbottom;
    DriveClass motorFunctions = new DriveClass();

    @Override
    public void runOpMode() throws InterruptedException {
        leftMotorbottom = hardwareMap.dcMotor.get("bottomLeft");
        rightMotorbottom = hardwareMap.dcMotor.get("bottomRight");
        leftMotortop = hardwareMap.dcMotor.get("topLeft");
        rightMotortop = hardwareMap.dcMotor.get("topRight");
        leftMotorbottom.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotortop.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();


        leftMotorbottom.setPower(.1);
        rightMotorbottom.setPower(.1);
        leftMotortop.setPower(.1);
        rightMotortop.setPower(.1);

        sleep(3000);

        leftMotorbottom.setPower(0);
        leftMotortop.setPower(0);
        rightMotortop.setPower(0);
        rightMotorbottom.setPower(0);

        leftMotorbottom.setPower(.5);
        leftMotortop.setPower(.5);
        rightMotorbottom.setPower(-.5);
        rightMotortop.setPower(-.5);

        sleep(3000);

        leftMotorbottom.setPower(0);
        leftMotortop.setPower(0);
        rightMotorbottom.setPower(0);
        rightMotortop.setPower(0);

        leftMotorbottom.setPower(-.1);
        leftMotortop.setPower(-.1);
        rightMotorbottom.setPower(.1);
        rightMotortop.setPower(.1);

        sleep(3000);

        leftMotorbottom.setPower(0);
        leftMotortop.setPower(0);
        rightMotorbottom.setPower(0);
        rightMotortop.setPower(0);


    }
}
