package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.HardwareComponents.Hardware

abstract class OpMode: LinearOpMode() {

    protected val hw by lazy {
        Hardware(hardwareMap)
    }

    final override fun runOpMode() {
       //  hw.stop()

        preInit()


        while(!isStarted && !isStopRequested) {
            preInitLoop()
        }

        waitForStart()

        if (!opModeIsActive())
            return

        hw.run()

        //hw.stop()
    }

    open fun preInit() {}

    open fun preInitLoop() {}

    //Runs the op mode.
    abstract fun Hardware.run()
}

fun LinearOpMode.waitMillis(millis: Long) {
    val timer = ElapsedTime()
    while (opModeIsActive() && timer.milliseconds() <= millis)
        idle()
}