package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.Template.InputMapper;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.AnalogTypes;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.InputSetStates;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.InputSource;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class GeneralInputMapper extends InputMapper {
    public GeneralInputMapper(String name, Boolean active, TeamCore core) {
        super(name, active, core);
    }

    @Override
    public void step(TeamCore core) {

    }

    @Override
    public int test(TestingEnviromentCore core) {
        // class going to be deleted when inputs are moved to global variables
        return 0;
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

    @Override
    public void customUpdate(TeamCore core) {

    }
}
