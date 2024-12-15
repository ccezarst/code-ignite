package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template.InputMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.InputSetStates;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.InputSource;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.Map;

public class ButtonOutput extends InputMapper {
    public ButtonOutput(String name, Boolean active, DefaultCore core, Telemetry telem) {
        super(name, active, core);
    }



    @Override
    public void step(DefaultCore core) {
        // get UI_Manager
        UI_Manager caca = (UI_Manager) this.core.getComponentFromName("UI_Manager");
        for(Map.Entry<Integer, InputSetStates> current : this.states.entrySet()){
            caca.print(current.getValue().getAllButtonStates().toString() + "\n" + current.getValue().getAllAnalogValues().toString());
        }
    }

    @Override
    public void statesUpdated() {

    }
}
