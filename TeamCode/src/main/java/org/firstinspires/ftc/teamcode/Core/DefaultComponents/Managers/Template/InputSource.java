package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template.InputMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class InputSource extends CoreComponent {

    private ArrayList<InputMapper> mappers = new ArrayList< InputMapper >();
    private ButtonTypes btnTypes;
    private AnalogTypes anTypes;

    private InputSetStates states;
    public final int id;

    public InputSource(String name, Boolean active, DefaultCore core, ButtonTypes btnTypes, AnalogTypes anTypes, int id){
        super(name, active,  core, ComponentType.INPUT_SOURCE);
        this.btnTypes = btnTypes;
        this.anTypes = anTypes;
        this.id = id;
    }

    @Override
    public void update(DefaultCore core){
        ArrayList<CoreComponent> list = core.getComponentsOfType(ComponentType.INPUT_MAPPER);
        this.mappers.clear();
        for(int i = 0; i < list.size(); i++){
            ((InputMapper)list.get(i)).updateRefrenceToStates(this.id, this.states);
            this.mappers.add((InputMapper) list.get(i));
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
