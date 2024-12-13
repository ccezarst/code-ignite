package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

public abstract class Interface extends CoreComponent {
    public final InterfaceType interfaceType;
    public Interface(String name, Boolean active, DefaultCore core, InterfaceType interfType, ComponentType... type) {
        super(name, active, core, type);
        this.interfaceType = interfType;
    }
}
