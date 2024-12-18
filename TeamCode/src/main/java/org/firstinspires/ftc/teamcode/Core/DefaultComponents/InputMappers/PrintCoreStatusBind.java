package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template.OneButtonMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SW_UserInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

public class PrintCoreStatusBind extends OneButtonMapper {
    private SW_UserInterface ui;
    public PrintCoreStatusBind(int inputSourceID, DefaultCore core, Telemetry telem){
        super("PrintCoreStatusBind", true, core, ButtonTypes.START, inputSourceID);
        this.ui = (SW_UserInterface) (this.core.getInterfacesOfType(InterfaceType.USER_INTERFACE).get(0));
    }

    @Override
    public void step(DefaultCore core) {

    }

    @Override
    public void buttonPressed() {

    }

    @Override
    public void buttonDown() {
    }

    @Override
    public void buttonUp() {

    }

    @Override
    public void buttonToggle() {
        ((UI_Manager)this.core.getComponentFromName("UI_Manager")).showWarning(this.core.getStatus());

    }
}
