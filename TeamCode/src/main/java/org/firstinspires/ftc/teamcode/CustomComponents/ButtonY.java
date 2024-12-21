package org.firstinspires.ftc.teamcode.CustomComponents;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template.OneButtonMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

public class ButtonY extends OneButtonMapper {
    private StateMachine st;
    public ButtonY(String name, Boolean active, DefaultCore core) {
        super(name, active, core, ButtonTypes.A, 2);
        this.st = (StateMachine) this.core.getComponentsOfType(ComponentType.STATE_MACHINE).get(0);
    }

    @Override
    public void buttonPressed() {
        int res = this.st.changeStateBasedOnCurrent(1);
        if(res != 0){
            ((UI_Manager)this.core.getComponentFromName("UI_Manager")).showWarning("ButtonY -> failed to change state to output 1 : " + res);
        }
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
