package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

public abstract class OneButtonMapper extends InputMapper{
    private final ButtonTypes btn;
    private final int inputSourceID;
    private boolean pressed = false;
    private boolean toggle = false;
    public OneButtonMapper(String name, Boolean active, DefaultCore core, ButtonTypes button, int inputSourceID) {
        super(name, active, core);
        this.btn = button;
        this.inputSourceID = inputSourceID;
    }

    @Override
    public abstract void step(DefaultCore core);

    public abstract void buttonPressed();
    public abstract void buttonDown();
    public abstract void buttonUp();
    public abstract void buttonToggle();

    @Override
    public final void statesUpdated() {
        if(this.states.get(this.inputSourceID).getButtonState(this.btn)){
            if(this.pressed){
                this.buttonDown();
            }else{
                this.pressed = true;
                this.buttonPressed();
                this.buttonDown();
                this.toggle = !this.toggle;
                if(this.toggle){
                    this.buttonToggle();
                }
            }
        }else{
            if(this.pressed){
                this.pressed = false;
                this.buttonUp();
            }
        }
    }
}
