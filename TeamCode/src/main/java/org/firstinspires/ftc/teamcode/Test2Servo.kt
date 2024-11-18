package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import java.lang.Exception

@TeleOp
class Test2Servo: LinearOpMode() {
    override fun runOpMode() {
        val runtime = ElapsedTime()

        telemetry.addData("Status", "Initialized")
        telemetry.update()
        val hwMap = hardwareMap
        var servoUnit = 0.005

        val servoRotR = hwMap.servo["servoRotatieR"] ?: throw Exception("Failed to find servo outtakeTest1")
        val servoRotL = hwMap.servo["servoRotatieL"] ?: throw Exception("Failed to find servo outtakeTest2")
        val servoDifR = hwMap.servo["servoDiferentialR"]
        val servoDifl = hwMap.servo["servoDiferentialL"]

        // 0.7 start
          servoRotR.position = 0.4358 // 0.645
          servoRotL.position =0.4378

        servoDifR.position = 0.535
        servoDifl.position = 0.575
        waitForStart()
        runtime.reset()

// 0.45 right
        // 0.55 left
        val gp1 = Gamepad(gamepad1)
        val gp2 = Gamepad(gamepad2)
        while (opModeIsActive()) {


            if(gp1.checkToggle(Gamepad.Button.DPAD_UP))
            {
                servoDifR.position += servoUnit
                servoDifl.position -=servoUnit
            }
            if(gp1.checkToggle(Gamepad.Button.DPAD_DOWN))
            {
                servoDifR.position -= servoUnit
                servoDifl.position +=servoUnit
            }

            if(gp1.checkToggle(Gamepad.Button.DPAD_LEFT))
                {
                    servoDifR.position -= servoUnit
                    servoDifl.position -=servoUnit
                }
            if(gp1.checkToggle(Gamepad.Button.DPAD_RIGHT))
            {
                servoDifR.position += servoUnit
                servoDifl.position +=servoUnit
            }

            if(gp1.checkToggle(Gamepad.Button.X)) {
                servoRotR.position += servoUnit
                servoRotL.position -=servoUnit
            }
            if(gp1.checkToggle(Gamepad.Button.Y)) {
                servoRotR.position -= servoUnit
                servoRotL.position +=servoUnit
            }

            if (gp1.checkToggle(Gamepad.Button.B)){
                servoRotL.position = 0.3928
                servoRotR.position = 0.4806
            }
            if (gp1.checkToggle(Gamepad.Button.A)){
                servoRotL.position = 0.4378
                servoRotR.position = 0.4358
            }



            telemetry.addData("Status(servoDifR ): ", servoDifR.position)
            telemetry.addData("Status(servoDifL): ", servoDifl.position)
            telemetry.addData("Status(servoRotR ): ", servoRotR.position)
            telemetry.addData("Status(servoRotL): ", servoRotL.position)

            telemetry.update()
        }

    }
}