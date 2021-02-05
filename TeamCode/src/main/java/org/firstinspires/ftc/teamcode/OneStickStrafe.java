package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.security.KeyStore;

@TeleOp(name = "Teleop1", group = "Training")
public class OneStickStrafe extends OpMode {

    DcMotor backRightMotor;
    DcMotor backLeftMotor;
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor Shooter;
    DcMotor Intake;
    DcMotor Arm;
    Servo hammerServo;
    Servo wobbleServo;
    Servo clawServo;

    boolean isIntakeOn;
    boolean IntakeON;
    boolean wobbleSet;
    boolean iswobbleSet;
    boolean speedChangeBumper;
    boolean hasSpeedToggled;
    boolean isSpeedHalf;
    boolean IhaveRampedUp = false;
    boolean IhaveRampedDown = false;
    float trigger;
    double x;
    double rx;
    double y;
    int count = 0;
    boolean hascount = false;
    boolean hasToggled = false;
    boolean WSHasToggled = false;
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
    double g2RY;
    double g2LY;
    double motorMutiplier = 1;
    double lastRYval;
    double lastRXval;
    double lastLYval;
    double lastLXval;

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
        clawServo = hardwareMap.servo.get("claw");
        Arm = hardwareMap.dcMotor.get("arm");
        clawServo.setPosition(0.37); //closed the claw all the way


        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        Intake.setDirection(DcMotorSimple.Direction.REVERSE);
        Shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        //Shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    //this is the main loop for the teleOP program when play is pressed
    @Override
    public void loop() {
        g2RY = gamepad2.right_stick_y;
        g2LY = gamepad2.left_stick_y;

        y = gamepad1.left_stick_y;
        x = gamepad1.left_stick_x;
        rx = gamepad1.right_stick_x;

// This if statement is to help the robot ramp up
        if(isSpeedHalf == false) {
            if ((Math.abs(y) >= 0.5 || Math.abs(x) >= 0.5) && IhaveRampedUp == false) {
                runtime.reset();
                runtime.startTime();
                while (runtime.milliseconds() < 200) {
                    motorMutiplier = 0.2;
                    frontLeftMotor.setPower(motorMutiplier * (y - x - rx));
                    backLeftMotor.setPower(motorMutiplier * (y + x - rx));
                    frontRightMotor.setPower(motorMutiplier * (y + x + rx));
                    backRightMotor.setPower(motorMutiplier * (y - x + rx));
                    IhaveRampedUp = true;
                    IhaveRampedDown = false;
                }
            } else {
                motorMutiplier = 1;
            }
        }
// This if statement is to help the robot ramp down
        if (isSpeedHalf == false) {
            if (((Math.abs(y) < lastLYval && Math.abs(y) < 0.1)
                    || (Math.abs(x) < lastLXval && Math.abs(x) < 0.1)) && IhaveRampedDown == false) {
                runtime.reset();
                runtime.startTime();
                while (runtime.milliseconds() < 200) {
                    motorMutiplier = 0.2;
                    frontLeftMotor.setPower(motorMutiplier * (y - x - rx));
                    backLeftMotor.setPower(motorMutiplier * (y + x - rx));
                    frontRightMotor.setPower(motorMutiplier * (y + x + rx));
                    backRightMotor.setPower(motorMutiplier * (y - x + rx));
                    IhaveRampedDown = true;
                    IhaveRampedUp = false;
                }
            } else {
                motorMutiplier = 1;
            }
        }


        frontLeftMotor.setPower(motorMutiplier * (y - x - rx));
        backLeftMotor.setPower(motorMutiplier * (y + x - rx));
        frontRightMotor.setPower(motorMutiplier * (y + x + rx));
        backRightMotor.setPower(motorMutiplier * (y -  x + rx));

      /* if(g2LY>.1 || g2LY <-.1) {
           Arm.setPower(g2LY * 0.55);
       }*/
      // if (g2RY> .1|| g2RY <-.1) {
           Arm.setPower(g2RY);
       //}

        IntakeON = gamepad2.a;
        speedChangeBumper = gamepad1.right_bumper;

        dpadUp = gamepad2.dpad_up;
        dpadDown = gamepad2.dpad_down;
        dpadRight = gamepad2.dpad_right;
        dpadLeft = gamepad2.dpad_left;

        wobbleSet = gamepad2.y;

        if (dpadUp || dpadDown || dpadLeft || dpadRight) {
            if (dpadUp) {
                if (hascount == false) {
                    if (count < 4) {
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
                    count = 4;
                }
            }
            if (dpadLeft) {
                if (hascount == false) {
                    hascount = true;
                    count = 5;
                    Shooter.setPower(0);
                }
            }

        }
       if (!dpadUp && !dpadDown &&
                !dpadLeft && !dpadRight) {
            hascount = false;
            if (count == 5) {
                count = 0;
            }
        }
       //The following adjusts power for the shooting wheel. 1-10-21
        switch (count) {
            case 4:
                Shooter.setPower(-0.6);
                telemetry.addData("Shooter", Shooter.getPower());
                break;
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
        telemetry.addData("wobbleSet", clawServo.getPosition());

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
        if (IntakeON && isIntakeOn && hasToggled) {
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


       if (wobbleSet && iswobbleSet == false && WSHasToggled == false) {
            clawServo.setPosition(0.37);//close
            iswobbleSet = true;
        }
        if (wobbleSet && iswobbleSet && WSHasToggled) {
            iswobbleSet = false;
            clawServo.setPosition(0.49);//open
        }
        if (wobbleSet == false && iswobbleSet) {
            WSHasToggled = true;
        }
        if (wobbleSet == false && iswobbleSet == false) {
            WSHasToggled = false;
        }
        lastRYval = gamepad1.right_stick_y;
        lastRXval = gamepad1.right_stick_x;
        lastLYval = gamepad1.left_stick_y;
        lastLXval = gamepad1.left_stick_x;
    }
}
















