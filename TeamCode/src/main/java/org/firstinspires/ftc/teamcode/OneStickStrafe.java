package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "oneStickStraff", group = "Training")
public class OneStickStrafe extends OpMode {

    DcMotor topLeft;
    DcMotor topRight;
    DcMotor bottomLeft;
    DcMotor backRightMotor;
    DcMotor backLeftMotor;
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;

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
    double lsX;
    double lsY;
    double rsX;
    double x;
    double rx;
    double y;

    CRServo lIntake;
    CRServo rIntake;

    Servo lFoundation;
    Servo rFoundation;


    @Override
    public void init() {
        // topLeft = hardwareMap.dcMotor.get("topLeft");
        // topRight = hardwareMap.dcMotor.get("topRight");
        // bottomRight = hardwareMap.dcMotor.get("bottomRight");
        //bottomLeft = hardwareMap.dcMotor.get("bottomLeft");
        frontLeftMotor = hardwareMap.dcMotor.get("topLeft");
        frontRightMotor = hardwareMap.dcMotor.get("topRight");
        backRightMotor = hardwareMap.dcMotor.get("bottomRight");
        backLeftMotor = hardwareMap.dcMotor.get("bottomLeft");

        rIntake = hardwareMap.crservo.get("Right Intake");
        lIntake = hardwareMap.crservo.get("left intake servo");

        lFoundation = hardwareMap.servo.get("Left Foundation");
        rFoundation = hardwareMap.servo.get("Right Foundation");


        //topRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //bottomRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {
        y = gamepad1.left_stick_y;
        x = gamepad1.left_stick_x;
        rx = gamepad1.right_stick_x;
        frontLeftMotor.setPower(-1 * (y + x + rx));
        backLeftMotor.setPower(-1 * (y - x + rx));
        frontRightMotor.setPower(y - x - rx);
        backRightMotor.setPower(y + x - rx);


       /* leftWheelPower = -gamepad1.left_stick_y;
        rightWheelPower = -gamepad1.right_stick_y;
        leftStraffPower = gamepad1.left_stick_x;
       rightStraffPower = -gamepad1.right_stick_x;
*/

        rightBmp = gamepad1.right_bumper;
        leftBmp = gamepad1.left_bumper;
        intakeOn = gamepad1.b;
        intakeOff = gamepad1.x;
        intakeRev = gamepad1.a;
        foundationDown = gamepad2.left_bumper;
        foundationUp = gamepad2.right_bumper;

        /*if (rightBmp && straffOn == false) {

            straffOn = true;
        }
        if (straffOn == true) {
            backRightMotor
.setPower(rightStraffPower);
            bottomLeft.setPower(leftStraffPower);
            topRight.setPower(-rightStraffPower);
            topLeft.setPower(-leftStraffPower);
        }
        if (leftBmp && straffOn == true)
        {
            straffOn = false;
        }

        if (straffOn == false)
        {
            backRightMotor
.setPower(rightWheelPower);
            bottomLeft.setPower(leftWheelPower);
            topLeft.setPower(leftWheelPower);
            topRight.setPower(rightWheelPower);
        }


        if (intakeOn) {
            lIntake.setPower(1);
            rIntake.setPower(-1);
            //rIntake.setPosition(1);


        }
        if (intakeOff) {
            lIntake.setPower(0);
            rIntake.setPower(0);

        }
        if (intakeRev) {
            lIntake.setPower(-1);
            rIntake.setPower(1);
        }
        if (foundationUp) {
            lFoundation.setPosition(.5);
            rFoundation.setPosition(.6);


        }

        if (foundationDown) {
            rFoundation.setPosition(0);
            lFoundation.setPosition(1);

        }*/
    }
}