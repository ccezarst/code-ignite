package org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.SoftwareInterface;

import java.util.ArrayList;

public abstract class State {
    private ArrayList<StateToStatePath> pathsToOtherStates;
    public final ArrayList<String> inputs;
    public final ArrayList<String> outputs;
    public final String name;
    public final void pushToPathToOtherStates(StateToStatePath path){
        this.pathsToOtherStates.add(path);
    }
    public final boolean isConnectedToState(State st){
        for(int i = 0; i < this.pathsToOtherStates.size(); i++){
            if(st.name == this.pathsToOtherStates.get(i).endStateName){
                return true;
            }
        }
        return false;
    }
    public final boolean isConnectedToState(String st){
        for(int i = 0; i < this.pathsToOtherStates.size(); i++){
            if(st == this.pathsToOtherStates.get(i).endStateName){
                return true;
            }
        }
        return false;
    }


    public final StateToStatePath getPathToState(State st){
        for(int i = 0; i < this.pathsToOtherStates.size(); i++){
            if(st.name == this.pathsToOtherStates.get(i).endStateName){
                return this.pathsToOtherStates.get(i);
            }
        }
        return null;
    }
    public final StateToStatePath getPathToState(String st){
        for(int i = 0; i < this.pathsToOtherStates.size(); i++){
            if(st == this.pathsToOtherStates.get(i).endStateName){
                return this.pathsToOtherStates.get(i);
            }
        }
        return null;
    }
    public final ArrayList<String> getPathToStateNames(State st){
        for(int i = 0; i < this.pathsToOtherStates.size(); i++){
            if(st.name == this.pathsToOtherStates.get(i).endStateName){
                ArrayList<String> toReturn = new ArrayList<>();
                for(int b = 0; b < this.pathsToOtherStates.get(b).path.size(); b++){
                    toReturn.add(this.pathsToOtherStates.get(b).path.get(b).name);
                }
                return toReturn;
            }
        }
        return null;
    }
    public final ArrayList<String> getPathToStateNames(String st){
        for(int i = 0; i < this.pathsToOtherStates.size(); i++){
            ArrayList<String> toReturn = new ArrayList<>();
            for(int b = 0; b < this.pathsToOtherStates.get(b).path.size(); b++){
                toReturn.add(this.pathsToOtherStates.get(b).path.get(b).name);
            }
            return toReturn;
        }
        return null;
    }
    // incase we need a special state that has special requirements(eg. robot is in position x)
    public abstract boolean checkRequirements(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf);
    public abstract void call(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf);
    public State(String name, ArrayList<String> inputs, ArrayList<String> outputs){
        this.inputs = inputs;
        this.outputs = outputs;
        if(name.isEmpty()){
            throw new IllegalArgumentException("A empty name cannot be passed to a State constructor");
        }
        this.name = name;
    }
}
