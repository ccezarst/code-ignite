package org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.AnalogTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.InputSetStates;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.InputSource;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class InputMapper extends CoreComponent {
    protected Map<Integer, InputSource> inSources = new HashMap<>();
    protected Map<Integer, InputSetStates> states = new HashMap<>();
    public InputMapper(String name, Boolean active, DefaultCore core) {
        super(name, active, core, ComponentType.INPUT_MAPPER);
    }

    @Override
    public abstract void step(DefaultCore core);

    public final void updateRefrenceToStates(int inputSourceID, InputSetStates st){
        this.states.remove(inputSourceID);
        this.states.put(inputSourceID, st);
    }

    public abstract void statesUpdated();

    @Override
    public final void update(DefaultCore core) {
        this.inSources.clear();
        ArrayList<CoreComponent> list = this.core.getComponentsOfType(ComponentType.INPUT_SOURCE);
        for(int i = 0; i < list.size(); i ++){
            inSources.put(((InputSource)list.get(i)).id, ((InputSource)list.get(i)));
        }
    }
}
