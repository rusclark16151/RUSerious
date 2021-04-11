 package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import java.util.List;


@Autonomous(name = "L1HighGoal", group = "L1")
public class L1HighGoal extends LinearOpMode {
 
    DriveClass moveClark = new DriveClass();
    //DriveClassRamp moveClark = new DriveClassRamp();

    //TensorFlowWebcam TF = new TensorFlowWebcam();
    private ElapsedTime runtime = new ElapsedTime();
    int timeoutS = 5000;
    int newRightTarget;
    int newLeftTarget;
    int shooterleftTarget;
    int shooterrightTarget;
    int ringState = 0;
    double servoMax = 0.25;
    double servoMin = 0.5;
    Servo hammer;
    DcMotor shooter;
    DcMotor intake;
    int shooterOn = 0;
    Servo wobbleServo;
    boolean toggle = false;
    boolean intakeHasToggled = false;

    //Drive states
    boolean AAE = false;
    boolean ABE = false;
    boolean ACE = false;

    //battery volt
    double bVoltage;

    //Sensor count
    int count = 0;
    boolean hasCount = false;
    NormalizedColorSensor colorSensor;






    //Bennett is short

    String howManyRings;
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    String ringType;
    private static final String VUFORIA_KEY = "AfYhcgT/////AAABmY16qBV+VUYup/dTff2epNNJVPrr6uAhiU+zTVjCmmRK0D7Gbushq8A3zvGHLjMYQAnPMfHjH5lsZeNxs5fv7lqc4ZlfOiwIONYnMsBXyQlNRrlL22Xsm01kFIMJNeF64yt2R5ztxYyb9DdHI+ycINWovCtyx1+d2bvleCzUbj81z3gL9YWdzIfGxfOY6mbqEfo1ZozuuF104RKaUI6GtlNxXBGEkTBXMjxqEnzDKxtuIOwz43rHDKNVkluOxFbMZRG3mfcHIVk0egf5vC9v+0LoGf+tavMfpuMzMvkZ1nZakPKWUc2TOgOIhIaBMNCsCK/vXOB6fr+iGVjdegkGq2hlTBFo7/eqBim1VCAlZzt5";
    private TFObjectDetector tfod;
    private VuforiaLocalizer vuforia;


    @Override
    public void runOpMode() throws InterruptedException {

        //Hardware map configurations
        bVoltage = hardwareMap.voltageSensor.get("Control Hub").getVoltage();
        moveClark.hardwareSetup(hardwareMap);
        hammer = hardwareMap.servo.get("hammer");
        shooter = hardwareMap.dcMotor.get("shooter");
        intake = hardwareMap.dcMotor.get("intake");
        wobbleServo = hardwareMap.servo.get("wobble");
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");



        //TF.tensorFlowMain(hardwareMap);
        initVuforia();
        initTfod();
        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();
            //tfod.setZoom(2.5, 1.78);
            tfod.setZoom(2.5, 1);
        }

        waitForStart();


//Main Code uncomment


        if (toggle == true) {
            ABE = true;
            goToPosition(-500,0.25,3);
            goToPosition(500,0.25,3);

        }
        if (toggle == false) {
        /*goToPosition(800, 0.6, 5);
        goToPosition(-250,0.25,7);
        goToPosition(-300,0.25,3);
        sleep(500);
        goToPosition(200,0.25,3);*/
            //sleep(250);
            //update screen is for tensorFlow to see the rings
            updateScreen();
            //telemetry.addData(String.format("ringtype"), ringState);
            //telemetry.update();
            //updateScreen();


            //wobbleServo.setPosition(0);
            //This switch tells what position to deliver wobble goal
            switch (ringState) {
                case 0:
                    //Go to A
                    telemetry.addData("Path A", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                    /*goToPosition(1100, 0.6, 4);
                    sleep(250);
                    goToPosition(-150, 0.25, 3);*/
                    goToPosition(2550, 0.65 , 3);
                    sleep(250);
                    setWobble();
                    wobbleServo.setPosition(.75);
                    goToPosition(-150, 0.65, 3);
                    sleep(500);
                    goToPosition(-120,0.50, 7);
                    goToPosition(1250, 0.6, 5);

                    if (bVoltage >= 12.8) {
                        trigger(3000, 0.665);
                        trigger(2000, 0.665);
                        trigger(2000, 0.665);
                    }
                    else if (bVoltage < 12.8){
                        //Don't forget to change the first tPower to .72
                        trigger(3000, 0.72);
                        trigger(2000, 0.72);
                        trigger(2000, 0.72);
                    }
                    shooter.setPower(0);
                    goToPosition(700, 0.5, 3);
                    break;
                case 1:
                    //Go to B
                    telemetry.addData("Path B", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                    /*goToPosition(1100, 0.6, 4);
                    sleep(250);
                    goToPosition(-150, 0.25, 3);*/
                    goToPosition(3300, 0.65, 3);
                    sleep(750);
                    goToPosition(1200, 0.5, 5);
                    sleep(500);
                    setWobble();
                    wobbleServo.setPosition(.75);
                    goToPosition(-1100, 0.5, 3);
                    goToPosition(-100,0.5,7);
                    if (bVoltage >= 12.8) {
                        trigger(3000, 0.665);
                        trigger(2000, 0.665);
                        trigger(2000, 0.665);
                    }
                    else if (bVoltage < 12.8){
                        trigger(3000, 0.73);
                        trigger(2000, 0.73);
                        trigger(2000, 0.73);
                    }
                    shooter.setPower(0);
                    shooterleftTarget = moveClark.topLeft.getTargetPosition();
                    shooterrightTarget = moveClark.topRight.getTargetPosition();
                    sleep(250);
                    ABE = true;
                    goToPosition(-1000, 0.25, 3);
                    goToPosition(-1*newLeftTarget, 0.5, 3);
                    if (bVoltage >= 12.8) {
                        trigger(3000, 0.665);
                    }
                    else if (bVoltage < 12.8){
                        trigger(3000, 0.73);
                    }
                    goToPosition(500, 0.5, 3);
                    break;
                case 2:
                    //Go to C
                    telemetry.addData("Path C", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                    /*goToPosition(1000, 0.6, 4);
                    sleep(250);
                    goToPosition(-150, 0.25, 3);*/
                    goToPosition(4400, 0.65,3);
                    sleep(250);
                    setWobble();
                    wobbleServo.setPosition(.75);
                    goToPosition(-1850, 0.65, 3);
                    sleep(500);
                    goToPosition(1250, 0.6, 5);
                   // goToPosition(50, 0.25,6);
                    if (bVoltage >= 12.8) {
                        trigger(3000, 0.665);
                        trigger(2000, 0.665);
                        trigger(2000, 0.665);
                    }
                    else if (bVoltage < 12.8){
                        trigger(3000, 0.72);
                        trigger(2000, 0.72);
                        trigger(2000, 0.72);
                    }
                    shooter.setPower(0);
                    shooterleftTarget = moveClark.topLeft.getTargetPosition();
                    shooterrightTarget = moveClark.topRight.getTargetPosition();
                    sleep(250);
                    goToPosition(-1000, 1, 3);
                    goToPosition(150, 0.5, 3);
                    sleep(250);
                    ACE = true;
                    goToPosition(-600, 0.25, 3);
                    goToPosition(-1*newLeftTarget, 0.5, 3);
                    intake.setPower(0);
                    goToPosition(-225,0.25,7);
                    if (bVoltage >= 12.8) {
                        trigger(2000, 0.665);
                        trigger(1500, 0.665);
                        trigger(1500, 0.665);
                    }
                    else if (bVoltage < 12.8){
                        trigger(2000, 0.72);
                        trigger(1500, 0.72);
                        trigger(1500, 0.72);
                    }
                    ACE = false;
                    goToPosition(1300, 0.85, 3);
                    break;
            }
        }
    }
    public void goToPosition(int Ptarget, double Pspeed, int Pstate) throws InterruptedException {
        encoders(0, 0, 1);
        encoders(Ptarget, Pspeed, 2);
        if (Pstate == 3) {
            encoders(Ptarget, Pspeed, 3);
        }
        if (Pstate == 4) {
            encoders(Ptarget, Pspeed, 4);
        }
        if (Pstate == 5) {
            encoders(Ptarget, Pspeed, 5);
        }
        if (Pstate == 6) {
            encoders(Ptarget, Pspeed, 6);
        }
        if (Pstate == 7) {
            encoders(Ptarget, Pspeed, 7);
        }
        if (Pstate == 8) {
            encoders(Ptarget, Pspeed, 8);
        }
        if (Pstate == 9) {
            encoders(Ptarget, Pspeed, 9);
        }
    }

    public void encoders(int target, double speed, int state) throws InterruptedException {
            /*For DriveClass.encoder
            state 1 = stop and reset encoders
            state 2 = set target position
            state 3 = run to position
            state 4 = strafe left
            state 5 = strafe right
            state 6 = pivot right
            state 7 = pivot left
            state 8 = point turn right
            state 9 = point turn left
             */

        //this is for set target run
        if (state == 2) {
            moveClark.topRight.setTargetPosition(target);
            moveClark.topLeft.setTargetPosition(target);
            moveClark.bottomRight.setTargetPosition(target);
            moveClark.bottomLeft.setTargetPosition(target);
            moveClark.speed = speed;
        }
        //this is for strafe left
        if (state == 4) {
            moveClark.Strafe = true;
            moveClark.topRight.setTargetPosition(target);
            moveClark.topLeft.setTargetPosition(-target);
            moveClark.bottomRight.setTargetPosition(-target);
            moveClark.bottomLeft.setTargetPosition(target);
/*            moveClark.speedTl = speed - 0.2;
            moveClark.speedTr = speed - 0.2;
            moveClark.speedBl = speed - 0.17;
            moveClark.speedBr = speed - 0.18;*/
            moveClark.speedTl = speed;
            moveClark.speedTr = speed;
            moveClark.speedBl = speed;
            moveClark.speedBr = speed;
            moveClark.encoderMove(3);
        }
        //this is for strafe right
        if (state == 5) {
            moveClark.Strafe = true;
            moveClark.topRight.setTargetPosition(-target);
            moveClark.topLeft.setTargetPosition(target);
            moveClark.bottomRight.setTargetPosition(target);
            moveClark.bottomLeft.setTargetPosition(-target);
/*            moveClark.speedTl = speed - 0.2;
            moveClark.speedTr = speed - 0.2;
            moveClark.speedBl = speed - 0.17;
            moveClark.speedBr = speed - 0.18;*/
            moveClark.speedTl = speed;
            moveClark.speedTr = speed;
            moveClark.speedBl = speed;
            moveClark.speedBr = speed;
            moveClark.encoderMove(3);
        }
        //this is for right pivot
        if (state == 6) {
            moveClark.Strafe = false;
            moveClark.topRight.setTargetPosition(0);
            moveClark.topLeft.setTargetPosition(target);
            moveClark.bottomRight.setTargetPosition(0);
            moveClark.bottomLeft.setTargetPosition(target);
            moveClark.speed = speed;
            moveClark.encoderMove(3);
        }
        // this is for left pivot
        if (state == 7) {
            moveClark.Strafe = false;
            moveClark.topRight.setTargetPosition(-target);
            moveClark.topLeft.setTargetPosition(0);
            moveClark.bottomRight.setTargetPosition(-target);
            moveClark.bottomLeft.setTargetPosition(0);
            moveClark.speed = speed;
            moveClark.encoderMove(3);
        }
        // this is for point turn right
        if (state == 8) {
            moveClark.Strafe = false;
            moveClark.topRight.setTargetPosition(-target);
            moveClark.topLeft.setTargetPosition(target);
            moveClark.bottomRight.setTargetPosition(-target);
            moveClark.bottomLeft.setTargetPosition(target);
            moveClark.speed = speed;
            moveClark.encoderMove(3);
        }
        // this is for point turn left
        if (state == 9) {
            moveClark.Strafe = false;
            moveClark.topRight.setTargetPosition(target);
            moveClark.topLeft.setTargetPosition(-target);
            moveClark.bottomRight.setTargetPosition(target);
            moveClark.bottomLeft.setTargetPosition(-target);
            moveClark.speed = speed;
            moveClark.encoderMove(3);
        }
        if (state <= 3) {
            moveClark.Strafe = false;
            moveClark.encoderMove(state);
        }
      if (state >= 3) {
            moveRobot();
        }

    }


    public void moveRobot(){
        timeoutS = 50;
       // moveClark.encoderMove(3);
    //Without intake
    if (!AAE && !ABE && !ACE) {
        while (opModeIsActive() &&
                (moveClark.topLeft.isBusy() && moveClark.topRight.isBusy() &&
                        moveClark.bottomLeft.isBusy() && moveClark.bottomRight.isBusy())) {
            // Display it for the driver.
            // telemetry.addData("voltage", bVoltage);

            newLeftTarget = moveClark.topLeft.getCurrentPosition();
            newRightTarget = moveClark.topRight.getCurrentPosition();
            telemetry.addData("Speed", moveClark.speed);
            telemetry.update();
        }
    }
    //With intake-- Path B
    if (!AAE && ABE && !ACE) {
        while (opModeIsActive() &&
                (moveClark.topLeft.isBusy() && moveClark.topRight.isBusy() &&
                        moveClark.bottomLeft.isBusy() && moveClark.bottomRight.isBusy() && ABE)) {
            // Display it for the driver.
            intake.setPower(-1);
            newLeftTarget = moveClark.topLeft.getCurrentPosition();
            newRightTarget = moveClark.topRight.getCurrentPosition();
            telemetry.addData("Speed", moveClark.speed);
            
            if (count < 1) {
                if (colorSensor instanceof DistanceSensor) {
                    double distance = ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM);
                    telemetry.addData("Distance (cm)", "%.3f", distance);
                    if ((distance < 3) && (hasCount == false)) {
                        hasCount = true;
                    }
                    if ((distance > 3) && (hasCount == true)) {
                        count = count + 1;
                        hasCount = false;
                        intake.setPower(0);
                        ABE = false;
                    }
                    telemetry.addData("count", count);
                    telemetry.addData("intake", intake);

                }
            }

            telemetry.update();
        }
    }
    //With intake-- Path C
        if (!AAE && !ABE && ACE) {
            while (opModeIsActive() &&
                    (moveClark.topLeft.isBusy() && moveClark.topRight.isBusy() &&
                            moveClark.bottomLeft.isBusy() && moveClark.bottomRight.isBusy() && ACE)) {
                // Display it for the driver.
                intake.setPower(-1);
                newLeftTarget = moveClark.topLeft.getCurrentPosition();
                newRightTarget = moveClark.topRight.getCurrentPosition();
                telemetry.addData("Speed", moveClark.speed);

                if (count <= 3) {
                    if (colorSensor instanceof DistanceSensor) {
                        double distance = ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM);
                        telemetry.addData("Distance (cm)", "%.3f", distance);
                        if ((distance < 3) && (hasCount == false)) {
                            hasCount = true;
                        }
                        if ((distance > 3) && (hasCount == true)) {
                            count = count + 1;
                            hasCount = false;
                        }
                        if (count == 3){
                            intake.setPower(0);
                            ACE = false;
                        }
                        telemetry.addData("count", count);
                    }
                }

                telemetry.update();
            }
        }
    }



    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    public void updateScreen() {

        /** Wait for the game to begin */
      /*  telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
      */  //waitForStart();

        runtime.reset();
        timeoutS = 2000;
        while (runtime.milliseconds() < timeoutS) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        ringType = recognition.getLabel();
                        switch (ringType) {
                            case "Single":
                                ringState = 1;
                                telemetry.addData(String.format("ringType Single (%d)", i), ringState);
                                break;
                            case "Quad":
                                ringState = 2;
                                telemetry.addData(String.format("ringType Quad (%d)", i), ringState);
                               break;
                        }
                    }
                    telemetry.update();
                }
            }
        }
        if (ringState == 0) {
            telemetry.addData(String.format("ringType Zero(%d)", 0), "none");
            telemetry.update();
        }
        if (tfod != null) {
            tfod.shutdown();
        }
    }


    public void trigger(int maxTime, double tPower) {
        runtime.reset();
        while (runtime.milliseconds() <= maxTime) {
            shooter.setPower(tPower);
            if (runtime.milliseconds() > (maxTime - 1000) && runtime.milliseconds() <= (maxTime - 500)) {
                hammer.setPosition(servoMax);
            }
            if (runtime.milliseconds() > (maxTime - 500) && runtime.milliseconds() <= maxTime) {
                hammer.setPosition(servoMin);
            }
        }
    }
    public void setWobble() {
        wobbleServo.setPosition(1);
        sleep(1000);
        wobbleServo.setPosition(0);
    }


}


