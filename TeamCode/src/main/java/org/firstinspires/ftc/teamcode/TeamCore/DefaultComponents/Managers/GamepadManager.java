package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.AnalogTypes;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.InputSource;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.CustomGamepad;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;
import java.util.Objects;

public class GamepadManager extends InputSource {
    private CustomGamepad gp;
    public GamepadManager(String name, Boolean active, TeamCore core, CustomGamepad gp){
        super(name, active, core, gp.getNumber());
        this.gp = gp;
    }

    @Override
    public void step(TeamCore core){
        // first send thumbstick values


        // we will look for all button mapper components and send them pressed keys
        // and the button mappers will have no step functions, only waiting to recieve keys
        // from this function

        for(ButtonTypes btnType: Objects.requireNonNull(ButtonTypes.values())){
            this.updateButtonState(btnType, gp.checkHold(btnType));
        }

        for(AnalogTypes anType: Objects.requireNonNull(AnalogTypes.values())){
            this.updateAnalogValue(anType, (double) gp.getAnalog(anType));
        }
        this.updateInputMappers();

    }

    @Override
    public int test(TestingEnviromentCore core) {
        // code going to be replaced anyways
        return 0;
    }

    @Override
    public ArrayList<String> getStatus() {
        return null;
    }
}
