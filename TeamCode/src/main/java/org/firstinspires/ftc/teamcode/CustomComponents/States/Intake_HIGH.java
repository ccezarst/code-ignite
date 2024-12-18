package org.firstinspires.ftc.teamcode.CustomComponents.States;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine.State;
import org.firstinspires.ftc.teamcode.CustomComponents.IntakeInterface;

import java.util.ArrayList;
import java.util.Objects;

public class Intake_HIGH extends State {
    public Intake_HIGH(ArrayList<String> inputs, ArrayList<String> outputs) {
        super("IntakeHIGH", inputs, outputs);
    }

    public Intake_HIGH() {
        super("IntakeHIGH");
    }

    @Override
    public boolean checkRequirements(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {
        return false;
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


    @Override
    public void call(ArrayList<HardwareInterface> hwIntf, ArrayList<SoftwareInterface> swIntf) {
        Objects.requireNonNull(this.getIntake(hwIntf)).extend(100);
        Objects.requireNonNull(this.getIntake(hwIntf)).rotateMouth(60);

    }
}
