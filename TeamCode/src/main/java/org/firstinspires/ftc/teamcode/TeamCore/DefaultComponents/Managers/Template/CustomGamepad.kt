package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template

import com.qualcomm.robotcore.hardware.Gamepad

class CustomGamepad(private val gp: Gamepad, var number: Int) {
    enum class Button {
        A,
        B,
        X,
        Y,
        START,
        LEFT_BUMPER,
        RIGHT_BUMPER,
        DPAD_UP,
        DPAD_DOWN,
        DPAD_RIGHT,
        DPAD_LEFT
    }
    enum class Analog{
        LEFT_TRIGGER,
        RIGHT_TRIGGER,
        LEFT_STICK_X,
        LEFT_STICK_Y,
        RIGHT_STICK_X,
        RIGHT_STICK_Y
    }


    private var lastStates = Button.values().map { it to false }.toMap().toMutableMap()

    val left_trigger
        get() = gp.left_trigger

    val right_trigger
        get() = gp.right_trigger

    val left_bumper
        get() = gp.left_bumper

    val right_bumper
        get() = gp.right_bumper

    val left_stick_x
        get() = gp.left_stick_x

    val left_stick_y
        get() = gp.left_stick_y

    val right_stick_x
        get() = gp.right_stick_x

    val right_stick_y
        get() = gp.right_stick_y

    val dpad_up
        get() = gp.dpad_up

    val dpad_down
        get() = gp.dpad_down

    val dpad_right
        get() = gp.dpad_right

    val dpad_left
        get() = gp.dpad_left

    fun getAnalog(an: AnalogTypes): Float =
        when (an) {
            AnalogTypes.LEFT_STICK_X -> gp.left_stick_x
            AnalogTypes.LEFT_TRIGGER -> gp.left_trigger
            AnalogTypes.RIGHT_TRIGGER -> gp.right_trigger
            AnalogTypes.LEFT_STICK_Y -> gp.left_stick_y
            AnalogTypes.RIGHT_STICK_X -> gp.right_stick_x
            AnalogTypes.RIGHT_STICK_Y -> gp.right_stick_y
        }

    fun checkHold(button: ButtonTypes): Boolean =
            when (button) {
                ButtonTypes.A -> gp.a
                ButtonTypes.B -> gp.b
                ButtonTypes.X -> gp.x
                ButtonTypes.Y -> gp.y
                ButtonTypes.START -> gp.start
                ButtonTypes.LEFT_BUMPER -> gp.left_bumper
                ButtonTypes.RIGHT_BUMPER -> gp.right_bumper
                ButtonTypes.DPAD_UP -> gp.dpad_up
                ButtonTypes.DPAD_DOWN -> gp.dpad_down
                ButtonTypes.DPAD_RIGHT -> gp.dpad_right
                ButtonTypes.DPAD_LEFT -> gp.dpad_left
                ButtonTypes.BACK -> gp.back
            }

    fun checkToggle(button : Button): Boolean {
        val pressed = true;
        val ok = pressed && (lastStates[button] != pressed)
        lastStates[button] = pressed
        return ok
    }
}
