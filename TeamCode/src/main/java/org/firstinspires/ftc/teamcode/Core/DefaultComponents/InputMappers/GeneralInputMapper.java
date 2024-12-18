package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template.InputMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.AnalogTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.InputSetStates;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.InputSource;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class GeneralInputMapper extends InputMapper {
    public GeneralInputMapper(String name, Boolean active, DefaultCore core) {
        super(name, active, core);
    }

    @Override
    public void step(DefaultCore core) {

    }

    public Double getAnalogValue(int InputSourceID, AnalogTypes an){
        if(this.states.containsKey(InputSourceID)){
            return Objects.requireNonNull(this.states.get(InputSourceID)).getAnalogValue(an);

        }
        return 0.0;
    }

    public Boolean checkButtonDown(int InputSourceID, ButtonTypes btn){
        if(this.states.containsKey(InputSourceID)){
            return Objects.requireNonNull(this.states.get(InputSourceID)).getButtonState(btn);
        }
        return false;
    }
    public Boolean checkButtonDownOnAll(ButtonTypes btn){
        for(Map.Entry<Integer, InputSetStates> current: this.states.entrySet()){
           if(current.getValue().getButtonState(btn)){
               return true;
           }
        }
        return false;
    }

    @Override
    public ArrayList<String> getStatus(){
        ArrayList<String> caca = new ArrayList<>();
        for(Map.Entry<Integer, InputSource> in: this.inSources.entrySet()){
            caca.add("IN_SOURCE #" + in.getValue().id + ": " + in.getValue().name);
        }
        return caca;
    }

    @Override
    public void statesUpdated() {

    }
}
