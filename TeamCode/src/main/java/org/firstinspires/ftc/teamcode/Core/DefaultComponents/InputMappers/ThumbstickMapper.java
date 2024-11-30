package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

public abstract class ThumbstickMapper extends CoreComponent {

    public ThumbstickMapper(String name, Boolean active, DefaultCore core){
        super(name, active, ComponentType.BUTTON_MAPPER, core);
    }

    public abstract void thumbstick(Gamepad gp, float Lx, float Ly, float Rx, float Ry);
}
