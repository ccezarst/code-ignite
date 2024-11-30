package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;

public class StateMachineButtonMapper extends ButtonMapper {
    private final ButtonMapperKeybindMap[] keybinds;
    private final StateMachine st;
    public StateMachineButtonMapper(String name, Boolean active, StateMachine st, DefaultCore core, ButtonMapperKeybindMap... bt){
        // mapping is gamepadNr-(button-status)
        super(name, active, core); // no need to specify type as this extends button mapper which sets the type in it's constructor
        this.keybinds = bt;
        this.st = st; // single state machine
        // this is specified in code and not searched for automatically
        // to allow multiple independent state machines
    }


    @Override
    public void buttonHold(Gamepad gp, Gamepad.Button btn){
        for(int i = 0; i < this.keybinds.length; i++){
            if(this.keybinds[i].gamepadNr == gp.getNumber()){
                if(keybinds[i].keybinds.containsKey(btn)){
                    this.st.changeState(keybinds[i].keybinds.get(btn));
                }
            }
        }
    }

    @Override
    public void buttonToggle(Gamepad gp, Gamepad.Button btn) {}
    @Override
    public ArrayList<String> getStatus(){
        ArrayList<String> toReturn = new ArrayList<>();
        for(int i = 0; i < this.keybinds.length; i++){
            for(int b = 0; b < this.keybinds[i].keybinds.keySet().size(); b++){
                toReturn.add(this.keybinds[i].gamepadNr + "-" + this.keybinds[i].keybinds.keySet().toArray()[i] + " -> " + this.keybinds[i].keybinds.get(this.keybinds[i].keybinds.keySet().toArray()[i]));
            }
        }
        return toReturn;
    }

    @Override
    public void step(DefaultCore core) {}

    @Override
    public void update(DefaultCore core) {}
}
