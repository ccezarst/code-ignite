package org.firstinspires.ftc.teamcode.CustomComponents.States;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.State;
import org.firstinspires.ftc.teamcode.CustomComponents.IntakeInterface;
import org.firstinspires.ftc.teamcode.CustomComponents.OuttakeInterface;

import java.util.ArrayList;
import java.util.Objects;

public class Idle extends State {
    public Idle() {
        super("Idle");
    }

    @Override
    public boolean checkRequirements(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {
        return true;
    }

    @Override
    public boolean isInState(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {
        return true;
    }

    private IntakeInterface getIntake(ArrayList<HardwareInterface> hwInterface){
        for(int i = 0; i < hwInterface.size(); i++){
            if(hwInterface.get(i).interfaceType == InterfaceType.INTAKE){
                return (IntakeInterface)hwInterface.get(i);
            }
        }
        return null;
    }

    private OuttakeInterface getOuttake(ArrayList<HardwareInterface> hwInterface){
        for(int i = 0; i < hwInterface.size(); i++){
            if(hwInterface.get(i).interfaceType == InterfaceType.OUTTAKE){
                return (OuttakeInterface) hwInterface.get(i);
            }
        }
        return null;
    }


    @Override
    public void call(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {
        IntakeInterface intf = Objects.requireNonNull(this.getIntake(hwIntf));
        intf.extend(0);
        intf.stopEating();
        intf.rotateMouth(66);

        OuttakeInterface out = Objects.requireNonNull(this.getOuttake(hwIntf));
        out.rotateAss(15);
        out.extend(0);
        out.secondRotateBasket(0);
        out.openClaw();
    }

    @Override
    public void step(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {

    }

    public Idle(ArrayList<String> inputs, ArrayList<String> outputs) {
        super("Idle", inputs, outputs);
    }
}
