package org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SoftwareInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StateMachine extends CoreComponent {
    private ArrayList<HardwareInterface> hwInterfaces = new ArrayList<HardwareInterface>();
    private ArrayList<SoftwareInterface> swInterfaces = new ArrayList<SoftwareInterface>();
    private ArrayList<State> states;
    private State currentState;
    private ArrayList<String> stateQueue;

    private void checkStatesConnections(ArrayList<State> states) {
            // keep track if connections are reciprocated in both states
            // map with the keys being a state and being it's inputs assembled from other states ouputs
            Map<String, ArrayList<String>> pula = new HashMap<>();
            for(int i = 0; i < states.size(); i++){
                ArrayList<String> currentStateOutputs = states.get(i).outputs;
                for(int b = 0; b < currentStateOutputs.size(); b++){
                    if(pula.containsValue(currentStateOutputs.get(b))){
                        ArrayList<String> old = pula.get(currentStateOutputs.get(b));
                        old.add(states.get(i).name);
                        pula.replace(currentStateOutputs.get(b), old);
                    }else{
                        ArrayList<String> caca = new ArrayList<String>();
                        caca.add(states.get(i).name);
                        pula.put(currentStateOutputs.get(b), caca);
                    }
                }
            }
            // now re-iterate over states but check if inputs are same as outputs
            for(int i =0; i < states.size(); i++){
                // if the current state inputs contain all the outputs saved in the mapz
                try{
                    if(states.get(i).inputs.containsAll(Objects.requireNonNull(pula.get(states.get(i).name)))){
                        // acc do nothing
                    }else{
                        throw new IllegalArgumentException("State Machine configuration is not proper; " + states.get(i).name + "'s inputs do not math other states outputs; check connections");
                    }
                }catch(Exception exp){
                    if(exp instanceof NullPointerException){
                        throw new IllegalArgumentException("Failed to find state while checking inputs, maybe state is not connected to anything. State name: " + states.get(i).name);
                    }else{
                        throw exp;
                    }
                }
            }
    }
    private State lookupStateFromName(String name, ArrayList<State> allStates){
        for(int i = 0; i < allStates.size(); i++){
            if(allStates.get(i).name == name){
                return allStates.get(i);
            }
        }
        throw new IllegalArgumentException("lookupStateFromName failed to find state, check allState argument " + name);
    }

    private <T> int countOf(ArrayList<T> list, T elem){
        int count = 0;
        for(T curElem: list){
            if(curElem == elem){
                count += 1;
            }
        }
        return count;
    }
    private ArrayList<ArrayList<State>> findPathes(State currentState, State targetState, ArrayList<State> currentPath, ArrayList<ArrayList<State>> allFoundPaths,ArrayList<State> allState){
        if(currentState.name == targetState.name){
            ArrayList<State> caca = currentPath;
            caca.add(currentState);
            allFoundPaths.add(caca);
            return null;
        }else if(this.countOf(currentPath, currentState) > 1){
            // if this state is more then once in the path it means we circled back, so we need to abort
            // means we are circling, so abort
            return null;
            // returning null because if we returned then atleast one state was parsed before we went back.
            // that state may contain other outputs, so if we return null this same function(which is repetitive) can catch it and go to the next option
        }
        else{
            for(int i = 0; i < currentState.outputs.size(); i++){
                currentPath.add(currentState);
                findPathes(lookupStateFromName(currentState.outputs.get(i), allState), targetState, (ArrayList<State>) currentPath.clone(), allFoundPaths, allState);
            }
        }
        return allFoundPaths;
    }

    private ArrayList<State> getBestPath(ArrayList<ArrayList<State>> pathes){
        if(pathes.size() > 0){
            ArrayList<State> best = pathes.get(0); // instead of empty it is first element(if it was empty it would always be the smallest)
            for(int i = 0; i < pathes.size(); i++){
                if(pathes.get(i).size() < best.size()){
                    best = pathes.get(i);
                }
            }
            return best;
        }
        return null;
    }

    public StateMachine( ArrayList<State> states, Boolean active, String name, DefaultCore core) {
        super(name, active,core, ComponentType.STATE_MACHINE);
        this.states = states;
        this.stateQueue = new ArrayList<String>();
        this.checkStatesConnections(this.states);
        // calculate best pathes for each state to others
        // iterate through all states
        for(int i = 0; i < this.states.size(); i++){
            // iterate through all other states and for each calculate shortest path
            State currentState = this.states.get(i);
            for(int b = 0; b < this.states.size(); b++){
                if(this.states.get(b).name != currentState.name) {
                    ArrayList<ArrayList<State>> pathes = findPathes(currentState, this.states.get(b), new ArrayList<State>(), new ArrayList<ArrayList<State>>(), this.states);
                    ArrayList<State> bestPath = getBestPath(pathes);
                    if(bestPath != null){
                        StateToStatePath nPath = new StateToStatePath(bestPath, this.states.get(b).name);
                        currentState.pushToPathToOtherStates(nPath);
                    }
                }
            }
        }
    }

    public int changeState(String newStateName){
        // check if we can change to that state from current state
        if(this.currentState != null){
            if(this.currentState.isConnectedToState(newStateName) && newStateName != null){
                if(newStateName != this.currentState.name){
                    this.stateQueue.addAll(this.currentState.getPathToStateNames(newStateName));
                    this.step(null);
                    ((UI_Manager)this.core.getComponentFromName("UI_Manager")).showWarning(newStateName + ": " + this.currentState.getPathToStateNames(newStateName));
                    return 0;
                }else{
                    return 1;
                }
            }else{
                throw new IllegalArgumentException("Failed to change state, is connectedToCurrent: " + this.currentState.isConnectedToState(newStateName) + "; newStateName: " + newStateName);
            }
        }else{
            this.stateQueue.add(newStateName);
            return 0;
        }
    }

    private Boolean contains(State cur, State[] ls){
        if(ls != null){
            for(State caca: ls){
                if(caca.name == cur.name){
                    return true;
                }
            }
        }
        return false;
    }

    public int changeState(String newStateName, State... required){
        // check if we can change to that state from current state
        if(this.currentState != null){
            if(this.currentState.isConnectedToState(newStateName) && newStateName != null && contains(this.currentState, required)){
                if(newStateName != this.currentState.name){
                    this.stateQueue.addAll(this.currentState.getPathToStateNames(newStateName));
                    this.step(null);
                    ((UI_Manager)this.core.getComponentFromName("UI_Manager")).showWarning(newStateName + ": " + this.currentState.getPathToStateNames(newStateName));
                    return 0;
                }else{
                    return 1;
                }
            }else{
                throw new IllegalArgumentException("Failed to change state, is connectedToCurrent: " + this.currentState.isConnectedToState(newStateName) + "; newStateName: " + newStateName);
            }
        }else{
            this.stateQueue.add(newStateName);
            return 0;
        }
    }

    public int changeStateBasedOnCurrent(int child){
        // check if we can change to that state from current state
        if(this.currentState != null){
            if(child < this.currentState.outputs.size()){
                return this.changeState(this.currentState.outputs.get(child));

            }else{
                return 2;
            }
        }
        return 3;
    }
    @Override
    public void step(DefaultCore core){
        ((UI_Manager)this.core.getComponentFromName("UI_Manager")).print(this.stateQueue.toString());
        if(this.active){
            if(this.currentState != null){
                this.currentState.step(this.hwInterfaces, this.swInterfaces);
                if(!this.stateQueue.isEmpty()) {
                    if(this.currentState.isInState(this.hwInterfaces, this.swInterfaces)){
                        String newStateName = this.stateQueue.remove(0);
                        if (newStateName != currentState.name && this.currentState.checkRequirements(hwInterfaces, swInterfaces) && this.currentState.name != newStateName) {
                            State newState = lookupStateFromName(newStateName, this.states);
                            newState.call(this.hwInterfaces, this.swInterfaces);
                            this.currentState = newState;
                        }else if(!this.currentState.checkRequirements(hwInterfaces, swInterfaces)){
                            // if states not ready yet OR mistake in code
                            this.stateQueue.add(0, newStateName);
                        }
                    }
                }
            }else{
                State newState = lookupStateFromName(this.stateQueue.remove(0), this.states);
                newState.call(this.hwInterfaces, this.swInterfaces);
                this.currentState = newState;
            }

        }
    }
    @Override
    public ArrayList<String> getStatus(){
        // send information about current state
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("Current state: " + this.currentState.name);
        for(State st: this.states){
            toReturn.add(st.getStatus());
        }
        return toReturn;
    }

    @Override
    public void update(DefaultCore core){
        ArrayList<CoreComponent> caca = this.core.getComponentsOfType(ComponentType.HARDARE_INTERFACE);
        this.hwInterfaces.clear();
        for(int i = 0; i < caca.size(); i++){
            this.hwInterfaces.add((HardwareInterface) caca.get(i));
        }

        ArrayList<CoreComponent> maca = this.core.getComponentsOfType(ComponentType.SOFTWARE_INTERFACE);
        this.swInterfaces.clear();
        for(int i = 0; i < maca.size(); i++){
            this.swInterfaces.add((SoftwareInterface) maca.get(i));
        }
    }
}
