package org.firstinspires.ftc.teamcode.Core.Interfaces;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;

public class SoftwareInterface extends CoreComponent {
    public SoftwareInterface(String cName, Boolean active){
        super(cName, active, ComponentType.SOFTWARE_INTERFACE);
    }
}
