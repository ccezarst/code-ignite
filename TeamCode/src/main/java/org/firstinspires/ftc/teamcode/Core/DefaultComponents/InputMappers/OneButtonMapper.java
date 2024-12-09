package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

// maps a button to a callback
public class OneButtonMapper extends ButtonMapper {
    private Gamepad.Button btn;

    private int gamepadNr;
    private Boolean hold;
    public OneButtonMapper(Gamepad.Button btn, Boolean hold, int gamepadNr, String name, Boolean active, DefaultCore core){
        super(name, active, core);
        this.btn = btn;
        this.hold = hold;
        this.gamepadNr = gamepadNr;
    }

    public void callback(){}

    @Override
    public final void buttonHold(Gamepad gp, Gamepad.Button btn){
        if(this.hold && gp.getNumber() == this.gamepadNr && btn == this.btn){
            this.callback();
        }
    }

    @Override
    public final void buttonToggle(Gamepad gp, Gamepad.Button btn){
        if(!this.hold && gp.getNumber() == this.gamepadNr && btn == this.btn){
            this.callback();
        }
    }

    @Override
    public void step(DefaultCore core) {}

    @Override
    public void update(DefaultCore core) {}
}
