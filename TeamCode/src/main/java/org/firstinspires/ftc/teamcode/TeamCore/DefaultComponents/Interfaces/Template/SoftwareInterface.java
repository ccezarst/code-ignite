package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;

public abstract class SoftwareInterface extends Interface {
    public SoftwareInterface(String cName, Boolean active, TeamCore core, InterfaceType interfaceTye){
        super(cName, active, core, interfaceTye, ComponentType.SOFTWARE_INTERFACE);
    }

}
