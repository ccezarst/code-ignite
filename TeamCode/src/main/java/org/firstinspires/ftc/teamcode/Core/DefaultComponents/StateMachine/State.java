package org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SoftwareInterface;

import java.util.ArrayList;

public abstract class State {
    private ArrayList<StateToStatePath> pathsToOtherStates = new ArrayList<>();
    public ArrayList<String> inputs;
    public ArrayList<String> outputs;
    public final String name;
    public final void pushToPathToOtherStates(StateToStatePath path){
        this.pathsToOtherStates.add(path);
    }
    public final boolean isConnectedToState(State st){
        if(st.name == this.name){return true;}
        for(int i = 0; i < this.pathsToOtherStates.size(); i++){
            if(st.name == this.pathsToOtherStates.get(i).endStateName){
                return true;
            }
        }
        return false;
    }
    public final boolean isConnectedToState(String st){
        if(st == this.name){return true;}
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
            if(this.pathsToOtherStates.get(i).endStateName == st){
                ArrayList<String> toReturn = new ArrayList<>();
                for(int b = 0; b < this.pathsToOtherStates.get(i).path.size(); b++){
                    toReturn.add(this.pathsToOtherStates.get(i).path.get(b).name);
                }
                return toReturn;
            }
        }
        return null;
    }
    // incase we need a special state that has special requirements(eg. robot is in position x)
    public abstract boolean checkRequirements(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf);
    public abstract boolean isInState(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf);
    public abstract void call(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf);
    public State(String name, ArrayList<String> inputs, ArrayList<String> outputs){
        this.inputs = inputs;
        this.inputs.add(name);
        this.outputs = outputs;
        this.outputs.add(name); // hotfix
        if(name.isEmpty()){
            throw new IllegalArgumentException("A empty name cannot be passed to a State constructor");
        }
        this.name = name;
    }
    public State(String name){
        this.inputs = new ArrayList<>();
        this.inputs.add(name);
        this.outputs = new ArrayList<>();
        this.outputs.add(name);
        if(name.isEmpty()){
            throw new IllegalArgumentException("A empty name cannot be passed to a State constructor");
        }
        this.name = name;
    }
}
