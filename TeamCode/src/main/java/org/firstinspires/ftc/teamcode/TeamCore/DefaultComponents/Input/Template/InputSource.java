package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input.Template;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

public abstract class InputSource extends CoreComponent {
    private int inputSourceID;
    public InputSource(String name, Boolean active, TeamCore core, int inputSourceID) {
        super(name, active, core, ComponentType.INPUT_SOURCE);
        this.inputSourceID = inputSourceID;
    }

    public final void registerActionsForButtons(ButtonTypes... btns){
        for(ButtonTypes btn: btns){
            this.core.addAction(this.inputSourceID + btn.name() + "_PRESSED", ButtonTypes.class);
            this.core.addAction(this.inputSourceID + btn.name() + "_DOWN", ButtonTypes.class);
            this.core.addAction(this.inputSourceID + btn.name() + "_UP", ButtonTypes.class);
            this.core.addAction(this.inputSourceID + btn.name() + "_TOGGLE", ButtonTypes.class);
        }
    }
}
