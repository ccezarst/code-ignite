package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.StateMachineButtonMapper;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template.MultipleButtonMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;
import java.util.Map;

public class StateMachineButtonMapper extends MultipleButtonMapper {
    private final StateMachineButtonMapperKeybinds keybinds;
    private final StateMachine st;
    public StateMachineButtonMapper(String name, Boolean active, String stName, DefaultCore core, StateMachineButtonMapperKeybinds keys){
        // mapping is gamepadNr-(button-status)
        super(name, active, core, keys.inputSourceID, keys.actionTypes.keySet().toArray(new ButtonTypes[0])); // no need to specify type as this extends button mapper which sets the type in it's constructor
        this.keybinds = keys;
        this.st = (StateMachine) this.core.getComponentFromName(stName); // single state machine
        // this is specified in code and not searched for automatically
        // to allow multiple independent state machines
    }

    @Override
    public ArrayList<String> getStatus(){
        ArrayList<String> toReturn = new ArrayList<>();
        for(Map.Entry<ButtonTypes, String> entry : this.keybinds.parameters.entrySet()){
            toReturn.add(entry.getKey().name() + ": " + this.keybinds.actionTypes.get(entry.getKey()) + " - " + entry.getValue());
        }
        return toReturn;
    }

    @Override
    public void step(DefaultCore core) {}

    @Override
    public void buttonPressed(ArrayList<ButtonTypes> btns) {
        for(ButtonTypes btn: btns){
            if(this.keybinds.parameters.containsKey(btn)){
                if(this.keybinds.actionTypes.get(btn) == STMBMK_ActionTypes.CHANGE_STATE){
                    st.changeState(this.keybinds.parameters.get(btn));
                }else{
                    st.changeStateBasedOnCurrent(Integer.parseInt(this.keybinds.parameters.get(btn)));
                }
            }
        }
    }

    @Override
    public void buttonDown(ArrayList<ButtonTypes> btns) {

    }

    @Override
    public void buttonUp(ArrayList<ButtonTypes> btns) {

    }

    @Override
    public void buttonToggle(ArrayList<ButtonTypes> btns) {

    }
}
