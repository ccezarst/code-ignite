package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

public class SW_Telemetry extends SoftwareInterface {
    public final Telemetry telemetry;

    public SW_Telemetry(String cName, Boolean active, DefaultCore core, Telemetry telem) {
        super(cName, active, core, InterfaceType.TELEMETRY);
        this.telemetry = telem;
    }

    @Override
    public void step(DefaultCore core) {

    }

    @Override
    public void update(DefaultCore core) {

    }
}
