package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import java.lang.Exception

@TeleOp
class TestOuttakeServo: LinearOpMode() {
    override fun runOpMode() {
        val runtime = ElapsedTime()

        telemetry.addData("Status", "Initialized")
        telemetry.update()
        val hwMap = hardwareMap
        var servoUnit = 0.005

        val servoRotR = hwMap.servo["servoRotatieOuttakeR"] ?: throw Exception("Failed to find servo outtakeTest1")
        val servoRotL = hwMap.servo["servoRotatieOuttakeL"] ?: throw Exception("Failed to find servo outtakeTest2")
        val servoArt = hwMap.servo["servoArticulatieOuttake"]

        // 0.7 start
        servoRotR.position = 0.5 // 0.645
        servoRotL.position =0.5
        servoArt.position = 0.7

        waitForStart()
        runtime.reset()

// 0.45 right
        // 0.55 left
        val gp1 = Gamepad(gamepad1)
        val gp2 = Gamepad(gamepad2)
        while (opModeIsActive()) {

            if(gp1.checkToggle(Gamepad.Button.DPAD_DOWN)) {
                servoArt.position += servoUnit
            }
            if(gp1.checkToggle(Gamepad.Button.DPAD_UP)) {
                servoArt.position -=servoUnit
            }
            if(gp1.checkToggle(Gamepad.Button.B)) {
                servoRotR.position += servoUnit
                servoRotL.position -=servoUnit
            }
            if(gp1.checkToggle(Gamepad.Button.Y)) {
                servoRotR.position -= servoUnit
                servoRotL.position +=servoUnit
            }
            telemetry.addData("Status(servoArt ): ", servoArt.position)
            telemetry.addData("Status(servoRotR ): ", servoRotR.position)
            telemetry.addData("Status(servoRotL): ", servoRotL.position)

            telemetry.update()
        }

    }
}