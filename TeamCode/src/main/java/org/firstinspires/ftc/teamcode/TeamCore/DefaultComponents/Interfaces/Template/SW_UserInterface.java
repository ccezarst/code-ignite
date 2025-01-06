package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template;

import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;

// class to handle user-interactions
public abstract class SW_UserInterface extends InputMapperSoftwareInterface implements UserInterfaceMethodDefinitions {
    public SW_UserInterface(String cName, Boolean active, TeamCore core) {
        super(cName, active, core, InterfaceType.USER_INTERFACE);
    }

}
