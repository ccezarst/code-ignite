package org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Template;

import EngineCore.EngineCore;

// class to handle user-interactions
public abstract class SW_UserInterface extends SoftwareInterface implements UserInterfaceMethodDefinitions {
    public SW_UserInterface(String cName, Boolean active, EngineCore core) {
        super(cName, active, core, InterfaceType.USER_INTERFACE);
    }

}
