package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input.Template;

import org.firstinspires.ftc.teamcode.TeamCore.Actions.ActionDataContainer;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;

public abstract class OneButtonMapper extends CoreComponent {
    private ButtonTypes btn;
    private int inputSourceID;
    public OneButtonMapper(String name, Boolean active, TeamCore core, ButtonTypes btn, int inputSourceID, ComponentType... type) {
        super(name, active, core, type);
        this.btn = btn;
        this.inputSourceID = inputSourceID;
    }
    @Override
    public void update(TeamCore core){
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
    public abstract void customUpdate(TeamCore core);
}
