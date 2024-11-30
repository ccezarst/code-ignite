package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.ButtonMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.ThumbstickMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;

public class
GamepadManager extends InputSource {
    public GamepadManager(String name, Boolean active, DefaultCore core,  Gamepad... gp){
        super(name, active, core, gp);
    }

    @Override
    public void step(DefaultCore core){
        // first send thumbstick values


        // we will look for all button mapper components and send them pressed keys
        // and the button mappers will have no step functions, only waiting to recieve keys
        // from this function
        for(int i = 0; i < this.gps.length; i++){
            Gamepad gp = this.gps[i];
            for(int b = 0; b < this.thmbstMappers.size(); b++){
                this.thmbstMappers.get(i).thumbstick(gp, gp.getLeft_stick_x(), gp.getLeft_stick_y(), gp.getRight_stick_x(), gp.getRight_stick_y());
            }

            ArrayList<Gamepad.Button> btnHold = this.getHoldButtons(gp);
            ArrayList<Gamepad.Button> btnToggle = this.getToggleButtons(gp);
            // loop through holding/toggled keys and then loop through each button mapper
            for(int b = 0; b < btnHold.size(); b++){
                for(int c = 0; c < this.btnMappers.size(); c++){
                    this.btnMappers.get(c).buttonHold(gp, btnHold.get(b));
                }
            }
            for(int b = 0; b < btnHold.size(); b++){
                for(int c = 0; c < this.btnMappers.size(); c++){
                    this.btnMappers.get(c).buttonToggle(gp, btnToggle.get(b));
                }
            }
        }

    }
}
