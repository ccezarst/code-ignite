package org.firstinspires.ftc.teamcode.CustomComponents;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template.OneButtonMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

public class ButtonDPAD_DOWN extends OneButtonMapper {
    private StateMachine st;
    public ButtonDPAD_DOWN(String name, Boolean active, DefaultCore core) {
        super(name, active, core, ButtonTypes.DPAD_DOWN, 2);
        this.st = (StateMachine) this.core.getComponentsOfType(ComponentType.STATE_MACHINE).get(0);
    }

    @Override
    public void buttonPressed() {
    }

    @Override
    public void buttonDown() {

    }

    @Override
    public void buttonUp() {

    }

    @Override
    public void buttonToggle() {
        this.st.changeState("Idle");
    }
}
