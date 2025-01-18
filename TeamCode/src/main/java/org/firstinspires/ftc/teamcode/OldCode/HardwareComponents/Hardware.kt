package org.firstinspires.ftc.teamcode.OldCode.HardwareComponents

import com.qualcomm.robotcore.hardware.HardwareMap

class Hardware(hwMap: HardwareMap) {
    val motors = DriveMotors(hwMap)
    val intake = Intake(hwMap)
    val outtake = Outtake(hwMap)

    fun stop(){
        motors.stop()

    }
}