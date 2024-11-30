package org.firstinspires.ftc.teamcode.Core.DefaultComponents;

import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;

public class GamepadManager extends CoreComponent {
    private Gamepad[] gp;
    public GamepadManager(String name, Boolean active, Gamepad... gp){
        super(name, active, ComponentType.GAMEPAD_MANAGER);
        this.gp = gp;
    }

    @Override
    public ArrayList<String> getStatus(){
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("Gamepads: " + this.gp.toString());
        return toReturn;
    }

    @Override
    public void step(DefaultCore core){
        // we will look for all button mapper components and send them pressed keys
        // and the button mappers will have no step functions, only waiting to recieve keys
        // from this function
    }
}
