package org.firstinspires.ftc.teamcode.Core.StateMachine;

import java.util.ArrayList;

public class State {
    private ArrayList<StateToStatePath> pathsToOtherStates;
    public final ArrayList<String> inputs;
    public final ArrayList<String> outputs;
    public final String name;
    public void updatePathsToOtherStates(ArrayList<StateToStatePath> paths){
        this.pathsToOtherStates = paths;
    }
    public State(String name, ArrayList<String> inputs, ArrayList<String> outputs){
        this.inputs = inputs;
        this.outputs = outputs;
        if(name.isEmpty()){
            throw new IllegalArgumentException("A empty name cannot be passed to a State constructor");
        }
        this.name = name;
    }
}
