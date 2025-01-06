package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.Template.InputMapper;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.Template.InputMapperDefinitions;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;

import java.util.ArrayList;

public abstract class InputSource extends CoreComponent {

    private ArrayList<InputMapperDefinitions> mappers = new ArrayList< InputMapperDefinitions >();

    private InputSetStates states;
    public final int id;

    public InputSource(String name, Boolean active, TeamCore core, int id){
        super(name, active,  core, ComponentType.INPUT_SOURCE);
        this.id = id;
        this.states = new InputSetStates();
    }

    @Override
    public void update(TeamCore core){
        ArrayList<CoreComponent> list = core.getComponentsOfType(ComponentType.INPUT_MAPPER);
        this.mappers.clear();
        for(int i = 0; i < list.size(); i++){
            ((InputMapperDefinitions)list.get(i)).updateRefrenceToStates(this.id, this.states);
            this.mappers.add((InputMapperDefinitions) list.get(i));
        }
    }

    protected void updateButtonState(ButtonTypes btn, Boolean newS){
        states.updateButtonState(newS, btn);
    }

    protected void updateAnalogValue(AnalogTypes btn, Double newS){
        states.updateAnalogValue(newS, btn);
    }

    protected void updateInputMappers(){
        for(int i = 0; i < this.mappers.size(); i++){
            this.mappers.get(i).statesUpdated();
        }
    }

    @Override
    public abstract ArrayList<String> getStatus();

}
