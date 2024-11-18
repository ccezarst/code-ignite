package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.hardware.Hardware
import java.lang.Math.atan2

@TeleOp(name = "CompleteDrive", group = "Main")
class CompleteDrive: OpMode() {

    override fun preInit() {
    }
    override fun preInitLoop() {
        //   hw.outtake.initSlider()
        //   hw.outtake.initClaws()
        telemetry.addLine("Waiting for start...")
        telemetry.update()
        idle()
    }


    enum class LiftState {;


        enum class Intake {

            LIFT_START,
            LIFT_LOW,

            LIFT_MEDIUM,
            LIFT_UP,
        }

        enum class Outtake {

            LIFT_START,
            LIFT_LOW,

            LIFT_MEDIUM,
            LIFT_UP,
        }
    };



    override fun Hardware.run() {

        val gp1 = Gamepad(gamepad1)
        val gp2 = Gamepad(gamepad2)


        var leftTriggerIsPressed = false
        var rightTriggerIsPressed = false
        var scale= 0.5 // 0.6
        var ok:Int = 0

        var Timer = ElapsedTime()
        Timer.reset()

        //TODO Adjust these variables according to the needs

        var DUMP_TIME_LOW = 0.1
        var DUMP_TIME_UP = 0.2
        var DUMP_TIME_MEDIUM = 0.9


        var liftStateIntake = LiftState.Intake.LIFT_START
        var liftStateOuttake = LiftState.Outtake.LIFT_START


        var isSlowMode:Boolean = false
        var k:Boolean = false
        var k1:Boolean = false


        var isReleased = false
        waitForStart()
        while(opModeIsActive()) {

            /*** Gp1 - control sasiu + glisiere orizontale
             *  Gp2- control glisiere verticale + in oglinda - dr gamepad outtake, stanga ganepad cu Dpad-uri intake
             */
            val power = speed
            val rotPower = rotation
            if(gp1.right_trigger > 0.2)
                hw.motors.move(direction,power*scale,rotPower*scale)
            else if(gp1.left_trigger>0.2)
                hw.motors.move(direction,power*0.3,rotPower*0.3)
            else{
                hw.motors.move(direction, power*1.0, rotPower*1.0) // normal driving
            }



            if(gp2.checkToggle(Gamepad.Button.Y))
                outtake.toggleOuttakePixel()

            if(gp2.checkToggle(Gamepad.Button.DPAD_UP))
                intake.toggleIntakeSample()

            if(gp2.checkToggle(Gamepad.Button.X))
                outtake.pozTranzitie()

            if(gp2.checkToggle(Gamepad.Button.DPAD_LEFT))
                intake.pozTranzitie()

            if(gp2.checkToggle(Gamepad.Button.B))
                outtake.pozPus()

            if(gp2.checkToggle(Gamepad.Button.DPAD_RIGHT))
                intake.toggleSample()





            intake.showPositions(telemetry)
            /*** Glisiere intake ***/
            when (liftStateIntake) {

                LiftState.Intake.LIFT_START -> {

                    if (gp1.checkToggle(Gamepad.Button.RIGHT_BUMPER)) {
                        intake.lowSlider()
                        liftStateIntake = LiftState.Intake.LIFT_LOW
                    }
                }

                LiftState.Intake.LIFT_LOW -> {

                    if (gp1.checkToggle(Gamepad.Button.RIGHT_BUMPER)) {
                        intake.midSlider()
                        liftStateIntake = LiftState.Intake.LIFT_MEDIUM

                    }
                    if (gp1.checkToggle(Gamepad.Button.LEFT_BUMPER)) {
                        intake.closeSlider()
                        liftStateIntake = LiftState.Intake.LIFT_START

                    }
                    if (gp1.checkToggle(Gamepad.Button.DPAD_DOWN)) {
                        intake.closeSlider()
                        liftStateIntake = LiftState.Intake.LIFT_START


                    }
                }

                LiftState.Intake.LIFT_MEDIUM -> {
                    if (gp1.checkToggle(Gamepad.Button.RIGHT_BUMPER)) {
                        intake.openSlider()
                        liftStateIntake = LiftState.Intake.LIFT_UP

                    }
                    if (gp1.checkToggle(Gamepad.Button.DPAD_DOWN)) {
                        intake.closeSlider()
                        liftStateIntake = LiftState.Intake.LIFT_START

                    }
                    if (gp1.checkToggle(Gamepad.Button.LEFT_BUMPER)) {
                        intake.lowSlider()
                        liftStateIntake = LiftState.Intake.LIFT_LOW

                    }
                }

                LiftState.Intake.LIFT_UP -> {

                    if (gp1.checkToggle(Gamepad.Button.DPAD_DOWN)) {
                        intake.closeSlider()
                        liftStateIntake = LiftState.Intake.LIFT_START

                    }
                    if (gp1.checkToggle(Gamepad.Button.LEFT_BUMPER)) {
                        intake.midSlider()
                        liftStateIntake = LiftState.Intake.LIFT_MEDIUM

                    }

                }
            }

            /*** Glisiere outtake ***/
            when (liftStateOuttake) {

                LiftState.Outtake.LIFT_START -> {

                    if (gp2.checkToggle(Gamepad.Button.RIGHT_BUMPER)) {
                        outtake.lowSlider()
                        liftStateOuttake = LiftState.Outtake.LIFT_LOW
                    }
                }

                LiftState.Outtake.LIFT_LOW -> {

                    if (gp2.checkToggle(Gamepad.Button.RIGHT_BUMPER)) {
                        outtake.midSlider()
                        liftStateOuttake = LiftState.Outtake.LIFT_MEDIUM

                    }
                    if (gp2.checkToggle(Gamepad.Button.LEFT_BUMPER)) {
                        outtake.closeSlider()
                        liftStateOuttake = LiftState.Outtake.LIFT_START

                    }
                    if (gp2.checkToggle(Gamepad.Button.DPAD_DOWN)) {
                        outtake.closeSlider()
                        liftStateOuttake = LiftState.Outtake.LIFT_START


                    }
                }

                LiftState.Outtake.LIFT_MEDIUM -> {
                    if (gp2.checkToggle(Gamepad.Button.RIGHT_BUMPER)) {
                        outtake.openSlider()
                        liftStateOuttake = LiftState.Outtake.LIFT_UP

                    }
                    if (gp2.checkToggle(Gamepad.Button.DPAD_DOWN)) {
                        outtake.closeSlider()
                        liftStateOuttake = LiftState.Outtake.LIFT_START

                    }
                    if (gp2.checkToggle(Gamepad.Button.LEFT_BUMPER)) {
                        outtake.lowSlider()
                        liftStateOuttake = LiftState.Outtake.LIFT_LOW

                    }
                }

                LiftState.Outtake.LIFT_UP -> {

                    if (gp2.checkToggle(Gamepad.Button.DPAD_DOWN)) {
                        outtake.closeSlider()
                        liftStateOuttake = LiftState.Outtake.LIFT_START

                    }
                    if (gp2.checkToggle(Gamepad.Button.LEFT_BUMPER)) {
                        outtake.midSlider()
                        liftStateOuttake = LiftState.Outtake.LIFT_MEDIUM

                    }

                }
            }

        }
    }

    ///The direction in which the robot is translating
    private val direction: Double
        get() {
            val x = +gamepad1.left_stick_x.toDouble()  // -
            val y = +gamepad1.left_stick_y.toDouble() // +

            return atan2(y, x) / Math.PI * 180.0 - 90.0
        }

    /// Rotation around the robot's Z axis.
    private val rotation: Double
        get() = -gamepad1.right_stick_x.toDouble()  // -

    /// Translation speed.
    private val speed: Double
        get() {
            val x = gamepad1.left_stick_x.toDouble() //+
            val y = gamepad1.left_stick_y.toDouble() //+

            return Math.sqrt((x * x) + (y * y))
        }



}