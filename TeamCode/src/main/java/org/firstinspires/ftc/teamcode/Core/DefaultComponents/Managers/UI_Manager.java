package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.Interface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SW_UserInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;

public class UI_Manager extends CoreComponent {
    public ArrayList<Interface> interfs;
    public UI_Manager(String name, Boolean active, DefaultCore core, ComponentType... type) {
        super(name, active, core, type);
    }



    @Override
    public void step(DefaultCore core) {

    }

    @Override
    public void update(DefaultCore core) {
        this.interfs = this.core.getInterfacesOfType(InterfaceType.USER_INTERFACE);
    }
}
