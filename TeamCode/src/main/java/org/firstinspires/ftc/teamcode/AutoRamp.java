package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Auto Ramp", group = "Training")
public class AutoRamp extends LinearOpMode {
    DcMotor topLeft;
    DcMotor bottomLeft;
    DcMotor bottomRight;
    ;
    DcMotor topRight;
    double mPower;

    @Override
    public void runOpMode() throws InterruptedException {

        topLeft = hardwareMap.dcMotor.get("topLeft");
        topRight = hardwareMap.dcMotor.get("topRight");
        bottomRight = hardwareMap.dcMotor.get("bottomRight");
        bottomLeft = hardwareMap.dcMotor.get("bottomLeft");


        while (mPower < 1) {

            topLeft.setPower(mPower);
            topRight.setPower(-mPower);
            bottomLeft.setPower(mPower);
            bottomRight.setPower(-mPower);
            mPower = mPower + 0.001;
        }
        topLeft.setPower(1);
        topRight.setPower(-1);
        bottomLeft.setPower(1);
        bottomRight.setPower(-1);
        sleep(5000);
        mPower = 1;

        while (mPower > 0) {

            topLeft.setPower(mPower);
            topRight.setPower(-mPower);
            bottomLeft.setPower(mPower);
            bottomRight.setPower(-mPower);
            mPower = mPower - 0.001;
        }
        topLeft.setPower(0);
        topRight.setPower(0);
        bottomLeft.setPower(0);
        bottomRight.setPower(0);
    }
}
