package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.Template.InputMapper;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.InputSetStates;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.Map;

public class ButtonOutput extends InputMapper {
    private UI_Manager man;
    public ButtonOutput(String name, Boolean active, TeamCore core, Telemetry telem) {
        super(name, active, core);
    }


    @Override
    public void step(TeamCore core) {
        // get UI_Manager
        for(Map.Entry<Integer, InputSetStates> current : this.states.entrySet()){
            man.print(current.getValue().getAllButtonStates().toString() + "\n" + current.getValue().getAllAnalogValues().toString());
        }
    }

    @Override
    public int test(TestingEnviromentCore core) {
        // same shit code is going to be deleted
        return 0;
    }

    @Override
    public void statesUpdated() {

    }

    @Override
    public void customUpdate(TeamCore core) {
        this.man = (UI_Manager) this.core.getComponentFromName("UI_Manager");
    }
}
