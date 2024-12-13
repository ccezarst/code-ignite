package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

public abstract class SoftwareInterface extends CoreComponent {

    public SoftwareInterface(String cName, Boolean active, DefaultCore core, InterfaceType interfaceTye){
        super(cName, active, core, ComponentType.SOFTWARE_INTERFACE);
    }

}
