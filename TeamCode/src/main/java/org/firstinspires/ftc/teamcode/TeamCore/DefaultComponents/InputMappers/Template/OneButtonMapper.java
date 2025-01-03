package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.Template;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;

import java.util.ArrayList;
import java.util.Objects;

public abstract class OneButtonMapper extends InputMapper{
    private final ButtonTypes btn;
    private final int inputSourceID;
    private boolean pressed = false;
    private boolean toggle = false;
    public OneButtonMapper(String name, Boolean active, TeamCore core, ButtonTypes button, int inputSourceID) {
        super(name, active, core);
        this.btn = button;
        this.inputSourceID = inputSourceID;
    }

    @Override
    public void step(TeamCore core){
    };

    public abstract void buttonPressed();
    public abstract void buttonDown();
    public abstract void buttonUp();
    public abstract void buttonToggle();
    @Override
    public final ArrayList<String> getStatus(){
        ArrayList<String> caca = new ArrayList<>();
        caca.add("pressed:" + this.pressed);
        caca.add("toggled:" + this.toggle);
        return caca;
    }
    @Override
    public final void statesUpdated() {
        if(Objects.requireNonNull(this.states.get(this.inputSourceID)).getButtonState(this.btn)){
            if(this.pressed){
                this.buttonDown();
                if(this.toggle){
                    this.buttonToggle();
                }
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
            if(this.toggle){
                this.buttonToggle();
            }
        }
    }
}
