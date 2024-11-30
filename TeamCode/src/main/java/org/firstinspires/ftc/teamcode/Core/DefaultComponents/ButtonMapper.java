package org.firstinspires.ftc.teamcode.Core.DefaultComponents;

import org.firstinspires.ftc.teamcode.Gamepad;

public class ButtonMapper extends CoreComponent{
    public ButtonMapper(String name, Boolean active){
        super(name, active, ComponentType.BUTTON_MAPPER);
    }

    public void buttonToggle(Gamepad gp, Gamepad.Button btn){}
    public void buttonHold(Gamepad gp, Gamepad.Button btn){}

}
