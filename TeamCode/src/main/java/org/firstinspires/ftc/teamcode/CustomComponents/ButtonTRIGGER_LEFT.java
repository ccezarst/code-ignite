package org.firstinspires.ftc.teamcode.CustomComponents;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template.OneButtonMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

public class ButtonTRIGGER_LEFT extends OneButtonMapper {
    private StateMachine st;
    public ButtonTRIGGER_LEFT(String name, Boolean active, DefaultCore core) {
        super(name, active, core, ButtonTypes.LEFT_BUMPER, 2);
        this.st = (StateMachine) this.core.getComponentsOfType(ComponentType.STATE_MACHINE).get(0);
    }

    @Override
    public void buttonPressed() {
        ((UI_Manager)this.core.getComponentFromName("UI_Manager")).print("caca");
        this.st.changeState("IntakeHIGH");
    }

    @Override
    public void buttonDown() {
    }

    @Override
    public void buttonUp() {

    }

    @Override
    public void buttonToggle() {

    }
}
