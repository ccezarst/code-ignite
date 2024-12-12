package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

public abstract class HardwareInterface extends Interface {
    public HardwareInterface(String cName, Boolean active, DefaultCore core, InterfaceType interfaceTye){
        super(cName, active, core, interfaceTye, ComponentType.HARDARE_INTERFACE, ComponentType.INTERFACE);
    }
}
