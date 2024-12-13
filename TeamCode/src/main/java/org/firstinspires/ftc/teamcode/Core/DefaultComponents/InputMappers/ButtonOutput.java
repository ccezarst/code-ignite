package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

public class ButtonOutput extends ButtonMapper {
    private Telemetry telem;
    public ButtonOutput(String name, Boolean active, DefaultCore core, Telemetry telem) {
        super(name, active, core);
        this.telem = telem;
    }

    @Override
    public void buttonToggle(Gamepad gp, Gamepad.Button btn) {
    }

    @Override
    public void buttonHold(Gamepad gp, Gamepad.Button btn) {
        telem.addLine("Gamepad " + gp.getNumber() + ": " + btn);
    }

    @Override
    public void step(DefaultCore core) {

    }

    @Override
    public void update(DefaultCore core) {

    }
}
