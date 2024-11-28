package org.firstinspires.ftc.teamcode.Core.StateMachine;

import java.util.ArrayList;

public class StateToStatePath {
    public final int length;
    public final ArrayList<State> path;
    public final String startStateName;
    public final String endStateName;
    public StateToStatePath(int  length, ArrayList<State> path, String startStateName, String endStateName){
        this.length = length;
        this.path = path;
        this.startStateName = startStateName;
        this.endStateName = endStateName;
    }
}
