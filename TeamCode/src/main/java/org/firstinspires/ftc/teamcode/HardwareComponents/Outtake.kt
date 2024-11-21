package org.firstinspires.ftc.teamcode.HardwareComponents

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap

import org.firstinspires.ftc.robotcore.external.Telemetry


/**
 * OutTake subsystem.
 *
 * This class controls the hardware for placing freight
 */

class Outtake(hwMap: HardwareMap) {
    companion object {
        /***Pozitii de tranzitie***/
        const val servoRotatieL_down = 0.54
        const val servoRotatieR_down = 0.46
        const val servoArt_down = 0.78

        /*** Pozitii luat specimen***/
        const val servoRotatieL_specimen = 0.58
        const val servoRotatieR_specimen = 0.42
        const val servoArt_specimen = 0.685

        /***Pozitii de pus***/
        const val servoRotatieL_up = 0.62
        const val servoRotatieR_up = 0.38
        const val servoArt_up = 0.70

        /*** Poz cleste ***/
        const val cleste_open = 0.40
        const val cleste_closed = 0.10


        const val putere_motoare = 0.7

    }
    private val motorOuttakeR = hwMap.dcMotor["motorOuttakeR"]
        ?: throw Exception("Failed to find motor motorOuttakeR")

    private val motorOuttakeL = hwMap.dcMotor["motorOuttakeL"]
        ?: throw Exception("Failed to find motor motorOuttakeL")


    private val servoRotatieOuttakeR = hwMap.servo["servoRotatieOuttakeR"]
        ?: throw Exception("Failed to find servo servoRotatieR")

    private val servoRotatieOuttakeL = hwMap.servo["servoRotatieOuttakeL"]
        ?: throw Exception("Failed to find servo servoRotatieL")

    private val servoArticulatieOuttake = hwMap.servo["servoArticulatieOuttake"]
        ?: throw Exception("Failed to find servo servoRotatieL")

    private val servoOuttakeCleste = hwMap.servo["servoOuttakeCleste "]
        ?: throw Exception("Failed to find servo servoOuttakeCleste ")


    /*** PT diferential, setezi pozitia de start ale servo-urilor ca pozitia la care ar lua, si doar prima data :
     * rotesti clestele ( cresti un sevrvo,scazi altul) si apoi dai peste cap ; pt intoarcere faci pasul invers;
     * te folosesti de adunare/scadere, nu de poz
     */







    var isOpen:Boolean = true
    var eSus:Boolean =true
    init {
        motorOuttakeL.zeroPowerBehavior=DcMotor.ZeroPowerBehavior.FLOAT
        motorOuttakeL.mode=DcMotor.RunMode.RUN_USING_ENCODER
        motorOuttakeL.mode=DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motorOuttakeL.direction=DcMotorSimple.Direction.REVERSE // de schimbat in REVERSE daca e gresta directia
        motorOuttakeL.power=0.0


        motorOuttakeR.zeroPowerBehavior=DcMotor.ZeroPowerBehavior.FLOAT
        motorOuttakeR.mode=DcMotor.RunMode.RUN_USING_ENCODER
        motorOuttakeR.mode=DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motorOuttakeR.direction=DcMotorSimple.Direction.FORWARD // de schimbat in FORWARD daca e gresta directia
        motorOuttakeR.power=0.0


        servoArticulatieOuttake.position = servoArt_down
        servoOuttakeCleste.position = cleste_open
        servoRotatieOuttakeR.position = servoRotatieR_down
        servoRotatieOuttakeL.position = servoRotatieL_down
    }
    /*** de modificat pozitiile de target ***/
    /*** COD GLISIERA ***/
    fun openSlider()
    {
        motorOuttakeL.targetPosition = 1900
        motorOuttakeL.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorOuttakeL.power= putere_motoare

        motorOuttakeR.targetPosition = 1900
        motorOuttakeR.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorOuttakeR.power=putere_motoare

    }

    fun midSlider()
    {
        motorOuttakeR.targetPosition = 1400
        motorOuttakeR.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorOuttakeR.power=putere_motoare

        motorOuttakeL.targetPosition =1400
        motorOuttakeL.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorOuttakeL.power=putere_motoare

    }

    fun lowSlider()
    {
        motorOuttakeR.targetPosition = 700
        motorOuttakeR.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorOuttakeR.power=putere_motoare

        motorOuttakeL.targetPosition = 700
        motorOuttakeL.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorOuttakeL.power=putere_motoare

    }

    fun closeSlider()
    {
        motorOuttakeR.targetPosition = 0
        motorOuttakeR.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorOuttakeR.power=putere_motoare

        motorOuttakeL.targetPosition = 0
        motorOuttakeL.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorOuttakeL.power=putere_motoare

    }


    /*** COD SERVO ***/
    fun releaseOuttakeSample() {
        isOpen = true
        servoOuttakeCleste.position = cleste_open
    }

    fun holdOuttakeSample() {
        isOpen = false
        servoOuttakeCleste.position = cleste_closed
    }

    fun toggleOuttakePixel() {
        if (isOpen) {
            holdOuttakeSample()
        } else {
            releaseOuttakeSample()
        }
    }

    fun takeSpecimen(){
        servoRotatieOuttakeR.position = servoRotatieR_specimen
        servoRotatieOuttakeL.position = servoRotatieL_specimen
        servoArticulatieOuttake.position = servoArt_specimen
    }


    fun pozTranzitie()
    {
        motorOuttakeR.targetPosition = 400
        motorOuttakeR.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorOuttakeR.power=putere_motoare

        motorOuttakeL.targetPosition = 400
        motorOuttakeL.mode=DcMotor.RunMode.RUN_TO_POSITION
        motorOuttakeL.power=putere_motoare

        servoRotatieOuttakeR.position = servoRotatieR_down
        servoRotatieOuttakeL.position = servoRotatieL_down
        servoArticulatieOuttake.position = servoArt_down
    }
    fun pozPus(){
        servoRotatieOuttakeR.position = servoRotatieR_up
        servoRotatieOuttakeL.position = servoRotatieL_up
        servoArticulatieOuttake.position = servoArt_up
    }

    fun showPositions(telemetry: Telemetry)
    {
        telemetry.addData("OuttakeMotorRight:",motorOuttakeR.currentPosition,)
        telemetry.addData("OuttakeMotorLeft:",motorOuttakeL.currentPosition)
        telemetry.addData("ServoRotR:",servoRotatieOuttakeR.position)
        telemetry.addData("ServoRotL:",servoRotatieOuttakeL.position)
        telemetry.addData("ServoArt:",servoArticulatieOuttake.position)
        telemetry.addData("ServoCleste:",servoOuttakeCleste.position)
        telemetry.update()
    }


}