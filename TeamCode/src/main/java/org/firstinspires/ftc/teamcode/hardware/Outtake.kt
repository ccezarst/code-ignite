/*package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.hardware.rev.RevTouchSensor
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.TouchSensor

import org.firstinspires.ftc.robotcore.external.Telemetry
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.util.*
import kotlin.math.absoluteValue
import com.qualcomm.robotcore.hardware.DistanceSensor


/**
 * OutTake subsystem.
 *
 * This class controls the hardware for placing freight
 */

class Outtake(hwMap: HardwareMap) {
    companion object {

        // New
        const val sliderHigh = 3000;
        const val sliderLow = 0;
        const val intakeServoLeft = 1.0
        const val intakeServoRight = 0.0

        const val SLIDER_HIGHL = 700
        const val SLIDER_MEDIUML= 500
        const val SLIDER_LOWL= 200

        const val claw1Closed =0.55
        const val claw2Closed =0.45

        const val claw1Opened =0.53
        const val claw2Opened = 0.47

        /*** AUTO ***/
        //START
         const val openGate =0.6320 // 0.648




    }

    private val outtakeMotor = hwMap.dcMotor["outtakeMotor"]
            ?: throw java.lang.Exception("Failed to find motor outtakeMotor")
    private val outtakeMotor1 = hwMap.dcMotor["outtakeMotor1"]
            ?: throw java.lang.Exception("Failed to find motor outtakeMotor")

    private val outtakeIntake = hwMap.dcMotor["outtakeIntake"]
            ?: throw Exception("Failed to find motor outtakeIntake")
    private val outtakeIntake1 = hwMap.dcMotor["outtakeIntake1"]
            ?: throw Exception("Failed to find motor outtakeIntake")

    private val outtakeClaw1 = hwMap.servo["outtakeClaw1"]
            ?: throw Exception("Failed to find motor outtakeIntake")
    private val outtakeClaw2 = hwMap.servo["outtakeClaw2"]
            ?: throw Exception("Failed to find motor outtakeIntake")
    private val outtakeGate = hwMap.servo["outtakeGate"]
            ?: throw Exception("Failed to find motor outtakeGate")


    var isReleased:Boolean = false
    var outtakePosition: Int = 0

    init {
2
        outtakeIntake.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        outtakeIntake.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        outtakeIntake.direction = DcMotorSimple.Direction.REVERSE
        outtakeIntake.power = 0.0

        outtakeMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        outtakeMotor.direction = DcMotorSimple.Direction.FORWARD
        outtakeMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        outtakeMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        outtakeMotor.power = 0.0



        outtakeMotor1.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        outtakeMotor1.direction = DcMotorSimple.Direction.REVERSE
        outtakeMotor1.mode = DcMotor.RunMode.RUN_USING_ENCODER
        outtakeMotor1.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        outtakeMotor1.power = 0.0

        outtakeIntake1.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        outtakeIntake1.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        outtakeIntake1.direction = DcMotorSimple.Direction.REVERSE
        outtakeIntake1.power = 0.0

        outtakeClaw2.position = claw2Opened
        outtakeClaw1.position = claw1Opened
      outtakePosition = 0
        outtakeGate.position =0.6420 // 0.650
    }
    fun takePixel(){
        isReleased = false
        outtakeClaw1.position = claw1Closed
        outtakeClaw2.position = claw2Closed
    }
    fun openGateAuto()
    {

        outtakeGate.position = openGate
    }

    fun openUp()
    {
        outtakeClaw1.position = 0.51
        outtakeClaw2.position = 0.49
    }
    fun resetEncoders()
    {
        outtakeMotor1.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        outtakeMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

    }
    fun open()
    {
        outtakeClaw1.position = claw1Opened
        outtakeClaw2.position = claw2Opened
    }

    fun dropPixel(){
        isReleased = true
        outtakeClaw1.position = claw1Opened
        outtakeClaw2.position = claw2Opened

    }

    fun togglePixel() {
        if (isReleased) {
            takePixel()
        } else {
            dropPixel()
        }
    }

    fun openSlider() {

        outtakePosition = SLIDER_HIGHL
        outtakeMotor.targetPosition = outtakePosition
        outtakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor.power = 1.0

        outtakePosition = SLIDER_HIGHL
        outtakeMotor1.targetPosition = outtakePosition
        outtakeMotor1.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor1.power = 1.0
    }
    fun openLowSlider()
    {
        outtakePosition = SLIDER_LOWL
        outtakeMotor.targetPosition = outtakePosition
        outtakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor.power = 1.0

        outtakePosition = SLIDER_LOWL
        outtakeMotor1.targetPosition = outtakePosition
        outtakeMotor1.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor1.power = 1.0
    }
    fun openMidSlider()
    {
        outtakePosition = SLIDER_MEDIUML
        outtakeMotor.targetPosition = outtakePosition
        outtakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor.power = 1.0

        outtakePosition = SLIDER_MEDIUML
        outtakeMotor1.targetPosition = outtakePosition
        outtakeMotor1.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor1.power = 1.0
    }


    fun closeSlider()
    {
        outtakePosition = 0
        outtakeMotor.targetPosition = outtakePosition
        outtakeMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor.power = 1.0

        outtakePosition = 0
        outtakeMotor1.targetPosition = outtakePosition
        outtakeMotor1.mode = DcMotor.RunMode.RUN_TO_POSITION
        outtakeMotor1.power = 1.0
    }

    /** FOR NOW **/
    fun openSlider(motorPower : Double) {
        outtakeIntake.power = motorPower
    }

    fun closeSlider(motorPower : Double) {
        outtakeIntake.power = 0.0
    }


    fun openSlider1(motorPower : Double) {
        outtakeIntake1.power = motorPower
    }

    fun closeSlider1(motorPower : Double) {
        outtakeIntake1.power = 0.0
    }
    fun printPosition(telemetry: Telemetry) {
        telemetry.addData("Position outtake 1:",outtakeMotor.currentPosition)
        telemetry.addLine()
        telemetry.addData("Position outtake 2:",outtakeMotor1.currentPosition)

    }
    fun reverseIntake()
    {
        outtakeIntake1.direction = DcMotorSimple.Direction.FORWARD
        outtakeIntake.direction = DcMotorSimple.Direction.FORWARD


    }

    fun normalIntake()
    {
        outtakeIntake.direction = DcMotorSimple.Direction.REVERSE
        outtakeIntake1.direction = DcMotorSimple.Direction.REVERSE

    }

*/



package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.hardware.rev.RevTouchSensor
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.TouchSensor

import org.firstinspires.ftc.robotcore.external.Telemetry
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.util.*
import kotlin.math.absoluteValue
import com.qualcomm.robotcore.hardware.DistanceSensor


/**
 * intake subsystem.
 *
 * This class controls the hardware for placing freight
 */

class Outtake(hwMap: HardwareMap) {
    companion object {
        //SERVOS
        const val intakeClawOpenPosition = 0.80
        const val intakeClawHoldPosition = 0.30


    }
    var isOpen:Boolean = false
    //val touchSensor = hwMap.get(RevTouchSensor::class.java,"touchSensor") ?: throw Exception("Failed to find RevTouchSensor touchSensor")
    private val motorR = hwMap.dcMotor["motorR"]
    private val motorL = hwMap.dcMotor["motorL"]



    //servos

    //private val intakeServoClaw = hwMap.servo["intakeServoClaw"] ?: throw Exception("Failed to find servo intakeServoClaw")

    init {
        motorR.zeroPowerBehavior=DcMotor.ZeroPowerBehavior.FLOAT
        motorR.direction = DcMotorSimple.Direction.FORWARD
        motorR.mode=DcMotor.RunMode.RUN_USING_ENCODER
        motorR.mode=DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motorR.power =0.0


        motorL.zeroPowerBehavior=DcMotor.ZeroPowerBehavior.FLOAT
        motorL.direction = DcMotorSimple.Direction.REVERSE
        motorL.mode=DcMotor.RunMode.RUN_USING_ENCODER
        motorL.mode=DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motorL.power =0.0
    }

    fun openSlider()
    {
        motorR.targetPosition = 500
        motorR.mode = DcMotor.RunMode.RUN_TO_POSITION
        motorR.power = 1.0


        motorL.targetPosition = 500
        motorL.mode = DcMotor.RunMode.RUN_TO_POSITION
        motorL.power = 1.0
    }

    fun closeSlider()
    {
        motorR.targetPosition = 0
        motorR.mode = DcMotor.RunMode.RUN_TO_POSITION
        motorR.power = 1.0


        motorL.targetPosition = 0
        motorL.mode = DcMotor.RunMode.RUN_TO_POSITION
        motorL.power = 1.0
    }


    /* fun releaseIntakePixel() {
         isOpen = true
         intakeServoClaw.position = intakeClawOpenPosition
     }

     fun holdIntakePixel() {
         isOpen = false
         intakeServoClaw.position = intakeClawHoldPosition
     }

     fun toggleIntakePixel() {
         if (isOpen) {
             holdIntakePixel()
         } else {
             releaseIntakePixel()
         }
     }
     */












}
