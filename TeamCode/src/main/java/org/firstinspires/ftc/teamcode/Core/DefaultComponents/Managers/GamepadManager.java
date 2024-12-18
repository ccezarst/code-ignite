package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.AnalogTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.InputSource;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GamepadManager extends InputSource {
    private Gamepad gp;
    public GamepadManager(String name, Boolean active, DefaultCore core, Gamepad gp){
        super(name, active, core, gp.getNumber());
        this.gp = gp;
    }

    @Override
    public void step(DefaultCore core){
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
    public ArrayList<String> getStatus() {
        return null;
    }
}
