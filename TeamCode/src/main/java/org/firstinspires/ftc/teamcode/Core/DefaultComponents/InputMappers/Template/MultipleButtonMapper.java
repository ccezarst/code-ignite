package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class MultipleButtonMapper extends InputMapper{
    private final ButtonTypes[] btns;
    private final int inputSourceID;
    private Map<ButtonTypes, Boolean> pressed = new HashMap<>();
    private Map<ButtonTypes, Boolean> toggle = new HashMap<>();
    public MultipleButtonMapper(String name, Boolean active, DefaultCore core, int inputSourceID, ButtonTypes... button) {
        super(name, active, core);
        for(int i = 0; i < button.length; i++){
            this.pressed.put(button[i], false);
            this.pressed.put(button[i], false);
        }
        this.btns = button;
        this.inputSourceID = inputSourceID;
    }

    @Override
    public abstract void step(DefaultCore core);

    public abstract void buttonPressed(ArrayList<ButtonTypes> btns);
    public abstract void buttonDown(ArrayList<ButtonTypes> btns);
    public abstract void buttonUp(ArrayList<ButtonTypes> btns);
    public abstract void buttonToggle(ArrayList<ButtonTypes> btns);

    @Override
    public final void statesUpdated() {
        ArrayList<ButtonTypes> pressed = new ArrayList<>();
        ArrayList<ButtonTypes> down = new ArrayList<>();
        ArrayList<ButtonTypes> up = new ArrayList<>();
        ArrayList<ButtonTypes> toggle = new ArrayList<>();
        for(int i = 0; i < this.btns.length; i++){
            if(Objects.requireNonNull(this.states.get(this.inputSourceID)).getButtonState(this.btns[i])){
                if(this.pressed.get(this.btns[i])){
                    down.add(this.btns[i]);
                }else{
                    this.pressed.remove(this.btns[i]);
                    this.pressed.put(this.btns[i], true);
                    pressed.add(this.btns[i]);
                    down.add(this.btns[i]);
                    this.toggle.put(this.btns[i], !this.toggle.remove(this.btns[i]));
                    if(this.toggle.get(this.btns[i])){
                        toggle.add(this.btns[i]);
                    }
                }
            }else{
                if(this.pressed.get(this.btns[i])){
                    this.pressed.remove(this.btns[i]);
                    this.pressed.put(this.btns[i], false);
                    up.add(this.btns[i]);
                }
            }
        this.buttonPressed(pressed);
        this.buttonDown(down);
        this.buttonUp(up);
        this.buttonToggle(toggle);
        }
    }
}
