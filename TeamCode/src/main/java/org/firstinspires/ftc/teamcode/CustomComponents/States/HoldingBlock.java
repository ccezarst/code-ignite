package org.firstinspires.ftc.teamcode.CustomComponents.States;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.State;
import org.firstinspires.ftc.teamcode.CustomComponents.IntakeInterface;
import org.firstinspires.ftc.teamcode.CustomComponents.OuttakeInterface;

import java.util.ArrayList;

public class HoldingBlock extends State {
    private long last = 0;
    private final long delayMS = 500;
    public HoldingBlock() {
        super("HoldingBlock");
    }

    @Override
    public boolean checkRequirements(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {
        return true;
    }

    @Override
    public boolean isInState(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {
        if(System.currentTimeMillis() - last > delayMS){
            return true;
        }
        return false;
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
        this.last = System.currentTimeMillis();
        IntakeInterface in = this.getIntake(hwIntf);
        in.rotateMouth(55);
        in.extend(8, 0.5);

        this.getOuttake(hwIntf).rotateAss(8);
        this.getOuttake(hwIntf).secondRotateBasket(6);
    }

    @Override
    public void step(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {
        if(this.isInState(hwIntf, swIntf)){
            // because it moves so fast we want to keep holding the block until we are in position
            this.getIntake(hwIntf).stopEating();
        }
    }
}
