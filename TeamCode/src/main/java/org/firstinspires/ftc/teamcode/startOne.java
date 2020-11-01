package org.firstinspires.ftc.teamcode;

import com.google.gson.internal.$Gson$Preconditions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;


@Autonomous(name = "StartOne", group = "StartOne")
public class startOne extends LinearOpMode {

    DriveClass moveClark = new DriveClass();
    //TensorFlowWebcam TF = new TensorFlowWebcam();
    private ElapsedTime runtime = new ElapsedTime();
    int timeoutS = 5000;
    int newRightTarget;
    int newLeftTarget;
    int ringState = 0;
    //short

    String howManyRings;
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    String ringType;
    private static final String VUFORIA_KEY = "AfYhcgT/////AAABmY16qBV+VUYup/dTff2epNNJVPrr6uAhiU+zTVjCmmRK0D7Gbushq8A3zvGHLjMYQAnPMfHjH5lsZeNxs5fv7lqc4ZlfOiwIONYnMsBXyQlNRrlL22Xsm01kFIMJNeF64yt2R5ztxYyb9DdHI+ycINWovCtyx1+d2bvleCzUbj81z3gL9YWdzIfGxfOY6mbqEfo1ZozuuF104RKaUI6GtlNxXBGEkTBXMjxqEnzDKxtuIOwz43rHDKNVkluOxFbMZRG3mfcHIVk0egf5vC9v+0LoGf+tavMfpuMzMvkZ1nZakPKWUc2TOgOIhIaBMNCsCK/vXOB6fr+iGVjdegkGq2hlTBFo7/eqBim1VCAlZzt5";
    private TFObjectDetector tfod;
    private VuforiaLocalizer vuforia;


    // HardwareMap hardwareMap = null; Do not do this. It broke it
    @Override
    public void runOpMode() throws InterruptedException {
            /*For DriveClass.encoder
            state 1 = stop and reset encoders
            state 2 = set target position
            state 3 = run to position
            state 4 = strafe right
            state 5 = strafe left
             */

        moveClark.hardwareSetup(hardwareMap);
        //TF.tensorFlowMain(hardwareMap);
        initVuforia();
        initTfod();
        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();
        }
        waitForStart();
        updateScreen();


        switch (ringState) {
            case 0:
                //Go to A
                 goToPosition(871, 0.25, 5);
                  //goToPosition(3600, 0.25, 3);
                break;
            case 1:
                //Go to B
                //  goToPosition(4516, 0.25, 3);
                break;
            case 2:
                //Go to C
                //   goToPosition(871, 0.25, 5);
                //   goToPosition(5300, 0.25, 3);
                break;
        }
    }

    public void encoders(int target, double speed, int state) throws InterruptedException {

        if (state == 2) {
            moveClark.topRight.setTargetPosition(target);
            moveClark.topLeft.setTargetPosition(target);
            moveClark.bottomRight.setTargetPosition(target);
            moveClark.bottomLeft.setTargetPosition(target);
            moveClark.speed = speed;
        }
        if (state == 4) {

            moveClark.topRight.setTargetPosition(target);
            moveClark.topLeft.setTargetPosition(-target);
            moveClark.bottomRight.setTargetPosition(-target);
            moveClark.bottomLeft.setTargetPosition(target);
            moveClark.speed = speed;
            moveClark.encoderMove(3);
        }
        if (state == 5) {
            moveClark.topRight.setTargetPosition(-target);
            moveClark.topLeft.setTargetPosition(target);
            moveClark.bottomRight.setTargetPosition(target);
            moveClark.bottomLeft.setTargetPosition(-target);
            moveClark.speed = speed;
            moveClark.encoderMove(3);
        }
        moveClark.encoderMove(state);
        if (state >= 3) {
            moveRobot();
        }
    }

    public void moveRobot() {
        timeoutS = 50;
        while (opModeIsActive() &&
                moveClark.topLeft.isBusy() && moveClark.topRight.isBusy() &&
                moveClark.bottomLeft.isBusy() && moveClark.bottomRight.isBusy()) {

            // Display it for the driver.
            telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
            newLeftTarget = moveClark.topLeft.getCurrentPosition();
            newRightTarget = moveClark.topRight.getCurrentPosition();
            telemetry.update();
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
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        //waitForStart();

        runtime.reset();
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
    }
}

