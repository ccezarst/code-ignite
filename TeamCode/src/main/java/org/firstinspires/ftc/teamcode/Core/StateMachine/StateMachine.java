package org.firstinspires.ftc.teamcode.Core.StateMachine;

import android.nfc.FormatException;

import org.firstinspires.ftc.teamcode.Core.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.SoftwareInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StateMachine extends CoreComponent {
    private ArrayList<HardwareInterface> hwInterfaces;
    private ArrayList<SoftwareInterface> swInterfaces;
    private ArrayList<State> states;
    private ArrayList<String> stateQueue;

    private void checkStatesConnections(ArrayList<State> states) throws FormatException {
            // keep track if connections are reciprocated in both states
            // map with the keys being a state and being it's inputs assembled from other states ouputs
            Map<String, ArrayList<String>> pula = new HashMap<>();
            for(int i = 0; i < states.size(); i++){
                ArrayList<String> currentStateOutputs = states.get(i).outputs;
                for(int b = 0; b < currentStateOutputs.size(); b++){
                    if(pula.containsValue(currentStateOutputs.get(b))){
                        pula.get(currentStateOutputs.get(b)).add(states.get(i).name);
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
                if(states.get(i).inputs.containsAll(pula.get(states.get(i)))){
                    // acc do nothing
                }else{
                    throw new FormatException("State Machine configuration is not proper; " + states.get(i).name + "'s inputs do not math other states outputs; check connections");
                }
            }
    }
    private State lookupStateFromName(String name, ArrayList<State> allStates){
        for(int i = 0; i < allStates.size(); i++){
            if(allStates.get(i).name == name){
                return allStates.get(i);
            }
        }
        throw new IllegalArgumentException("lookupStateFromName failed to find state, check allState argument");
    }
    private ArrayList<ArrayList<State>> findPathes(State currentState, State targetState, ArrayList<State> currentPath, ArrayList<ArrayList<State>> allFoundPaths,ArrayList<State> allState){
        if(currentState.name == targetState.name){
            allFoundPaths.add(currentPath);
        }else if(!currentPath.isEmpty() && currentPath.indexOf(currentState) < currentPath.size() - 1){
            // normally the current state is the last in the path, so if it's not last we are circling
            // means we are circling, so abort
            return null;
            // returning null because if we returned then atleast one state was parsed before we went back.
            // that state may contain other outputs, so if we return null this same function(which is repetitive) can catch it and go to the next option
        }
        else{
            for(int i = 0; i < currentState.outputs.size(); i++){
                currentPath.add(currentState);
                findPathes(lookupStateFromName(currentState.outputs.get(i), allState), targetState, currentPath, allFoundPaths, allState);
            }
        }
        return allFoundPaths;
    }
    public StateMachine(ArrayList<HardwareInterface> hwInterfaces, ArrayList<SoftwareInterface> swInterfaces, ArrayList<State> states) throws FormatException {
        this.hwInterfaces = hwInterfaces;
        this.swInterfaces = swInterfaces;
        this.states = states;
        this.stateQueue = new ArrayList<String>();
        this.checkStatesConnections(this.states);
        // calculate paths
        for(int i = 0; i < this.states.size(); i++){
            // iterate through all other states and for each calculate shortest path
            State currentState = this.states.get(i);
            for(int b = 0; b < this.states.size(); b++){
                if(this.states.get(b).name != currentState.name) {
                    ArrayList<ArrayList<State>> pathes = findPathes(currentState, this.states.get(b), new ArrayList<State>(), new ArrayList<ArrayList<State>>(), this.states);
                }
            }
        }
    }

    public void changeState(String newStateName){}

    public void step(){}
}
