package org.firstinspires.ftc.teamcode.Core.DefaultComponents;

import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;

public class GamepadManager extends CoreComponent {
    private Gamepad[] gps;
    private ArrayList<ButtonMapper> btnMappers = new ArrayList<ButtonMapper>();
    public GamepadManager(String name, Boolean active, Gamepad... gp){
        super(name, active, ComponentType.GAMEPAD_MANAGER);
        this.gps = gp;
    }

    @Override
    public void update(DefaultCore core){
        ArrayList<CoreComponent> list = core.getComponentsOfType(ComponentType.BUTTON_MAPPER);
        this.btnMappers.clear();
        for(int i = 0; i < list.size(); i++){
            this.btnMappers.add((ButtonMapper)list.get(i));
        }
    }

    @Override
    public ArrayList<String> getStatus(){
        ArrayList<String> toReturn = new ArrayList<String>();
        toReturn.add("Gamepads: " + this.gps.toString());
        return toReturn;
    }

    public ArrayList<Gamepad.Button> getHoldButtons(Gamepad gp){
        ArrayList<Gamepad.Button> toReturn = new ArrayList<Gamepad.Button>();
        for(int i = 0; i < Gamepad.Button.values().length; i++){
            if(gp.checkHold(Gamepad.Button.values()[i])){
                toReturn.add(Gamepad.Button.values()[i]);
            }
        }
        return toReturn;
    }

    public ArrayList<Gamepad.Button> getToggleButtons(Gamepad gp){
        ArrayList<Gamepad.Button> toReturn = new ArrayList<Gamepad.Button>();
        for(int i = 0; i < Gamepad.Button.values().length; i++){
            if(gp.checkToggle(Gamepad.Button.values()[i])){
                toReturn.add(Gamepad.Button.values()[i]);
            }
        }
        return toReturn;
    }

    @Override
    public void step(DefaultCore core){
        // we will look for all button mapper components and send them pressed keys
        // and the button mappers will have no step functions, only waiting to recieve keys
        // from this function
        for(int i = 0; i < this.gps.length; i++){
            Gamepad gp = this.gps[i];
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
