package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.StateMachineButtonMapper;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;

import java.util.Map;
import java.util.Vector;

public class StateMachineButtonMapperKeybinds {
    public final int inputSourceID;
    public final Map<ButtonTypes, STMBMK_ActionTypes> actionTypes;

    public final Map<ButtonTypes, String> parameters;

    public StateMachineButtonMapperKeybinds(int inputSourceID, Map<ButtonTypes, STMBMK_ActionTypes> actionTypes, Map<ButtonTypes, String> parameters){
        this.inputSourceID = inputSourceID;
        this.actionTypes = actionTypes;
        this. parameters = parameters;
    }
}
