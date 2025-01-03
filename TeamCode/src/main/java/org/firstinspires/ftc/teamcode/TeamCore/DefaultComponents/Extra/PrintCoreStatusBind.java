package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Extra;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.Template.OneButtonMapper;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

public class PrintCoreStatusBind extends OneButtonMapper {
    private UI_Manager man;
    public PrintCoreStatusBind(int inputSourceID, TeamCore core, Telemetry telem){
        super("PrintCoreStatusBind", true, core, ButtonTypes.BACK, inputSourceID);
    }

    @Override
    public void step(TeamCore core) {

    }

    @Override
    public int test(TestingEnviromentCore core) {
        return 0;
    }

    @Override
    public void customUpdate(TeamCore core) {
        this.man = (UI_Manager) this.core.getComponentFromName("UI_Manager");
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
        ((UI_Manager)this.core.getComponentFromName("UI_Manager")).showWarning(this.core.getStatus());
    }
}
