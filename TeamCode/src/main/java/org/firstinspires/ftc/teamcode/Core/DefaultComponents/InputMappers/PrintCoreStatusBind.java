package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

public class PrintCoreStatusBind extends OneButtonMapper {
    private Telemetry telem;
    public PrintCoreStatusBind(int gamepadNr, DefaultCore core, Telemetry telem){
        super(Gamepad.Button.START, true, gamepadNr,"Core status printer", true, core);
        this.telem = telem;
    }
    @Override
    public void callback(){
        this.telem.addLine(this.core.getStatus());
    }
}
