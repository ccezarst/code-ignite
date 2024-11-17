package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import java.lang.Exception

@TeleOp
class TestServo: LinearOpMode() {
    override fun runOpMode() {
        val runtime = ElapsedTime()

        telemetry.addData("Status", "Initialized")
        telemetry.update()
        val hwMap = hardwareMap

        val servoTest = hwMap.servo["outtakeTest"] ?: throw Exception("Failed to find servo outtakeTest1")
        servoTest.position = 0.0

        waitForStart()
        runtime.reset()

        val gp1 = Gamepad(gamepad1)

        while (opModeIsActive()) {
            if(gp1.checkToggle(Gamepad.Button.DPAD_UP))
            {
                servoTest.position += 0.01
            }

            if(gp1.checkToggle(Gamepad.Button.DPAD_DOWN))
            {
                servoTest.position -= 0.01
            }
            if(gp1.checkToggle(Gamepad.Button.X))
                servoTest.position = 0.40




            telemetry.addData("Status", servoTest.position)
            telemetry.update()
        }
    }
}