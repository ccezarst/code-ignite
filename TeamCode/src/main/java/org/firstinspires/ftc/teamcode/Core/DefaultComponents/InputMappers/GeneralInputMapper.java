package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template.InputMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.InputSetStates;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.Map;
import java.util.Objects;

public class GeneralInputMapper extends InputMapper {
    public GeneralInputMapper(String name, Boolean active, DefaultCore core) {
        super("GeneralInputMapper", active, core);
    }

    @Override
    public void step(DefaultCore core) {

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
    public void statesUpdated() {

    }
}
