package org.firstinspires.ftc.teamcode.OldCode.hardware

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap

import org.firstinspires.ftc.robotcore.external.Telemetry


/**
 * OutTake subsystem.
 *
 * This class controls the hardware for placing freight
 */

class Intake(hwMap: HardwareMap) {
    companion object {
        /***Pozitii de tranzitie***/
        const val servoRotatieL_up = 0.4378
        const val servoRotatieR_up = 0.4358
        const val servoDifR_up = 0.40
        const val servoDifL_up = 0.224


        /***Pozitie cu care tii intake-ul putin deasupra sample-ului, urmand sa il lasi jos cu poz de luat***/
        const val servoRotatieL_toggle = 0.4028
        const val servoRotatieR_toggle = 0.4706
        const val servoDifR_toggle = 0.284
        const val servoDifL_toggle = 0.334


        /***Pozitii de luat***/
        const val servoRotatieL_down = 0.3858
        const val servoRotatieR_down = 0.4856

        /*** Poz cleste ***/
        const val cleste_open = 0.5
        const val cleste_closed = 0.0



    }
    private val motorIntakeR = hwMap.dcMotor["motorIntakeR"]
        ?: throw Exception("Failed to find motor motorIntakeR")

    private val motorIntakeL = hwMap.dcMotor["motorIntakeL"]
        ?: throw Exception("Failed to find motor motorIntakeL")


    private val servoRotatieR = hwMap.servo["servoRotatieR"]
        ?: throw Exception("Failed to find servo servoRotatieR")

    private val servoRotatieL = hwMap.servo["servoRotatieL"]
        ?: throw Exception("Failed to find servo servoRotatieL")

    private val servoDiferentialR = hwMap.servo["servoDiferentialR "]
        ?: throw Exception("Failed to find servo servoDiferentialR ")

    private val servoDiferentialL = hwMap.servo["servoDiferentialL "]
        ?: throw Exception("Failed to find servo servoDiferentialL ")

    /*** PT diferential, setezi pozitia de start ale servo-urilor ca pozitia la care ar lua, si doar prima data :
     * rotesti clestele ( cresti un sevrvo,scazi altul) si apoi dai peste cap ; pt intoarcere faci pasul invers;
     * te folosesti de adunare/scadere, nu de poz
     */

    private val servoIntakeCleste = hwMap.servo["servoIntakeCleste "]
        ?: throw Exception("Failed to find servo servoIntakeCleste ")


    private val senzorTouch = hwMap.colorSensor["senzorIntake"]
        ?: throw Exception("Failed to find the intake color sensor")


    var isOpen:Boolean = true
    var eSus:Boolean =true
    init {
        motorIntakeL.zeroPowerBehavior=DcMotor.ZeroPowerBehavior.FLOAT
        motorIntakeL.mode=DcMotor.RunMode.RUN_USING_ENCODER
        motorIntakeL.mode=DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motorIntakeL.direction=DcMotorSimple.Direction.FORWARD // de schimbat in REVERSE daca e gresta directia
        motorIntakeL.power=0.0


        motorIntakeR.zeroPowerBehavior=DcMotor.ZeroPowerBehavior.FLOAT
        motorIntakeR.mode=DcMotor.RunMode.RUN_USING_ENCODER
        motorIntakeR.mode=DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motorIntakeR.direction=DcMotorSimple.Direction.REVERSE // de schimbat in FORWARD daca e gresta directia
        motorIntakeR.power=0.0

        //servos
        servoRotatieL.position = servoRotatieL_toggle
        servoRotatieR.position = servoRotatieR_toggle

        servoDiferentialL.position = servoDifL_toggle
        servoDiferentialR.position = servoDifR_toggle

        servoIntakeCleste.position = cleste_open

    }
    /*** de modificat pozitiile de target ***/
    /*** COD GLISIERA ***/
    fun openSlider()
    {
        motorIntakeR.targetPosition = 600
        motorIntakeR.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorIntakeR.power=1.0

        motorIntakeL.targetPosition = 600
        motorIntakeL.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorIntakeL.power=1.0

    }

    fun midSlider()
    {
        motorIntakeR.targetPosition = 400
        motorIntakeR.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorIntakeR.power=1.0

        motorIntakeL.targetPosition = 400
        motorIntakeL.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorIntakeL.power=1.0

    }

    fun lowSlider()
    {
        motorIntakeR.targetPosition = 200
        motorIntakeR.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorIntakeR.power=1.0

        motorIntakeL.targetPosition = 200
        motorIntakeL.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorIntakeL.power=1.0

    }

    fun closeSlider()
    {
        motorIntakeR.targetPosition = 0
        motorIntakeR.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorIntakeR.power=1.0

        motorIntakeL.targetPosition = 0
        motorIntakeL.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorIntakeL.power=1.0

    }


    /*** COD SERVO ***/
    fun releaseIntakeSample() {
        isOpen = true
        servoIntakeCleste.position = cleste_open
    }

    fun holdIntakeSample() {
        isOpen = false
        servoIntakeCleste.position = cleste_closed
    }

    fun toggleIntakeSample() {
        if (isOpen) {
            holdIntakeSample()
        } else {
            releaseIntakeSample()
        }
    }


    /*** de adaugat poz pt diferential***/
    fun pozTranzitie()
    {
        servoRotatieL.position= servoRotatieL_up
        servoRotatieR.position= servoRotatieR_up
        servoDiferentialL.position = servoDifL_up
        servoDiferentialR.position = servoDifR_up

    }
    fun pozDeasupraSample()
    {
        servoRotatieL.position= servoRotatieL_toggle
        servoRotatieR.position= servoRotatieR_toggle
        servoDiferentialL.position = servoDifL_toggle
        servoDiferentialR.position = servoDifR_toggle
    }
    fun pozLuat()
    {
        servoRotatieL.position= servoRotatieL_down
        servoRotatieR.position= servoRotatieR_down
    }

    fun deasupraSample() {
        eSus = true
        pozDeasupraSample()
    }

    fun luatSample() {
        eSus= false
        pozLuat()
    }

    fun toggleSample() {
        if (eSus) {
            luatSample()
        } else {
            deasupraSample()
        }
    }

    fun showPositions(telemetry: Telemetry) {
        telemetry.addData("IntakeMotorRight:",motorIntakeR.currentPosition,)
        telemetry.addData("IntakeMotorLeft:",motorIntakeL.currentPosition)
        telemetry.addData("ServoRotR:",servoRotatieR.position)
        telemetry.addData("ServoRotL:",servoRotatieL.position)
        telemetry.addData("ServoDifR:",servoDiferentialR.position)
        telemetry.addData("ServoDifL:",servoDiferentialL.position)
        telemetry.addData("ServoCleste:",servoIntakeCleste.position)
    }




}