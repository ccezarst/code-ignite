package org.firstinspires.ftc.teamcode.CustomComponents;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.Template.OneButtonMapper;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

public class ButtonBUMPER_RIGHT extends OneButtonMapper {
    private StateMachine st;
    public ButtonBUMPER_RIGHT(String name, Boolean active, TeamCore core) {
        super(name, active, core, ButtonTypes.RIGHT_BUMPER, 2);
        this.st = (StateMachine) this.core.getComponentsOfType(ComponentType.STATE_MACHINE).get(0);
    }

    @Override
    public void customUpdate(TeamCore core) {

    }

    @Override
    public void buttonPressed() {
        int res = this.st.changeState("WaitingForSpecimen");
        if(res != 0){
            ((UI_Manager)this.core.getComponentFromName("UI_Manager")).showWarning("ButtonBUMPER_RIGHT -> failed to change state to IntakeHIGH : " + res);
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

    @Override
    public int test(TestingEnviromentCore core) {
        return 0;
    }
}
