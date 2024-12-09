package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

public abstract class ButtonMapper extends CoreComponent {
    public ButtonMapper(String name, Boolean active, DefaultCore core){
        super(name, active, core, ComponentType.BUTTON_MAPPER);
    }

    public final void primitiveButtonToggle(Gamepad gp, Gamepad.Button btn){
        if(this.active){
            this.buttonToggle(gp, btn);
        }
    }

    public final void primitiveButtonHold(Gamepad gp, Gamepad.Button btn){
        if(this.active){
            this.buttonHold(gp, btn);
        }
    }

    public abstract void buttonToggle(Gamepad gp, Gamepad.Button btn);
    public abstract void buttonHold(Gamepad gp, Gamepad.Button btn);

}
