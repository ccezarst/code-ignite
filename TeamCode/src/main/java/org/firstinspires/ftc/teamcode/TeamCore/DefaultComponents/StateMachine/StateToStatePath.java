package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine;

import java.util.ArrayList;

public class StateToStatePath {
    public final ArrayList<State> path;
    public final String endStateName;
    public StateToStatePath(ArrayList<State> path, String endStateName){
        this.path = path;
        this.endStateName = endStateName;
    }
}
