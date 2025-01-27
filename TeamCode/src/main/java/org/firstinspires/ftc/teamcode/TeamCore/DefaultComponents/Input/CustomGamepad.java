package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input.Template.AnalogTypes;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input.Template.InputSource;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.OldCustomGamepad;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

public class CustomGamepad extends InputSource {
    private OldCustomGamepad gamepad;
    public CustomGamepad(String name, Boolean active, TeamCore core, int inputSourceID, Gamepad gamepad) {
        super(name, active, core, inputSourceID);
        this.gamepad = new OldCustomGamepad(gamepad, inputSourceID);
    }

    @Override
    protected void step(TeamCore core) {
        for(ButtonTypes btn: ButtonTypes.values()){
            this.buttonStates.put(btn, gamepad.checkHold(btn));
        }
        for(AnalogTypes an: AnalogTypes.values()){
            this.analogStates.put(an, (double) gamepad.getAnalog(an));
        }
        this.sendInputs();
    }

    @Override
    protected void update(TeamCore core) {
        this.setup();
        this.registerActionsForButtons(ButtonTypes.values()); // because the gamepad has them all for now
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
