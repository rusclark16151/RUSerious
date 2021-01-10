package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Teleop1", group = "Training")
public class OneStickStrafe extends OpMode {

    DcMotor backRightMotor;
    DcMotor backLeftMotor;
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor Shooter;
    DcMotor Intake;
    Servo hammerServo;
    Servo wobbleServo;

    boolean isIntakeOn;
    boolean IntakeON;
    boolean speedChangeBumper;
    boolean hasSpeedToggled;
    boolean isSpeedHalf;
    float trigger;
    double x;
    double rx;
    double y;
    int count = 0;
    boolean hascount = false;
    boolean hasToggled = false;
    double servoMax = 0.25;
    double servoMin = 0.5;
    boolean dpadUp;
    boolean dpadDown;
    boolean dpadRight;
    boolean dpadLeft;
    private ElapsedTime runtime = new ElapsedTime();
    double g1L;
    double g1R;
    double g2L;
    double g2R;
    double motorMutiplier = 1;

    @Override
    public void init() {
        //this is the set up for the hardware configuration during init
        frontLeftMotor = hardwareMap.dcMotor.get("topLeft");
        frontRightMotor = hardwareMap.dcMotor.get("topRight");
        backRightMotor = hardwareMap.dcMotor.get("bottomRight");
        backLeftMotor = hardwareMap.dcMotor.get("bottomLeft");
        Shooter = hardwareMap.dcMotor.get("shooter");
        Intake = hardwareMap.dcMotor.get("intake");
        hammerServo = hardwareMap.servo.get("hammer");
        wobbleServo = hardwareMap.servo.get("wobble");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        Intake.setDirection(DcMotorSimple.Direction.REVERSE);
        Shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        //Shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    //this is the main loop for the teleOP program when play is pressed
    @Override
    public void loop() {
       /* g1L = gamepad1.left_stick_y;
        g1R = gamepad1.right_stick_y;
        g2L = gamepad2.left_stick_y;
        g2R = gamepad2.right_stick_y;*/

       /* frontLeftMotor.setPower(g1R);
        backLeftMotor.setPower(g1L);
        frontRightMotor.setPower(g2L);
        backRightMotor.setPower(g2R);*/

        y = gamepad1.left_stick_y;
        x = gamepad1.left_stick_x;
        rx = gamepad1.right_stick_x;
        /*frontLeftMotor.setPower(-1 * (y + x + rx));
        backLeftMotor.setPower(-1 * (y + x - rx));
        frontRightMotor.setPower(1 * (y - x + rx));
        backRightMotor.setPower(1 * (y - x - rx));*/

        frontLeftMotor.setPower(motorMutiplier * (y - x - rx));
        backLeftMotor.setPower(motorMutiplier * (y + x - rx));
        frontRightMotor.setPower(motorMutiplier * (y + x + rx));
        backRightMotor.setPower(motorMutiplier * (y -  x + rx));


        IntakeON = gamepad2.a;
        speedChangeBumper = gamepad1.right_bumper;

        dpadUp = gamepad2.dpad_up;
        dpadDown = gamepad2.dpad_down;
        dpadRight = gamepad2.dpad_right;
        dpadLeft = gamepad2.dpad_left;


        if (dpadUp || dpadDown || dpadLeft || dpadRight) {
            if (dpadUp) {
                if (hascount == false) {
                    if (count < 3) {
                        runtime.reset();
                        runtime.startTime();
                        //Shooter.setDirection(DcMotorSimple.Direction.FORWARD);
                        while (runtime.milliseconds() <= 250 && count == 0){
                            Shooter.setPower(0.05);
                        }
                        Shooter.setDirection(DcMotorSimple.Direction.REVERSE);
                        /*runtime.reset();
                        runtime.startTime();
                        if (runtime.milliseconds() <= 500){
                            Shooter.setDirection(DcMotorSimple.Direction.REVERSE);
                            Shooter.setPower(1);
                        }*/
                        hascount = true;
                        count = count + 1;
                    }
                }
            }
            if (dpadDown) {
                if (hascount == false) {
                    if (count > 0) {
                        hascount = true;
                        count = count - 1;
                    }
                }
            }
            if (dpadRight) {
                if (hascount == false) {
                    hascount = true;
                    count = 3;
                }
            }
            if (dpadLeft) {
                if (hascount == false) {
                    hascount = true;
                    count = 4;
                    Shooter.setPower(0);
                }
            }

        }
       if (!dpadUp && !dpadDown &&
                !dpadLeft && !dpadRight) {
            hascount = false;
            if (count == 4) {
                count = 0;
            }
        }
        switch (count) {
            case 3:
                Shooter.setPower(-0.75);
                telemetry.addData("Shooter", Shooter.getPower());
                break;
            case 2:
                Shooter.setPower(-0.70);
                telemetry.addData("Shooter", Shooter.getPower());
                break;
            case 1:
                Shooter.setPower(-0.65);
                telemetry.addData("Shooter", Shooter.getPower());
                break;
            //case 4:
              //  Shooter.setPower(0);
        }

        telemetry.addData("servoPosition", hammerServo.getPosition());
        telemetry.addData("count", count);
        telemetry.addData("hasCount", hascount);

/*      telemetry.addData("FLM", frontLeftMotor.getPower());
        telemetry.addData("BLM", backLeftMotor.getPower());
        telemetry.addData("FRM", frontRightMotor.getPower());
        telemetry.addData("BRM", backRightMotor.getPower());*/
        //telemetry.addData("Shooter", Shooter.getPower());
        telemetry.update();

        //This code turns the intake ON and OFF

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

        //This code changes the motorMultipler

        if (speedChangeBumper && isSpeedHalf == false && hasSpeedToggled == false) {
            isSpeedHalf = true;
            motorMutiplier = 0.5;
        }
        if (speedChangeBumper && isSpeedHalf && hasSpeedToggled == true) {
            isSpeedHalf = false;
            motorMutiplier = 1;
        }
        if (speedChangeBumper == false && isSpeedHalf) {
            hasSpeedToggled = true;
        }
        if (speedChangeBumper == false && isSpeedHalf == false) {
            hasSpeedToggled = false;
        }

        //Reverse shooting motor
       if (gamepad2.left_trigger > 0.1 && count == 0 ){
            Shooter.setPower(0.5*gamepad2.left_trigger);
        }
        if (gamepad2.left_trigger < 0.1 && count == 0 ){
            Shooter.setPower(0);
        }
        //setting trigger
        trigger = gamepad2.right_trigger;

        //this code is used for the trigger
        if (trigger > 0) {
            hammerServo.setPosition(servoMax);
        }
        else {
            hammerServo.setPosition(servoMin);
        }
        if (gamepad2.y){
            wobbleServo.setPosition(.25);

        }
        else {
            wobbleServo.setPosition(0);
        }
    }
}
















