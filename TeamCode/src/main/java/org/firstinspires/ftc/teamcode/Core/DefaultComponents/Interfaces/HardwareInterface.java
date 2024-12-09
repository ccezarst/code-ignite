package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

public abstract class HardwareInterface extends CoreComponent {
    public final InterfaceType interfaceType;
    public HardwareInterface(String cName, Boolean active, DefaultCore core, InterfaceType interfaceTye){
        super(cName, active, core, ComponentType.HARDARE_INTERFACE);
        this.interfaceType = interfaceTye;
    }
}
