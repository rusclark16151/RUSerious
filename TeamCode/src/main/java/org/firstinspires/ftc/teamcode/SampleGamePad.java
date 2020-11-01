package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "gamepad", group = "Training")
public class SampleGamePad extends OpMode {

    DcMotor topLeft;
    DcMotor topRight;
    DcMotor bottomRight;
    DcMotor bottomLeft;
    DcMotor LeftArm;
    DcMotor RightArm;

    double rightStraffPower;
    double leftStraffPower;
    double leftWheelPower;
    double rightWheelPower;
    boolean intakeOn;
    boolean intakeOff;
    boolean intakeRev;
    boolean foundationUp;
    boolean foundationDown;
    boolean straffOn;
    boolean rightBmp;
    boolean leftBmp;


    //CRServo lIntake;
    //CRServo rIntake;

    Servo lFoundation;
    Servo rFoundation;


    @Override
    public void init() {
        topLeft = hardwareMap.dcMotor.get("topLeft");
        topRight = hardwareMap.dcMotor.get("topRight");
        bottomRight = hardwareMap.dcMotor.get("bottomRight");
        bottomLeft = hardwareMap.dcMotor.get("bottomLeft");
        LeftArm = hardwareMap.dcMotor.get("LeftArm");
        RightArm = hardwareMap.dcMotor.get("RightArm");

        //rIntake = hardwareMap.crservo.get("Right Intake");
        //lIntake = hardwareMap.crservo.get("left intake servo");

        lFoundation = hardwareMap.servo.get("Left Foundation");
        // rFoundation = hardwareMap.servo.get("Right Foundation");


        topRight.setDirection(DcMotorSimple.Direction.REVERSE);
        bottomRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {
        leftWheelPower = -gamepad1.left_stick_y;
        rightWheelPower = -gamepad1.right_stick_y;
        leftStraffPower = gamepad1.left_stick_x;
        rightStraffPower = -gamepad1.right_stick_x;


        rightBmp = gamepad1.right_bumper;
        leftBmp = gamepad1.left_bumper;
        intakeOn = gamepad1.b;
        intakeOff = gamepad1.a;
        //intakeRev = gamepad1.a;
        foundationDown = gamepad2.left_bumper;
        foundationUp = gamepad2.right_bumper;

        if (rightBmp && straffOn == false) {

            straffOn = true;
        }
        if (straffOn == true) {
            bottomRight.setPower(rightStraffPower);
            bottomLeft.setPower(leftStraffPower);
            topRight.setPower(-rightStraffPower);
            topLeft.setPower(-leftStraffPower);
        }
        if (leftBmp && straffOn == true) {
            straffOn = false;
        }

        if (straffOn == false) {
            bottomRight.setPower(rightWheelPower);
            bottomLeft.setPower(leftWheelPower);
            topLeft.setPower(leftWheelPower);
            topRight.setPower(rightWheelPower);
        }


        if (intakeOn) {
            RightArm.setPower(-1);
            LeftArm.setPower(1);


        }
        if (intakeOff) {
            RightArm.setPower(0);
            LeftArm.setPower(0);

        }
        /*if (intakeRev) {
            lIntake.setPower(-1);
            rIntake.setPower(1);
        }*/
        if (foundationUp) {
            lFoundation.setPosition(.5);
            // rFoundation.setPosition(.6);


        }

        if (foundationDown) {
            // rFoundation.setPosition(0);
            lFoundation.setPosition(0);

        }
    }
}