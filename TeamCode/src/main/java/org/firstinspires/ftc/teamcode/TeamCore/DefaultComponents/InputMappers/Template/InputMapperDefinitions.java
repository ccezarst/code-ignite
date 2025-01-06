package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.Template;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.InputSetStates;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;

public interface InputMapperDefinitions {
    public void updateRefrenceToStates(int inputSourceID, InputSetStates st);
    public abstract void customUpdate(TeamCore core);
    public abstract void statesUpdated();

}
