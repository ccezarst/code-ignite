package org.firstinspires.ftc.teamcode.Core.Interfaces;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;

public class HardwareInterface extends CoreComponent {
    public HardwareInterface(String cName, Boolean active){
        super(cName, active, ComponentType.HARDARE_INTERFACE);
    }
}
