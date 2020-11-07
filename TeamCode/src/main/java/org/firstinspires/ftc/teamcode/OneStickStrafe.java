package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;

@TeleOp(name = "Teleop1", group = "Training")
public class OneStickStrafe extends OpMode {

    DcMotor topLeft;
    DcMotor topRight;
    DcMotor bottomLeft;
    DcMotor backRightMotor;
    DcMotor backLeftMotor;
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor Shooter;
    DcMotor Intake;

    double rightStraffPower;
    double leftStraffPower;
    double leftWheelPower;
    double rightWheelPower;
    boolean shooter50;
    boolean shooter100;
    boolean shooterOff;
    boolean isIntakeOn;
    boolean IntakeON;
    //boolean intakeOff;
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
    boolean shooter;
    int count = 0;
    int count2 = 0;
    boolean hascount = false;
    boolean hasToggled = false;

   /*CRServo lIntake;
    CRServo rIntake;

    Servo lFoundation;
    Servo rFoundation;*/


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
        Shooter = hardwareMap.dcMotor.get("shooter");
        Intake = hardwareMap.dcMotor.get("intake"); //this is a place holder for intake

        // rIntake = hardwareMap.crservo.get("Right Intake");
        //lIntake = hardwareMap.crservo.get("left intake servo");

        //  lFoundation = hardwareMap.servo.get("Left Foundation");
        //   rFoundation = hardwareMap.servo.get("Right Foundation");


        //topRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //bottomRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {
        y = gamepad1.left_stick_y;
        x = gamepad1.left_stick_x;
        rx = gamepad1.right_stick_x;
        frontLeftMotor.setPower(-0.25 * (y - x + rx));
        backLeftMotor.setPower(0.25 * (y - x + rx));
        frontRightMotor.setPower(-0.25 * (y + x - rx));
        backRightMotor.setPower(0.25 * (y + x - rx));


       /* leftWheelPower = -gamepad1.left_stick_y;
        rightWheelPower = -gamepad1.right_stick_y;
        leftStraffPower = gamepad1.left_stick_x;
       rightStraffPower = -gamepad1.right_stick_x;
*/

        rightBmp = gamepad1.right_bumper;
        leftBmp = gamepad1.left_bumper;
        shooterOff = gamepad1.dpad_down;
        shooter50 = gamepad1.dpad_left;
        shooter100 = gamepad1.dpad_up;
        // intakeRev = gamepad1.a;
        foundationDown = gamepad2.left_bumper;
        foundationUp = gamepad2.right_bumper;
        IntakeON = gamepad1.a;


        if (gamepad1.dpad_up || gamepad1.dpad_down || gamepad1.dpad_left || gamepad1.dpad_right) {
            if (gamepad1.dpad_up) {
                if (hascount == false) {
                    if (count < 3) {
                        hascount = true;
                        count = count + 1;
                    }
                }
            }
            if (gamepad1.dpad_down) {
                if (hascount == false) {
                    if (count > 0) {
                        hascount = true;
                        count = count - 1;
                    }
                }
            }
            if (gamepad1.dpad_right) {
                if (hascount == false) {
                    hascount = true;
                    count = 3;
                }
            }
            if (gamepad1.dpad_left) {
                if (hascount == false) {
                    hascount = true;
                    count = 4;
                }
            }

        }
        if (!gamepad1.dpad_up && !gamepad1.dpad_down && !gamepad1.dpad_left && !gamepad1.dpad_right) {
            hascount = false;
            if (count == 4) {
                count = 0;
            }
        }
        switch (count) {
            case 1:
                Shooter.setPower(0.25);
            case 2:
                Shooter.setPower(0.5);
            case 3:
                Shooter.setPower(1);
            case 4:
                Shooter.setPower(0);
        }

        telemetry.addData("toggle", hasToggled);
        telemetry.addData("hasCount", hascount);
        telemetry.addData("isIntakeon", isIntakeOn);
        telemetry.update();

        //This is a place holder for when we add the intake motor

        if (IntakeON && isIntakeOn == false && hasToggled == false) {
            isIntakeOn = true;
            Intake.setPower(1);
        }
        if (IntakeON && isIntakeOn && hasToggled == true) {
            isIntakeOn = false;
            Intake.setPower(0);
        }
        if (IntakeON == false && isIntakeOn) {
            hasToggled = true;
        }
        if (IntakeON == false && isIntakeOn == false) {
            hasToggled = false;
        }
    }
}















