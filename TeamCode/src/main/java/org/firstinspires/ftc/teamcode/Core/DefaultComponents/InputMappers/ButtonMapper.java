package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

public abstract class ButtonMapper extends CoreComponent {
    public ButtonMapper(String name, Boolean active, DefaultCore core){
        super(name, active, ComponentType.BUTTON_MAPPER, core);
    }

    public abstract void buttonToggle(Gamepad gp, Gamepad.Button btn);
    public abstract void buttonHold(Gamepad gp, Gamepad.Button btn);

}
