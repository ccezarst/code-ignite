package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input.Template;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.HashMap;
import java.util.Map;

public abstract class InputSource extends CoreComponent {
    private int inputSourceID;
    public InputSource(String name, Boolean active, TeamCore core, int inputSourceID) {
        super(name, active, core, ComponentType.INPUT_SOURCE);
        this.inputSourceID = inputSourceID;
        this.buttonStates = new HashMap<>();
        this.buttonStatesLast = new HashMap<>();
        this.analogStates = new HashMap<>();
        this.analogStatesLast = new HashMap<>();
        this.buttonToggleStates = new HashMap<>();
        for(ButtonTypes btn: ButtonTypes.values()){
            this.core.setGlobalVariable(this.inputSourceID + btn.name(), false);
            this.buttonToggleStates.put(btn, false);
        }
        for(AnalogTypes an: AnalogTypes.values()){
            this.core.setGlobalVariable(this.inputSourceID + an.name(), 0);
        }
    }

    protected final void registerActionsForButtons(ButtonTypes... btns){
        for(ButtonTypes btn: btns){
            this.core.addAction(this.inputSourceID + btn.name() + "_PRESSED", ButtonTypes.class);
            this.core.addAction(this.inputSourceID + btn.name() + "_DOWN", ButtonTypes.class);
            this.core.addAction(this.inputSourceID + btn.name() + "_UP", ButtonTypes.class);
            this.core.addAction(this.inputSourceID + btn.name() + "_TOGGLE", ButtonTypes.class);
        }
    }

    private void triggerPressed(ButtonTypes btn){
        this.core.getActionFromName(this.inputSourceID + btn.name() + "_PRESSED").trigger();
    }

    private void triggerDown(ButtonTypes btn){
        this.core.getActionFromName(this.inputSourceID + btn.name() + "_DOWN").trigger();
    }

    private void triggerUP(ButtonTypes btn){
        this.core.getActionFromName(this.inputSourceID + btn.name() + "_UP").trigger();
    }

    private void triggerToggle(ButtonTypes btn){
        this.core.getActionFromName(this.inputSourceID + btn.name() + "_TOGGLE").trigger();
    }

    protected Map<ButtonTypes, Boolean> buttonStates;
    private Map<ButtonTypes, Boolean> buttonStatesLast;
    private Map<ButtonTypes, Boolean> buttonToggleStates;

    protected Map<AnalogTypes, Double> analogStates;
    private Map<AnalogTypes, Double> analogStatesLast;

    // calls actions and updates global variables
    protected final void updateAllStates(){
        for(ButtonTypes btn: this.buttonStates.keySet()){
            this.core.setGlobalVariable(this.inputSourceID + btn.name(), this.buttonStates.get(btn));
        }
        for(AnalogTypes an: this.analogStates.keySet()){
            this.core.setGlobalVariable(this.inputSourceID + an.name(), this.analogStates.get(an));
        }
        for(ButtonTypes btn: this.buttonStates.keySet()){
            if(this.buttonStatesLast.get(btn) == false && this.buttonStates.get(btn) == true){
                this.triggerPressed(btn);
                this.buttonToggleStates.put(btn, !this.buttonToggleStates.get(btn));
            }else if(this.buttonStatesLast.get(btn) == true && this.buttonStates.get(btn) == true){
                this.triggerDown(btn);
                if (this.buttonToggleStates.get(btn)) {
                    this.triggerToggle(btn);
                }
            }else{
                this.triggerUP(btn);
            }
        }
        for(ButtonTypes btn: this.buttonStates.keySet()){
            this.buttonStatesLast.put(btn, this.buttonStates.get(btn));
        }
        for(AnalogTypes an: this.analogStates.keySet()){
            this.analogStatesLast.put(an, this.analogStates.get(an));
        }
    }
}
