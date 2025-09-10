package org.firstinspires.ftc.teamcode.TeamCore.Input.Template;



import EngineCore.Actions.ActionDataContainer;
import EngineCore.DefaultComponents.ComponentType;
import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;

public abstract class OneButtonMapper extends CoreComponent {
    private ButtonTypes btn;
    private int inputSourceID;
    public OneButtonMapper(String name, Boolean active, EngineCore core, ButtonTypes btn, int inputSourceID, ComponentType... type) {
        super(name, active, core, type);
        this.btn = btn;
        this.inputSourceID = inputSourceID;
    }
    @Override
    public final void update(EngineCore core){
        this.core.subscribeToAction(inputSourceID + btn.name() + "_PRESSED", (ActionDataContainer data) ->{this.buttonPressed();});
        this.core.subscribeToAction(inputSourceID + btn.name() + "_DOWN", (ActionDataContainer data) ->{this.buttonDown();});
        this.core.subscribeToAction(inputSourceID + btn.name() + "_UP", (ActionDataContainer data) ->{this.buttonUp();});
        this.core.subscribeToAction(inputSourceID + btn.name() + "_TOGGLE", (ActionDataContainer data) ->{this.buttonToggle();});
        this.customUpdate(core);
    }
    public abstract void buttonPressed();

    public abstract void buttonDown() ;

    public abstract void buttonUp();

    public abstract void buttonToggle();
    public abstract void customUpdate(EngineCore core);
}
