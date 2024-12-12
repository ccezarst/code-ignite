package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.InputSource;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class
GamepadManager extends InputSource {
    private Map<Gamepad.Analog, Double> vals = new HashMap<>();
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
            for(int b = 0; b < this.analogMappers.size(); b++){
                this.vals.clear();
                this.vals.put(Gamepad.Analog.LEFT_STICK_X, (double) gp.getLeft_stick_x());
                this.vals.put(Gamepad.Analog.LEFT_STICK_Y, (double) gp.getLeft_stick_y());
                this.vals.put(Gamepad.Analog.RIGHT_STICK_X, (double) gp.getRight_stick_x());
                this.vals.put(Gamepad.Analog.RIGHT_STICK_Y, (double) gp.getRight_stick_y());
                this.vals.put(Gamepad.Analog.LEFT_TRIGGER, (double) gp.getLeft_trigger());
                this.vals.put(Gamepad.Analog.RIGHT_TRIGGER, (double) gp.getRight_trigger());
                 this.analogMappers.get(i).primitiveAnalog(gp, this.vals);
            }

            ArrayList<Gamepad.Button> btnHold = this.getHoldButtons(gp);
            ArrayList<Gamepad.Button> btnToggle = this.getToggleButtons(gp);
            // loop through holding/toggled keys and then loop through each button mapper
            for(int b = 0; b < btnHold.size(); b++){
                for(int c = 0; c < this.btnMappers.size(); c++){
                    this.btnMappers.get(c).primitiveButtonHold(gp, btnHold.get(b));
                }
            }
            for(int b = 0; b < btnToggle.size(); b++){
                for(int c = 0; c < this.btnMappers.size(); c++){
                    this.btnMappers.get(c).primitiveButtonToggle(gp, btnToggle.get(b));
                }
            }
        }

    }
}
