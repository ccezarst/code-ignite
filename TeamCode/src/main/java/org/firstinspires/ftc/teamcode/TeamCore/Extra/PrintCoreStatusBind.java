package org.firstinspires.ftc.teamcode.TeamCore.Extra;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.Input.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.Input.Template.OneButtonMapper;
import org.firstinspires.ftc.teamcode.TeamCore.Managers.UI_Manager;

import EngineCore.EngineCore;
import EngineCore.TestingEnviromentCore;
public class PrintCoreStatusBind extends OneButtonMapper {
    private UI_Manager man;
    public PrintCoreStatusBind(int inputSourceID, EngineCore core, Telemetry telem){
        super("PrintCoreStatusBind", true, core, ButtonTypes.BACK, inputSourceID);
    }

    @Override
    public void step(EngineCore core) {

    }

    @Override
    public int test(TestingEnviromentCore core) {
        return 0;
    }

    @Override
    public void customUpdate(EngineCore core) {
        synchronized (this.man){
            this.man = (UI_Manager) this.core.getComponentFromName("UI_Manager");
        }
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
