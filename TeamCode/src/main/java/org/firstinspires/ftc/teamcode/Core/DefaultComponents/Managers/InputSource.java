package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.ButtonMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.ThumbstickMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;

public abstract class InputSource extends CoreComponent {
    Gamepad[] gps;
    ArrayList<ButtonMapper> btnMappers = new ArrayList<ButtonMapper>();
    ArrayList<ThumbstickMapper> thmbstMappers = new ArrayList<ThumbstickMapper>();

    public InputSource(String name, Boolean active, DefaultCore core, Gamepad... gp){
        super(name, active, ComponentType.INPUT_SOURCE, core);
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

    public boolean checkHold(int gamepadNumber, Gamepad.Button btn){
        for(int i = 0; i < this.gps.length; i ++){
            if(this.gps[i].getNumber() == gamepadNumber){
                return this.gps[i].checkHold(btn);
            }
        }
        return false;
    }

    public boolean checkToggle(int gamepadNumber, Gamepad.Button btn){
        for(int i = 0; i < this.gps.length; i ++){
            if(this.gps[i].getNumber() == gamepadNumber){
                return this.gps[i].checkHold(btn);
            }
        }
        return false;
    }
}
