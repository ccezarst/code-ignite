package org.firstinspires.ftc.teamcode.Core.DefaultComponents;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Core.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Gamepad;
import org.opencv.core.Core;

import java.util.ArrayList;
import java.util.Map;

public class StateMachineButtonMapper extends ButtonMapper {
    private final ButtonMapperKeybindMap[] keybinds;
    private final StateMachine st;
    public StateMachineButtonMapper(String name, Boolean active, StateMachine st, ButtonMapperKeybindMap... bt){
        // mapping is gamepadNr-(button-status)
        super(name, active); // no need to specify type as this extends button mapper which sets the type in it's constructor
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
    public ArrayList<String> getStatus(){
        ArrayList<String> toReturn = new ArrayList<>();
        for(int i = 0; i < this.keybinds.length; i++){
            for(int b = 0; b < this.keybinds[i].keybinds.keySet().size(); b++){
                toReturn.add(this.keybinds[i].gamepadNr + "-" + this.keybinds[i].keybinds.keySet().toArray()[i] + " -> " + this.keybinds[i].keybinds.get(this.keybinds[i].keybinds.keySet().toArray()[i]));
            }
        }
        return toReturn;
    }
}
