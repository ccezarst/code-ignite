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

class Intake(hwMap: HardwareMap) {
    companion object {
        //SERVOS
        const val intakeClawOpenPosition = 0.80
        const val intakeClawHoldPosition = 0.30


    }
    var isOpen:Boolean = false
    //val touchSensor = hwMap.get(RevTouchSensor::class.java,"touchSensor") ?: throw Exception("Failed to find RevTouchSensor touchSensor")

    var intakePosition: Int = 0
    private val motor1 = hwMap.dcMotor["intakeRight"]
    private val motor2 = hwMap.dcMotor["intakeLeft"]


    private val servoHang = hwMap.servo["outtakeTest"]



    //servos
    private val intakeSliderServoRight = hwMap.servo["intakeSliderServoRight"] ?: throw Exception("Failed to find servo intakeSliderServoRight")
    private val intakeSliderServoLeft = hwMap.servo["intakeSliderServoLeft"] ?: throw Exception("Failed to find servo intakeSliderServoLeft")
    private val intakeDifServoRight = hwMap.servo["intakeDifServoRight"] ?: throw Exception("Failed to find servo intakeSliderServoRight")
    private val intakeDifServoLeft = hwMap.servo["intakeDifServoLeft"] ?: throw Exception("Failed to find servo intakeSliderServoLeft")
    //private val intakeServoClaw = hwMap.servo["intakeServoClaw"] ?: throw Exception("Failed to find servo intakeServoClaw")

    init {


        motor1.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        motor1.mode = DcMotor.RunMode.RUN_USING_ENCODER
        motor1.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor1.direction = DcMotorSimple.Direction.REVERSE
        motor1.power = 0.0

        motor2.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        motor2.mode = DcMotor.RunMode.RUN_USING_ENCODER
        motor2.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor2.direction = DcMotorSimple.Direction.FORWARD
        motor2.power = 0.0

        //servo
        intakeSliderServoRight.position = 0.52
        intakeSliderServoLeft.position = 0.48
        intakeDifServoRight.position = 0.18
        intakeDifServoLeft.position = 0.73
    }

    fun extendSlider(){
        intakePosition += 50
        motor1.targetPosition = intakePosition
        motor1.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor1.power = 0.8

        motor2.targetPosition = intakePosition
        motor2.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor2.power = 0.8
    }

    fun openSlider() {

        intakePosition =  550
        motor1.targetPosition = intakePosition
        motor1.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor1.power = 0.8

        motor2.targetPosition = intakePosition
        motor2.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor2.power = 0.8
    }
    fun RetractSlider(){
            intakePosition -= 50
            motor1.targetPosition = intakePosition
            motor1.mode = DcMotor.RunMode.RUN_TO_POSITION
            motor1.power = 1.0

            motor2.targetPosition = intakePosition
            motor2.mode = DcMotor.RunMode.RUN_TO_POSITION
            motor2.power = 1.0

    }


    fun closeSlider() {

        intakePosition = 0
        motor1.targetPosition = intakePosition
        motor1.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor1.power = 1.0

        intakePosition = 0
        motor2.targetPosition = intakePosition
        motor2.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor2.power = 1.0
    }


    fun increaseSliderServo(){
        intakeSliderServoRight.position += 0.005
        intakeSliderServoLeft.position -= 0.005
    }

    fun decreaseSliderServo(){
        intakeSliderServoRight.position -= 0.005
        intakeSliderServoLeft.position += 0.005
    }

    fun rotateDif(direction: Boolean){
        if (direction){
            intakeDifServoRight.position += 0.005
            intakeDifServoLeft.position += 0.005
        }else{
            intakeDifServoRight.position -= 0.005
            intakeDifServoLeft.position -= 0.005
        }
    }

    fun difUp(){
        intakeDifServoRight.position += 0.005
        intakeDifServoLeft.position -= 0.005
    }

    fun difDown(){
        intakeDifServoRight.position -= 0.005
        intakeDifServoLeft.position += 0.005
    }

    fun pos(telemetry: Telemetry)
    {
        telemetry.addData("SliderRight:",intakeSliderServoRight.position)
        telemetry.addData("SliderLeft",intakeSliderServoLeft.position)
        telemetry.addData("DifRight",intakeDifServoRight.position)
        telemetry.addData("DifLeft",intakeDifServoLeft.position)
        telemetry.addData("Motor1:",motor1.currentPosition)
        telemetry.addData("Motor2:",motor2.currentPosition)
        telemetry.update()

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
