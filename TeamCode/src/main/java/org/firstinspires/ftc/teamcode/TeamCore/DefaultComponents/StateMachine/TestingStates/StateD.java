package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.TestingStates;


import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.State;

import java.util.ArrayList;

public final class StateD extends State {
    public StateD() {
        super("D");
    }

    @Override
    public boolean checkRequirements(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {
        return true;
    }

    @Override
    public boolean isInState(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {
        return true;
    }

    @Override
    public void call(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {

    }

    @Override
    public void step(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {

    }
}
