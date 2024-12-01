package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.Map;

public abstract class AnalogMapper extends CoreComponent {

    public AnalogMapper(String name, Boolean active, DefaultCore core){
        super(name, active, ComponentType.BUTTON_MAPPER, core);
    }
    public final void primitiveAnalog(Gamepad gp, Map<Gamepad.Analog, Double> in){
        if(this.active){
            this.analog(gp, in);
        }
    }

    public abstract void analog(Gamepad gp, Map<Gamepad.Analog, Double> in);
}
