package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveClassRamp {

    public DcMotor topLeft = null;
    public DcMotor bottomLeft = null;
    public DcMotor bottomRight = null;
    public DcMotor topRight = null;
    public double speed = 0.25;
    int CurrPosition;
    int newTarget;
    double MaxSpeed;
    int target = 0;
    double speedBr;
    double speedBl;
    double speedTr;
    double speedTl;
    HardwareMap hwMap = null; //<-You for sure need this in order to pass the HW methods


    public void hardwareSetup(HardwareMap hwMap) {
        //hwMap =ahwMap;
            /*The above works but is not needed. If you use it, change the hwMap
            in the above line to ahwMap*/
           /*
        compBot
        hub2 motors 0: bottomLeft, 1: topLeft, 2: topRight, 3: bottomRight
         */
        topLeft = hwMap.dcMotor.get("topLeft");
        topRight = hwMap.dcMotor.get("topRight");
        bottomRight = hwMap.dcMotor.get("bottomRight");
        bottomLeft = hwMap.dcMotor.get("bottomLeft");
        bottomRight.setDirection(DcMotorSimple.Direction.REVERSE);
        topRight.setDirection(DcMotorSimple.Direction.REVERSE);


           /*This all works. Do not change it. This is what I used to begin with.
           I wanted to try the above code because it made sense.
            topLeft = hwMap.get(DcMotor.class,"topLeft");
            topRight = hwMap.get(DcMotor.class,"topRight");
            bottomRight = hwMap.get(DcMotor.class,"bottomRight");
            bottomLeft = hwMap.get(DcMotor.class,"bottomLeft");
            bottomLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            topLeft.setDirection(DcMotorSimple.Direction.REVERSE);*/
    }

    public void Drive(double tL, double tR, double bL, double bR) {
        topLeft.setPower(tL);
        topRight.setPower(tR);
        bottomLeft.setPower(bL);
        bottomRight.setPower(bR);
    }

    public void motorStop() {
        topLeft.setPower(0);
        topRight.setPower(0);
        bottomRight.setPower(0);
        bottomLeft.setPower(0);
    }

    public void motorGo() {
        topLeft.setPower(speed);
        topRight.setPower(speed);
        bottomRight.setPower(speed);
        bottomLeft.setPower(speed);
    }

    public int encoderMove(int state) throws InterruptedException {
        final double COUNTS_PER_MOTOR_REV = 538;
        final double WHEEL_DIAMETER_INCHES = 4.0;

        switch (state) {
            case 1:
                motorStop();
                topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                bottomLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                bottomRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                break;
            case 2:
                topLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                bottomLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                topRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                bottomRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                break;
            case 3:
                topLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                bottomLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                topRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                bottomRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                MaxSpeed = speed;
                speed = 0;
                newTarget = Math.abs(bottomLeft.getTargetPosition()/3);
                while (topLeft.isBusy() && topRight.isBusy() &&
                        bottomLeft.isBusy() && bottomRight.isBusy()){

                        CurrPosition = Math.abs(topRight.getCurrentPosition());

                    if(CurrPosition <= newTarget) {
                        speed = speed + 0.01;
                    }
                    if(CurrPosition >= newTarget && CurrPosition <= newTarget*2 ){
                        speed = MaxSpeed;
                    }
                    if(CurrPosition >= newTarget*2 ){
                        speed = speed - 0.01;
                    }

                    topLeft.setPower(speed);
                    topRight.setPower(speed);
                    bottomLeft.setPower(speed);
                    bottomRight.setPower(speed);



                }


                topLeft.setPower(speed);
                topRight.setPower(speed);
                bottomLeft.setPower(speed);
                bottomRight.setPower(speed);
                break;
            default:
                break;
        }
        return 0;
    }

}

