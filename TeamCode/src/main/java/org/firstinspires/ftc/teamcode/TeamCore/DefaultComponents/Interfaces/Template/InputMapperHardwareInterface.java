package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.Template.InputMapperDefinitions;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.InputSetStates;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.InputSource;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class InputMapperHardwareInterface extends HardwareInterface implements InputMapperDefinitions {
    public Map<Integer, InputSource> inSources = new HashMap<>();
    public Map<Integer, InputSetStates> states = new HashMap<>();

    public InputMapperHardwareInterface(String name, Boolean active, TeamCore core, InterfaceType interfType) {
        super(name, active, core, interfType, ComponentType.INPUT_MAPPER);
    }

    @Override
    public abstract void step(TeamCore core);
    public final void updateRefrenceToStates(int inputSourceID, InputSetStates st){
        this.states.remove(inputSourceID);
        this.states.put(inputSourceID, st);
    }

    public abstract void customUpdate(TeamCore core);

    public abstract void statesUpdated();

    @Override
    public final void update(TeamCore core) {
        this.inSources.clear();
        ArrayList<CoreComponent> list = this.core.getComponentsOfType(ComponentType.INPUT_SOURCE);
        for(int i = 0; i < list.size(); i ++){
            inSources.put(((InputSource)list.get(i)).id, ((InputSource)list.get(i)));
        }
        this.customUpdate(core);
    }



}
