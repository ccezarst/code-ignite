package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.TestingStates.StateA;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.TestingStates.StateB;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.TestingStates.StateC;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.TestingStates.StateD;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StateMachine extends CoreComponent {
    private ArrayList<HardwareInterface> hwInterfaces = new ArrayList<HardwareInterface>();
    private ArrayList<SoftwareInterface> swInterfaces = new ArrayList<SoftwareInterface>();
    private ArrayList<State> history = new ArrayList<>();
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
                        throw new IllegalArgumentException("State Machine configuration is not proper; " + states.get(i).name + "'s inputs do not math other states outputs; check connections of " + subtractLists(this.states.get(i).inputs, Objects.requireNonNull(pula.get(states.get(i).name))));
                    }
                }catch(Exception exp){
                    if(exp instanceof NullPointerException){
                        throw new IllegalArgumentException("Failed to find state while checking inputs, maybe state is not connected to anything. State name: " + states.get(i).name + " \n" + exp);
                        //throw exp;
                    }else{
                        throw exp;
                    }
                }
            }
    }

    private ArrayList<String> subtractLists(ArrayList<String> caca, ArrayList<String> piss){
        ArrayList<String> rez = new ArrayList<>();
        for(String copiluLuMata: piss){
            if(!caca.contains(copiluLuMata) && !rez.contains(copiluLuMata)){
                rez.add(copiluLuMata);
            }
        }
        for(String mata: caca){
            if(!rez.contains(mata) && !piss.contains(mata)){
                rez.add(mata);
            }
        }
        return rez;
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
            caca.remove(0);
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

    public StateMachine( ArrayList<State> states, Boolean active, String name, TeamCore core) {
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
                    //throw new IllegalArgumentException("Failed to change state, is currentState: " + this.currentState + "; newStateName: " + newStateName +"; " + this.history);
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
    public int forcedChangeState(String newStateName){
        this.stateQueue.add(newStateName);
        this.step(null);
        ((UI_Manager)this.core.getComponentFromName("UI_Manager")).showWarning(newStateName + ": " + this.currentState.getPathToStateNames(newStateName));
        return 0;
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

    public void undo(){
        this.revert();
    }
    public void revert(){
        this.forcedChangeState(this.history.remove(0).name);
    }

    private ArrayList<String> getClassNames(ArrayList<State> caca){
        ArrayList<String> mata = new ArrayList<>();
        for(State pula: caca){
            mata.add(pula.getClass().getName() + " -> " + pula.name);
        }
        return mata;
    }

    @Override
    public void step(TeamCore core){
        if(this.active){
            if(this.currentState != null){
                ((UI_Manager)this.core.getComponentFromName("UI_Manager")).print(this.currentState.toString());
                ((UI_Manager)this.core.getComponentFromName("UI_Manager")).print(this.stateQueue.toString());
                ((UI_Manager)this.core.getComponentFromName("UI_Manager")).print(this.getClassNames(this.history).toString());
                this.currentState.step(this.hwInterfaces, this.swInterfaces);
                if(!this.stateQueue.isEmpty()) {
                    if(this.currentState.isInState(this.hwInterfaces, this.swInterfaces)){
                        String newStateName = this.stateQueue.remove(0);
                        if (newStateName != currentState.name && this.currentState.checkRequirements(hwInterfaces, swInterfaces)) {
                            State newState = lookupStateFromName(newStateName, this.states);
                            newState.call(this.hwInterfaces, this.swInterfaces);
                            this.history.add(this.currentState);
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
                this.history.add(this.currentState);
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
    public void update(TeamCore core){
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

    private int getNumberFromLetter(String letter){
        switch (letter){
            case "A":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;
            case "D":
                return 4;
        }
        return 0;
    }

    @Override
    public int test(TestingEnviromentCore core) {
        // test this component

        // 1. make test states
        //1
        //2
        //3
        //4
        //1 2 <->
        //2 3 <->
        //3 4 ->
        //4 1 <-
        //2 4 <-
        // states are based on
        StateA A = new StateA();
        A.inputs.add("B");
        A.outputs.add("B");
        StateB B = new StateB();
        B.inputs.add("A");
        B.inputs.add("C");
        B.outputs.add("D");
        B.outputs.add("C");
        StateC C = new StateC();
        C.inputs.add("B");
        C.inputs.add("D");
        C.outputs.add("B");
        StateD D = new StateD();
        D.inputs.add("B");
        D.outputs.add("C");
        ArrayList<State> testingStates = new ArrayList<>();
        testingStates.add(A);
        testingStates.add(B);
        testingStates.add(C);
        testingStates.add(D);
        StateMachine st = new StateMachine(testingStates, true, "StateMachineTesting", core);
        core.addComponent(st);
        core.init();
        st.changeState("A");

        //2. request state changes and step the core and see if the state changes are correct
        // if we throw an error, the digit will be the actual state
        // eg we expected state A but we have B so error will be xx02( 2 for B)
        /// #1 -- check if we have invited successfully
        core.step();
        if(st.currentState == null || st.currentState.name != "A"){
            return 11* 100 + this.getNumberFromLetter(st.currentState.name);
        }

        /// #2 -- attempt basic state changes(A-B, B-A)
        st.changeState("B");
        core.step();
        if(this.currentState.name != "B"){
            return 21* 1000 + this.getNumberFromLetter(st.currentState.name);
        }

        st.changeState("A");
        core.step();
        if(this.currentState.name != "A"){
            return 22* 1000 + this.getNumberFromLetter(st.currentState.name);
        }
        /// #3 -- attempt longer state change(A-B-C, C-B,A)
        st.changeState("C");
        core.step();
        if(this.currentState.name != "B"){
            return 31* 1000 + this.getNumberFromLetter(st.currentState.name);
        }
        core.step();
        if(this.currentState.name != "C"){
            return 32* 1000 + this.getNumberFromLetter(st.currentState.name);
        }

        st.changeState("A");
        core.step();
        if(this.currentState.name != "B"){
            return 33* 1000 + this.getNumberFromLetter(st.currentState.name);
        }
        core.step();
        if(this.currentState.name != "A"){
            return 34* 1000 + this.getNumberFromLetter(st.currentState.name);
        }

        /// #4 -- attempt complicated state change ( A-B-D, D-C-B-A )
        st.changeState("D");
        core.step();
        if(this.currentState.name != "B"){
            return 41* 1000 + this.getNumberFromLetter(st.currentState.name);
        }
        core.step();
        if(this.currentState.name != "D"){
            return 42* 1000 + this.getNumberFromLetter(st.currentState.name);
        }

        st.changeState("A");
        core.step();
        if(this.currentState.name != "C"){
            return 43* 1000 + this.getNumberFromLetter(st.currentState.name);
        }
        core.step();
        if(this.currentState.name != "B"){
            return 44* 1000 + this.getNumberFromLetter(st.currentState.name);
        }
        core.step();
        if(this.currentState.name != "A"){
            return 45* 1000 + this.getNumberFromLetter(st.currentState.name);
        }

        /// #5 -- test state change based on current and revert func
        st.changeStateBasedOnCurrent(1);
        core.step();
        if(this.currentState.name != "B"){
            return 51 * 1000 + this.getNumberFromLetter(st.currentState.name);
        }

        st.revert();
        core.step();
        if(this.currentState.name != "A"){
            return 52 * 1000 + this.getNumberFromLetter(st.currentState.name);
        }

        // yeah so if none of those checks fail then the state machine works!!
        return 0;
    }
}
