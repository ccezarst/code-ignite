package org.firstinspires.ftc.teamcode.TeamCore.Input.Template;

import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;
import EngineCore.DefaultComponents.ComponentType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class InputSource extends CoreComponent {
    private int inputSourceID;
    public InputSource(String name, Boolean active, EngineCore core, int inputSourceID) {
        super(name, active, core, ComponentType.INPUT_SOURCE);
        this.inputSourceID = inputSourceID;
        this.setup();
    }

    protected final void setup(){
        this.buttonStates = new HashMap<>();
        this.buttonStatesLast = new HashMap<>();
        this.analogStates = new HashMap<>();
        this.buttonToggleStates = new HashMap<>();
        for(ButtonTypes btn: ButtonTypes.values()){
            this.core.setGlobalVariable(this.inputSourceID + btn.name(), false);
            this.buttonToggleStates.put(btn, false);
            this.buttonStates.put(btn, false);
            this.buttonStatesLast.put(btn, false);
        }
        for(AnalogTypes an: AnalogTypes.values()){
            this.core.setGlobalVariable(this.inputSourceID + an.name(), 0);
            this.analogStates.put(an, 0.0);
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

    protected Map<ButtonTypes, Boolean> buttonStates = new HashMap<>();
    private Map<ButtonTypes, Boolean> buttonStatesLast = new HashMap<>();
    private Map<ButtonTypes, Boolean> buttonToggleStates = new HashMap<>();

    protected Map<AnalogTypes, Double> analogStates = new HashMap<>();

    // calls actions and updates global variables
    protected final void sendInputs(){
        synchronized (this.buttonStates){
            synchronized (this.analogStates){
                synchronized (this.buttonStatesLast){
                    synchronized (this.buttonToggleStates){
                        synchronized (this.buttonStatesLast){
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
                        }
                    }
                }
            }
        }
    }
}
